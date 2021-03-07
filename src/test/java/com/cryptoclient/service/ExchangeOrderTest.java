package com.cryptoclient.service;

import com.cryptoclient.domain.model.*;
import com.cryptoclient.repository.ExchangePortalRepository;
import com.cryptoclient.repository.ExchangeOrderRepository;
import com.cryptoclient.repository.WalletItemRepository;
import com.cryptoclient.repository.WalletRepository;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import com.cryptoclient.service.interfaces.ExchangeOrderService;
import com.cryptoclient.service.interfaces.WalletItemService;
import com.cryptoclient.service.interfaces.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ExchangeOrderTest {

    ExchangeRatio exchangeRatio = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.XMR)
            .currencyToPay(Currency.USD)
            .ratio(10.0)
            .time(LocalDateTime.of(2021, 12, 2, 6, 23))
            .build();

    ExchangeOrder exchangeOrder = ExchangeOrder.builder()
            .exchangeRatio(exchangeRatio)
            .quantity(200.0)
            .build();

    @Autowired
    private ExchangeOrderService exchangeOrderService;
    @Autowired
    private ExchangeRatioService exchangeRatioService;
    @Autowired
    private ExchangeOrderRepository exchangeOrderRepository;
    @Autowired
    private ExchangePortalRepository exchangePortalRepository;
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletItemService walletItemService;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletItemRepository walletItemRepository;

    @Test
    public void shouldCreateexchangeOrder() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalLong = exchangeRatio.getId();
        exchangeOrderService.createExchangeOrder(exchangeOrder);
        Long exchangeOrderLong = exchangeOrder.getId();
        //When
        //Then
        assertEquals(exchangeOrder.getExchangeRatio().getRatio(), 10.0, 0.0001);
        assertEquals(exchangeOrder.getQuantity(), 200.0, 0.0001);
        //Clean-up
        exchangeOrderRepository.deleteById(exchangeOrderLong);
        exchangePortalRepository.deleteById(exchangePortalLong);
    }

    @Test
    public void shouldFindexchangeOrder() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalLong = exchangeRatio.getId();
        exchangeOrderService.createExchangeOrder(exchangeOrder);
        Long exchangeOrderLong = exchangeOrder.getId();
        //When
        ExchangeOrder exchangeOrderById = exchangeOrderService.findExchangeOrderById(exchangeOrderLong);
        //Then
        assertEquals(exchangeOrderById.getId(), exchangeOrderLong);
        //Clean-up
        exchangeOrderRepository.deleteById(exchangeOrderLong);
        exchangePortalRepository.deleteById(exchangePortalLong);
    }

    @Test
    public void shouldGetItemsToBuy() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalLong = exchangeRatio.getId();
        exchangeOrderService.createExchangeOrder(exchangeOrder);
        Long exchangeOrderLong = exchangeOrder.getId();
        List<ExchangeOrder> exchangeOrderList = new ArrayList<>();
        //When
        exchangeOrderList = exchangeOrderService.getExchangeOrders();
        //Then
        assertTrue(!exchangeOrderList.isEmpty());
        //Clean-up
        exchangeOrderRepository.deleteById(exchangeOrderLong);
        exchangePortalRepository.deleteById(exchangePortalLong);
    }

    @Test
    public void shouldFinalizeexchangeOrder() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalLong = exchangeRatio.getId();

        exchangeOrderService.createExchangeOrder(exchangeOrder);
        Long exchangeOrderLong = exchangeOrder.getId();

        Wallet wallet = Wallet.builder()
                .name(LocalDateTime.now() + "test")
                .build();
        walletService.save(wallet);
        Long walletLong = wallet.getId();

        WalletItem walletItem = new WalletItem(wallet, Currency.USD, 1000000.0);
        walletItemService.postWalletItem(walletItem);
        Long walletItemLong = walletItem.getId();

        //When
        exchangeOrderService.finalizeExchangeOrder(exchangeOrderLong, walletLong);

        //Then
        WalletItem walletItemXmr = walletItemService.returnCurrencyWalletItem(walletLong, Currency.XMR);

        assertEquals(200.0, walletItemXmr.getQuantity(), 0.001);

        //Clean-up
        exchangePortalRepository.deleteById(exchangePortalLong);
        walletItemRepository.deleteById(walletItemLong);
        walletItemRepository.deleteById(walletItemXmr.getId());
        walletRepository.deleteById(walletLong);
    }

}
