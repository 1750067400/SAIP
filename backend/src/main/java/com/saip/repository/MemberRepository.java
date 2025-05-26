package com.saip.repository;

import java.util.List;

import com.saip.entity.Member;

public interface MemberRepository {
    List<Member> findAll();
    Member findById(Long id);
    void add(Member member);
    void update(Member member);
    void delete(Long id);
} 