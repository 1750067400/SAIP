package com.saip.service;

import com.saip.entity.Payment;
import com.saip.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 支付服务类
 */
@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    /**
     * 获取所有支付记录
     */
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    /**
     * 根据ID获取支付记录
     */
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
    
    /**
     * 创建支付记录
     */
    public Payment createPayment(Payment payment) {
        payment.setCreatedTime(LocalDateTime.now());
        payment.setUpdatedTime(LocalDateTime.now());
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }
        if (payment.getStatus() == null) {
            payment.setStatus("PENDING");
        }
        return paymentRepository.save(payment);
    }
    
    /**
     * 更新支付记录
     */
    public Payment updatePayment(Long id, Payment paymentDetails) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setMemberId(paymentDetails.getMemberId());
            payment.setPaymentType(paymentDetails.getPaymentType());
            payment.setAmount(paymentDetails.getAmount());
            payment.setPaymentDate(paymentDetails.getPaymentDate());
            payment.setDescription(paymentDetails.getDescription());
            payment.setStatus(paymentDetails.getStatus());
            payment.setUpdatedTime(LocalDateTime.now());
            return paymentRepository.save(payment);
        }
        return null;
    }
    
    /**
     * 删除支付记录
     */
    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 根据状态获取支付记录
     */
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }
    
    /**
     * 根据会员ID获取支付记录
     */
    public List<Payment> getPaymentsByMemberId(Long memberId) {
        return paymentRepository.findByMemberId(memberId);
    }
    
    /**
     * 根据支付类型获取支付记录
     */
    public List<Payment> getPaymentsByType(String paymentType) {
        return paymentRepository.findByPaymentType(paymentType);
    }
    
    /**
     * 根据日期范围获取支付记录
     */
    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }
    
    /**
     * 获取支付统计
     */
    public long getPaymentCount() {
        return paymentRepository.count();
    }
    
    /**
     * 获取总支付金额
     */
    public BigDecimal getTotalPaymentAmount() {
        return paymentRepository.sumAmountByStatus("COMPLETED");
    }
    
    /**
     * 根据状态获取总金额
     */
    public BigDecimal getTotalAmountByStatus(String status) {
        return paymentRepository.sumAmountByStatus(status);
    }
    
    /**
     * 获取最近支付金额
     */
    public BigDecimal getRecentPaymentAmount(LocalDateTime since) {
        return paymentRepository.sumAmountByStatusAndPaymentDateAfter("COMPLETED", since);
    }
    
    /**
     * 获取待处理支付数量
     */
    public long getPendingPaymentCount() {
        return paymentRepository.countByStatus("PENDING");
    }
    
    /**
     * 获取已完成支付数量
     */
    public long getCompletedPaymentCount() {
        return paymentRepository.countByStatus("COMPLETED");
    }
    
    /**
     * 获取失败支付数量
     */
    public long getFailedPaymentCount() {
        return paymentRepository.countByStatus("FAILED");
    }
} 