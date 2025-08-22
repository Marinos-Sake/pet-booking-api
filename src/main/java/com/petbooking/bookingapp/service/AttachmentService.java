package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppInvalidInputException;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.dto.AttachmentReadOnlyDTO;
import com.petbooking.bookingapp.entity.Attachment;
import com.petbooking.bookingapp.mapper.AttachmentMapper;
import com.petbooking.bookingapp.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    private final String uploadDir = System.getProperty("user.home") + "/uploads";

    public AttachmentReadOnlyDTO uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new AppInvalidInputException("ATTACH_", "Uploaded file is empty");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            createDirectories(uploadPath);
        }

        String originalName = file.getOriginalFilename();
        String extension = getExtension(originalName);
        String savedName = UUID.randomUUID() + "." + extension;
        Path fullPath = uploadPath.resolve(savedName);

        transferFile(file, fullPath);

        Attachment attachment = new Attachment();
        attachment.setFileName(originalName);
        attachment.setSavedName(savedName);
        attachment.setFilePath(fullPath.toString());
        attachment.setContentType(file.getContentType());
        attachment.setExtension(extension);

        Attachment saved = attachmentRepository.save(attachment);
        return attachmentMapper.mapToDTO(saved);
    }

    public byte[] downloadFile(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "ATTACH_",
                        "Attachment with ID " + attachmentId + " not found"));

        return readFile(attachment.getFilePath());
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    private void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new AppInvalidInputException("ATTACH_", "Failed to create upload directory");
        }
    }

    private void transferFile(MultipartFile file, Path targetPath) {
        try {
            file.transferTo(targetPath.toFile());
        } catch (IOException e) {
            throw new AppInvalidInputException("ATTACH", "Failed to store uploaded file");
        }
    }

    private byte[] readFile(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new AppInvalidInputException("ATTACH", "Failed to read stored file");
        }
    }
}
