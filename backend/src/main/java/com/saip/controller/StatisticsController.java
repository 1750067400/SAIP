package com.saip.controller;

import java.util.HashMap;
import java.util.Map;

import com.saip.common.Result;
import com.saip.service.MemberService;
import com.saip.service.impl.MemberServiceImpl;

public class StatisticsController {

    private MemberService memberService = new MemberServiceImpl();

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