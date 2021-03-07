package com.cryptoclient.controller;

import com.cryptoclient.currencyrateclient.ApiService;
import com.cryptoclient.currencyrateclient.ServiceFactory;
import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.domain.dto.ExchangeRatioDto;
import com.cryptoclient.mapper.ExchangeRatioMapper;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRatioController {

    private final ServiceFactory serviceFactory;
    private final ExchangeRatioMapper mapper;
    private final ExchangeRatioService exchangeRatioService;

    @PostMapping
    public ExchangeRatioDto createNewResponse(@RequestParam Currency currency, @RequestParam String serviceName) {
        ApiService apiService = serviceFactory.createService(serviceName);
        ExchangeRatio exchangeRatio = apiService.createExchangePortal(currency);
        exchangeRatioService.save(exchangeRatio);
        return mapper.toDto(exchangeRatio);
    }

    @GetMapping
    public List<ExchangeRatioDto> getExchangePortals() {
        return mapper.toDto(exchangeRatioService.getExchangeRatioList());
    }

    @GetMapping("/id/{exchangePortalId}")
    public ExchangeRatioDto getExchangePortalById(@PathVariable Long exchangePortalId) {
        return mapper.toDto(exchangeRatioService.findExchangeRatioById(exchangePortalId));
    }

    @DeleteMapping("/{exchangePortalId}")
    public void deleteExchangePortal(@PathVariable Long exchangePortalId) {
        exchangeRatioService.delete(exchangePortalId);
    }

    @GetMapping("/currency")
    public List<ExchangeRatioDto> getExchangePortalsWithCurrency(@RequestParam Currency currency) {
        return mapper.toDto(exchangeRatioService.getExchangeRatioWithCurrency(currency));
    }
}
