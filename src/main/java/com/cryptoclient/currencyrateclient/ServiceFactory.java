package com.cryptoclient.currencyrateclient;

import com.cryptoclient.currencyrateclient.coinlayer.CoinLayerClient;
import com.cryptoclient.currencyrateclient.coinlayer.CoinLayerConfig;
import com.cryptoclient.currencyrateclient.coinlayer.CoinLayerService;
import com.cryptoclient.currencyrateclient.nomics.NomicsClient;
import com.cryptoclient.currencyrateclient.nomics.NomicsConfig;
import com.cryptoclient.currencyrateclient.nomics.NomicsService;
import com.cryptoclient.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

    public static final String COIN_LAYER = "coinlayer";
    public static final String NOMICS = "nomics";
    @Autowired
    private CoinLayerConfig coinLayerConfig;
    @Autowired
    private CoinLayerClient coinLayerClient;
    @Autowired
    private NomicsConfig nomicsConfig;
    @Autowired
    private NomicsClient nomicsClient;

    public ApiService createService(final String serviceName) throws NotFoundException {
        switch (serviceName) {
            case COIN_LAYER:
                return new CoinLayerService(coinLayerClient, coinLayerConfig);
            case NOMICS:
                return new NomicsService(nomicsClient, nomicsConfig);
            default:
                throw new NotFoundException("Please type 'coinlayer' or 'nomics'," +
                        " otherwise Your request cannot be " +
                        "processed");
        }
    }
}
