package com.cryptoclient.controller;

import com.cryptoclient.domain.model.Currency;
import com.cryptoclient.domain.dto.ExchangeRatioDto;
import com.cryptoclient.mapper.ExchangeRatioMapper;
import com.cryptoclient.service.interfaces.AnalyzerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/analyzer")
@RequiredArgsConstructor
public class AnalyzerController {

    private final AnalyzerService analyzerService;
    private final ExchangeRatioMapper mapper;

    @GetMapping(value = "/min")
    public ExchangeRatioDto findMin(@RequestParam Currency currency) {
        return mapper.toDto(analyzerService.findMinRatio(currency));
    }

    @GetMapping(value = "/max")
    public ExchangeRatioDto findMax(@RequestParam Currency currency) {
        return mapper.toDto(analyzerService.findMaxRatio(currency));
    }

    @GetMapping(value = "/old")
    public ExchangeRatioDto findOldest(@RequestParam Currency currency) {
        return mapper.toDto(analyzerService.findOldestRatio(currency));
    }

    @GetMapping(value = "/young")
    public ExchangeRatioDto findYoungest(@RequestParam Currency currency) {
        return mapper.toDto(analyzerService.findNewestRatio(currency));
    }


}
