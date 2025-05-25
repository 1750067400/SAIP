package com.saip.service.impl;

import com.saip.entity.Member;
import com.saip.service.MemberService;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class MemberServiceImpl implements MemberService {

    private static final List<Member> members = new ArrayList<>();

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    @Override
    public Member findById(Long id) {
        for (Member member : members) {
            if (member.getId() != null && member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public Member add(Member member) {
        // 简化ID生成
        long newId = members.size() + 1;
        Member newMember = new Member();
        newMember.setId(newId);
        newMember.setName(member.getName());
        newMember.setType(member.getType());
        newMember.setLevel(member.getLevel());
        newMember.setCreatedAt(LocalDateTime.now());
        newMember.setUpdatedAt(LocalDateTime.now());
        members.add(newMember);
        return newMember;
    }

    @Override
    public Member update(Member member) {
        // 简化更新逻辑
        return member;
    }

    @Override
    public void delete(Long id) {
        members.removeIf(member -> member.getId() != null && member.getId().equals(id));
    }
} 