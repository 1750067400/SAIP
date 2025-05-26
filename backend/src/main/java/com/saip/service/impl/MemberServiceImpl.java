package com.saip.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saip.entity.Member;
import com.saip.service.MemberService;
import com.saip.repository.MemberRepository;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Member add(Member member) {
        memberRepository.add(member);
        return member;
    }

    @Override
    public Member update(Member member) {
        memberRepository.update(member);
        return member;
    }

    @Override
    public void delete(Long id) {
        memberRepository.delete(id);
    }
} 