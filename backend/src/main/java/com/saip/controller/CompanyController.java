package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Company;
import com.saip.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公司控制器
 */
@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {
    
    @Autowired
    private CompanyService companyService;
    
    /**
     * 获取所有公司
     */
    @GetMapping
    public Result<List<Company>> getAllCompanies() {
        try {
            List<Company> companies = companyService.getAllCompanies();
            return Result.success(companies);
        } catch (Exception e) {
            return Result.error("获取公司列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取公司
     */
    @GetMapping("/{id}")
    public Result<Company> getCompanyById(@PathVariable Long id) {
        try {
            return companyService.getCompanyById(id)
                    .map(Result::success)
                    .orElse(Result.error("公司不存在"));
        } catch (Exception e) {
            return Result.error("获取公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建公司
     */
    @PostMapping
    public Result<Company> createCompany(@RequestBody Company company) {
        try {
            // 检查邮箱是否已存在
            if (company.getEmail() != null && companyService.isEmailExists(company.getEmail())) {
                return Result.error("邮箱已存在");
            }
            
            // 检查电话是否已存在
            if (company.getPhone() != null && companyService.isPhoneExists(company.getPhone())) {
                return Result.error("电话已存在");
            }
            
            Company createdCompany = companyService.createCompany(company);
            return Result.success(createdCompany);
        } catch (Exception e) {
            return Result.error("创建公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新公司
     */
    @PutMapping("/{id}")
    public Result<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        try {
            Company updatedCompany = companyService.updateCompany(id, company);
            if (updatedCompany != null) {
                return Result.success(updatedCompany);
            } else {
                return Result.error("公司不存在");
            }
        } catch (Exception e) {
            return Result.error("更新公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除公司
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteCompany(@PathVariable Long id) {
        try {
            boolean deleted = companyService.deleteCompany(id);
            if (deleted) {
                return Result.success("公司删除成功");
            } else {
                return Result.error("公司不存在");
            }
        } catch (Exception e) {
            return Result.error("删除公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据类型获取公司
     */
    @GetMapping("/type/{type}")
    public Result<List<Company>> getCompaniesByType(@PathVariable String type) {
        try {
            List<Company> companies = companyService.getCompaniesByType(type);
            return Result.success(companies);
        } catch (Exception e) {
            return Result.error("获取公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索公司
     */
    @GetMapping("/search")
    public Result<List<Company>> searchCompanies(@RequestParam String keyword) {
        try {
            List<Company> companies = companyService.searchCompaniesByName(keyword);
            return Result.success(companies);
        } catch (Exception e) {
            return Result.error("搜索公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据地址搜索公司
     */
    @GetMapping("/search/address")
    public Result<List<Company>> searchCompaniesByAddress(@RequestParam String keyword) {
        try {
            List<Company> companies = companyService.searchCompaniesByAddress(keyword);
            return Result.success(companies);
        } catch (Exception e) {
            return Result.error("搜索公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据联系人搜索公司
     */
    @GetMapping("/search/contact")
    public Result<List<Company>> searchCompaniesByContact(@RequestParam String keyword) {
        try {
            List<Company> companies = companyService.searchCompaniesByContact(keyword);
            return Result.success(companies);
        } catch (Exception e) {
            return Result.error("搜索公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据邮箱查找公司
     */
    @GetMapping("/email/{email}")
    public Result<Company> getCompanyByEmail(@PathVariable String email) {
        try {
            Company company = companyService.getCompanyByEmail(email);
            if (company != null) {
                return Result.success(company);
            } else {
                return Result.error("公司不存在");
            }
        } catch (Exception e) {
            return Result.error("查找公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据电话查找公司
     */
    @GetMapping("/phone/{phone}")
    public Result<Company> getCompanyByPhone(@PathVariable String phone) {
        try {
            Company company = companyService.getCompanyByPhone(phone);
            if (company != null) {
                return Result.success(company);
            } else {
                return Result.error("公司不存在");
            }
        } catch (Exception e) {
            return Result.error("查找公司失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取公司统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getCompanyStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalCount", companyService.getCompanyCount());
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }
} 