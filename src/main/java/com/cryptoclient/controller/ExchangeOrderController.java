package com.cryptoclient.controller;

import com.cryptoclient.domain.dto.ExchangeOrderDto;
import com.cryptoclient.mapper.ExchangeOrderMapper;
import com.cryptoclient.service.interfaces.ExchangeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class ExchangeOrderController {

    private final ExchangeOrderService exchangeOrderService;
    private final ExchangeOrderMapper exchangeOrderMapper;

    @GetMapping
    public List<ExchangeOrderDto> getAllExchangeOrders() {
        return exchangeOrderMapper.toDto(exchangeOrderService.getExchangeOrders());
    }

    @GetMapping("{exchangeOrderId}")
    public ExchangeOrderDto getExchangeOrderById(@PathVariable Long exchangeOrderId) {
        return exchangeOrderMapper.toDto(exchangeOrderService.findExchangeOrderById(exchangeOrderId));
    }

    @PostMapping
    public ExchangeOrderDto createExchangeOrder(@RequestBody ExchangeOrderDto exchangeOrderDto) {
        return exchangeOrderMapper.toDto(exchangeOrderService.createExchangeOrder(
                (exchangeOrderMapper.fromDto(exchangeOrderDto))));
    }

    @GetMapping("/finalize/{walletId}")
    public void finalizeExchangeOrder(@PathVariable Long walletId, @RequestParam Long exchangeOrderId) {
        exchangeOrderService.finalizeExchangeOrder(exchangeOrderId, walletId);
    }

    @DeleteMapping("{exchangeOrderId}")
    public void deleteExchangeOrder(@PathVariable Long exchangeOrderId) {
        exchangeOrderService.deleteExchangeOrder(exchangeOrderId);
    }
}
