package com.saip.repository;

import com.saip.entity.Project;
import com.saip.enums.ProjectStatus;
import com.saip.enums.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目Repository接口
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * 根据项目类型查找
     */
    List<Project> findByProjectType(ProjectType projectType);
    
    /**
     * 根据项目状态查找
     */
    List<Project> findByStatus(ProjectStatus status);
    
    /**
     * 根据项目经理查找
     */
    List<Project> findByManagerId(Long managerId);
    
    /**
     * 根据创建者查找
     */
    List<Project> findByCreatedBy(Long createdBy);
    
    /**
     * 查找进行中的项目
     */
    List<Project> findByStatusIn(List<ProjectStatus> statuses);
    
    /**
     * 根据名称模糊查找
     */
    List<Project> findByNameContainingIgnoreCase(String keyword);
    
    /**
     * 查找进度大于指定值的项目
     */
    @Query("SELECT p FROM Project p WHERE p.progress >= :minProgress")
    List<Project> findByProgressGreaterThanEqual(@Param("minProgress") Integer minProgress);
    
    /**
     * 根据状态统计数量
     */
    long countByStatus(ProjectStatus status);
    
    /**
     * 根据状态列表统计数量
     */
    long countByStatusIn(List<ProjectStatus> statuses);
} 