package com.cryptoclient.service.interfaces;

import com.cryptoclient.domain.model.ExchangeOrder;
import com.cryptoclient.domain.model.ExchangeRatio;
import com.cryptoclient.exceptions.NotFoundException;

import java.util.List;

public interface ExchangeOrderService {
    List<ExchangeOrder> getExchangeOrders();

    ExchangeOrder createExchangeOrder(final ExchangeOrder exchangeOrder);

    ExchangeOrder findExchangeOrderById(final Long exchangeOrderId);

    List<ExchangeOrder> findAllExchangeOrderByIdList(List<Long> exchangeOrderIdList);

    void deleteExchangeOrder(Long id);

    ExchangeOrder save(final ExchangeOrder exchangeOrder);

    void finalizeExchangeOrder(final Long exchangeOrderId, final Long walletId);
}
