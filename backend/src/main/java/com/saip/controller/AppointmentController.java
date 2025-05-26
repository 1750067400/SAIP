package com.saip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.saip.common.Result;
import com.saip.entity.Appointment;
import com.saip.service.AppointmentService;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public Result<List<Appointment>> findAll() {
        return Result.success(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Appointment> findById(@PathVariable Long id) {
        return Result.success(appointmentService.findById(id));
    }

    @PostMapping
    public Result<Appointment> add(@RequestBody Appointment appointment) {
        return Result.success(appointmentService.add(appointment));
    }

    @PutMapping("/{id}")
    public Result<Appointment> update(@PathVariable Long id, @RequestBody Appointment appointment) {
        appointment.setId(id);
        return Result.success(appointmentService.update(appointment));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return Result.success();
    }
} 