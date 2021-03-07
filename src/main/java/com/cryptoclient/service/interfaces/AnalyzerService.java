package com.cryptoclient.service.interfaces;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;

public interface AnalyzerService {
    ExchangeRatio findMinRatio(Currency currency);

    ExchangeRatio findMaxRatio(Currency currency);

    ExchangeRatio findOldestRatio(Currency currency);

    ExchangeRatio findNewestRatio(Currency currency);
}
