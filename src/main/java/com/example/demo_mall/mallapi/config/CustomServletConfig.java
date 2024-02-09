package com.example.demo_mall.mallapi.config;

import com.example.demo_mall.mallapi.formatter.LocalDateFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("addFormatters 동작 확인");
        registry.addFormatter(new LocalDateFormatter());
    }
}
