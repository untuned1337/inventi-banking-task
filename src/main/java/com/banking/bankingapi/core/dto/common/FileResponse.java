package com.banking.bankingapi.core.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;

@Data
@AllArgsConstructor
public class FileResponse {
    private InputStreamResource inputStream;

    private String filename;

    private String contentType;
}
