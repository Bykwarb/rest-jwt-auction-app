package com.example.demo.entity.lot;

public enum LotStatus {
    STATUS_ANNOUNCED("ANNOUNCED"),
    STATUS_STARTED("STARTED"),
    STATUS_ENDED("ENDED"),
    STATUS_STOPPED("STOPPED"),
    STATUS_CANCELED("CANCELED");

    private final String status;

    LotStatus(String started) {
        status = started;
    }

    public String getStatus() {
        return status;
    }


}
