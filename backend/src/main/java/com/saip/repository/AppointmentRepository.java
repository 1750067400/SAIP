package com.saip.repository;

import com.saip.entity.Appointment;
import java.util.List;

public interface AppointmentRepository {
    List<Appointment> findAll();
    Appointment findById(Long id);
    void add(Appointment appointment);
    void update(Appointment appointment);
    void delete(Long id);
} 