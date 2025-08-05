package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Payment;
import com.saip.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    /**
     * 获取所有支付记录
     */
    @GetMapping
    public Result<List<Payment>> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            return Result.success(payments);
        } catch (Exception e) {
            return Result.error("获取支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取支付记录
     */
    @GetMapping("/{id}")
    public Result<Payment> getPaymentById(@PathVariable Long id) {
        try {
            return paymentService.getPaymentById(id)
                    .map(Result::success)
                    .orElse(Result.error("支付记录不存在"));
        } catch (Exception e) {
            return Result.error("获取支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建支付记录
     */
    @PostMapping
    public Result<Payment> createPayment(@RequestBody Payment payment) {
        try {
            Payment createdPayment = paymentService.createPayment(payment);
            return Result.success(createdPayment);
        } catch (Exception e) {
            return Result.error("创建支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新支付记录
     */
    @PutMapping("/{id}")
    public Result<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        try {
            Payment updatedPayment = paymentService.updatePayment(id, payment);
            if (updatedPayment != null) {
                return Result.success(updatedPayment);
            } else {
                return Result.error("支付记录不存在");
            }
        } catch (Exception e) {
            return Result.error("更新支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除支付记录
     */
    @DeleteMapping("/{id}")
    public Result<String> deletePayment(@PathVariable Long id) {
        try {
            boolean deleted = paymentService.deletePayment(id);
            if (deleted) {
                return Result.success("支付记录删除成功");
            } else {
                return Result.error("支付记录不存在");
            }
        } catch (Exception e) {
            return Result.error("删除支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据状态获取支付记录
     */
    @GetMapping("/status/{status}")
    public Result<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        try {
            List<Payment> payments = paymentService.getPaymentsByStatus(status);
            return Result.success(payments);
        } catch (Exception e) {
            return Result.error("获取支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据会员ID获取支付记录
     */
    @GetMapping("/member/{memberId}")
    public Result<List<Payment>> getPaymentsByMemberId(@PathVariable Long memberId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByMemberId(memberId);
            return Result.success(payments);
        } catch (Exception e) {
            return Result.error("获取支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据支付类型获取支付记录
     */
    @GetMapping("/type/{paymentType}")
    public Result<List<Payment>> getPaymentsByType(@PathVariable String paymentType) {
        try {
            List<Payment> payments = paymentService.getPaymentsByType(paymentType);
            return Result.success(payments);
        } catch (Exception e) {
            return Result.error("获取支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据日期范围获取支付记录
     */
    @GetMapping("/date-range")
    public Result<List<Payment>> getPaymentsByDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        try {
            List<Payment> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
            return Result.success(payments);
        } catch (Exception e) {
            return Result.error("获取支付记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取支付统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getPaymentStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalCount", paymentService.getPaymentCount());
            statistics.put("totalAmount", paymentService.getTotalPaymentAmount());
            statistics.put("pendingCount", paymentService.getPendingPaymentCount());
            statistics.put("completedCount", paymentService.getCompletedPaymentCount());
            statistics.put("failedCount", paymentService.getFailedPaymentCount());
            statistics.put("recentAmount", paymentService.getRecentPaymentAmount(java.time.LocalDateTime.now().minusMonths(1)));
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取收入统计
     */
    @GetMapping("/revenue")
    public Result<Map<String, BigDecimal>> getRevenueStatistics() {
        try {
            Map<String, BigDecimal> revenue = new HashMap<>();
            revenue.put("totalRevenue", paymentService.getTotalPaymentAmount());
            revenue.put("monthlyRevenue", paymentService.getRecentPaymentAmount(java.time.LocalDateTime.now().minusMonths(1)));
            revenue.put("pendingRevenue", paymentService.getTotalAmountByStatus("PENDING"));
            return Result.success(revenue);
        } catch (Exception e) {
            return Result.error("获取收入统计失败: " + e.getMessage());
        }
    }
} 