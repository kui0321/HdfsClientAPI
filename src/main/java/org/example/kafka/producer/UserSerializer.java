package org.example.kafka.producer;


import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserSerializer implements Serializer<UserVo> {
    private ObjectMapper objectMapper;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, UserVo userVo) {
        byte[] ret = null;
        try {
            ret = objectMapper.writeValueAsString(userVo).getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new SerializationException("Error when serializing UserVo to byte[],exception is " + e.getMessage());
        }
        return ret;
    }

    @Override
    public void close() {
        objectMapper = null;
    }
}
