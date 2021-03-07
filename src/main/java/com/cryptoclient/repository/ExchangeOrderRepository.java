package com.cryptoclient.repository;

import com.cryptoclient.domain.model.ExchangeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeOrderRepository extends JpaRepository<ExchangeOrder, Long> {

    @Override
    <S extends ExchangeOrder> S save(S ExchangeOrder);

}
