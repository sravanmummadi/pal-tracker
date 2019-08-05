package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {


   Map<String,String> envConfig=new HashMap<>();

    public EnvController(@Value("${PORT:NOT SET}")String s, @Value("${MEMORY_LIMIT:NOT SET}") String s1, @Value("${CF_INSTANCE_INDEX:NOT SET}") String s2, @Value("${CF_INSTANCE_ADDR:NOT SET}") String s3) {
        envConfig.put("PORT",s);
        envConfig.put("MEMORY_LIMIT",s1);
        envConfig.put("CF_INSTANCE_INDEX",s2);
        envConfig.put("CF_INSTANCE_ADDR",s3);
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {

        return envConfig;
    }
}
