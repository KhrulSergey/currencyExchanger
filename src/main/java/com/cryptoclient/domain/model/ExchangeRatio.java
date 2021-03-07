package com.cryptoclient.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@SuperBuilder
@Entity(name = "Exchange_ratio")
public class ExchangeRatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String provider;

    @Column
    private Currency currencyToBuy;

    @Column
    private Currency currencyToPay;

    @Column
    private Double ratio;

    @Column
    private LocalDateTime time;

    @OneToMany(mappedBy = "exchangeRatio")
    private List<ExchangeOrder> exchangeOrder;
}
