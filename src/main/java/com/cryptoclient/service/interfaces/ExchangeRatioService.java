package com.cryptoclient.service.interfaces;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;

import java.util.List;

public interface ExchangeRatioService {
    ExchangeRatio findExchangeRatioById(final Long exchangeRatioId);

    List<ExchangeRatio> getExchangeRatioList();

    List<ExchangeRatio> getExchangeRatioWithCurrency(final Currency currency);

    ExchangeRatio save(final ExchangeRatio exchangeRatio);

    void delete(final Long exchangeRatioId);
}
