package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.AttachmentReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Attachment", description = "Endpoints for managing file uploads and retrieval")
@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Operation(
            summary = "Upload a file",
            description = "Uploads a new file and associates it with the authenticated user"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentReadOnlyDTO> uploadIdentityFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user
    ) {
        AttachmentReadOnlyDTO dto = attachmentService.uploadIdentityFileForCurrentUser(file, user);
        return ResponseEntity.ok(dto);
    }


    @Operation(
            summary = "[ADMIN] Download a file",
            description = "Downloads the file associated with the given attachment ID."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        byte[] fileBytes = attachmentService.downloadFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file_" + id + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }
}
