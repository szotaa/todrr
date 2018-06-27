package pl.szotaa.todrr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Dev profile Spring MVC configuration class.
 */

@EnableWebMvc
@Configuration
@Profile("dev")
public class DevMvcConfig implements WebMvcConfigurer {

    /**
     * Enables cross origin request for localhost-served Angular app for development convenience.
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200");
    }
}
