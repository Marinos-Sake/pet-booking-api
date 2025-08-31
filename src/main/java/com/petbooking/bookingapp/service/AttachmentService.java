package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppInvalidInputException;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.service.internal.storage.AttachmentStorageInfo;
import com.petbooking.bookingapp.dto.AttachmentReadOnlyDTO;
import com.petbooking.bookingapp.entity.Attachment;
import com.petbooking.bookingapp.entity.Person;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.AttachmentMapper;
import com.petbooking.bookingapp.repository.AttachmentRepository;
import com.petbooking.bookingapp.repository.PersonRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    private final String uploadDir = System.getProperty("user.home") + "/uploads";

    @Transactional
    public AttachmentReadOnlyDTO uploadIdentityFileForCurrentUser(MultipartFile file, User user) {
        if (file == null || file.isEmpty()) {
            throw new AppInvalidInputException("ATTACH_", "Uploaded file is empty");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            createDirectories(uploadPath);
        }

        String originalName = file.getOriginalFilename();
        String extension = getExtension(originalName);
        String savedName = UUID.randomUUID() + (extension.isBlank() ? "" : "." + extension);
        Path fullPath = uploadPath.resolve(savedName);

        transferFile(file, fullPath);

        AttachmentStorageInfo insertDTO = new AttachmentStorageInfo();
        insertDTO.setFileName(originalName);
        insertDTO.setSavedName(savedName);
        insertDTO.setFilePath(fullPath.toString());
        insertDTO.setContentType(file.getContentType());
        insertDTO.setExtension(extension);

        Attachment toSave = attachmentMapper.mapToAttachmentEntity(insertDTO);
        Attachment savedAttachment = attachmentRepository.save(toSave);


        Person person = user.getPerson();
        if (person == null) {
            throw new AppObjectNotFoundException("PERSON_", "Current user has no person profile");
        }

        person.setIdentityNumberFile(savedAttachment);
        personRepository.save(person);

        return attachmentMapper.mapToDTO(savedAttachment);
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
