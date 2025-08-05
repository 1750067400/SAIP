package com.saip.repository;

import com.saip.entity.IndustrialPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产业园Repository接口
 */
@Repository
public interface IndustrialParkRepository extends JpaRepository<IndustrialPark, Long> {
    
    /**
     * 根据名称模糊查找
     */
    List<IndustrialPark> findByNameContainingIgnoreCase(String keyword);
    
    /**
     * 根据地址模糊查找
     */
    List<IndustrialPark> findByAddressContainingIgnoreCase(String keyword);
    
    /**
     * 根据联系人模糊查找
     */
    List<IndustrialPark> findByContactContainingIgnoreCase(String keyword);
    
    /**
     * 根据邮箱查找
     */
    IndustrialPark findByEmail(String email);
    
    /**
     * 根据电话查找
     */
    IndustrialPark findByPhone(String phone);
} 