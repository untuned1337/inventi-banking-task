package com.banking.bankingapi.core.dto.imports;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImportCsvRequest {
    MultipartFile file;

    boolean stopOnErrors;
}
