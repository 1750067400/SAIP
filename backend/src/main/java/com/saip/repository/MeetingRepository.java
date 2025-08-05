package com.saip.repository;

import com.saip.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议Repository接口
 */
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    
    /**
     * 根据组织者查找会议
     */
    List<Meeting> findByOrganizerId(Long organizerId);
    
    /**
     * 根据会议类型查找
     */
    List<Meeting> findByMeetingType(String meetingType);
    
    /**
     * 根据状态查找会议
     */
    List<Meeting> findByStatus(String status);
    
    /**
     * 查找在线会议
     */
    List<Meeting> findByIsOnlineTrue();
    
    /**
     * 查找即将开始的会议
     */
    @Query("SELECT m FROM Meeting m WHERE m.startTime >= :now AND m.startTime <= :endTime ORDER BY m.startTime ASC")
    List<Meeting> findUpcomingMeetings(@Param("now") LocalDateTime now, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查找今天的会议
     */
    @Query("SELECT m FROM Meeting m WHERE DATE(m.startTime) = DATE(:today)")
    List<Meeting> findTodayMeetings(@Param("today") LocalDateTime today);
    
    /**
     * 查找用户参与的会议
     */
    @Query("SELECT m FROM Meeting m WHERE m.participants LIKE %:userId% OR m.organizerId = :userId")
    List<Meeting> findMeetingsByParticipant(@Param("userId") Long userId);
    
    /**
     * 根据标题搜索会议
     */
    List<Meeting> findByTitleContainingIgnoreCase(String keyword);
    
    /**
     * 根据描述搜索会议
     */
    List<Meeting> findByDescriptionContainingIgnoreCase(String keyword);
    
    /**
     * 根据地点搜索会议
     */
    List<Meeting> findByLocationContainingIgnoreCase(String keyword);
    
    /**
     * 统计会议数量
     */
    long countByStatus(String status);
    
    /**
     * 统计在线会议数量
     */
    long countByIsOnlineTrue();
    
    /**
     * 统计今日会议数量
     */
    @Query("SELECT COUNT(m) FROM Meeting m WHERE DATE(m.startTime) = DATE(:today)")
    long countTodayMeetings(@Param("today") LocalDateTime today);
} 