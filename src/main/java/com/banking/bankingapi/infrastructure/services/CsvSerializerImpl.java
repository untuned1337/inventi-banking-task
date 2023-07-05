package com.banking.bankingapi.infrastructure.services;

import com.banking.bankingapi.core.services.csv.CsvSerializer;
import com.banking.bankingapi.core.services.csv.SerializeCsvResponse;
import net.sf.jsefa.csv.CsvDeserializer;
import net.sf.jsefa.csv.CsvIOFactory;
import net.sf.jsefa.csv.config.CsvConfiguration;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvSerializerImpl implements CsvSerializer {
    private final CsvConfiguration configuration;

    public CsvSerializerImpl() {
        this.configuration = new CsvConfiguration();
        this.configuration.setFieldDelimiter(',');
    }

    public <T>ByteArrayInputStream serialize(List<T> objList, Class<?> classType) {
        var serializer = CsvIOFactory
                .createFactory(configuration, classType)
                .createSerializer();

        var out = new ByteArrayOutputStream();
        var writer = new PrintWriter(out);
        serializer.open(writer);
        objList.forEach(serializer::write);
        serializer.close(true);

        return new ByteArrayInputStream(out.toByteArray());
    }

    public <T> SerializeCsvResponse<T> deserialize(Class<T> classType, byte[] data){
        var deserializer = openDeserializer(classType, data);
        SerializeCsvResponse<T> response = deserialize(deserializer);
        deserializer.close(true);

        return response;
    }

    private <T> SerializeCsvResponse<T> deserialize(CsvDeserializer deserializer) {
        var successfulRows = new ArrayList<T>();
        var failedRows = new ArrayList<Integer>();
        var rowIndex = 1;

        while(deserializer.hasNext()) {
            try {
                T row = deserializer.next();
                successfulRows.add(row);
            }
            catch (Exception e) {
                failedRows.add(rowIndex);
            } finally {
                rowIndex++;
            }
        }

        return new SerializeCsvResponse<>(successfulRows, failedRows);
    }

    private CsvDeserializer openDeserializer(Class<?> classType, byte[] data) {
        var deserializer = CsvIOFactory
                .createFactory(configuration, classType)
                .createDeserializer();
        var content = new String(data);
        var reader = new StringReader(content);

        deserializer.open(reader);

        return deserializer;
    }
}
