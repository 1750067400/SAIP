package com.saip.repository;

import com.saip.entity.Event;
import com.saip.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动Repository接口
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    /**
     * 根据活动类型查找
     */
    List<Event> findByEventType(EventType eventType);
    
    /**
     * 查找已发布的活动
     */
    List<Event> findByIsPublishedTrue();
    
    /**
     * 查找指定时间范围内的活动
     */
    @Query("SELECT e FROM Event e WHERE e.startTime BETWEEN :startDate AND :endDate")
    List<Event> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                @Param("endDate") LocalDateTime endDate);
    
    /**
     * 查找即将开始的活动（未来7天内）
     */
    @Query("SELECT e FROM Event e WHERE e.startTime BETWEEN :now AND :futureDate AND e.isPublished = true")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now, 
                                   @Param("futureDate") LocalDateTime futureDate);
    
    /**
     * 根据创建者查找活动
     */
    List<Event> findByCreatedBy(Long createdBy);
    
    /**
     * 查找标题包含关键词的活动
     */
    List<Event> findByTitleContainingIgnoreCase(String keyword);
    
    /**
     * 统计已发布的活动数量
     */
    long countByIsPublishedTrue();
} 