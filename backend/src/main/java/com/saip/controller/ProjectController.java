package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Project;
import com.saip.enums.ProjectStatus;
import com.saip.enums.ProjectType;
import com.saip.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目控制器
 */
@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    /**
     * 获取所有项目
     */
    @GetMapping
    public Result<List<Project>> getAllProjects() {
        try {
            List<Project> projects = projectRepository.findAll();
            return Result.success(projects);
        } catch (Exception e) {
            return Result.error("获取项目列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取项目
     */
    @GetMapping("/{id}")
    public Result<Project> getProjectById(@PathVariable Long id) {
        try {
            return projectRepository.findById(id)
                    .map(Result::success)
                    .orElse(Result.error("项目不存在"));
        } catch (Exception e) {
            return Result.error("获取项目信息失败：" + e.getMessage());
        }
    }
    
    /**
     * 创建项目
     */
    @PostMapping
    public Result<Project> createProject(@RequestBody Project project) {
        try {
            Project savedProject = projectRepository.save(project);
            return Result.success(savedProject);
        } catch (Exception e) {
            return Result.error("创建项目失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新项目
     */
    @PutMapping("/{id}")
    public Result<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        try {
            return projectRepository.findById(id)
                    .map(existingProject -> {
                        existingProject.setName(projectDetails.getName());
                        existingProject.setDescription(projectDetails.getDescription());
                        existingProject.setProjectType(projectDetails.getProjectType());
                        existingProject.setBudget(projectDetails.getBudget());
                        existingProject.setStartDate(projectDetails.getStartDate());
                        existingProject.setEndDate(projectDetails.getEndDate());
                        existingProject.setStatus(projectDetails.getStatus());
                        existingProject.setProgress(projectDetails.getProgress());
                        existingProject.setManagerId(projectDetails.getManagerId());
                        
                        Project updatedProject = projectRepository.save(existingProject);
                        return Result.success(updatedProject);
                    })
                    .orElse(Result.error("项目不存在"));
        } catch (Exception e) {
            return Result.error("更新项目失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除项目
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteProject(@PathVariable Long id) {
        try {
            if (projectRepository.existsById(id)) {
                projectRepository.deleteById(id);
                return Result.success("项目删除成功");
            } else {
                return Result.error("项目不存在");
            }
        } catch (Exception e) {
            return Result.error("删除项目失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据类型获取项目
     */
    @GetMapping("/type/{projectType}")
    public Result<List<Project>> getProjectsByType(@PathVariable ProjectType projectType) {
        try {
            List<Project> projects = projectRepository.findByProjectType(projectType);
            return Result.success(projects);
        } catch (Exception e) {
            return Result.error("获取项目列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据状态获取项目
     */
    @GetMapping("/status/{status}")
    public Result<List<Project>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        try {
            List<Project> projects = projectRepository.findByStatus(status);
            return Result.success(projects);
        } catch (Exception e) {
            return Result.error("获取项目列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据项目经理获取项目
     */
    @GetMapping("/manager/{managerId}")
    public Result<List<Project>> getProjectsByManager(@PathVariable Long managerId) {
        try {
            List<Project> projects = projectRepository.findByManagerId(managerId);
            return Result.success(projects);
        } catch (Exception e) {
            return Result.error("获取项目列表失败：" + e.getMessage());
        }
    }
} 