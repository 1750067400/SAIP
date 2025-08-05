package com.saip.service;

import com.saip.entity.News;
import com.saip.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 新闻服务类
 */
@Service
public class NewsService {
    
    @Autowired
    private NewsRepository newsRepository;
    
    /**
     * 获取所有新闻
     */
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
    
    /**
     * 根据ID获取新闻
     */
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }
    
    /**
     * 创建新闻
     */
    public News createNews(News news) {
        news.setCreatedTime(LocalDateTime.now());
        news.setUpdatedTime(LocalDateTime.now());
        if (news.getIsPublished() == null) {
            news.setIsPublished(false);
        }
        if (news.getViewCount() == null) {
            news.setViewCount(0);
        }
        return newsRepository.save(news);
    }
    
    /**
     * 更新新闻
     */
    public News updateNews(Long id, News newsDetails) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            news.setTitle(newsDetails.getTitle());
            news.setContent(newsDetails.getContent());
            news.setSummary(newsDetails.getSummary());
            news.setAuthor(newsDetails.getAuthor());
            news.setCategory(newsDetails.getCategory());
            news.setCoverImage(newsDetails.getCoverImage());
            news.setIsPublished(newsDetails.getIsPublished());
            news.setUpdatedTime(LocalDateTime.now());
            
            // 如果发布状态改变为已发布，设置发布时间
            if (newsDetails.getIsPublished() && !news.getIsPublished()) {
                news.setPublishedTime(LocalDateTime.now());
            }
            
            return newsRepository.save(news);
        }
        return null;
    }
    
    /**
     * 删除新闻
     */
    public boolean deleteNews(Long id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 发布新闻
     */
    public News publishNews(Long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            news.setIsPublished(true);
            news.setPublishedTime(LocalDateTime.now());
            news.setUpdatedTime(LocalDateTime.now());
            return newsRepository.save(news);
        }
        return null;
    }
    
    /**
     * 取消发布新闻
     */
    public News unpublishNews(Long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            news.setIsPublished(false);
            news.setUpdatedTime(LocalDateTime.now());
            return newsRepository.save(news);
        }
        return null;
    }
    
    /**
     * 增加浏览量
     */
    public News incrementViewCount(Long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            news.setViewCount(news.getViewCount() + 1);
            return newsRepository.save(news);
        }
        return null;
    }
    
    /**
     * 根据分类获取新闻
     */
    public List<News> getNewsByCategory(String category) {
        return newsRepository.findByCategory(category);
    }
    
    /**
     * 获取已发布的新闻
     */
    public List<News> getPublishedNews() {
        return newsRepository.findByIsPublishedTrue();
    }
    
    /**
     * 根据作者获取新闻
     */
    public List<News> getNewsByAuthor(String author) {
        return newsRepository.findByAuthor(author);
    }
    
    /**
     * 搜索新闻
     */
    public List<News> searchNews(String keyword) {
        return newsRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    /**
     * 获取最近发布的新闻
     */
    public List<News> getRecentPublishedNews(LocalDateTime since) {
        return newsRepository.findRecentPublishedNews(since);
    }
    
    /**
     * 获取热门新闻
     */
    public List<News> getPopularNews() {
        return newsRepository.findPopularNews();
    }
    
    /**
     * 获取新闻统计
     */
    public long getNewsCount() {
        return newsRepository.count();
    }
    
    /**
     * 获取已发布新闻数量
     */
    public long getPublishedNewsCount() {
        return newsRepository.countByIsPublishedTrue();
    }
    
    /**
     * 获取分类新闻数量
     */
    public long getNewsCountByCategory(String category) {
        return newsRepository.countByCategory(category);
    }
} 