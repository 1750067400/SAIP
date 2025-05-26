package com.saip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import com.saip.common.Result;
import com.saip.service.MemberService;
import com.saip.service.AppointmentService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/members")
    public Result<Map<String, Object>> getMemberStatistics() {
        Map<String, Object> data = new HashMap<>();
        
        // 会员类型分布
        Map<String, Integer> memberTypeData = new HashMap<>();
        memberTypeData.put("公司", 235);
        memberTypeData.put("产业园单位", 274);
        data.put("memberTypeData", memberTypeData);
        
        // 会员等级分布
        Map<String, Integer> memberLevelData = new HashMap<>();
        memberLevelData.put("普通会员", 335);
        memberLevelData.put("理事", 234);
        memberLevelData.put("常务理事", 158);
        memberLevelData.put("副会长", 135);
        memberLevelData.put("会长", 48);
        data.put("memberLevelData", memberLevelData);
        
        return Result.success(data);
    }

    @GetMapping("/appointments")
    public Result<Map<String, Object>> getAppointmentStatistics() {
        Map<String, Object> data = new HashMap<>();
        
        // 服务类型分布
        Map<String, Integer> serviceTypeData = new HashMap<>();
        serviceTypeData.put("咨询服务", 120);
        serviceTypeData.put("培训服务", 85);
        serviceTypeData.put("交流活动", 65);
        serviceTypeData.put("其他服务", 30);
        data.put("serviceTypeData", serviceTypeData);
        
        // 预约状态分布
        Map<String, Integer> statusData = new HashMap<>();
        statusData.put("待确认", 45);
        statusData.put("已确认", 180);
        statusData.put("已取消", 25);
        statusData.put("已完成", 50);
        data.put("statusData", statusData);
        
        return Result.success(data);
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboardData() {
        Map<String, Object> data = new HashMap<>();
        
        // 模拟统计数据
        data.put("totalMembers", 509);
        data.put("totalParks", 4);
        data.put("occupancyRate", "85.8%");
        data.put("todayVisitors", 210);
        
        // 会员类型分布
        Map<String, Integer> memberTypeData = new HashMap<>();
        memberTypeData.put("公司", 235);
        memberTypeData.put("产业园单位", 274);
        data.put("memberTypeData", memberTypeData);
        
        // 产业园入住率
        Map<String, Integer> occupancyData = new HashMap<>();
        occupancyData.put("产业园A", 85);
        occupancyData.put("产业园B", 92);
        occupancyData.put("产业园C", 78);
        occupancyData.put("产业园D", 88);
        data.put("occupancyData", occupancyData);
        
        // 访客趋势
        int[] visitorTrend = {120, 132, 101, 134, 90, 230, 210};
        data.put("visitorTrend", visitorTrend);
        
        // 会员等级分布
        Map<String, Integer> memberLevelData = new HashMap<>();
        memberLevelData.put("普通会员", 335);
        memberLevelData.put("理事", 234);
        memberLevelData.put("常务理事", 158);
        memberLevelData.put("副会长", 135);
        memberLevelData.put("会长", 48);
        data.put("memberLevelData", memberLevelData);
        
        return Result.success(data);
    }
} 