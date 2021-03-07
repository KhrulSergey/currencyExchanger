package com.cryptoclient.service.implementations;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.exceptions.NotFoundException;
import com.cryptoclient.repository.ExchangePortalRepository;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExchangeRatioServiceImpl implements ExchangeRatioService {

    private final ExchangePortalRepository exchangePortalRepository;

    @Override
    public ExchangeRatio findExchangeRatioById(Long exchangeRatioId) throws NotFoundException {
        return exchangePortalRepository.findById(exchangeRatioId)
                .orElseThrow(() -> new NotFoundException("Exchange Ratio with id: " + exchangeRatioId + " does not exist"));
    }

    @Override
    public List<ExchangeRatio> getExchangeRatioList() {
        return exchangePortalRepository.findAll();
    }

    @Override
    public ExchangeRatio save(ExchangeRatio exchangeRatio) {
        return exchangePortalRepository.save(exchangeRatio);
    }

    @Override
    public List<ExchangeRatio> getExchangeRatioWithCurrency(Currency currency) {
        return getExchangeRatioList().stream()
                .filter(n -> n.getCurrencyToBuy() == currency)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long exchangeRatioId) {
        findExchangeRatioById(exchangeRatioId);
        exchangePortalRepository.deleteById(exchangeRatioId);
    }

}
