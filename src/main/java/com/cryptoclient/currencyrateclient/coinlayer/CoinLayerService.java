package com.cryptoclient.currencyrateclient.coinlayer;

import com.cryptoclient.currencyrateclient.ApiService;
import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CoinLayerService implements ApiService {

    @Autowired
    private final CoinLayerClient coinLayerClient;
    @Autowired
    private final CoinLayerConfig coinLayerConfig;

    @Override
    public ExchangeRatio createExchangePortal(Currency currency) {
        CoinLayerResponse coinLayerResponse = coinLayerClient.getCryptoReadings(currency);
        LocalDateTime now = LocalDateTime.now();

        ExchangeRatio exchangeRatio = ExchangeRatio.builder()
                .provider(coinLayerConfig.getNAME())
                .currencyToBuy(currency)
                .currencyToPay(Currency.USD)
                .ratio(coinLayerResponse.getRates().get(currency.toString()))
                .time(now)
                .build();

        return exchangeRatio;
    }

}
