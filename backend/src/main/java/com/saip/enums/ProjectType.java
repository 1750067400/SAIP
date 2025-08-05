package com.saip.enums;

/**
 * 项目类型枚举
 */
public enum ProjectType {
    RESEARCH("研究项目"),
    DEVELOPMENT("开发项目"),
    INFRASTRUCTURE("基础设施"),
    TRAINING("培训项目"),
    CONFERENCE("会议项目"),
    EXHIBITION("展览项目"),
    OTHER("其他");
    
    private final String description;
    
    ProjectType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 