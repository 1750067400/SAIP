package com.saip.service;

import com.saip.entity.Appointment;
import com.saip.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 预约服务类
 */
@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    /**
     * 获取所有预约
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    /**
     * 根据ID获取预约
     */
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    /**
     * 创建预约
     */
    public Appointment createAppointment(Appointment appointment) {
        appointment.setCreatedTime(LocalDateTime.now());
        appointment.setUpdatedTime(LocalDateTime.now());
        return appointmentRepository.save(appointment);
    }
    
    /**
     * 更新预约
     */
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setMemberName(appointmentDetails.getMemberName());
            appointment.setServiceType(appointmentDetails.getServiceType());
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
            appointment.setRemarks(appointmentDetails.getRemarks());
            appointment.setStatus(appointmentDetails.getStatus());
            appointment.setUpdatedTime(LocalDateTime.now());
            return appointmentRepository.save(appointment);
        }
        return null;
    }
    
    /**
     * 删除预约
     */
    public boolean deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 根据状态获取预约
     */
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
    
    /**
     * 根据服务类型获取预约
     */
    public List<Appointment> getAppointmentsByServiceType(String serviceType) {
        return appointmentRepository.findByServiceType(serviceType);
    }
    
    /**
     * 根据日期获取预约
     */
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date);
    }
    
    /**
     * 获取今日预约
     */
    public List<Appointment> getTodayAppointments() {
        return appointmentRepository.findTodayAppointments(LocalDate.now());
    }
    
    /**
     * 获取待处理预约
     */
    public List<Appointment> getPendingAppointments() {
        return appointmentRepository.findPendingAppointments();
    }
    
    /**
     * 根据会员名称搜索预约
     */
    public List<Appointment> searchAppointmentsByMemberName(String keyword) {
        return appointmentRepository.findByMemberNameContainingIgnoreCase(keyword);
    }
    
    /**
     * 获取预约统计
     */
    public long getAppointmentCount() {
        return appointmentRepository.count();
    }
    
    /**
     * 获取今日预约数量
     */
    public long getTodayAppointmentCount() {
        return appointmentRepository.findTodayAppointments(LocalDate.now()).size();
    }
    
    /**
     * 获取待处理预约数量
     */
    public long getPendingAppointmentCount() {
        return appointmentRepository.countByStatus("PENDING");
    }
} 