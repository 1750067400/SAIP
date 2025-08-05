package com.saip.service;

import com.saip.entity.Company;
import com.saip.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 公司服务类
 */
@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    /**
     * 获取所有公司
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    
    /**
     * 根据ID获取公司
     */
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }
    
    /**
     * 创建公司
     */
    public Company createCompany(Company company) {
        company.setCreatedTime(LocalDateTime.now());
        company.setUpdatedTime(LocalDateTime.now());
        return companyRepository.save(company);
    }
    
    /**
     * 更新公司
     */
    public Company updateCompany(Long id, Company companyDetails) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            company.setName(companyDetails.getName());
            company.setType(companyDetails.getType());
            company.setAddress(companyDetails.getAddress());
            company.setContact(companyDetails.getContact());
            company.setPhone(companyDetails.getPhone());
            company.setEmail(companyDetails.getEmail());
            company.setUpdatedTime(LocalDateTime.now());
            return companyRepository.save(company);
        }
        return null;
    }
    
    /**
     * 删除公司
     */
    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 根据类型获取公司
     */
    public List<Company> getCompaniesByType(String type) {
        return companyRepository.findByType(type);
    }
    
    /**
     * 根据名称搜索公司
     */
    public List<Company> searchCompaniesByName(String keyword) {
        return companyRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据地址搜索公司
     */
    public List<Company> searchCompaniesByAddress(String keyword) {
        return companyRepository.findByAddressContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据联系人搜索公司
     */
    public List<Company> searchCompaniesByContact(String keyword) {
        return companyRepository.findByContactContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据邮箱查找公司
     */
    public Company getCompanyByEmail(String email) {
        return companyRepository.findByEmail(email);
    }
    
    /**
     * 根据电话查找公司
     */
    public Company getCompanyByPhone(String phone) {
        return companyRepository.findByPhone(phone);
    }
    
    /**
     * 获取公司统计
     */
    public long getCompanyCount() {
        return companyRepository.count();
    }
    
    /**
     * 根据类型统计公司数量
     */
    public long getCompanyCountByType(String type) {
        return companyRepository.countByType(type);
    }
    
    /**
     * 检查邮箱是否已存在
     */
    public boolean isEmailExists(String email) {
        return companyRepository.findByEmail(email) != null;
    }
    
    /**
     * 检查电话是否已存在
     */
    public boolean isPhoneExists(String phone) {
        return companyRepository.findByPhone(phone) != null;
    }
} 