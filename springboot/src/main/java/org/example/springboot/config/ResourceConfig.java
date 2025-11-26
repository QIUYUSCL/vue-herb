package org.example.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /video-resource/** 映射到 D:/视频/Captures/ 目录
        registry.addResourceHandler("/video-resource/**")
                .addResourceLocations("File:D:/视频/Captures/");
    }
}