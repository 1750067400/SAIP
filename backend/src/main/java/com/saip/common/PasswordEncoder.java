package com.saip.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 简单的密码编码器
 */
public class PasswordEncoder {
    
    private static final SecureRandom RANDOM = new SecureRandom();
    
    /**
     * 编码密码
     */
    public String encode(String rawPassword) {
        try {
            String salt = generateSalt();
            String hashedPassword = hashPassword(rawPassword, salt);
            return salt + ":" + hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码编码失败", e);
        }
    }
    
    /**
     * 验证密码
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        try {
            String[] parts = encodedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }
            String salt = parts[0];
            String storedHash = parts[1];
            String computedHash = hashPassword(rawPassword, salt);
            return storedHash.equals(computedHash);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
    
    private String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
} 