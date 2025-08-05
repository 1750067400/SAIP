package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.InternalMessage;
import com.saip.entity.Meeting;
import com.saip.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内部沟通控制器
 */
@RestController
@RequestMapping("/api/communication")
@CrossOrigin(origins = "*")
public class CommunicationController {
    
    @Autowired
    private CommunicationService communicationService;
    
    // ==================== 内部消息管理 ====================
    
    /**
     * 获取所有内部消息
     */
    @GetMapping("/messages")
    public Result<List<InternalMessage>> getAllMessages() {
        try {
            List<InternalMessage> messages = communicationService.getAllMessages();
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取消息列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取内部消息
     */
    @GetMapping("/messages/{id}")
    public Result<InternalMessage> getMessageById(@PathVariable Long id) {
        try {
            return communicationService.getMessageById(id)
                    .map(Result::success)
                    .orElse(Result.error("消息不存在"));
        } catch (Exception e) {
            return Result.error("获取消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建内部消息
     */
    @PostMapping("/messages")
    public Result<InternalMessage> createMessage(@RequestBody InternalMessage message) {
        try {
            InternalMessage createdMessage = communicationService.createMessage(message);
            return Result.success(createdMessage);
        } catch (Exception e) {
            return Result.error("创建消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新内部消息
     */
    @PutMapping("/messages/{id}")
    public Result<InternalMessage> updateMessage(@PathVariable Long id, @RequestBody InternalMessage message) {
        try {
            InternalMessage updatedMessage = communicationService.updateMessage(id, message);
            if (updatedMessage != null) {
                return Result.success(updatedMessage);
            } else {
                return Result.error("消息不存在");
            }
        } catch (Exception e) {
            return Result.error("更新消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除内部消息
     */
    @DeleteMapping("/messages/{id}")
    public Result<String> deleteMessage(@PathVariable Long id) {
        try {
            boolean deleted = communicationService.deleteMessage(id);
            if (deleted) {
                return Result.success("消息删除成功");
            } else {
                return Result.error("消息不存在");
            }
        } catch (Exception e) {
            return Result.error("删除消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 标记消息为已读
     */
    @PostMapping("/messages/{id}/read")
    public Result<InternalMessage> markMessageAsRead(@PathVariable Long id) {
        try {
            InternalMessage message = communicationService.markMessageAsRead(id);
            if (message != null) {
                return Result.success(message);
            } else {
                return Result.error("消息不存在");
            }
        } catch (Exception e) {
            return Result.error("标记已读失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户收到的消息
     */
    @GetMapping("/messages/user/{userId}")
    public Result<List<InternalMessage>> getMessagesForUser(@PathVariable Long userId) {
        try {
            List<InternalMessage> messages = communicationService.getMessagesForUser(userId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取用户消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户未读的消息
     */
    @GetMapping("/messages/user/{userId}/unread")
    public Result<List<InternalMessage>> getUnreadMessagesForUser(@PathVariable Long userId) {
        try {
            List<InternalMessage> messages = communicationService.getUnreadMessagesForUser(userId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取未读消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户未读消息数量
     */
    @GetMapping("/messages/user/{userId}/unread-count")
    public Result<Long> getUnreadMessageCountForUser(@PathVariable Long userId) {
        try {
            long count = communicationService.getUnreadMessageCountForUser(userId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("获取未读消息数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据发送者获取消息
     */
    @GetMapping("/messages/sender/{senderId}")
    public Result<List<InternalMessage>> getMessagesBySender(@PathVariable Long senderId) {
        try {
            List<InternalMessage> messages = communicationService.getMessagesBySender(senderId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取发送者消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据消息类型获取消息
     */
    @GetMapping("/messages/type/{messageType}")
    public Result<List<InternalMessage>> getMessagesByType(@PathVariable String messageType) {
        try {
            List<InternalMessage> messages = communicationService.getMessagesByType(messageType);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取紧急消息
     */
    @GetMapping("/messages/urgent")
    public Result<List<InternalMessage>> getUrgentMessages() {
        try {
            List<InternalMessage> messages = communicationService.getUrgentMessages();
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取紧急消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索消息
     */
    @GetMapping("/messages/search")
    public Result<List<InternalMessage>> searchMessages(@RequestParam String keyword) {
        try {
            List<InternalMessage> messages = communicationService.searchMessages(keyword);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("搜索消息失败: " + e.getMessage());
        }
    }
    
    // ==================== 会议管理 ====================
    
    /**
     * 获取所有会议
     */
    @GetMapping("/meetings")
    public Result<List<Meeting>> getAllMeetings() {
        try {
            List<Meeting> meetings = communicationService.getAllMeetings();
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取会议列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取会议
     */
    @GetMapping("/meetings/{id}")
    public Result<Meeting> getMeetingById(@PathVariable Long id) {
        try {
            return communicationService.getMeetingById(id)
                    .map(Result::success)
                    .orElse(Result.error("会议不存在"));
        } catch (Exception e) {
            return Result.error("获取会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建会议
     */
    @PostMapping("/meetings")
    public Result<Meeting> createMeeting(@RequestBody Meeting meeting) {
        try {
            Meeting createdMeeting = communicationService.createMeeting(meeting);
            return Result.success(createdMeeting);
        } catch (Exception e) {
            return Result.error("创建会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新会议
     */
    @PutMapping("/meetings/{id}")
    public Result<Meeting> updateMeeting(@PathVariable Long id, @RequestBody Meeting meeting) {
        try {
            Meeting updatedMeeting = communicationService.updateMeeting(id, meeting);
            if (updatedMeeting != null) {
                return Result.success(updatedMeeting);
            } else {
                return Result.error("会议不存在");
            }
        } catch (Exception e) {
            return Result.error("更新会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除会议
     */
    @DeleteMapping("/meetings/{id}")
    public Result<String> deleteMeeting(@PathVariable Long id) {
        try {
            boolean deleted = communicationService.deleteMeeting(id);
            if (deleted) {
                return Result.success("会议删除成功");
            } else {
                return Result.error("会议不存在");
            }
        } catch (Exception e) {
            return Result.error("删除会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取即将开始的会议
     */
    @GetMapping("/meetings/upcoming")
    public Result<List<Meeting>> getUpcomingMeetings(@RequestParam(defaultValue = "7") int days) {
        try {
            LocalDateTime endTime = LocalDateTime.now().plusDays(days);
            List<Meeting> meetings = communicationService.getUpcomingMeetings(endTime);
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取即将开始的会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取今天的会议
     */
    @GetMapping("/meetings/today")
    public Result<List<Meeting>> getTodayMeetings() {
        try {
            List<Meeting> meetings = communicationService.getTodayMeetings();
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取今日会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户参与的会议
     */
    @GetMapping("/meetings/user/{userId}")
    public Result<List<Meeting>> getMeetingsByParticipant(@PathVariable Long userId) {
        try {
            List<Meeting> meetings = communicationService.getMeetingsByParticipant(userId);
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取用户会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据组织者获取会议
     */
    @GetMapping("/meetings/organizer/{organizerId}")
    public Result<List<Meeting>> getMeetingsByOrganizer(@PathVariable Long organizerId) {
        try {
            List<Meeting> meetings = communicationService.getMeetingsByOrganizer(organizerId);
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取组织者会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据会议类型获取会议
     */
    @GetMapping("/meetings/type/{meetingType}")
    public Result<List<Meeting>> getMeetingsByType(@PathVariable String meetingType) {
        try {
            List<Meeting> meetings = communicationService.getMeetingsByType(meetingType);
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据状态获取会议
     */
    @GetMapping("/meetings/status/{status}")
    public Result<List<Meeting>> getMeetingsByStatus(@PathVariable String status) {
        try {
            List<Meeting> meetings = communicationService.getMeetingsByStatus(status);
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取在线会议
     */
    @GetMapping("/meetings/online")
    public Result<List<Meeting>> getOnlineMeetings() {
        try {
            List<Meeting> meetings = communicationService.getOnlineMeetings();
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("获取在线会议失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索会议
     */
    @GetMapping("/meetings/search")
    public Result<List<Meeting>> searchMeetings(@RequestParam String keyword) {
        try {
            List<Meeting> meetings = communicationService.searchMeetings(keyword);
            return Result.success(meetings);
        } catch (Exception e) {
            return Result.error("搜索会议失败: " + e.getMessage());
        }
    }
    
    // ==================== 统计信息 ====================
    
    /**
     * 获取内部沟通统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getCommunicationStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            
            // 消息统计
            statistics.put("totalMessages", communicationService.getAllMessages().size());
            statistics.put("urgentMessages", communicationService.getUrgentMessageCount());
            
            // 会议统计
            statistics.put("totalMeetings", communicationService.getMeetingCount());
            statistics.put("todayMeetings", communicationService.getTodayMeetingCount());
            statistics.put("onlineMeetings", communicationService.getOnlineMeetingCount());
            statistics.put("upcomingMeetings", communicationService.getUpcomingMeetings(LocalDateTime.now().plusDays(7)).size());
            
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }
} 