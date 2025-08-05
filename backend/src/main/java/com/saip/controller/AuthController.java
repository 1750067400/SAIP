package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.User;
import com.saip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        if (username == null || password == null) {
            return Result.error("用户名和密码不能为空");
        }
        
        Result<User> result = userService.login(username, password);
        if (result.isSuccess()) {
            User user = result.getData();
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", "mock-jwt-token-" + user.getId()); // 模拟JWT token
            return Result.success(response);
        } else {
            return Result.error(result.getMessage());
        }
    }
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/profile")
    public Result<User> getProfile(@RequestParam Long userId) {
        return userService.getUserById(userId);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/profile/{userId}")
    public Result<User> updateProfile(@PathVariable Long userId, @RequestBody User userDetails) {
        return userService.updateUser(userId, userDetails);
    }
} 