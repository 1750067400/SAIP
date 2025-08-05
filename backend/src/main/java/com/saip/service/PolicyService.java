package com.saip.service;

import com.saip.entity.Policy;
import com.saip.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 政策服务类
 */
@Service
public class PolicyService {
    
    @Autowired
    private PolicyRepository policyRepository;
    
    /**
     * 获取所有政策
     */
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }
    
    /**
     * 根据ID获取政策
     */
    public Optional<Policy> getPolicyById(Long id) {
        return policyRepository.findById(id);
    }
    
    /**
     * 创建政策
     */
    public Policy createPolicy(Policy policy) {
        policy.setCreatedTime(LocalDateTime.now());
        policy.setUpdatedTime(LocalDateTime.now());
        if (policy.getIsPublished() == null) {
            policy.setIsPublished(false);
        }
        if (policy.getIsUrgent() == null) {
            policy.setIsUrgent(false);
        }
        if (policy.getViewCount() == null) {
            policy.setViewCount(0);
        }
        return policyRepository.save(policy);
    }
    
    /**
     * 更新政策
     */
    public Policy updatePolicy(Long id, Policy policyDetails) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            policy.setTitle(policyDetails.getTitle());
            policy.setContent(policyDetails.getContent());
            policy.setPolicyNumber(policyDetails.getPolicyNumber());
            policy.setIssuingAuthority(policyDetails.getIssuingAuthority());
            policy.setPolicyType(policyDetails.getPolicyType());
            policy.setEffectiveDate(policyDetails.getEffectiveDate());
            policy.setExpiryDate(policyDetails.getExpiryDate());
            policy.setIsUrgent(policyDetails.getIsUrgent());
            policy.setIsPublished(policyDetails.getIsPublished());
            policy.setUpdatedTime(LocalDateTime.now());
            
            // 如果发布状态改变为已发布，设置发布时间
            if (policyDetails.getIsPublished() && !policy.getIsPublished()) {
                policy.setPublishedTime(LocalDateTime.now());
            }
            
            return policyRepository.save(policy);
        }
        return null;
    }
    
    /**
     * 删除政策
     */
    public boolean deletePolicy(Long id) {
        if (policyRepository.existsById(id)) {
            policyRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 发布政策
     */
    public Policy publishPolicy(Long id) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            policy.setIsPublished(true);
            policy.setPublishedTime(LocalDateTime.now());
            policy.setUpdatedTime(LocalDateTime.now());
            return policyRepository.save(policy);
        }
        return null;
    }
    
    /**
     * 取消发布政策
     */
    public Policy unpublishPolicy(Long id) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            policy.setIsPublished(false);
            policy.setUpdatedTime(LocalDateTime.now());
            return policyRepository.save(policy);
        }
        return null;
    }
    
    /**
     * 增加浏览量
     */
    public Policy incrementViewCount(Long id) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            policy.setViewCount(policy.getViewCount() + 1);
            return policyRepository.save(policy);
        }
        return null;
    }
    
    /**
     * 根据政策类型获取政策
     */
    public List<Policy> getPoliciesByType(String policyType) {
        return policyRepository.findByPolicyType(policyType);
    }
    
    /**
     * 获取已发布的政策
     */
    public List<Policy> getPublishedPolicies() {
        return policyRepository.findByIsPublishedTrue();
    }
    
    /**
     * 获取紧急政策
     */
    public List<Policy> getUrgentPolicies() {
        return policyRepository.findByIsUrgentTrue();
    }
    
    /**
     * 根据发布机构获取政策
     */
    public List<Policy> getPoliciesByAuthority(String issuingAuthority) {
        return policyRepository.findByIssuingAuthority(issuingAuthority);
    }
    
    /**
     * 搜索政策
     */
    public List<Policy> searchPolicies(String keyword) {
        return policyRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    /**
     * 获取最近发布的政策
     */
    public List<Policy> getRecentPublishedPolicies(LocalDateTime since) {
        return policyRepository.findRecentPublishedPolicies(since);
    }
    
    /**
     * 获取有效的政策（未过期）
     */
    public List<Policy> getValidPolicies() {
        return policyRepository.findValidPolicies(LocalDateTime.now());
    }
    
    /**
     * 获取政策统计
     */
    public long getPolicyCount() {
        return policyRepository.count();
    }
    
    /**
     * 获取已发布政策数量
     */
    public long getPublishedPolicyCount() {
        return policyRepository.countByIsPublishedTrue();
    }
    
    /**
     * 获取紧急政策数量
     */
    public long getUrgentPolicyCount() {
        return policyRepository.countByIsUrgentTrue();
    }
    
    /**
     * 获取政策类型数量
     */
    public long getPolicyCountByType(String policyType) {
        return policyRepository.countByPolicyType(policyType);
    }
} 