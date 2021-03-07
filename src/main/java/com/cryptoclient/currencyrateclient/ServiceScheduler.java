package com.cryptoclient.currencyrateclient;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServiceScheduler {

    public static final String COIN_LAYER = "coinlayer";
    public static final String NOMICS = "nomics";
    public static final Currency XMR = Currency.XMR;
    public static final Currency BTC = Currency.BTC;
    @Autowired
    ExchangeRatioService exchangeRatioService;
    @Autowired
    private ServiceFactory serviceFactory;

    @Scheduled(cron = "2 0 * * * *", zone = "Europe/Paris")
    public void downloadCurrencyRatioCoinLayer() {
        ApiService apiService = serviceFactory.createService(COIN_LAYER);
        exchangeRatioService.save(apiService.createExchangePortal(XMR));
    }

    @Scheduled(cron = "2 0 * * * *", zone = "Europe/Paris")
    public void downloadCurrencyRatioNomics() {
        ApiService apiService = serviceFactory.createService(NOMICS);
        exchangeRatioService.save(apiService.createExchangePortal(BTC));
    }
}
