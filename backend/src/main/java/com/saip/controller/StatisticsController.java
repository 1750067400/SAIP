package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Member;
import com.saip.entity.Event;
import com.saip.entity.Project;
import com.saip.entity.Payment;
import com.saip.entity.Appointment;
import com.saip.entity.Company;
import com.saip.entity.IndustrialPark;
import com.saip.enums.ProjectStatus;
import com.saip.repository.MemberRepository;
import com.saip.repository.EventRepository;
import com.saip.repository.ProjectRepository;
import com.saip.repository.PaymentRepository;
import com.saip.repository.AppointmentRepository;
import com.saip.repository.CompanyRepository;
import com.saip.repository.IndustrialParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计控制器
 */
@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private IndustrialParkRepository industrialParkRepository;
    
    /**
     * 获取仪表盘统计数据
     */
    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboardStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            
            // 会员统计
            long totalMembers = memberRepository.count();
            long activeMembers = memberRepository.countByStatus("ACTIVE");
            statistics.put("totalMembers", totalMembers);
            statistics.put("activeMembers", activeMembers);
            
            // 活动统计
            long totalEvents = eventRepository.count();
            long publishedEvents = eventRepository.countByIsPublishedTrue();
            statistics.put("totalEvents", totalEvents);
            statistics.put("publishedEvents", publishedEvents);
            
            // 项目统计
            long totalProjects = projectRepository.count();
            long activeProjects = projectRepository.countByStatusIn(Arrays.asList(ProjectStatus.PLANNING, ProjectStatus.IN_PROGRESS));
            statistics.put("totalProjects", totalProjects);
            statistics.put("activeProjects", activeProjects);
            
            // 预约统计
            long totalAppointments = appointmentRepository.count();
            long todayAppointments = appointmentRepository.findTodayAppointments(LocalDate.now()).size();
            long pendingAppointments = appointmentRepository.countByStatus("PENDING");
            statistics.put("totalAppointments", totalAppointments);
            statistics.put("todayAppointments", todayAppointments);
            statistics.put("pendingAppointments", pendingAppointments);
            
            // 公司统计
            long totalCompanies = companyRepository.count();
            statistics.put("totalCompanies", totalCompanies);
            
            // 产业园统计
            long totalIndustrialParks = industrialParkRepository.count();
            statistics.put("totalIndustrialParks", totalIndustrialParks);
            
            // 财务统计
            BigDecimal totalRevenue = paymentRepository.sumAmountByStatus("COMPLETED");
            statistics.put("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
            
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计数据失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取会员增长趋势
     */
    @GetMapping("/members/growth")
    public Result<List<Map<String, Object>>> getMemberGrowthTrend() {
        try {
            List<Member> members = memberRepository.findAll();
            Map<String, Long> monthlyStats = members.stream()
                    .filter(member -> member.getCreatedTime() != null)
                    .collect(Collectors.groupingBy(
                            member -> member.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                            Collectors.counting()
                    ));
            
            List<Map<String, Object>> result = monthlyStats.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> monthData = new HashMap<>();
                        monthData.put("month", entry.getKey());
                        monthData.put("count", entry.getValue());
                        return monthData;
                    })
                    .sorted(Comparator.comparing(m -> (String) m.get("month")))
                    .collect(Collectors.toList());
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取会员增长趋势失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取会员行业分布
     */
    @GetMapping("/members/industry")
    public Result<List<Map<String, Object>>> getMemberIndustryDistribution() {
        try {
            List<Member> members = memberRepository.findAll();
            Map<String, Long> industryStats = members.stream()
                    .filter(member -> member.getIndustry() != null)
                    .collect(Collectors.groupingBy(
                            Member::getIndustry,
                            Collectors.counting()
                    ));
            
            List<Map<String, Object>> result = industryStats.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> industryData = new HashMap<>();
                        industryData.put("industry", entry.getKey());
                        industryData.put("count", entry.getValue());
                        return industryData;
                    })
                    .collect(Collectors.toList());
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取会员行业分布失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取活动参与统计
     */
    @GetMapping("/events/participation")
    public Result<List<Map<String, Object>>> getEventParticipationStats() {
        try {
            List<Event> events = eventRepository.findAll();
            List<Map<String, Object>> result = events.stream()
                    .map(event -> {
                        Map<String, Object> eventData = new HashMap<>();
                        eventData.put("eventId", event.getId());
                        eventData.put("title", event.getTitle());
                        eventData.put("maxParticipants", event.getMaxParticipants());
                        eventData.put("currentParticipants", event.getCurrentParticipants());
                        eventData.put("participationRate", event.getMaxParticipants() != null && event.getMaxParticipants() > 0 
                                ? (double) event.getCurrentParticipants() / event.getMaxParticipants() * 100 
                                : 0);
                        return eventData;
                    })
                    .collect(Collectors.toList());
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取活动参与统计失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取项目进度统计
     */
    @GetMapping("/projects/progress")
    public Result<List<Map<String, Object>>> getProjectProgressStats() {
        try {
            List<Project> projects = projectRepository.findAll();
            List<Map<String, Object>> result = projects.stream()
                    .map(project -> {
                        Map<String, Object> projectData = new HashMap<>();
                        projectData.put("projectId", project.getId());
                        projectData.put("name", project.getName());
                        projectData.put("status", project.getStatus());
                        projectData.put("progress", project.getProgress());
                        return projectData;
                    })
                    .collect(Collectors.toList());
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取项目进度统计失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取财务收入统计
     */
    @GetMapping("/finance/revenue")
    public Result<List<Map<String, Object>>> getRevenueStatistics() {
        try {
            List<Payment> payments = paymentRepository.findByStatus("COMPLETED");
            Map<String, BigDecimal> monthlyRevenue = payments.stream()
                    .filter(payment -> payment.getPaymentDate() != null)
                    .collect(Collectors.groupingBy(
                            payment -> payment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                            Collectors.reducing(BigDecimal.ZERO, Payment::getAmount, BigDecimal::add)
                    ));
            
            List<Map<String, Object>> result = monthlyRevenue.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> monthData = new HashMap<>();
                        monthData.put("month", entry.getKey());
                        monthData.put("revenue", entry.getValue());
                        return monthData;
                    })
                    .sorted(Comparator.comparing(m -> (String) m.get("month")))
                    .collect(Collectors.toList());
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取财务收入统计失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取自定义报表数据
     */
    @GetMapping("/custom")
    public Result<Map<String, Object>> getCustomReport(@RequestParam String reportType,
                                                      @RequestParam(required = false) String timeRange) {
        try {
            Map<String, Object> reportData = new HashMap<>();
            
            switch (reportType.toLowerCase()) {
                case "member":
                    reportData.put("data", getMemberReportData(timeRange));
                    break;
                case "event":
                    reportData.put("data", getEventReportData(timeRange));
                    break;
                case "project":
                    reportData.put("data", getProjectReportData(timeRange));
                    break;
                case "finance":
                    reportData.put("data", getFinanceReportData(timeRange));
                    break;
                default:
                    return Result.error("不支持的报表类型");
            }
            
            return Result.success(reportData);
        } catch (Exception e) {
            return Result.error("生成自定义报表失败：" + e.getMessage());
        }
    }
    
    private Map<String, Object> getMemberReportData(String timeRange) {
        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", memberRepository.count());
        data.put("activeCount", memberRepository.countByStatus("ACTIVE"));
        data.put("newThisMonth", memberRepository.countByCreatedTimeAfter(LocalDateTime.now().minusMonths(1)));
        return data;
    }
    
    private Map<String, Object> getEventReportData(String timeRange) {
        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", eventRepository.count());
        data.put("publishedCount", eventRepository.countByIsPublishedTrue());
        data.put("upcomingCount", eventRepository.findUpcomingEvents(LocalDateTime.now(), LocalDateTime.now().plusDays(7)).size());
        return data;
    }
    
    private Map<String, Object> getProjectReportData(String timeRange) {
        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", projectRepository.count());
        data.put("activeCount", projectRepository.countByStatusIn(Arrays.asList(ProjectStatus.PLANNING, ProjectStatus.IN_PROGRESS)));
        data.put("completedCount", projectRepository.countByStatus(ProjectStatus.COMPLETED));
        return data;
    }
    
    private Map<String, Object> getFinanceReportData(String timeRange) {
        Map<String, Object> data = new HashMap<>();
        BigDecimal totalRevenue = paymentRepository.sumAmountByStatus("COMPLETED");
        data.put("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
        BigDecimal monthlyRevenue = paymentRepository.sumAmountByStatusAndPaymentDateAfter("COMPLETED", LocalDateTime.now().minusMonths(1));
        data.put("monthlyRevenue", monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);
        return data;
    }
} 