package com.banking.bankingapi.core.extensions;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperExtensions {
    public static <S, T>List<T> mapList(ModelMapper mapper, List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
