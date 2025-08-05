package com.saip.repository;

import com.saip.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 预约Repository接口
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    /**
     * 根据状态查找预约
     */
    List<Appointment> findByStatus(String status);
    
    /**
     * 根据服务类型查找
     */
    List<Appointment> findByServiceType(String serviceType);
    
    /**
     * 根据预约日期查找
     */
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
    
    /**
     * 根据预约日期范围查找
     */
    List<Appointment> findByAppointmentDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * 根据会员名称模糊查找
     */
    List<Appointment> findByMemberNameContainingIgnoreCase(String keyword);
    
    /**
     * 根据创建时间统计数量
     */
    long countByCreatedTimeAfter(LocalDateTime createdTime);
    
    /**
     * 根据状态统计数量
     */
    long countByStatus(String status);
    
    /**
     * 查找今日预约
     */
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = :today")
    List<Appointment> findTodayAppointments(@Param("today") LocalDate today);
    
    /**
     * 查找待处理的预约
     */
    @Query("SELECT a FROM Appointment a WHERE a.status IN ('PENDING', 'CONFIRMED')")
    List<Appointment> findPendingAppointments();
} 