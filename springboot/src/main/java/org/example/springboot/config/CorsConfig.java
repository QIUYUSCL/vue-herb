package org.example.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 标识为配置类，让Spring扫描到
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有接口生效
                // 允许的前端域名（开发环境+生产环境）
                .allowedOrigins(
                        "http://localhost:5173", // 本地开发环境
                        "https://vue-herb.vercel.app" // 生产环境前端域名
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
                .allowCredentials(true) // 允许携带Cookie（前后端都需要开启）
                .maxAge(3600) // 预检请求的有效期（秒），避免频繁预检
                .allowedHeaders("*"); // 允许所有请求头
    }
}