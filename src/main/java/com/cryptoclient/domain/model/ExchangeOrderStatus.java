package com.cryptoclient.domain.model;

public enum ExchangeOrderStatus {

    OPEN("Open", 0),
    CLOSED("Closed", 1),
    IN_TRANSFER("In Transfer", 2),
    CANCELED("Canceled", 3);

    private final String name;
    private final Integer value;

    ExchangeOrderStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
