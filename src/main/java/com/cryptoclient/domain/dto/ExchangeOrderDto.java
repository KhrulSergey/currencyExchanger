package com.cryptoclient.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeOrderDto {

    private Long id;
    private Long exchangeRatioId;
    private Double quantity;

    public ExchangeOrderDto(Long exchangeRatioId, Double quantity) {
        this.exchangeRatioId = exchangeRatioId;
        this.quantity = quantity;
    }
}
