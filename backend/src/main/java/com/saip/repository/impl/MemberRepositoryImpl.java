package com.saip.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.saip.entity.Member;
import com.saip.repository.MemberRepository;
import com.saip.enums.MemberType;
import com.saip.enums.MemberLevel;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> rowMapper = (rs, rowNum) -> {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        member.setCompany(rs.getString("company"));
        member.setPosition(rs.getString("position"));
        member.setPhone(rs.getString("phone"));
        member.setEmail(rs.getString("email"));
        member.setType(MemberType.valueOf(rs.getString("type")));
        member.setLevel(MemberLevel.valueOf(rs.getString("level")));
        member.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        member.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return member;
    };

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member ORDER BY created_at DESC", rowMapper);
    }

    @Override
    public Member findById(Long id) {
        List<Member> members = jdbcTemplate.query(
            "SELECT * FROM member WHERE id = ?",
            rowMapper,
            id
        );
        return members.isEmpty() ? null : members.get(0);
    }

    @Override
    public void add(Member member) {
        LocalDateTime now = LocalDateTime.now();
        if (member.getType() == null) {
            member.setType(MemberType.COMPANY);
        }
        if (member.getLevel() == null) {
            member.setLevel(MemberLevel.NORMAL);
        }
        member.setCreatedAt(now);
        member.setUpdatedAt(now);
        
        jdbcTemplate.update(
            "INSERT INTO member (name, company, position, phone, email, type, level, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            member.getName(),
            member.getCompany(),
            member.getPosition(),
            member.getPhone(),
            member.getEmail(),
            member.getType().name(),
            member.getLevel().name(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }

    @Override
    public void update(Member member) {
        member.setUpdatedAt(LocalDateTime.now());
        
        jdbcTemplate.update(
            "UPDATE member SET name = ?, company = ?, position = ?, " +
            "phone = ?, email = ?, type = ?, level = ?, updated_at = ? WHERE id = ?",
            member.getName(),
            member.getCompany(),
            member.getPosition(),
            member.getPhone(),
            member.getEmail(),
            member.getType().name(),
            member.getLevel().name(),
            member.getUpdatedAt(),
            member.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM member WHERE id = ?", id);
    }
} 