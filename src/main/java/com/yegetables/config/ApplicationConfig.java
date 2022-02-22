package com.yegetables.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config.properties")
public class ApplicationConfig {
    @Bean
    public Gson getGson() throws Exception {
        return new Gson();
    }

}
