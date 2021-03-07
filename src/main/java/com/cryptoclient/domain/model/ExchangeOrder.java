package com.cryptoclient.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "Exchange_order")
public class ExchangeOrder extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "exchange_ratio_id")
    private ExchangeRatio exchangeRatio;

    @Column
    private Double quantity;

    @Column
    @Enumerated
    private OperationType operationType;

    @Column
    @Enumerated
    private ExchangeOrderStatus exchangeOrderStatus;

    @Column
    private String creatorUser;

    @Column
    private String clientUser;

    @Override
    public String toString() {
        return "id=" + getId() +
                ", exchangePortal=" + exchangeRatio.getProvider();
    }
}
