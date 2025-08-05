package com.saip.service;

import com.saip.entity.Event;
import com.saip.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 活动服务类
 */
@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    /**
     * 获取所有活动
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    /**
     * 根据ID获取活动
     */
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    /**
     * 创建活动
     */
    public Event createEvent(Event event) {
        event.setCreatedTime(LocalDateTime.now());
        event.setUpdatedTime(LocalDateTime.now());
        if (event.getCurrentParticipants() == null) {
            event.setCurrentParticipants(0);
        }
        if (event.getIsPublished() == null) {
            event.setIsPublished(false);
        }
        return eventRepository.save(event);
    }
    
    /**
     * 更新活动
     */
    public Event updateEvent(Long id, Event eventDetails) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setTitle(eventDetails.getTitle());
            event.setDescription(eventDetails.getDescription());
            event.setEventType(eventDetails.getEventType());
            event.setStartTime(eventDetails.getStartTime());
            event.setEndTime(eventDetails.getEndTime());
            event.setLocation(eventDetails.getLocation());
            event.setMaxParticipants(eventDetails.getMaxParticipants());
            event.setIsPublished(eventDetails.getIsPublished());
            event.setUpdatedTime(LocalDateTime.now());
            return eventRepository.save(event);
        }
        return null;
    }
    
    /**
     * 删除活动
     */
    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 发布活动
     */
    public Event publishEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setIsPublished(true);
            event.setUpdatedTime(LocalDateTime.now());
            return eventRepository.save(event);
        }
        return null;
    }
    
    /**
     * 取消发布活动
     */
    public Event unpublishEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setIsPublished(false);
            event.setUpdatedTime(LocalDateTime.now());
            return eventRepository.save(event);
        }
        return null;
    }
    
    /**
     * 增加参与人数
     */
    public Event incrementParticipants(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            if (event.getMaxParticipants() == null || 
                event.getCurrentParticipants() < event.getMaxParticipants()) {
                event.setCurrentParticipants(event.getCurrentParticipants() + 1);
                event.setUpdatedTime(LocalDateTime.now());
                return eventRepository.save(event);
            }
        }
        return null;
    }
    
    /**
     * 减少参与人数
     */
    public Event decrementParticipants(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            if (event.getCurrentParticipants() > 0) {
                event.setCurrentParticipants(event.getCurrentParticipants() - 1);
                event.setUpdatedTime(LocalDateTime.now());
                return eventRepository.save(event);
            }
        }
        return null;
    }
    
    /**
     * 根据活动类型获取活动
     */
    public List<Event> getEventsByType(String eventType) {
        return eventRepository.findByEventType(com.saip.enums.EventType.valueOf(eventType));
    }
    
    /**
     * 获取已发布的活动
     */
    public List<Event> getPublishedEvents() {
        return eventRepository.findByIsPublishedTrue();
    }
    
    /**
     * 获取即将开始的活动
     */
    public List<Event> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plusMonths(1);
        return eventRepository.findUpcomingEvents(now, endTime);
    }
    
    /**
     * 根据创建者获取活动
     */
    public List<Event> getEventsByCreator(Long createdBy) {
        return eventRepository.findByCreatedBy(createdBy);
    }
    
    /**
     * 搜索活动
     */
    public List<Event> searchEvents(String keyword) {
        return eventRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    /**
     * 获取活动统计
     */
    public long getEventCount() {
        return eventRepository.count();
    }
    
    /**
     * 获取已发布活动数量
     */
    public long getPublishedEventCount() {
        return eventRepository.countByIsPublishedTrue();
    }
    
    /**
     * 获取即将开始的活动数量
     */
    public long getUpcomingEventCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plusMonths(1);
        return eventRepository.findUpcomingEvents(now, endTime).size();
    }
} 