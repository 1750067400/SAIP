package com.saip.service;

import com.saip.entity.User;
import com.saip.enums.UserRole;
import com.saip.repository.UserRepository;
import com.saip.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saip.common.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务类
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * 用户登录
     */
    public Result<User> login(String usernameOrEmail, String password) {
        try {
            Optional<User> userOpt = userRepository.findByUsernameOrEmail(usernameOrEmail);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (passwordEncoder.matches(password, user.getPassword())) {
                    if (user.getIsActive()) {
                        // 更新最后登录时间
                        user.setLastLoginTime(LocalDateTime.now());
                        userRepository.save(user);
                        return Result.success(user);
                    } else {
                        return Result.error("账户已被禁用");
                    }
                } else {
                    return Result.error("密码错误");
                }
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }
    
    /**
     * 创建用户
     */
    public Result<User> createUser(User user) {
        try {
            // 检查用户名是否已存在
            if (userRepository.existsByUsername(user.getUsername())) {
                return Result.error("用户名已存在");
            }
            
            // 检查邮箱是否已存在
            if (userRepository.existsByEmail(user.getEmail())) {
                return Result.error("邮箱已存在");
            }
            
            // 加密密码
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setIsActive(true);
            
            User savedUser = userRepository.save(user);
            return Result.success(savedUser);
        } catch (Exception e) {
            return Result.error("创建用户失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新用户信息
     */
    public Result<User> updateUser(Long userId, User userDetails) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // 更新基本信息
                user.setName(userDetails.getName());
                user.setEmail(userDetails.getEmail());
                user.setPhoneNumber(userDetails.getPhoneNumber());
                
                // 如果提供了新密码，则更新密码
                if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                }
                
                User updatedUser = userRepository.save(user);
                return Result.success(updatedUser);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("更新用户失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除用户
     */
    public Result<String> deleteUser(Long userId) {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return Result.success("用户删除成功");
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("删除用户失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取所有用户
     */
    public Result<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return Result.success(users);
        } catch (Exception e) {
            return Result.error("获取用户列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据角色获取用户
     */
    public Result<List<User>> getUsersByRole(UserRole role) {
        try {
            List<User> users = userRepository.findByRole(role);
            return Result.success(users);
        } catch (Exception e) {
            return Result.error("获取用户列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取用户
     */
    public Result<User> getUserById(Long userId) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                return Result.success(userOpt.get());
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("获取用户信息失败：" + e.getMessage());
        }
    }
    
    /**
     * 启用/禁用用户
     */
    public Result<User> toggleUserStatus(Long userId) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setIsActive(!user.getIsActive());
                User updatedUser = userRepository.save(user);
                return Result.success(updatedUser);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("更新用户状态失败：" + e.getMessage());
        }
    }
} 