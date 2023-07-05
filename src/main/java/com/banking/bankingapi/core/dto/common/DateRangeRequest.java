package com.banking.bankingapi.core.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateFrom = LocalDateTime.MIN;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTo = LocalDateTime.MAX;

    public boolean includes(LocalDateTime date) {
        if(date == null){
            return false;
        }
        return date.isAfter(dateFrom) && date.isBefore(dateTo);
    }
}
