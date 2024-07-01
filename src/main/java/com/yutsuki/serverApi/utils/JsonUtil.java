package com.yutsuki.serverApi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Objects;

public class JsonUtil {
    @Resource
    private static ObjectMapper objectMapper;


    public static class jsonTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            long timestamp = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            jsonGenerator.writeNumber(timestamp);
        }
    }


    public static String toJson(Object javaObject) {
        try {
            return objectMapper.writeValueAsString(javaObject);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format("Can't resolve from class: %s.", javaObject.getClass()), e);
        }
    }

    public static String toJsonIfNotNull(Object obj) {
        Map<String, Object> objectMap = toMap(obj);
        objectMap.entrySet().removeIf((entry) -> Objects.isNull(entry.getValue()));
        return toJson(objectMap);
    }

    private static Map<String, Object> toMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
        });
    }


}