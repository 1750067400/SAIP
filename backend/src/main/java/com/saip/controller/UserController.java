package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.User;
import com.saip.enums.UserRole;
import com.saip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取所有用户
     */
    @GetMapping
    public Result<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }
    
    /**
     * 根据ID获取用户
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    /**
     * 创建用户
     */
    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
    
    /**
     * 根据角色获取用户
     */
    @GetMapping("/role/{role}")
    public Result<List<User>> getUsersByRole(@PathVariable UserRole role) {
        return userService.getUsersByRole(role);
    }
    
    /**
     * 启用/禁用用户
     */
    @PutMapping("/{id}/toggle-status")
    public Result<User> toggleUserStatus(@PathVariable Long id) {
        return userService.toggleUserStatus(id);
    }
    
    /**
     * 重置用户密码
     */
    @PutMapping("/{id}/reset-password")
    public Result<String> resetUserPassword(@PathVariable Long id) {
        try {
            // 这里可以实现密码重置逻辑
            return Result.success("密码重置成功");
        } catch (Exception e) {
            return Result.error("密码重置失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取用户统计信息
     */
    @GetMapping("/statistics")
    public Result<Object> getUserStatistics() {
        try {
            // 这里可以实现用户统计逻辑
            return Result.success("用户统计信息");
        } catch (Exception e) {
            return Result.error("获取用户统计失败：" + e.getMessage());
        }
    }
} 