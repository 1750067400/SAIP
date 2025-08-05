package com.saip.service;

import com.saip.entity.Member;
import com.saip.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 会员服务类
 */
@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    /**
     * 获取所有会员
     */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    /**
     * 根据ID获取会员
     */
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }
    
    /**
     * 创建会员
     */
    public Member createMember(Member member) {
        member.setCreatedTime(LocalDateTime.now());
        member.setUpdatedTime(LocalDateTime.now());
        if (member.getJoinDate() == null) {
            member.setJoinDate(LocalDate.now());
        }
        if (member.getStatus() == null) {
            member.setStatus("ACTIVE");
        }
        return memberRepository.save(member);
    }
    
    /**
     * 更新会员
     */
    public Member updateMember(Long id, Member memberDetails) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setName(memberDetails.getName());
            member.setCompanyName(memberDetails.getCompanyName());
            member.setContactPerson(memberDetails.getContactPerson());
            member.setPhone(memberDetails.getPhone());
            member.setEmail(memberDetails.getEmail());
            member.setAddress(memberDetails.getAddress());
            member.setIndustry(memberDetails.getIndustry());
            member.setMemberType(memberDetails.getMemberType());
            member.setJoinDate(memberDetails.getJoinDate());
            member.setStatus(memberDetails.getStatus());
            member.setUpdatedTime(LocalDateTime.now());
            return memberRepository.save(member);
        }
        return null;
    }
    
    /**
     * 删除会员
     */
    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 根据状态获取会员
     */
    public List<Member> getMembersByStatus(String status) {
        return memberRepository.findByStatus(status);
    }
    
    /**
     * 根据会员类型获取会员
     */
    public List<Member> getMembersByType(String memberType) {
        return memberRepository.findByMemberType(memberType);
    }
    
    /**
     * 根据行业获取会员
     */
    public List<Member> getMembersByIndustry(String industry) {
        return memberRepository.findByIndustry(industry);
    }
    
    /**
     * 根据名称搜索会员
     */
    public List<Member> searchMembersByName(String keyword) {
        return memberRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据公司名称搜索会员
     */
    public List<Member> searchMembersByCompanyName(String keyword) {
        return memberRepository.findByCompanyNameContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据邮箱查找会员
     */
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    
    /**
     * 根据电话查找会员
     */
    public Member getMemberByPhone(String phone) {
        return memberRepository.findByPhone(phone);
    }
    
    /**
     * 获取会员统计
     */
    public long getMemberCount() {
        return memberRepository.count();
    }
    
    /**
     * 获取活跃会员数量
     */
    public long getActiveMemberCount() {
        return memberRepository.countByStatus("ACTIVE");
    }
    
    /**
     * 获取最近加入的会员数量
     */
    public long getRecentMemberCount(LocalDateTime since) {
        return memberRepository.countByCreatedTimeAfter(since);
    }
    
    /**
     * 检查邮箱是否已存在
     */
    public boolean isEmailExists(String email) {
        return memberRepository.findByEmail(email) != null;
    }
    
    /**
     * 检查电话是否已存在
     */
    public boolean isPhoneExists(String phone) {
        return memberRepository.findByPhone(phone) != null;
    }
} 