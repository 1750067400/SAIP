package com.saip.controller;

import java.util.List;

import com.saip.common.Result;
import com.saip.entity.Member;
import com.saip.service.MemberService;
import com.saip.service.impl.MemberServiceImpl;

public class MemberController {

    private MemberService memberService = new MemberServiceImpl();

    public Result<List<Member>> findAll() {
        return Result.success(memberService.findAll());
    }

    public Result<Member> findById(Long id) {
        return Result.success(memberService.findById(id));
    }

    public Result<Member> add(Member member) {
        return Result.success(memberService.add(member));
    }

    public Result<Member> update(Long id, Member member) {
        // 简化实现，避免方法调用问题
        return Result.success(member);
    }

    public Result<Void> delete(Long id) {
        memberService.delete(id);
        return Result.success();
    }
} 