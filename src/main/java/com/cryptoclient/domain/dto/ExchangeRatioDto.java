package com.cryptoclient.domain.dto;

import com.cryptoclient.domain.model.Currency;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRatioDto {

    private Long id;
    private String provider;
    private Currency currencyToBuy;
    private Currency currencyToPay;
    private Double ratio;
    private LocalDateTime time;
    private List<Long> exchangeOrderListId;
}
