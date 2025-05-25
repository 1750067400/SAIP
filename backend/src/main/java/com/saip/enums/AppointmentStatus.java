package com.saip.enums;

public enum AppointmentStatus {
    PENDING("待确认"),
    CONFIRMED("已确认"),
    CANCELLED("已取消"),
    COMPLETED("已完成");

    private final String value;

    AppointmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}