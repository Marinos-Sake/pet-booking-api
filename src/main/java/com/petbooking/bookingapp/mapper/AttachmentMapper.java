package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.service.internal.storage.AttachmentStorageInfo;
import com.petbooking.bookingapp.dto.AttachmentReadOnlyDTO;
import com.petbooking.bookingapp.entity.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {

    public AttachmentReadOnlyDTO mapToDTO(Attachment attachment) {
        if (attachment == null) return null;

        AttachmentReadOnlyDTO dto = new AttachmentReadOnlyDTO();
        dto.setId(attachment.getId());
        dto.setFileName(attachment.getFileName());
        dto.setContentType(attachment.getContentType());
        dto.setExtension(attachment.getExtension());
        return dto;
    }

    public Attachment mapToAttachmentEntity(AttachmentStorageInfo storageInfo) {
        if (storageInfo == null) return null;

        Attachment attachment = new Attachment();
        attachment.setFileName(storageInfo.getFileName());
        attachment.setSavedName(storageInfo.getSavedName());
        attachment.setFilePath(storageInfo.getFilePath());
        attachment.setContentType(storageInfo.getContentType());
        attachment.setExtension(storageInfo.getExtension());
        return attachment;
    }

}
