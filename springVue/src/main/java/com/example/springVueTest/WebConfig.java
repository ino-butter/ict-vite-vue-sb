package com.example.springVueTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/auth/**") // CORS 설정 auth 도메인만 인증없이 허용
                        .allowedOrigins("http://localhost:5173") // 주소
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") // 허용하는 메소드타입
                        .allowCredentials(true);
            }
        };
    }
}
