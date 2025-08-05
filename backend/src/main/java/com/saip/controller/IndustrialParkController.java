package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.IndustrialPark;
import com.saip.service.IndustrialParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产业园控制器
 */
@RestController
@RequestMapping("/api/industrial-parks")
@CrossOrigin(origins = "*")
public class IndustrialParkController {
    
    @Autowired
    private IndustrialParkService industrialParkService;
    
    /**
     * 获取所有产业园
     */
    @GetMapping
    public Result<List<IndustrialPark>> getAllIndustrialParks() {
        try {
            List<IndustrialPark> industrialParks = industrialParkService.getAllIndustrialParks();
            return Result.success(industrialParks);
        } catch (Exception e) {
            return Result.error("获取产业园列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取产业园
     */
    @GetMapping("/{id}")
    public Result<IndustrialPark> getIndustrialParkById(@PathVariable Long id) {
        try {
            return industrialParkService.getIndustrialParkById(id)
                    .map(Result::success)
                    .orElse(Result.error("产业园不存在"));
        } catch (Exception e) {
            return Result.error("获取产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建产业园
     */
    @PostMapping
    public Result<IndustrialPark> createIndustrialPark(@RequestBody IndustrialPark industrialPark) {
        try {
            // 检查邮箱是否已存在
            if (industrialPark.getEmail() != null && industrialParkService.isEmailExists(industrialPark.getEmail())) {
                return Result.error("邮箱已存在");
            }
            
            // 检查电话是否已存在
            if (industrialPark.getPhone() != null && industrialParkService.isPhoneExists(industrialPark.getPhone())) {
                return Result.error("电话已存在");
            }
            
            IndustrialPark createdIndustrialPark = industrialParkService.createIndustrialPark(industrialPark);
            return Result.success(createdIndustrialPark);
        } catch (Exception e) {
            return Result.error("创建产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新产业园
     */
    @PutMapping("/{id}")
    public Result<IndustrialPark> updateIndustrialPark(@PathVariable Long id, @RequestBody IndustrialPark industrialPark) {
        try {
            IndustrialPark updatedIndustrialPark = industrialParkService.updateIndustrialPark(id, industrialPark);
            if (updatedIndustrialPark != null) {
                return Result.success(updatedIndustrialPark);
            } else {
                return Result.error("产业园不存在");
            }
        } catch (Exception e) {
            return Result.error("更新产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除产业园
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteIndustrialPark(@PathVariable Long id) {
        try {
            boolean deleted = industrialParkService.deleteIndustrialPark(id);
            if (deleted) {
                return Result.success("产业园删除成功");
            } else {
                return Result.error("产业园不存在");
            }
        } catch (Exception e) {
            return Result.error("删除产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索产业园
     */
    @GetMapping("/search")
    public Result<List<IndustrialPark>> searchIndustrialParks(@RequestParam String keyword) {
        try {
            List<IndustrialPark> industrialParks = industrialParkService.searchIndustrialParksByName(keyword);
            return Result.success(industrialParks);
        } catch (Exception e) {
            return Result.error("搜索产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据地址搜索产业园
     */
    @GetMapping("/search/address")
    public Result<List<IndustrialPark>> searchIndustrialParksByAddress(@RequestParam String keyword) {
        try {
            List<IndustrialPark> industrialParks = industrialParkService.searchIndustrialParksByAddress(keyword);
            return Result.success(industrialParks);
        } catch (Exception e) {
            return Result.error("搜索产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据联系人搜索产业园
     */
    @GetMapping("/search/contact")
    public Result<List<IndustrialPark>> searchIndustrialParksByContact(@RequestParam String keyword) {
        try {
            List<IndustrialPark> industrialParks = industrialParkService.searchIndustrialParksByContact(keyword);
            return Result.success(industrialParks);
        } catch (Exception e) {
            return Result.error("搜索产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据邮箱查找产业园
     */
    @GetMapping("/email/{email}")
    public Result<IndustrialPark> getIndustrialParkByEmail(@PathVariable String email) {
        try {
            IndustrialPark industrialPark = industrialParkService.getIndustrialParkByEmail(email);
            if (industrialPark != null) {
                return Result.success(industrialPark);
            } else {
                return Result.error("产业园不存在");
            }
        } catch (Exception e) {
            return Result.error("查找产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据电话查找产业园
     */
    @GetMapping("/phone/{phone}")
    public Result<IndustrialPark> getIndustrialParkByPhone(@PathVariable String phone) {
        try {
            IndustrialPark industrialPark = industrialParkService.getIndustrialParkByPhone(phone);
            if (industrialPark != null) {
                return Result.success(industrialPark);
            } else {
                return Result.error("产业园不存在");
            }
        } catch (Exception e) {
            return Result.error("查找产业园失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取产业园统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getIndustrialParkStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalCount", industrialParkService.getIndustrialParkCount());
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }
} 