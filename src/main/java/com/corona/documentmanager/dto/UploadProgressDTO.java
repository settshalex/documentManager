package com.corona.documentmanager.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class UploadProgressDTO {
    private String fileName;
    private String status;
    private String error;
    private Long documentId;
}

