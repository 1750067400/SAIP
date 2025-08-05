package com.saip.controller;

import com.saip.common.Result;
import com.saip.entity.Document;
import com.saip.enums.DocumentType;
import com.saip.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * 文档控制器
 */
@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    private static final String UPLOAD_DIR = "uploads/documents/";
    
    /**
     * 获取所有文档
     */
    @GetMapping
    public Result<List<Document>> getAllDocuments() {
        try {
            List<Document> documents = documentRepository.findAll();
            return Result.success(documents);
        } catch (Exception e) {
            return Result.error("获取文档列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取文档
     */
    @GetMapping("/{id}")
    public Result<Document> getDocumentById(@PathVariable Long id) {
        try {
            return documentRepository.findById(id)
                    .map(Result::success)
                    .orElse(Result.error("文档不存在"));
        } catch (Exception e) {
            return Result.error("获取文档信息失败：" + e.getMessage());
        }
    }
    
    /**
     * 上传文档
     */
    @PostMapping("/upload")
    public Result<Document> uploadDocument(@RequestParam("file") MultipartFile file,
                                         @RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("documentType") DocumentType documentType,
                                         @RequestParam("isPublic") Boolean isPublic,
                                         @RequestParam("uploadedBy") Long uploadedBy) {
        try {
            // 创建上传目录
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileExtension;
            String filePath = UPLOAD_DIR + fileName;
            
            // 保存文件
            Path targetPath = Paths.get(filePath);
            Files.copy(file.getInputStream(), targetPath);
            
            // 创建文档记录
            Document document = new Document();
            document.setTitle(title);
            document.setDescription(description);
            document.setFileName(originalFilename);
            document.setFilePath(filePath);
            document.setFileSize(file.getSize());
            document.setFileType(file.getContentType());
            document.setDocumentType(documentType);
            document.setIsPublic(isPublic);
            document.setUploadedBy(uploadedBy);
            
            Document savedDocument = documentRepository.save(document);
            return Result.success(savedDocument);
        } catch (IOException e) {
            return Result.error("文件上传失败：" + e.getMessage());
        } catch (Exception e) {
            return Result.error("创建文档记录失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新文档信息
     */
    @PutMapping("/{id}")
    public Result<Document> updateDocument(@PathVariable Long id, @RequestBody Document documentDetails) {
        try {
            return documentRepository.findById(id)
                    .map(existingDocument -> {
                        existingDocument.setTitle(documentDetails.getTitle());
                        existingDocument.setDescription(documentDetails.getDescription());
                        existingDocument.setDocumentType(documentDetails.getDocumentType());
                        existingDocument.setIsPublic(documentDetails.getIsPublic());
                        existingDocument.setVersion(documentDetails.getVersion());
                        
                        Document updatedDocument = documentRepository.save(existingDocument);
                        return Result.success(updatedDocument);
                    })
                    .orElse(Result.error("文档不存在"));
        } catch (Exception e) {
            return Result.error("更新文档失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除文档
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteDocument(@PathVariable Long id) {
        try {
            return documentRepository.findById(id)
                    .map(document -> {
                        // 删除物理文件
                        try {
                            Path filePath = Paths.get(document.getFilePath());
                            if (Files.exists(filePath)) {
                                Files.delete(filePath);
                            }
                        } catch (IOException e) {
                            // 记录日志但不阻止删除数据库记录
                            System.err.println("删除物理文件失败：" + e.getMessage());
                        }
                        
                        // 删除数据库记录
                        documentRepository.deleteById(id);
                        return Result.success("文档删除成功");
                    })
                    .orElse(Result.error("文档不存在"));
        } catch (Exception e) {
            return Result.error("删除文档失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据类型获取文档
     */
    @GetMapping("/type/{documentType}")
    public Result<List<Document>> getDocumentsByType(@PathVariable DocumentType documentType) {
        try {
            List<Document> documents = documentRepository.findByDocumentType(documentType);
            return Result.success(documents);
        } catch (Exception e) {
            return Result.error("获取文档列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取公开文档
     */
    @GetMapping("/public")
    public Result<List<Document>> getPublicDocuments() {
        try {
            List<Document> documents = documentRepository.findByIsPublicTrue();
            return Result.success(documents);
        } catch (Exception e) {
            return Result.error("获取公开文档失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据上传者获取文档
     */
    @GetMapping("/uploader/{uploadedBy}")
    public Result<List<Document>> getDocumentsByUploader(@PathVariable Long uploadedBy) {
        try {
            List<Document> documents = documentRepository.findByUploadedBy(uploadedBy);
            return Result.success(documents);
        } catch (Exception e) {
            return Result.error("获取文档列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 搜索文档
     */
    @GetMapping("/search")
    public Result<List<Document>> searchDocuments(@RequestParam String keyword) {
        try {
            List<Document> documents = documentRepository.findByTitleContainingIgnoreCase(keyword);
            return Result.success(documents);
        } catch (Exception e) {
            return Result.error("搜索文档失败：" + e.getMessage());
        }
    }
} 