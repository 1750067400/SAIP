package com.saip.repository;

import java.util.List;

import com.saip.entity.Member;

public interface MemberRepository {
    List<Member> findAll();
    Member findById(Long id);
    int add(Member member);
    int update(Member member);
    int delete(Long id);
} 