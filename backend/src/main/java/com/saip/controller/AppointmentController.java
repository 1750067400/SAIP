package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Appointment;
import com.saip.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约控制器
 */
@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    /**
     * 获取所有预约
     */
    @GetMapping
    public Result<List<Appointment>> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            return Result.success(appointments);
        } catch (Exception e) {
            return Result.error("获取预约列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取预约
     */
    @GetMapping("/{id}")
    public Result<Appointment> getAppointmentById(@PathVariable Long id) {
        try {
            return appointmentService.getAppointmentById(id)
                    .map(Result::success)
                    .orElse(Result.error("预约不存在"));
        } catch (Exception e) {
            return Result.error("获取预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建预约
     */
    @PostMapping
    public Result<Appointment> createAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return Result.success(createdAppointment);
        } catch (Exception e) {
            return Result.error("创建预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新预约
     */
    @PutMapping("/{id}")
    public Result<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
            if (updatedAppointment != null) {
                return Result.success(updatedAppointment);
            } else {
                return Result.error("预约不存在");
            }
        } catch (Exception e) {
            return Result.error("更新预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除预约
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteAppointment(@PathVariable Long id) {
        try {
            boolean deleted = appointmentService.deleteAppointment(id);
            if (deleted) {
                return Result.success("预约删除成功");
            } else {
                return Result.error("预约不存在");
            }
        } catch (Exception e) {
            return Result.error("删除预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据状态获取预约
     */
    @GetMapping("/status/{status}")
    public Result<List<Appointment>> getAppointmentsByStatus(@PathVariable String status) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
            return Result.success(appointments);
        } catch (Exception e) {
            return Result.error("获取预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据服务类型获取预约
     */
    @GetMapping("/service/{serviceType}")
    public Result<List<Appointment>> getAppointmentsByServiceType(@PathVariable String serviceType) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByServiceType(serviceType);
            return Result.success(appointments);
        } catch (Exception e) {
            return Result.error("获取预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取今日预约
     */
    @GetMapping("/today")
    public Result<List<Appointment>> getTodayAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getTodayAppointments();
            return Result.success(appointments);
        } catch (Exception e) {
            return Result.error("获取今日预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取待处理预约
     */
    @GetMapping("/pending")
    public Result<List<Appointment>> getPendingAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getPendingAppointments();
            return Result.success(appointments);
        } catch (Exception e) {
            return Result.error("获取待处理预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索预约
     */
    @GetMapping("/search")
    public Result<List<Appointment>> searchAppointments(@RequestParam String keyword) {
        try {
            List<Appointment> appointments = appointmentService.searchAppointmentsByMemberName(keyword);
            return Result.success(appointments);
        } catch (Exception e) {
            return Result.error("搜索预约失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取预约统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getAppointmentStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalCount", appointmentService.getAppointmentCount());
            statistics.put("todayCount", appointmentService.getTodayAppointmentCount());
            statistics.put("pendingCount", appointmentService.getPendingAppointmentCount());
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }
} 