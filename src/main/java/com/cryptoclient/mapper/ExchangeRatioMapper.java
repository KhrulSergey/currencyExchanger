package com.cryptoclient.mapper;

import com.cryptoclient.domain.dto.ExchangeRatioDto;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.service.interfaces.ExchangeOrderService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ExchangeRatioMapper {

    @Autowired
    protected ExchangeOrderService exchangeOrderService;

    @Mapping(target = "exchangeOrder", expression = "java(exchangeOrderService.findAllExchangeOrderByIdList(dto.getExchangeOrderListId()))")
    public abstract ExchangeRatio fromDto(ExchangeRatioDto dto);

    public abstract ExchangeRatioDto toDto(ExchangeRatio entity);

    public abstract List<ExchangeRatio> fromDto(List<ExchangeRatioDto> dto);

    public abstract List<ExchangeRatioDto> toDto(List<ExchangeRatio> entity);
}
