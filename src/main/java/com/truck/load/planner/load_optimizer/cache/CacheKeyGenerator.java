package com.truck.load.planner.load_optimizer.cache;

import com.truck.load.planner.load_optimizer.model.OptimizeRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

@Component
public class CacheKeyGenerator {

    private final ObjectMapper mapper =
            new ObjectMapper();

    public String generate(OptimizeRequest request) {

        try {
            String json = mapper.writeValueAsString(request);
            return DigestUtils.md5DigestAsHex(json.getBytes(StandardCharsets.UTF_8));

        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }
}
