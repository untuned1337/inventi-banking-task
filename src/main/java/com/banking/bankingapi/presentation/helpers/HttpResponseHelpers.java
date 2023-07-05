package com.banking.bankingapi.presentation.helpers;

import com.banking.bankingapi.core.dto.common.FileResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HttpResponseHelpers {
    public static ResponseEntity<Object> buildResponse(FileResponse fileResponse){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + fileResponse.getFilename())
                .contentType(MediaType.parseMediaType(fileResponse.getContentType()))
                .body(fileResponse.getInputStream());
    }
}
