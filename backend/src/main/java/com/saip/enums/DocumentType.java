package com.saip.enums;

/**
 * 文档类型枚举
 */
public enum DocumentType {
    POLICY("政策文件"),
    REGULATION("规章制度"),
    REPORT("报告文档"),
    CONTRACT("合同文件"),
    MANUAL("操作手册"),
    PRESENTATION("演示文稿"),
    OTHER("其他");
    
    private final String description;
    
    DocumentType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 