package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Event;
import com.saip.enums.EventType;
import com.saip.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动控制器
 */
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {
    
    @Autowired
    private EventRepository eventRepository;
    
    /**
     * 获取所有活动
     */
    @GetMapping
    public Result<List<Event>> getAllEvents() {
        try {
            List<Event> events = eventRepository.findAll();
            return Result.success(events);
        } catch (Exception e) {
            return Result.error("获取活动列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取活动
     */
    @GetMapping("/{id}")
    public Result<Event> getEventById(@PathVariable Long id) {
        try {
            return eventRepository.findById(id)
                    .map(Result::success)
                    .orElse(Result.error("活动不存在"));
        } catch (Exception e) {
            return Result.error("获取活动信息失败：" + e.getMessage());
        }
    }
    
    /**
     * 创建活动
     */
    @PostMapping
    public Result<Event> createEvent(@RequestBody Event event) {
        try {
            Event savedEvent = eventRepository.save(event);
            return Result.success(savedEvent);
        } catch (Exception e) {
            return Result.error("创建活动失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新活动
     */
    @PutMapping("/{id}")
    public Result<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        try {
            return eventRepository.findById(id)
                    .map(existingEvent -> {
                        existingEvent.setTitle(eventDetails.getTitle());
                        existingEvent.setDescription(eventDetails.getDescription());
                        existingEvent.setEventType(eventDetails.getEventType());
                        existingEvent.setStartTime(eventDetails.getStartTime());
                        existingEvent.setEndTime(eventDetails.getEndTime());
                        existingEvent.setLocation(eventDetails.getLocation());
                        existingEvent.setMaxParticipants(eventDetails.getMaxParticipants());
                        existingEvent.setIsPublished(eventDetails.getIsPublished());
                        
                        Event updatedEvent = eventRepository.save(existingEvent);
                        return Result.success(updatedEvent);
                    })
                    .orElse(Result.error("活动不存在"));
        } catch (Exception e) {
            return Result.error("更新活动失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除活动
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteEvent(@PathVariable Long id) {
        try {
            if (eventRepository.existsById(id)) {
                eventRepository.deleteById(id);
                return Result.success("活动删除成功");
            } else {
                return Result.error("活动不存在");
            }
        } catch (Exception e) {
            return Result.error("删除活动失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据类型获取活动
     */
    @GetMapping("/type/{eventType}")
    public Result<List<Event>> getEventsByType(@PathVariable EventType eventType) {
        try {
            List<Event> events = eventRepository.findByEventType(eventType);
            return Result.success(events);
        } catch (Exception e) {
            return Result.error("获取活动列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取已发布的活动
     */
    @GetMapping("/published")
    public Result<List<Event>> getPublishedEvents() {
        try {
            List<Event> events = eventRepository.findByIsPublishedTrue();
            return Result.success(events);
        } catch (Exception e) {
            return Result.error("获取已发布活动失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取即将开始的活动
     */
    @GetMapping("/upcoming")
    public Result<List<Event>> getUpcomingEvents() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime futureDate = now.plusDays(7);
            List<Event> events = eventRepository.findUpcomingEvents(now, futureDate);
            return Result.success(events);
        } catch (Exception e) {
            return Result.error("获取即将开始活动失败：" + e.getMessage());
        }
    }
} 