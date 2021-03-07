package com.cryptoclient.mapper;

import com.cryptoclient.domain.model.ExchangeOrder;
import com.cryptoclient.domain.dto.ExchangeOrderDto;
import com.cryptoclient.service.interfaces.ExchangeRatioService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ExchangeOrderMapper {

    @Autowired
    protected ExchangeRatioService exchangeRatioService;

    @Mapping(target = "exchangeRatio", expression = "java(exchangeRatioService.findExchangeRatioById(exchangeOrderDto.getExchangeRatioId()))")
    public abstract ExchangeOrder fromDto(ExchangeOrderDto exchangeOrderDto);

    @Mapping(target = "exchangeRatioId", source = "exchangeOrder.exchangeRatio.id")
    public abstract ExchangeOrderDto toDto(ExchangeOrder exchangeOrder);

    public abstract List<ExchangeOrder> fromDto(List<ExchangeOrderDto> dto);

    public abstract List<ExchangeOrderDto> toDto(List<ExchangeOrder> entity);
}
