package com.saip.repository;

import com.saip.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 新闻Repository接口
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    
    /**
     * 根据分类查找新闻
     */
    List<News> findByCategory(String category);
    
    /**
     * 查找已发布的新闻
     */
    List<News> findByIsPublishedTrue();
    
    /**
     * 根据作者查找新闻
     */
    List<News> findByAuthor(String author);
    
    /**
     * 根据标题搜索新闻
     */
    List<News> findByTitleContainingIgnoreCase(String keyword);
    
    /**
     * 根据内容搜索新闻
     */
    List<News> findByContentContainingIgnoreCase(String keyword);
    
    /**
     * 查找最近发布的新闻
     */
    @Query("SELECT n FROM News n WHERE n.isPublished = true AND n.publishedTime >= :since ORDER BY n.publishedTime DESC")
    List<News> findRecentPublishedNews(@Param("since") LocalDateTime since);
    
    /**
     * 统计已发布的新闻数量
     */
    long countByIsPublishedTrue();
    
    /**
     * 统计分类下的新闻数量
     */
    long countByCategory(String category);
    
    /**
     * 查找热门新闻（按浏览量排序）
     */
    @Query("SELECT n FROM News n WHERE n.isPublished = true ORDER BY n.viewCount DESC")
    List<News> findPopularNews();
} 