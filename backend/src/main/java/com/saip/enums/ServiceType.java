package com.saip.enums;

public enum ServiceType {
    CONSULTING("咨询服务"),
    TRAINING("培训服务"),
    NETWORKING("交流活动"),
    OTHER("其他服务");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
} 