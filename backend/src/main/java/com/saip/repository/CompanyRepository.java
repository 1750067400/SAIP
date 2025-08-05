package com.saip.repository;

import com.saip.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公司Repository接口
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    /**
     * 根据类型查找公司
     */
    List<Company> findByType(String type);
    
    /**
     * 根据名称模糊查找
     */
    List<Company> findByNameContainingIgnoreCase(String keyword);
    
    /**
     * 根据地址模糊查找
     */
    List<Company> findByAddressContainingIgnoreCase(String keyword);
    
    /**
     * 根据联系人模糊查找
     */
    List<Company> findByContactContainingIgnoreCase(String keyword);
    
    /**
     * 根据邮箱查找
     */
    Company findByEmail(String email);
    
    /**
     * 根据电话查找
     */
    Company findByPhone(String phone);
    
    /**
     * 根据类型统计数量
     */
    long countByType(String type);
} 