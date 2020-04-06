package ua.electro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*Class which contains configuration of Web Layer*/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        /*urlPath it is path of logic of controller
         * setViewName means that we add for this controller it's view*/
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("img/**")
                .addResourceLocations("file://" + uploadPath + "/");
        registry.addResourceHandler("static/**")
                /*classpath: - resources will be searched in tree of project*/
                .addResourceLocations("classpath:/static/");
    }
}
