package com.saip.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saip.entity.Appointment;
import com.saip.service.AppointmentService;
import com.saip.repository.AppointmentRepository;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment add(Appointment appointment) {
        appointment.setStatus("PENDING");
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());
        appointmentRepository.add(appointment);
        return appointment;
    }

    @Override
    public Appointment update(Appointment appointment) {
        appointment.setUpdatedAt(LocalDateTime.now());
        appointmentRepository.update(appointment);
        return appointment;
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.delete(id);
    }
} 