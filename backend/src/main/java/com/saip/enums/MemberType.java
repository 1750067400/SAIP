package com.saip.enums;

public enum MemberType {
    COMPANY("公司"),
    INDUSTRIAL_PARK("产业园单位");

    private final String value;

    MemberType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
} 