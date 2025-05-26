package com.saip.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.saip.common.Result;
import com.saip.entity.Member;
import com.saip.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public Result<List<Member>> findAll() {
        return Result.success(memberService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Member> findById(@PathVariable Long id) {
        return Result.success(memberService.findById(id));
    }

    @PostMapping
    public Result<Member> add(@RequestBody Member member) {
        return Result.success(memberService.add(member));
    }

    @PutMapping("/{id}")
    public Result<Member> update(@PathVariable Long id, @RequestBody Member member) {
        member.setId(id);
        return Result.success(memberService.update(member));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return Result.success();
    }
} 