package com.findthing.controller;

import com.findthing.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${file.upload.path:./uploads/}")
    private String uploadPath;

    @Value("${file.upload.url-prefix:http://localhost:8080/uploads/}")
    private String urlPrefix;

    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 验证文件
            if (file.isEmpty()) {
                return Result.error("请选择要上传的图片文件");
            }

            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只支持图片文件上传");
            }

            // 验证文件大小 (5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return Result.error("图片文件大小不能超过5MB");
            }

            // 创建上传目录
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = Paths.get(uploadPath, fileName);
            Files.copy(file.getInputStream(), filePath);

            // 返回访问URL
            String fileUrl = urlPrefix + fileName;
            log.info("图片上传成功: {}", fileUrl);
            
            return Result.success(fileUrl);

        } catch (IOException e) {
            log.error("图片上传失败", e);
            return Result.error("图片上传失败");
        }
    }

    @DeleteMapping("/image")
    public Result<Void> deleteImage(@RequestParam String imageUrl) {
        try {
            if (imageUrl == null || !imageUrl.startsWith(urlPrefix)) {
                return Result.error("无效的图片URL");
            }

            String fileName = imageUrl.substring(urlPrefix.length());
            Path filePath = Paths.get(uploadPath, fileName);
            
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("图片删除成功: {}", imageUrl);
            }
            
            return Result.success();
        } catch (IOException e) {
            log.error("图片删除失败", e);
            return Result.error("图片删除失败");
        }
    }
}