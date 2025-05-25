package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Appointment;
import com.saip.service.AppointmentService;
import com.saip.service.impl.AppointmentServiceImpl;

import java.util.List;

public class AppointmentController {

    private AppointmentService appointmentService = new AppointmentServiceImpl();

    public Result<List<Appointment>> findAll() {
        return Result.success(appointmentService.findAll());
    }

    public Result<Appointment> findById(Long id) {
        return Result.success(appointmentService.findById(id));
    }

    public Result<Appointment> add(Appointment appointment) {
        return Result.success(appointmentService.add(appointment));
    }

    public Result<Appointment> update(Long id, Appointment appointment) {
        appointment.setId(id);
        return Result.success(appointmentService.update(appointment));
    }

    public Result<Void> delete(Long id) {
        appointmentService.delete(id);
        return Result.success();
    }
} 