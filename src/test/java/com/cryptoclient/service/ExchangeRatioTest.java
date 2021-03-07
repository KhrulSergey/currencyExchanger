package com.cryptoclient.service;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.repository.ExchangePortalRepository;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ExchangeRatioTest {

    ExchangeRatio exchangeRatio = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.XMR)
            .currencyToPay(Currency.USD)
            .ratio(10.0)
            .time(LocalDateTime.of(2021, 12, 2, 6, 23))
            .build();

    ExchangeRatio exchangeRatio1 = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.BTC)
            .currencyToPay(Currency.USD)
            .ratio(10.0)
            .time(LocalDateTime.of(2021, 12, 2, 6, 23))
            .build();

    ExchangeRatio exchangeRatio2 = ExchangeRatio.builder()
            .provider("nomics")
            .currencyToBuy(Currency.BTC)
            .currencyToPay(Currency.USD)
            .ratio(10.0)
            .time(LocalDateTime.of(2021, 12, 2, 6, 23))
            .build();

    @Autowired
    private ExchangeRatioService exchangeRatioService;
    @Autowired
    private ExchangePortalRepository exchangePortalRepository;

    @Test
    public void shouldFindExchangePortalById() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalId = exchangeRatio.getId();
        //When
        ExchangeRatio foundExchangeRatio = exchangeRatioService.findExchangeRatioById(exchangePortalId);
        //Then
        assertEquals(foundExchangeRatio.getProvider(), "nomics");
        assertEquals(foundExchangeRatio.getCurrencyToBuy(), Currency.XMR);
        assertEquals(foundExchangeRatio.getCurrencyToPay(), Currency.USD);
        assertEquals(foundExchangeRatio.getRatio(), 10.0, 0.001);
        assertEquals(foundExchangeRatio.getTime(), LocalDateTime.of(2021, 12, 2, 6, 23));
        //Clean-Up
        exchangePortalRepository.deleteById(exchangePortalId);
    }

    @Test
    public void shouldGetExchangePortals() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalId = exchangeRatio.getId();
        List<ExchangeRatio> exchangeRatioList = new ArrayList<>();
        //When
        exchangeRatioList = exchangeRatioService.getExchangeRatioList();
        //Then
        assertTrue(!exchangeRatioList.isEmpty());
        //Clean-Up
        exchangePortalRepository.deleteById(exchangePortalId);
    }

    @Test
    public void shouldSaveExchangePortals() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalId = exchangeRatio.getId();
        //When
        ExchangeRatio foundExchangeRatio = exchangeRatioService.findExchangeRatioById(exchangePortalId);
        //Then
        assertEquals(foundExchangeRatio.getProvider(), "nomics");
        assertEquals(foundExchangeRatio.getCurrencyToBuy(), Currency.XMR);
        assertEquals(foundExchangeRatio.getCurrencyToPay(), Currency.USD);
        assertEquals(foundExchangeRatio.getRatio(), 10.0, 0.001);
        assertEquals(foundExchangeRatio.getTime(), LocalDateTime.of(2021, 12, 2, 6, 23));
        //Clean-Up
        exchangePortalRepository.deleteById(exchangePortalId);
    }

    @Test
    public void shouldGetExchangePortalsWithCurrency() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalId = exchangeRatio.getId();

        exchangeRatioService.save(exchangeRatio1);
        Long exchangePortalId1 = exchangeRatio1.getId();

        exchangeRatioService.save(exchangeRatio2);
        Long exchangePortalId2 = exchangeRatio2.getId();
        List<ExchangeRatio> exchangeRatioList = new ArrayList<>();
        //When
        exchangeRatioList = exchangeRatioService.getExchangeRatioWithCurrency(Currency.BTC);
        //Then
        List<ExchangeRatio> exchangeRatioFound = exchangeRatioList.stream()
                .filter(n -> n.getId() == exchangePortalId1 || n.getId() == exchangePortalId2)
                .collect(Collectors.toList());

        assertEquals(2, exchangeRatioFound.size());
        //Clean-Up
        exchangePortalRepository.deleteById(exchangePortalId);
        exchangePortalRepository.deleteById(exchangePortalId1);
        exchangePortalRepository.deleteById(exchangePortalId2);
    }

    @Test
    public void shouldDeleteExchangePortal() {
        //Given
        exchangeRatioService.save(exchangeRatio);
        Long exchangePortalId = exchangeRatio.getId();
        //When
        exchangeRatioService.delete(exchangePortalId);
        //Then
        Boolean checkIfExists = exchangePortalRepository.existsById(exchangePortalId);
        assertFalse(checkIfExists);
    }

}
