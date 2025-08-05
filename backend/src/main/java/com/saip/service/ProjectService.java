package com.saip.service;

import com.saip.entity.Project;
import com.saip.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 项目服务类
 */
@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    /**
     * 获取所有项目
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    /**
     * 根据ID获取项目
     */
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    /**
     * 创建项目
     */
    public Project createProject(Project project) {
        project.setCreatedTime(LocalDateTime.now());
        project.setUpdatedTime(LocalDateTime.now());
        if (project.getProgress() == null) {
            project.setProgress(0);
        }
        return projectRepository.save(project);
    }
    
    /**
     * 更新项目
     */
    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setProjectType(projectDetails.getProjectType());
            project.setBudget(projectDetails.getBudget());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            project.setStatus(projectDetails.getStatus());
            project.setProgress(projectDetails.getProgress());
            project.setManagerId(projectDetails.getManagerId());
            project.setUpdatedTime(LocalDateTime.now());
            return projectRepository.save(project);
        }
        return null;
    }
    
    /**
     * 删除项目
     */
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 更新项目进度
     */
    public Project updateProjectProgress(Long id, int progress) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setProgress(progress);
            project.setUpdatedTime(LocalDateTime.now());
            return projectRepository.save(project);
        }
        return null;
    }
    
    /**
     * 根据项目类型获取项目
     */
    public List<Project> getProjectsByType(String projectType) {
        return projectRepository.findByProjectType(com.saip.enums.ProjectType.valueOf(projectType));
    }
    
    /**
     * 根据状态获取项目
     */
    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(com.saip.enums.ProjectStatus.valueOf(status));
    }
    
    /**
     * 根据项目经理获取项目
     */
    public List<Project> getProjectsByManager(Long managerId) {
        return projectRepository.findByManagerId(managerId);
    }
    
    /**
     * 根据创建者获取项目
     */
    public List<Project> getProjectsByCreator(Long createdBy) {
        return projectRepository.findByCreatedBy(createdBy);
    }
    
    /**
     * 搜索项目
     */
    public List<Project> searchProjects(String keyword) {
        return projectRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    /**
     * 获取项目统计
     */
    public long getProjectCount() {
        return projectRepository.count();
    }
    
    /**
     * 根据状态统计项目数量
     */
    public long getProjectCountByStatus(String status) {
        return projectRepository.countByStatus(com.saip.enums.ProjectStatus.valueOf(status));
    }
    
    /**
     * 获取进行中的项目数量
     */
    public long getInProgressProjectCount() {
        return projectRepository.countByStatus(com.saip.enums.ProjectStatus.IN_PROGRESS);
    }
    
    /**
     * 获取已完成的项目数量
     */
    public long getCompletedProjectCount() {
        return projectRepository.countByStatus(com.saip.enums.ProjectStatus.COMPLETED);
    }
    
    /**
     * 获取计划中的项目数量
     */
    public long getPlannedProjectCount() {
        return projectRepository.countByStatus(com.saip.enums.ProjectStatus.PLANNING);
    }
} 