package com.saip.enums;

/**
 * 项目状态枚举
 */
public enum ProjectStatus {
    PLANNING("规划中"),
    IN_PROGRESS("进行中"),
    ON_HOLD("暂停"),
    COMPLETED("已完成"),
    CANCELLED("已取消");
    
    private final String description;
    
    ProjectStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 