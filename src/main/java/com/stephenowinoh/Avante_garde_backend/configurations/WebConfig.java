package com.stephenowinoh.Avante_garde_backend.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                String uploadDir = "file:" + new File("uploads").getAbsolutePath() + "/";
                System.out.println("Serving static resources from: " + uploadDir);
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations(uploadDir)
                        .setCachePeriod(0);
        }
}
