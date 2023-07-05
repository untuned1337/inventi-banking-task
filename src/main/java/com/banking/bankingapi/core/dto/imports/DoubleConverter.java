package com.banking.bankingapi.core.dto.imports;

import net.sf.jsefa.common.converter.SimpleTypeConverter;

public class DoubleConverter implements SimpleTypeConverter {
    @Override
    public String toString(Object o) {
        return o.toString();
    }

    @Override
    public Object fromString(String s) {
        return Double.parseDouble(s);
    }
}
