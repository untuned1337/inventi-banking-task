package com.banking.bankingapi.core.dto.imports;

import net.sf.jsefa.common.converter.SimpleTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements SimpleTypeConverter {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Override
    public String toString(Object o) {
        var dateTime = (LocalDateTime)o;
        return dateTime.format(formatter);
    }

    @Override
    public Object fromString(String s) {
        return LocalDateTime.parse(s, formatter);
    }
}
