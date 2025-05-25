package com.saip.service;

import com.saip.entity.Appointment;
import java.util.List;

public interface AppointmentService {
    List<Appointment> findAll();
    Appointment findById(Long id);
    Appointment add(Appointment appointment);
    Appointment update(Appointment appointment);
    void delete(Long id);
} 