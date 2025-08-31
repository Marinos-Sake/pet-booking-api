package com.petbooking.bookingapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentReadOnlyDTO {

    private Long id;

    private String fileName;

    private String contentType;

    private String extension;

}
