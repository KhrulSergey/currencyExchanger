package com.cryptoclient.service.implementations;

import com.cryptoclient.domain.model.ExchangeOrder;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.domain.model.WalletItem;
import com.cryptoclient.exceptions.ExchangeRatioPriceTooOldException;
import com.cryptoclient.exceptions.NotEnoughUsdInTheWalletException;
import com.cryptoclient.exceptions.NotFoundException;
import com.cryptoclient.repository.ExchangeOrderRepository;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import com.cryptoclient.service.interfaces.ExchangeOrderService;
import com.cryptoclient.service.interfaces.WalletItemService;
import com.cryptoclient.service.interfaces.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ExchangeOrderServiceImpl implements ExchangeOrderService {

    private final ExchangeOrderRepository exchangeOrderRepository;
    private final WalletService walletService;
    private final WalletItemService walletItemService;
    private final ExchangeRatioService exchangeRatioService;

    @Override
    public ExchangeOrder createExchangeOrder(ExchangeOrder exchangeOrder) {
        return exchangeOrderRepository.save(exchangeOrder);
    }

    @Override
    public ExchangeOrder findExchangeOrderById(Long exchangeOrderId) {
        Optional<ExchangeOrder> exchangeOrder = Optional.empty();
        if (nonNull(exchangeOrderId)){
            exchangeOrder = exchangeOrderRepository.findById(exchangeOrderId);
        }
        return exchangeOrder.orElseThrow(
                () -> new NotFoundException("Exchange Order with id " + exchangeOrderId + " does not exist"));
    }

    @Override
    public List<ExchangeOrder> findAllExchangeOrderByIdList(List<Long> exchangeOrderIdList) throws NotFoundException {
        return exchangeOrderIdList.stream().map(this::findExchangeOrderById).collect(Collectors.toList());
    }

    @Override
    public List<ExchangeOrder> getExchangeOrders() {
        return exchangeOrderRepository.findAll();
    }

    @Override
    public void deleteExchangeOrder(Long id) {
        findExchangeOrderById(id);
        exchangeOrderRepository.deleteById(id);
    }

    @Override
    public ExchangeOrder save(ExchangeOrder exchangeOrder) throws ExchangeRatioPriceTooOldException {
        ExchangeRatio exchangeRatio = exchangeRatioService.findExchangeRatioById(exchangeOrder.getExchangeRatio().getId());
        if (exchangeRatio.getExchangeOrder() == null) {
            return exchangeOrderRepository.save(exchangeOrder);
        } else {
            throw new ExchangeRatioPriceTooOldException("You cannot create Exchange Order with Exchange Ratio already taken");
        }
    }

    @Override
    public void finalizeExchangeOrder(Long exchangeOrderId, Long walletId) throws NotFoundException, ExchangeRatioPriceTooOldException,
            NotEnoughUsdInTheWalletException {
        ExchangeOrder exchangeOrder = findExchangeOrderById(exchangeOrderId);

        if (checkIfTimeIsNotOlderThen20Min(exchangeOrder.getExchangeRatio())
                && checkIfWalletHasSufficientFunds(walletId, exchangeOrder)) {
            //1) Subtract USD amount
            WalletItem walletItem = walletItemService.returnUsdWalletItem(walletId);
            Double oldValue = walletItem.getQuantity();
            Double costValue = exchangeOrder.getQuantity() * exchangeOrder.getExchangeRatio().getRatio();
            walletItem.setQuantity(oldValue - costValue);
            walletItemService.save(walletItem);
            //2) Add bought wallet item
            WalletItem addWalletItem = walletItemService.returnCurrencyWalletItem(walletId, exchangeOrder.getExchangeRatio().getCurrencyToBuy());
            Double oldCurrencyValue = addWalletItem.getQuantity();
            Double newCurrencyValue = oldCurrencyValue + exchangeOrder.getQuantity();
            addWalletItem.setQuantity(newCurrencyValue);
            walletItemService.save(addWalletItem);
            //3) Delete exchangeOrder
            deleteExchangeOrder(exchangeOrderId);
        } else { // Error handling
            if (!checkIfTimeIsNotOlderThen20Min(exchangeOrder.getExchangeRatio()))
                throw new ExchangeRatioPriceTooOldException("Your Exchange Ratio price might be too old (20 min validation)");
            if (!checkIfWalletHasSufficientFunds(walletId, exchangeOrder))
                throw new NotEnoughUsdInTheWalletException("You don't have sufficient funds in USD");
            else throw new NotFoundException("Common error");
        }
    }

    private boolean checkIfTimeIsNotOlderThen20Min(ExchangeRatio exchangeRatio) {
        LocalDateTime tenMinutesOld = LocalDateTime.now().minusMinutes(20);
        return exchangeRatio.getTime().isAfter(tenMinutesOld);
    }

    private boolean checkIfWalletHasSufficientFunds(Long walletId, ExchangeOrder exchangeOrder) {
        Double walletUsdAmount = walletService.checkHowManyUsdWalletHas(walletId);
        Double exchangeOrderWithRatio = exchangeOrder.getQuantity() * exchangeOrder.getExchangeRatio().getRatio();
        return exchangeOrderWithRatio.compareTo(walletUsdAmount) < 0;
    }

}
