package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.News;
import com.saip.entity.Policy;
import com.saip.service.NewsService;
import com.saip.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部沟通控制器（新闻和政策管理）
 */
@RestController
@RequestMapping("/api/external-communication")
@CrossOrigin(origins = "*")
public class ExternalCommunicationController {
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private PolicyService policyService;
    
    // ==================== 新闻管理 ====================
    
    /**
     * 获取所有新闻
     */
    @GetMapping("/news")
    public Result<List<News>> getAllNews() {
        try {
            List<News> news = newsService.getAllNews();
            return Result.success(news);
        } catch (Exception e) {
            return Result.error("获取新闻列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取新闻
     */
    @GetMapping("/news/{id}")
    public Result<News> getNewsById(@PathVariable Long id) {
        try {
            return newsService.getNewsById(id)
                    .map(Result::success)
                    .orElse(Result.error("新闻不存在"));
        } catch (Exception e) {
            return Result.error("获取新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建新闻
     */
    @PostMapping("/news")
    public Result<News> createNews(@RequestBody News news) {
        try {
            News createdNews = newsService.createNews(news);
            return Result.success(createdNews);
        } catch (Exception e) {
            return Result.error("创建新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新新闻
     */
    @PutMapping("/news/{id}")
    public Result<News> updateNews(@PathVariable Long id, @RequestBody News news) {
        try {
            News updatedNews = newsService.updateNews(id, news);
            if (updatedNews != null) {
                return Result.success(updatedNews);
            } else {
                return Result.error("新闻不存在");
            }
        } catch (Exception e) {
            return Result.error("更新新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除新闻
     */
    @DeleteMapping("/news/{id}")
    public Result<String> deleteNews(@PathVariable Long id) {
        try {
            boolean deleted = newsService.deleteNews(id);
            if (deleted) {
                return Result.success("新闻删除成功");
            } else {
                return Result.error("新闻不存在");
            }
        } catch (Exception e) {
            return Result.error("删除新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 发布新闻
     */
    @PostMapping("/news/{id}/publish")
    public Result<News> publishNews(@PathVariable Long id) {
        try {
            News publishedNews = newsService.publishNews(id);
            if (publishedNews != null) {
                return Result.success(publishedNews);
            } else {
                return Result.error("新闻不存在");
            }
        } catch (Exception e) {
            return Result.error("发布新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 取消发布新闻
     */
    @PostMapping("/news/{id}/unpublish")
    public Result<News> unpublishNews(@PathVariable Long id) {
        try {
            News unpublishedNews = newsService.unpublishNews(id);
            if (unpublishedNews != null) {
                return Result.success(unpublishedNews);
            } else {
                return Result.error("新闻不存在");
            }
        } catch (Exception e) {
            return Result.error("取消发布新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 增加新闻浏览量
     */
    @PostMapping("/news/{id}/view")
    public Result<News> incrementNewsViewCount(@PathVariable Long id) {
        try {
            News news = newsService.incrementViewCount(id);
            if (news != null) {
                return Result.success(news);
            } else {
                return Result.error("新闻不存在");
            }
        } catch (Exception e) {
            return Result.error("增加浏览量失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据分类获取新闻
     */
    @GetMapping("/news/category/{category}")
    public Result<List<News>> getNewsByCategory(@PathVariable String category) {
        try {
            List<News> news = newsService.getNewsByCategory(category);
            return Result.success(news);
        } catch (Exception e) {
            return Result.error("获取新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取已发布的新闻
     */
    @GetMapping("/news/published")
    public Result<List<News>> getPublishedNews() {
        try {
            List<News> news = newsService.getPublishedNews();
            return Result.success(news);
        } catch (Exception e) {
            return Result.error("获取新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取热门新闻
     */
    @GetMapping("/news/popular")
    public Result<List<News>> getPopularNews() {
        try {
            List<News> news = newsService.getPopularNews();
            return Result.success(news);
        } catch (Exception e) {
            return Result.error("获取热门新闻失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索新闻
     */
    @GetMapping("/news/search")
    public Result<List<News>> searchNews(@RequestParam String keyword) {
        try {
            List<News> news = newsService.searchNews(keyword);
            return Result.success(news);
        } catch (Exception e) {
            return Result.error("搜索新闻失败: " + e.getMessage());
        }
    }
    
    // ==================== 政策管理 ====================
    
    /**
     * 获取所有政策
     */
    @GetMapping("/policies")
    public Result<List<Policy>> getAllPolicies() {
        try {
            List<Policy> policies = policyService.getAllPolicies();
            return Result.success(policies);
        } catch (Exception e) {
            return Result.error("获取政策列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取政策
     */
    @GetMapping("/policies/{id}")
    public Result<Policy> getPolicyById(@PathVariable Long id) {
        try {
            return policyService.getPolicyById(id)
                    .map(Result::success)
                    .orElse(Result.error("政策不存在"));
        } catch (Exception e) {
            return Result.error("获取政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建政策
     */
    @PostMapping("/policies")
    public Result<Policy> createPolicy(@RequestBody Policy policy) {
        try {
            Policy createdPolicy = policyService.createPolicy(policy);
            return Result.success(createdPolicy);
        } catch (Exception e) {
            return Result.error("创建政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新政策
     */
    @PutMapping("/policies/{id}")
    public Result<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy policy) {
        try {
            Policy updatedPolicy = policyService.updatePolicy(id, policy);
            if (updatedPolicy != null) {
                return Result.success(updatedPolicy);
            } else {
                return Result.error("政策不存在");
            }
        } catch (Exception e) {
            return Result.error("更新政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除政策
     */
    @DeleteMapping("/policies/{id}")
    public Result<String> deletePolicy(@PathVariable Long id) {
        try {
            boolean deleted = policyService.deletePolicy(id);
            if (deleted) {
                return Result.success("政策删除成功");
            } else {
                return Result.error("政策不存在");
            }
        } catch (Exception e) {
            return Result.error("删除政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 发布政策
     */
    @PostMapping("/policies/{id}/publish")
    public Result<Policy> publishPolicy(@PathVariable Long id) {
        try {
            Policy publishedPolicy = policyService.publishPolicy(id);
            if (publishedPolicy != null) {
                return Result.success(publishedPolicy);
            } else {
                return Result.error("政策不存在");
            }
        } catch (Exception e) {
            return Result.error("发布政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取已发布的政策
     */
    @GetMapping("/policies/published")
    public Result<List<Policy>> getPublishedPolicies() {
        try {
            List<Policy> policies = policyService.getPublishedPolicies();
            return Result.success(policies);
        } catch (Exception e) {
            return Result.error("获取政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取紧急政策
     */
    @GetMapping("/policies/urgent")
    public Result<List<Policy>> getUrgentPolicies() {
        try {
            List<Policy> policies = policyService.getUrgentPolicies();
            return Result.success(policies);
        } catch (Exception e) {
            return Result.error("获取紧急政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取有效政策
     */
    @GetMapping("/policies/valid")
    public Result<List<Policy>> getValidPolicies() {
        try {
            List<Policy> policies = policyService.getValidPolicies();
            return Result.success(policies);
        } catch (Exception e) {
            return Result.error("获取有效政策失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索政策
     */
    @GetMapping("/policies/search")
    public Result<List<Policy>> searchPolicies(@RequestParam String keyword) {
        try {
            List<Policy> policies = policyService.searchPolicies(keyword);
            return Result.success(policies);
        } catch (Exception e) {
            return Result.error("搜索政策失败: " + e.getMessage());
        }
    }
    
    // ==================== 统计信息 ====================
    
    /**
     * 获取外部沟通统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getExternalCommunicationStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            
            // 新闻统计
            statistics.put("totalNews", newsService.getNewsCount());
            statistics.put("publishedNews", newsService.getPublishedNewsCount());
            statistics.put("recentNews", newsService.getRecentPublishedNews(LocalDateTime.now().minusDays(7)).size());
            
            // 政策统计
            statistics.put("totalPolicies", policyService.getPolicyCount());
            statistics.put("publishedPolicies", policyService.getPublishedPolicyCount());
            statistics.put("urgentPolicies", policyService.getUrgentPolicyCount());
            statistics.put("validPolicies", policyService.getValidPolicies().size());
            
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败: " + e.getMessage());
        }
    }
} 