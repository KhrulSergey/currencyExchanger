package com.cryptoclient.service.implementations;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.exceptions.NoExchangeRatioFoundException;
import com.cryptoclient.service.interfaces.AnalyzerService;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnalyzerServiceImpl implements AnalyzerService {

    private final ExchangeRatioService exchangeRatioService;

    @Override
    public ExchangeRatio findMinRatio(Currency currency) throws NoExchangeRatioFoundException {
        List<ExchangeRatio> exchangeRatios = exchangeRatioService.getExchangeRatioWithCurrency(currency);

        ExchangeRatio minExchangeRatio = exchangeRatios.stream()
                .min(Comparator.comparing(ExchangeRatio::getRatio))
                .orElseThrow(()
                        -> new NoExchangeRatioFoundException("Exchange Ratio not found"));
        return minExchangeRatio;
    }

    @Override
    public ExchangeRatio findMaxRatio(Currency currency) throws NoExchangeRatioFoundException {
        List<ExchangeRatio> exchangeRatios = exchangeRatioService.getExchangeRatioWithCurrency(currency);

        ExchangeRatio minExchangeRatio = exchangeRatios.stream()
                .max(Comparator.comparing(ExchangeRatio::getRatio))
                .orElseThrow(()
                        -> new NoExchangeRatioFoundException("Exchange Ratio not found"));
        return minExchangeRatio;
    }

    @Override
    public ExchangeRatio findOldestRatio(Currency currency) throws NoExchangeRatioFoundException {
        List<ExchangeRatio> exchangeRatios = exchangeRatioService.getExchangeRatioWithCurrency(currency);

        ExchangeRatio minExchangeRatio = exchangeRatios.stream()
                .min(Comparator.comparing(ExchangeRatio::getTime))
                .orElseThrow(()
                        -> new NoExchangeRatioFoundException("Exchange Ratio not found"));
        return minExchangeRatio;
    }

    @Override
    public ExchangeRatio findNewestRatio(Currency currency) throws NoExchangeRatioFoundException {
        List<ExchangeRatio> exchangeRatios = exchangeRatioService.getExchangeRatioWithCurrency(currency);

        ExchangeRatio minExchangeRatio = exchangeRatios.stream()
                .max(Comparator.comparing(ExchangeRatio::getTime))
                .orElseThrow(()
                        -> new NoExchangeRatioFoundException("Exchange Ratio not found"));
        return minExchangeRatio;
    }
}
