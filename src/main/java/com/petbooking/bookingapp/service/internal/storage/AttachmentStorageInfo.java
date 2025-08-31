package com.petbooking.bookingapp.service.internal.storage;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentStorageInfo {


    private String fileName;


    private String savedName;


    private String filePath;


    private String contentType;


    private String extension;
}
