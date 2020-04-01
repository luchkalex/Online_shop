//package com.example.sweater.conf;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//
//@Configuration
//@EnableJpaRepositories(basePackages = {"com.example.sweater.repos"})
//@EnableTransactionManagement
//public class AppConf {
//    @Bean
//    public LocalEntityManagerFactoryBean entityManagerFactory() {
//        LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
//        factoryBean.setPersistenceUnitName("shop");
////        factoryBean.setJpaProperties(this.additionalProperties());
//
//        return factoryBean;
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//
//        return transactionManager;
//    }
//
////    @Bean
////    public DataSource dataSource(){
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
////        dataSource.setUrl("jdbc:mysql://localhost:3306/online_shop");
////        dataSource.setUsername( "dbrider" );
////        dataSource.setPassword( "123" );
////        return dataSource;
////    }
//
////    @Bean
////    public LocalContainerEntityManagerFactoryBean
//
//}