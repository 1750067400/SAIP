package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Member;
import com.saip.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员控制器
 */
@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    /**
     * 获取所有会员
     */
    @GetMapping
    public Result<List<Member>> getAllMembers() {
        try {
            List<Member> members = memberService.getAllMembers();
            return Result.success(members);
        } catch (Exception e) {
            return Result.error("获取会员列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取会员
     */
    @GetMapping("/{id}")
    public Result<Member> getMemberById(@PathVariable Long id) {
        try {
            return memberService.getMemberById(id)
                    .map(Result::success)
                    .orElse(Result.error("会员不存在"));
        } catch (Exception e) {
            return Result.error("获取会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建会员
     */
    @PostMapping
    public Result<Member> createMember(@RequestBody Member member) {
        try {
            // 检查邮箱是否已存在
            if (member.getEmail() != null && memberService.isEmailExists(member.getEmail())) {
                return Result.error("邮箱已存在");
            }
            
            // 检查电话是否已存在
            if (member.getPhone() != null && memberService.isPhoneExists(member.getPhone())) {
                return Result.error("电话已存在");
            }
            
            Member createdMember = memberService.createMember(member);
            return Result.success(createdMember);
        } catch (Exception e) {
            return Result.error("创建会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新会员
     */
    @PutMapping("/{id}")
    public Result<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        try {
            Member updatedMember = memberService.updateMember(id, member);
            if (updatedMember != null) {
                return Result.success(updatedMember);
            } else {
                return Result.error("会员不存在");
            }
        } catch (Exception e) {
            return Result.error("更新会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除会员
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteMember(@PathVariable Long id) {
        try {
            boolean deleted = memberService.deleteMember(id);
            if (deleted) {
                return Result.success("会员删除成功");
            } else {
                return Result.error("会员不存在");
            }
        } catch (Exception e) {
            return Result.error("删除会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据状态获取会员
     */
    @GetMapping("/status/{status}")
    public Result<List<Member>> getMembersByStatus(@PathVariable String status) {
        try {
            List<Member> members = memberService.getMembersByStatus(status);
            return Result.success(members);
        } catch (Exception e) {
            return Result.error("获取会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据会员类型获取会员
     */
    @GetMapping("/type/{memberType}")
    public Result<List<Member>> getMembersByType(@PathVariable String memberType) {
        try {
            List<Member> members = memberService.getMembersByType(memberType);
            return Result.success(members);
        } catch (Exception e) {
            return Result.error("获取会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据行业获取会员
     */
    @GetMapping("/industry/{industry}")
    public Result<List<Member>> getMembersByIndustry(@PathVariable String industry) {
        try {
            List<Member> members = memberService.getMembersByIndustry(industry);
            return Result.success(members);
        } catch (Exception e) {
            return Result.error("获取会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索会员
     */
    @GetMapping("/search")
    public Result<List<Member>> searchMembers(@RequestParam String keyword) {
        try {
            List<Member> members = memberService.searchMembersByName(keyword);
            return Result.success(members);
        } catch (Exception e) {
            return Result.error("搜索会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据公司名称搜索会员
     */
    @GetMapping("/search/company")
    public Result<List<Member>> searchMembersByCompany(@RequestParam String keyword) {
        try {
            List<Member> members = memberService.searchMembersByCompanyName(keyword);
            return Result.success(members);
        } catch (Exception e) {
            return Result.error("搜索会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据邮箱查找会员
     */
    @GetMapping("/email/{email}")
    public Result<Member> getMemberByEmail(@PathVariable String email) {
        try {
            Member member = memberService.getMemberByEmail(email);
            if (member != null) {
                return Result.success(member);
            } else {
                return Result.error("会员不存在");
            }
        } catch (Exception e) {
            return Result.error("查找会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据电话查找会员
     */
    @GetMapping("/phone/{phone}")
    public Result<Member> getMemberByPhone(@PathVariable String phone) {
        try {
            Member member = memberService.getMemberByPhone(phone);
            if (member != null) {
                return Result.success(member);
            } else {
                return Result.error("会员不存在");
            }
        } catch (Exception e) {
            return Result.error("查找会员失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取会员统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getMemberStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalCount", memberService.getMemberCount());
            statistics.put("activeCount", memberService.getActiveMemberCount());
            statistics.put("recentCount", memberService.getRecentMemberCount(java.time.LocalDateTime.now().minusMonths(1)));
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }
} 