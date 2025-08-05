package com.petbooking.bookingapp.mapper;

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
}
