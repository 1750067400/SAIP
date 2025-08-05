package com.saip.enums;

/**
 * 活动类型枚举
 */
public enum EventType {
    TRAINING("培训活动"),
    CONFERENCE("会议活动"),
    EXHIBITION("展览活动"),
    NETWORKING("交流活动"),
    WORKSHOP("工作坊"),
    SEMINAR("研讨会"),
    OTHER("其他");
    
    private final String description;
    
    EventType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 