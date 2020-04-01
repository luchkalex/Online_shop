//package com.example.sweater.conf;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.web.accept.ContentNegotiationManager;
//import org.springframework.web.servlet.View;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.view.JstlView;
//import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
//import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration /*Indicates that class declares one or more  bean methods*/
//@EnableWebMvc /*Imports some special Spring MVC Configuration*/
//@ComponentScan("com.example.sweater") /*Specify the Base packages to scan for annotated beans*/
//public class WebApplicationContextConfig implements WebMvcConfigurer {
//
//   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//      configurer.enable();
//   }
//
//   @Bean
//   public InternalResourceViewResolver getInternalResourceViewResolver(){
//      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//      viewResolver.setViewClass(JstlView.class);
//      viewResolver.setPrefix("/WEB-INF/pages/");
//      viewResolver.setSuffix(".jsp");
//      return viewResolver;
//   }
//
//   @Bean
//   public MappingJackson2JsonView jsonView(){
//      MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
//      jsonView.setPrettyPrint(true);
//      return jsonView;
//   }
//
//   @Bean
//   public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager){
//      ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
//      viewResolver.setContentNegotiationManager(manager);
//      List<View> views = new ArrayList<View>();
//      views.add(jsonView());
//      views.add(new MappingJackson2XmlView());
//      viewResolver.setDefaultViews(views);
//      return viewResolver;
//   }
//
//
//
//
//}
