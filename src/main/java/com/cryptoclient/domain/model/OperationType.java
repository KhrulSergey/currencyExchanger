package com.cryptoclient.domain.model;

public enum OperationType {

    SELL("Sell", 0),
    BUY("BUY", 1);

    private final String name;
    private final Integer value;

    OperationType(String name, Integer value) {
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
