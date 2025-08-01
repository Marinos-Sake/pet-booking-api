package com.petbooking.bookingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This table is used to store uploaded identity documents for verifying a person's information.
 * It helps in association a scanned ID (e.g. identity card) with each person for authentication purposes.
 */


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name= "attachments")
public class Attachment extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "saved_name", nullable = false, length = 255)
    private String savedName;

    @Column(name = "file_path", nullable = false, length = 255)
    private String filePath;

    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    @Column(length = 10)
    private String extension;
}
