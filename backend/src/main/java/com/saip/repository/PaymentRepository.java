package com.saip.repository;

import com.saip.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付Repository接口
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    /**
     * 根据状态查找支付记录
     */
    List<Payment> findByStatus(String status);
    
    /**
     * 根据会员ID查找支付记录
     */
    List<Payment> findByMemberId(Long memberId);
    
    /**
     * 根据支付类型查找
     */
    List<Payment> findByPaymentType(String paymentType);
    
    /**
     * 根据状态统计金额
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") String status);
    
    /**
     * 根据状态和日期范围统计金额
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status AND p.paymentDate >= :startDate")
    BigDecimal sumAmountByStatusAndPaymentDateAfter(@Param("status") String status, 
                                                   @Param("startDate") LocalDateTime startDate);
    
    /**
     * 根据日期范围查找支付记录
     */
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findByPaymentDateBetween(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate);
    
    /**
     * 根据状态统计数量
     */
    long countByStatus(String status);
} 