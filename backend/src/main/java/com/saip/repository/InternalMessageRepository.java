package com.saip.repository;

import com.saip.entity.InternalMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 内部消息Repository接口
 */
@Repository
public interface InternalMessageRepository extends JpaRepository<InternalMessage, Long> {
    
    /**
     * 根据发送者查找消息
     */
    List<InternalMessage> findBySenderId(Long senderId);
    
    /**
     * 根据接收者查找消息
     */
    List<InternalMessage> findByReceiverId(Long receiverId);
    
    /**
     * 根据消息类型查找
     */
    List<InternalMessage> findByMessageType(String messageType);
    
    /**
     * 查找未读消息
     */
    List<InternalMessage> findByIsReadFalse();
    
    /**
     * 查找紧急消息
     */
    List<InternalMessage> findByIsUrgentTrue();
    
    /**
     * 根据优先级查找消息
     */
    List<InternalMessage> findByPriority(String priority);
    
    /**
     * 查找用户收到的消息
     */
    @Query("SELECT m FROM InternalMessage m WHERE m.receiverId = :userId OR m.receiverId IS NULL ORDER BY m.createdTime DESC")
    List<InternalMessage> findMessagesForUser(@Param("userId") Long userId);
    
    /**
     * 查找用户未读的消息
     */
    @Query("SELECT m FROM InternalMessage m WHERE (m.receiverId = :userId OR m.receiverId IS NULL) AND m.isRead = false")
    List<InternalMessage> findUnreadMessagesForUser(@Param("userId") Long userId);
    
    /**
     * 统计用户未读消息数量
     */
    @Query("SELECT COUNT(m) FROM InternalMessage m WHERE (m.receiverId = :userId OR m.receiverId IS NULL) AND m.isRead = false")
    long countUnreadMessagesForUser(@Param("userId") Long userId);
    
    /**
     * 统计紧急消息数量
     */
    long countByIsUrgentTrue();
    
    /**
     * 根据标题搜索消息
     */
    List<InternalMessage> findByTitleContainingIgnoreCase(String keyword);
    
    /**
     * 根据内容搜索消息
     */
    List<InternalMessage> findByContentContainingIgnoreCase(String keyword);
} 