package com.cryptoclient.currencyrateclient.nomics;

import com.cryptoclient.currencyrateclient.ApiService;
import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class NomicsService implements ApiService {

    @Autowired
    private final NomicsClient nomicsClient;
    @Autowired
    private final NomicsConfig nomicsConfig;

    @Override
    public ExchangeRatio createExchangePortal(Currency currency) {
        List<NomicsResponse> nomicsResponses = nomicsClient.getCryptoReadings(currency);

        LocalDateTime now = LocalDateTime.now();

        ExchangeRatio exchangeRatio = ExchangeRatio.builder()
                .provider(nomicsConfig.getNAME())
                .currencyToBuy(currency)
                .currencyToPay(Currency.USD)
                .ratio(Double.valueOf(nomicsResponses.get(0).getPrice()))
                .time(now)
                .build();

        return exchangeRatio;
    }

}
