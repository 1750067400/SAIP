package com.saip.repository;

import com.saip.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会员Repository接口
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    /**
     * 根据状态查找会员
     */
    List<Member> findByStatus(String status);
    
    /**
     * 根据会员类型查找
     */
    List<Member> findByMemberType(String memberType);
    
    /**
     * 根据行业查找
     */
    List<Member> findByIndustry(String industry);
    
    /**
     * 根据状态统计数量
     */
    long countByStatus(String status);
    
    /**
     * 根据创建时间统计数量
     */
    long countByCreatedTimeAfter(LocalDateTime createdTime);
    
    /**
     * 根据名称模糊查找
     */
    List<Member> findByNameContainingIgnoreCase(String keyword);
    
    /**
     * 根据公司名称模糊查找
     */
    List<Member> findByCompanyNameContainingIgnoreCase(String keyword);
    
    /**
     * 根据邮箱查找
     */
    Member findByEmail(String email);
    
    /**
     * 根据电话查找
     */
    Member findByPhone(String phone);
} 