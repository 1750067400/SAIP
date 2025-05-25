package com.saip.enums;

public enum MemberLevel {
    NORMAL("普通会员"),
    DIRECTOR("理事"),
    EXECUTIVE_DIRECTOR("常务理事"),
    VICE_PRESIDENT("副会长"),
    PRESIDENT("会长");

    private final String value;

    MemberLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
} 