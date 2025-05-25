package com.saip.service;

import java.util.List;

import com.saip.entity.Member;

public interface MemberService {
    List<Member> findAll();
    Member findById(Long id);
    Member add(Member member);
    Member update(Member member);
    void delete(Long id);
} 