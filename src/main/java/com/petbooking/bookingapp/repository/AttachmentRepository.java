package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
