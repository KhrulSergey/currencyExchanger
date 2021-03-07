package com.cryptoclient.currencyrateclient;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;

public interface ApiService {
    ExchangeRatio createExchangePortal(Currency currency);
}
