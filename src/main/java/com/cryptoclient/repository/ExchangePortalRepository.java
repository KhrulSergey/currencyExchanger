package com.cryptoclient.repository;

import com.cryptoclient.domain.model.ExchangeRatio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangePortalRepository extends JpaRepository<ExchangeRatio, Long> {

    @Override
    <S extends ExchangeRatio> S save(S ExchangePortal);

}
