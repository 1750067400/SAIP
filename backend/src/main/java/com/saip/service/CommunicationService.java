package com.saip.service;

import com.saip.entity.InternalMessage;
import com.saip.entity.Meeting;
import com.saip.repository.InternalMessageRepository;
import com.saip.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 沟通服务类（内部消息和会议管理）
 */
@Service
public class CommunicationService {
    
    @Autowired
    private InternalMessageRepository internalMessageRepository;
    
    @Autowired
    private MeetingRepository meetingRepository;
    
    // ==================== 内部消息管理 ====================
    
    /**
     * 获取所有内部消息
     */
    public List<InternalMessage> getAllMessages() {
        return internalMessageRepository.findAll();
    }
    
    /**
     * 根据ID获取内部消息
     */
    public Optional<InternalMessage> getMessageById(Long id) {
        return internalMessageRepository.findById(id);
    }
    
    /**
     * 创建内部消息
     */
    public InternalMessage createMessage(InternalMessage message) {
        message.setCreatedTime(LocalDateTime.now());
        if (message.getIsRead() == null) {
            message.setIsRead(false);
        }
        if (message.getIsUrgent() == null) {
            message.setIsUrgent(false);
        }
        if (message.getPriority() == null) {
            message.setPriority("NORMAL");
        }
        return internalMessageRepository.save(message);
    }
    
    /**
     * 更新内部消息
     */
    public InternalMessage updateMessage(Long id, InternalMessage messageDetails) {
        Optional<InternalMessage> optionalMessage = internalMessageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            InternalMessage message = optionalMessage.get();
            message.setTitle(messageDetails.getTitle());
            message.setContent(messageDetails.getContent());
            message.setReceiverId(messageDetails.getReceiverId());
            message.setMessageType(messageDetails.getMessageType());
            message.setPriority(messageDetails.getPriority());
            message.setIsUrgent(messageDetails.getIsUrgent());
            return internalMessageRepository.save(message);
        }
        return null;
    }
    
    /**
     * 删除内部消息
     */
    public boolean deleteMessage(Long id) {
        if (internalMessageRepository.existsById(id)) {
            internalMessageRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 标记消息为已读
     */
    public InternalMessage markMessageAsRead(Long id) {
        Optional<InternalMessage> optionalMessage = internalMessageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            InternalMessage message = optionalMessage.get();
            message.setIsRead(true);
            message.setReadTime(LocalDateTime.now());
            return internalMessageRepository.save(message);
        }
        return null;
    }
    
    /**
     * 获取用户收到的消息
     */
    public List<InternalMessage> getMessagesForUser(Long userId) {
        return internalMessageRepository.findMessagesForUser(userId);
    }
    
    /**
     * 获取用户未读的消息
     */
    public List<InternalMessage> getUnreadMessagesForUser(Long userId) {
        return internalMessageRepository.findUnreadMessagesForUser(userId);
    }
    
    /**
     * 获取用户未读消息数量
     */
    public long getUnreadMessageCountForUser(Long userId) {
        return internalMessageRepository.countUnreadMessagesForUser(userId);
    }
    
    /**
     * 根据发送者获取消息
     */
    public List<InternalMessage> getMessagesBySender(Long senderId) {
        return internalMessageRepository.findBySenderId(senderId);
    }
    
    /**
     * 根据消息类型获取消息
     */
    public List<InternalMessage> getMessagesByType(String messageType) {
        return internalMessageRepository.findByMessageType(messageType);
    }
    
    /**
     * 获取紧急消息
     */
    public List<InternalMessage> getUrgentMessages() {
        return internalMessageRepository.findByIsUrgentTrue();
    }
    
    /**
     * 搜索消息
     */
    public List<InternalMessage> searchMessages(String keyword) {
        return internalMessageRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    // ==================== 会议管理 ====================
    
    /**
     * 获取所有会议
     */
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }
    
    /**
     * 根据ID获取会议
     */
    public Optional<Meeting> getMeetingById(Long id) {
        return meetingRepository.findById(id);
    }
    
    /**
     * 创建会议
     */
    public Meeting createMeeting(Meeting meeting) {
        meeting.setCreatedTime(LocalDateTime.now());
        meeting.setUpdatedTime(LocalDateTime.now());
        if (meeting.getStatus() == null) {
            meeting.setStatus("SCHEDULED");
        }
        if (meeting.getIsOnline() == null) {
            meeting.setIsOnline(false);
        }
        return meetingRepository.save(meeting);
    }
    
    /**
     * 更新会议
     */
    public Meeting updateMeeting(Long id, Meeting meetingDetails) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(id);
        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
            meeting.setTitle(meetingDetails.getTitle());
            meeting.setDescription(meetingDetails.getDescription());
            meeting.setMeetingType(meetingDetails.getMeetingType());
            meeting.setStartTime(meetingDetails.getStartTime());
            meeting.setEndTime(meetingDetails.getEndTime());
            meeting.setLocation(meetingDetails.getLocation());
            meeting.setParticipants(meetingDetails.getParticipants());
            meeting.setAgenda(meetingDetails.getAgenda());
            meeting.setStatus(meetingDetails.getStatus());
            meeting.setIsOnline(meetingDetails.getIsOnline());
            meeting.setMeetingLink(meetingDetails.getMeetingLink());
            meeting.setUpdatedTime(LocalDateTime.now());
            return meetingRepository.save(meeting);
        }
        return null;
    }
    
    /**
     * 删除会议
     */
    public boolean deleteMeeting(Long id) {
        if (meetingRepository.existsById(id)) {
            meetingRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 获取即将开始的会议
     */
    public List<Meeting> getUpcomingMeetings(LocalDateTime endTime) {
        return meetingRepository.findUpcomingMeetings(LocalDateTime.now(), endTime);
    }
    
    /**
     * 获取今天的会议
     */
    public List<Meeting> getTodayMeetings() {
        return meetingRepository.findTodayMeetings(LocalDateTime.now());
    }
    
    /**
     * 获取用户参与的会议
     */
    public List<Meeting> getMeetingsByParticipant(Long userId) {
        return meetingRepository.findMeetingsByParticipant(userId);
    }
    
    /**
     * 根据组织者获取会议
     */
    public List<Meeting> getMeetingsByOrganizer(Long organizerId) {
        return meetingRepository.findByOrganizerId(organizerId);
    }
    
    /**
     * 根据会议类型获取会议
     */
    public List<Meeting> getMeetingsByType(String meetingType) {
        return meetingRepository.findByMeetingType(meetingType);
    }
    
    /**
     * 根据状态获取会议
     */
    public List<Meeting> getMeetingsByStatus(String status) {
        return meetingRepository.findByStatus(status);
    }
    
    /**
     * 获取在线会议
     */
    public List<Meeting> getOnlineMeetings() {
        return meetingRepository.findByIsOnlineTrue();
    }
    
    /**
     * 搜索会议
     */
    public List<Meeting> searchMeetings(String keyword) {
        return meetingRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    /**
     * 获取会议统计
     */
    public long getMeetingCount() {
        return meetingRepository.count();
    }
    
    /**
     * 获取今日会议数量
     */
    public long getTodayMeetingCount() {
        return meetingRepository.countTodayMeetings(LocalDateTime.now());
    }
    
    /**
     * 获取在线会议数量
     */
    public long getOnlineMeetingCount() {
        return meetingRepository.countByIsOnlineTrue();
    }
    
    /**
     * 获取紧急消息数量
     */
    public long getUrgentMessageCount() {
        return internalMessageRepository.countByIsUrgentTrue();
    }
} 