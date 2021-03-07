package com.cryptoclient.domain.model;

public enum WalletItemStatus {

    FREE("Free", 0),
    BLOCKED("Blocked", 1),
    IN_TRANSFER("In Transfer", 2);
    private final String name;
    private final Integer value;

    WalletItemStatus(String name, Integer value) {
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
