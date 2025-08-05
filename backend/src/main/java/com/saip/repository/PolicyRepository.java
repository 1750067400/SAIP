package com.saip.repository;

import com.saip.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 政策Repository接口
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    
    /**
     * 根据政策类型查找
     */
    List<Policy> findByPolicyType(String policyType);
    
    /**
     * 查找已发布的政策
     */
    List<Policy> findByIsPublishedTrue();
    
    /**
     * 查找紧急政策
     */
    List<Policy> findByIsUrgentTrue();
    
    /**
     * 根据发布机构查找
     */
    List<Policy> findByIssuingAuthority(String issuingAuthority);
    
    /**
     * 根据标题搜索政策
     */
    List<Policy> findByTitleContainingIgnoreCase(String keyword);
    
    /**
     * 根据内容搜索政策
     */
    List<Policy> findByContentContainingIgnoreCase(String keyword);
    
    /**
     * 查找最近发布的政策
     */
    @Query("SELECT p FROM Policy p WHERE p.isPublished = true AND p.publishedTime >= :since ORDER BY p.publishedTime DESC")
    List<Policy> findRecentPublishedPolicies(@Param("since") LocalDateTime since);
    
    /**
     * 查找有效的政策（未过期）
     */
    @Query("SELECT p FROM Policy p WHERE p.isPublished = true AND (p.expiryDate IS NULL OR p.expiryDate > :now)")
    List<Policy> findValidPolicies(@Param("now") LocalDateTime now);
    
    /**
     * 统计已发布的政策数量
     */
    long countByIsPublishedTrue();
    
    /**
     * 统计紧急政策数量
     */
    long countByIsUrgentTrue();
    
    /**
     * 统计政策类型数量
     */
    long countByPolicyType(String policyType);
} 