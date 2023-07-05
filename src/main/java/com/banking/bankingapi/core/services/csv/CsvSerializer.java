package com.banking.bankingapi.core.services.csv;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface CsvSerializer {
    <T> SerializeCsvResponse<T> deserialize(Class<T> classType, byte[] data);
    <T> ByteArrayInputStream serialize(List<T> obj, Class<?> classType);
}
