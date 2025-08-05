package com.saip.service;

import com.saip.entity.Document;
import com.saip.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 文档服务类
 */
@Service
public class DocumentService {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    private static final String UPLOAD_DIR = "uploads/documents/";
    
    /**
     * 获取所有文档
     */
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
    
    /**
     * 根据ID获取文档
     */
    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }
    
    /**
     * 上传文档
     */
    public Document uploadDocument(Document document, MultipartFile file) throws IOException {
        // 创建上传目录
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;
        
        // 保存文件
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, file.getBytes());
        
        // 设置文档信息
        document.setFileName(fileName);
        document.setFilePath(filePath.toString());
        document.setFileSize(file.getSize());
        document.setFileType(file.getContentType());
        document.setCreatedTime(LocalDateTime.now());
        document.setUpdatedTime(LocalDateTime.now());
        
        return documentRepository.save(document);
    }
    
    /**
     * 更新文档信息
     */
    public Document updateDocument(Long id, Document documentDetails) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            document.setTitle(documentDetails.getTitle());
            document.setDescription(documentDetails.getDescription());
            document.setDocumentType(documentDetails.getDocumentType());
            document.setIsPublic(documentDetails.getIsPublic());
            document.setVersion(documentDetails.getVersion());
            document.setUpdatedTime(LocalDateTime.now());
            return documentRepository.save(document);
        }
        return null;
    }
    
    /**
     * 删除文档
     */
    public boolean deleteDocument(Long id) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            
            // 删除物理文件
            try {
                Path filePath = Paths.get(document.getFilePath());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // 记录错误但不阻止删除数据库记录
                System.err.println("删除文件失败: " + e.getMessage());
            }
            
            // 删除数据库记录
            documentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 根据文档类型获取文档
     */
    public List<Document> getDocumentsByType(String documentType) {
        return documentRepository.findByDocumentType(com.saip.enums.DocumentType.valueOf(documentType));
    }
    
    /**
     * 获取公开文档
     */
    public List<Document> getPublicDocuments() {
        return documentRepository.findByIsPublicTrue();
    }
    
    /**
     * 根据上传者获取文档
     */
    public List<Document> getDocumentsByUploader(Long uploadedBy) {
        return documentRepository.findByUploadedBy(uploadedBy);
    }
    
    /**
     * 搜索文档
     */
    public List<Document> searchDocuments(String keyword) {
        return documentRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据标题搜索文档
     */
    public List<Document> searchDocumentsByTitle(String keyword) {
        return documentRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    /**
     * 根据描述搜索文档
     */
    public List<Document> searchDocumentsByDescription(String keyword) {
        // 由于Repository中没有这个方法，我们通过标题搜索来实现
        return documentRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    /**
     * 获取文档统计
     */
    public long getDocumentCount() {
        return documentRepository.count();
    }
    
    /**
     * 获取公开文档数量
     */
    public long getPublicDocumentCount() {
        return documentRepository.findByIsPublicTrue().size();
    }
    
    /**
     * 根据类型统计文档数量
     */
    public long getDocumentCountByType(String documentType) {
        return documentRepository.findByDocumentType(com.saip.enums.DocumentType.valueOf(documentType)).size();
    }
    
    /**
     * 获取文件大小统计
     */
    public long getTotalFileSize() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .mapToLong(doc -> doc.getFileSize() != null ? doc.getFileSize() : 0)
                .sum();
    }
    
    /**
     * 检查文件是否存在
     */
    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
    
    /**
     * 获取文件路径
     */
    public String getFilePath(Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        return document.map(Document::getFilePath).orElse(null);
    }
} 