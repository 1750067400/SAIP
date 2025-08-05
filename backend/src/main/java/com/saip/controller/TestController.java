package com.saip.controller;

import com.saip.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {
    
    /**
     * 测试API连接
     */
    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("API服务正常运行");
    }
    
    /**
     * 获取系统信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("systemName", "产业园促进会管理系统");
        info.put("version", "1.0.0");
        info.put("status", "running");
        info.put("timestamp", System.currentTimeMillis());
        return Result.success(info);
    }
} 