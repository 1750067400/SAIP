package com.saip.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.saip.entity.Appointment;
import com.saip.repository.AppointmentRepository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Appointment> rowMapper = (rs, rowNum) -> {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getLong("id"));
        appointment.setMemberName(rs.getString("member_name"));
        appointment.setServiceType(rs.getString("service_type"));
        appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
        appointment.setRemarks(rs.getString("remarks"));
        appointment.setStatus(rs.getString("status"));
        appointment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        appointment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return appointment;
    };

    @Override
    public List<Appointment> findAll() {
        return jdbcTemplate.query("SELECT * FROM appointment ORDER BY appointment_date, appointment_time", rowMapper);
    }

    @Override
    public Appointment findById(Long id) {
        List<Appointment> appointments = jdbcTemplate.query(
            "SELECT * FROM appointment WHERE id = ?",
            rowMapper,
            id
        );
        return appointments.isEmpty() ? null : appointments.get(0);
    }

    @Override
    public void add(Appointment appointment) {
        jdbcTemplate.update(
            "INSERT INTO appointment (member_name, service_type, appointment_date, appointment_time, remarks, status, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
            appointment.getMemberName(),
            appointment.getServiceType(),
            appointment.getAppointmentDate(),
            appointment.getAppointmentTime(),
            appointment.getRemarks(),
            appointment.getStatus(),
            appointment.getCreatedAt(),
            appointment.getUpdatedAt()
        );
    }

    @Override
    public void update(Appointment appointment) {
        jdbcTemplate.update(
            "UPDATE appointment SET member_name = ?, service_type = ?, appointment_date = ?, " +
            "appointment_time = ?, remarks = ?, status = ?, updated_at = ? WHERE id = ?",
            appointment.getMemberName(),
            appointment.getServiceType(),
            appointment.getAppointmentDate(),
            appointment.getAppointmentTime(),
            appointment.getRemarks(),
            appointment.getStatus(),
            appointment.getUpdatedAt(),
            appointment.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM appointment WHERE id = ?", id);
    }
} 