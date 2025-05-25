package com.saip;

import com.saip.controller.MemberController;
import com.saip.controller.AppointmentController;
import com.saip.entity.Member;
import com.saip.entity.Appointment;
import com.saip.enums.MemberType;
import com.saip.enums.MemberLevel;
import java.time.LocalDate;
import java.time.LocalTime;

public class SaipApplication {
    public static void main(String[] args) {
        System.out.println("SAIP Application Started");
        
        // 测试会员管理
        MemberController memberController = new MemberController();
        Member member = new Member();
        member.setName("测试会员");
        member.setType(MemberType.COMPANY);
        member.setLevel(MemberLevel.NORMAL);
        
        System.out.println("添加会员: " + memberController.add(member));
        System.out.println("获取所有会员: " + memberController.findAll());
        
        // 测试预约系统
        AppointmentController appointmentController = new AppointmentController();
        Appointment appointment = new Appointment();
        appointment.setMemberName("张三");
        appointment.setServiceType("CONSULTING");
        appointment.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment.setAppointmentTime(LocalTime.of(14, 0));
        appointment.setRemarks("需要咨询业务合作");
        
        System.out.println("添加预约: " + appointmentController.add(appointment));
        System.out.println("获取所有预约: " + appointmentController.findAll());
        
        System.out.println("SAIP Application Test Completed");
    }
} 