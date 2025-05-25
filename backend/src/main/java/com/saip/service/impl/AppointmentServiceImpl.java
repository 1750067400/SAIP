package com.saip.service.impl;

import com.saip.entity.Appointment;
import com.saip.service.AppointmentService;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class AppointmentServiceImpl implements AppointmentService {

    private static final List<Appointment> appointments = new ArrayList<>();

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments);
    }

    @Override
    public Appointment findById(Long id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() != null && appointment.getId().equals(id)) {
                return appointment;
            }
        }
        return null;
    }

    @Override
    public Appointment add(Appointment appointment) {
        long newId = appointments.size() + 1;
        Appointment newAppointment = new Appointment();
        newAppointment.setId(newId);
        newAppointment.setMemberName(appointment.getMemberName());
        newAppointment.setServiceType(appointment.getServiceType());
        newAppointment.setAppointmentDate(appointment.getAppointmentDate());
        newAppointment.setAppointmentTime(appointment.getAppointmentTime());
        newAppointment.setRemarks(appointment.getRemarks());
        newAppointment.setStatus("PENDING");
        newAppointment.setCreatedAt(LocalDateTime.now());
        newAppointment.setUpdatedAt(LocalDateTime.now());
        appointments.add(newAppointment);
        return newAppointment;
    }

    @Override
    public Appointment update(Appointment appointment) {
        // 简化更新逻辑
        return appointment;
    }

    @Override
    public void delete(Long id) {
        appointments.removeIf(appointment -> appointment.getId() != null && appointment.getId().equals(id));
    }
} 