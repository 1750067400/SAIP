package com.saip.repository;

import com.saip.entity.Document;
import com.saip.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文档Repository接口
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    /**
     * 根据文档类型查找
     */
    List<Document> findByDocumentType(DocumentType documentType);
    
    /**
     * 查找公开文档
     */
    List<Document> findByIsPublicTrue();
    
    /**
     * 根据上传者查找
     */
    List<Document> findByUploadedBy(Long uploadedBy);
    
    /**
     * 根据标题模糊查找
     */
    List<Document> findByTitleContainingIgnoreCase(String keyword);
    
    /**
     * 根据文件类型查找
     */
    List<Document> findByFileType(String fileType);
    
    /**
     * 查找指定大小范围内的文档
     */
    @Query("SELECT d FROM Document d WHERE d.fileSize BETWEEN :minSize AND :maxSize")
    List<Document> findByFileSizeBetween(@Param("minSize") Long minSize, 
                                        @Param("maxSize") Long maxSize);
    
    /**
     * 根据版本查找
     */
    List<Document> findByVersion(String version);
} 