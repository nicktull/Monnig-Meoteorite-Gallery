package edu.tcu.cs.monning_meteorite_gallery.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// current implementation allows CORS requests from all origins to all controllers
// and endpoints, which might be okay for development but is typically
// not recommended for production environments due to security concerns.

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**"); //enable cors for the whole application
            }
        };
    }

}
