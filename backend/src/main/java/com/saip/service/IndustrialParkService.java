package com.saip.service;

import com.saip.entity.IndustrialPark;
import com.saip.repository.IndustrialParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 产业园服务类
 */
@Service
public class IndustrialParkService {
    
    @Autowired
    private IndustrialParkRepository industrialParkRepository;
    
    /**
     * 获取所有产业园
     */
    public List<IndustrialPark> getAllIndustrialParks() {
        return industrialParkRepository.findAll();
    }
    
    /**
     * 根据ID获取产业园
     */
    public Optional<IndustrialPark> getIndustrialParkById(Long id) {
        return industrialParkRepository.findById(id);
    }
    
    /**
     * 创建产业园
     */
    public IndustrialPark createIndustrialPark(IndustrialPark industrialPark) {
        industrialPark.setCreatedTime(LocalDateTime.now());
        industrialPark.setUpdatedTime(LocalDateTime.now());
        return industrialParkRepository.save(industrialPark);
    }
    
    /**
     * 更新产业园
     */
    public IndustrialPark updateIndustrialPark(Long id, IndustrialPark industrialParkDetails) {
        Optional<IndustrialPark> optionalIndustrialPark = industrialParkRepository.findById(id);
        if (optionalIndustrialPark.isPresent()) {
            IndustrialPark industrialPark = optionalIndustrialPark.get();
            industrialPark.setName(industrialParkDetails.getName());
            industrialPark.setAddress(industrialParkDetails.getAddress());
            industrialPark.setContact(industrialParkDetails.getContact());
            industrialPark.setPhone(industrialParkDetails.getPhone());
            industrialPark.setEmail(industrialParkDetails.getEmail());
            industrialPark.setUpdatedTime(LocalDateTime.now());
            return industrialParkRepository.save(industrialPark);
        }
        return null;
    }
    
    /**
     * 删除产业园
     */
    public boolean deleteIndustrialPark(Long id) {
        if (industrialParkRepository.existsById(id)) {
            industrialParkRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 根据名称搜索产业园
     */
    public List<IndustrialPark> searchIndustrialParksByName(String keyword) {
        return industrialParkRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据地址搜索产业园
     */
    public List<IndustrialPark> searchIndustrialParksByAddress(String keyword) {
        return industrialParkRepository.findByAddressContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据联系人搜索产业园
     */
    public List<IndustrialPark> searchIndustrialParksByContact(String keyword) {
        return industrialParkRepository.findByContactContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据邮箱查找产业园
     */
    public IndustrialPark getIndustrialParkByEmail(String email) {
        return industrialParkRepository.findByEmail(email);
    }
    
    /**
     * 根据电话查找产业园
     */
    public IndustrialPark getIndustrialParkByPhone(String phone) {
        return industrialParkRepository.findByPhone(phone);
    }
    
    /**
     * 获取产业园统计
     */
    public long getIndustrialParkCount() {
        return industrialParkRepository.count();
    }
    
    /**
     * 检查邮箱是否已存在
     */
    public boolean isEmailExists(String email) {
        return industrialParkRepository.findByEmail(email) != null;
    }
    
    /**
     * 检查电话是否已存在
     */
    public boolean isPhoneExists(String phone) {
        return industrialParkRepository.findByPhone(phone) != null;
    }
} 