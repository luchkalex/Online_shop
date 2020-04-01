package com.example.sweater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*Class which contains configuration of Web Layer*/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        /*urlPath it is path of logic of controller
        * setViewName means that we add for this controller it's view*/
        registry.addViewController("/login").setViewName("login");
    }
}
