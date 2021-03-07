package com.cryptoclient.service;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.repository.ExchangePortalRepository;
import com.cryptoclient.service.interfaces.AnalyzerService;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AnalyzerTest {

    ExchangeRatio exchangeRatioNewest = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.XMR)
            .currencyToPay(Currency.USD)
            .ratio(300.0)
            .time(LocalDateTime.of(2100, 12, 2, 6, 23))
            .build();

    ExchangeRatio exchangeRatioOldest = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.XMR)
            .currencyToPay(Currency.USD)
            .ratio(300.0)
            .time(LocalDateTime.of(1800, 12, 2, 6, 23))
            .build();

    ExchangeRatio exchangeRatioMax = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.XMR)
            .currencyToPay(Currency.USD)
            .ratio(10000000000.0)
            .time(LocalDateTime.of(1800, 12, 2, 6, 23))
            .build();

    ExchangeRatio exchangeRatioMin = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.XMR)
            .currencyToPay(Currency.USD)
            .ratio(0.0001)
            .time(LocalDateTime.of(2021, 12, 2, 6, 23))
            .build();

    @Autowired
    private AnalyzerService analyzerService;
    @Autowired
    private ExchangeRatioService exchangeRatioService;
    @Autowired
    private ExchangePortalRepository exchangePortalRepository;

    @Test
    public void shouldReturnMinRatio() {
        //Given
        exchangeRatioService.save(exchangeRatioMin);
        Long exchangePortalId = exchangeRatioMin.getId();
        //When
        ExchangeRatio exchangeRatio = analyzerService.findMinRatio(Currency.XMR);
        assertEquals(exchangeRatio.getRatio(), 0.0001, 0.0001);
        //Clean-up
        exchangePortalRepository.deleteById(exchangePortalId);
    }

    @Test
    public void shouldReturnMaxRatio() {
        //Given
        exchangeRatioService.save(exchangeRatioMax);
        Long exchangePortalId = exchangeRatioMax.getId();
        //When
        ExchangeRatio exchangeRatio = analyzerService.findMaxRatio(Currency.XMR);
        assertEquals(exchangeRatio.getRatio(), 10000000000.0, 0.0001);
        //Clean-up
        exchangePortalRepository.deleteById(exchangePortalId);
    }


    @Test
    public void shouldReturnOldestRatio() {
        //Given
        exchangeRatioService.save(exchangeRatioOldest);
        Long exchangePortalId = exchangeRatioOldest.getId();
        //When
        ExchangeRatio exchangeRatio = analyzerService.findOldestRatio(Currency.XMR);
        assertEquals(exchangeRatio.getTime(), LocalDateTime.of(1800, 12, 2, 6, 23));
        //Clean-up
        exchangePortalRepository.deleteById(exchangePortalId);
    }

    @Test
    public void shouldReturnNewestRatio() {
        //Given
        exchangeRatioService.save(exchangeRatioNewest);
        Long exchangePortalId = exchangeRatioNewest.getId();
        //When
        ExchangeRatio exchangeRatio = analyzerService.findNewestRatio(Currency.XMR);
        assertEquals(exchangeRatio.getTime(), LocalDateTime.of(2100, 12, 2, 6, 23));
        //Clean-up
        exchangePortalRepository.deleteById(exchangePortalId);
    }
}
