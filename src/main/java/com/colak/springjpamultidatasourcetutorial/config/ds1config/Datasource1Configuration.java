package com.colak.springjpamultidatasourcetutorial.config.ds1config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.colak.springjpamultidatasourcetutorial.repository.ds1"})
public class Datasource1Configuration {

    @Bean
    @ConfigurationProperties("spring.datasource1")
    public DataSourceProperties dataSource1Properties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return dataSource1Properties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource) {

        HashMap<String, Object> properties = new HashMap<>();
        // Create the schema and columns if they don't exist
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.show-sql", "true");

        return builder
                .dataSource(dataSource)
                .packages("com.colak.springjpamultidatasourcetutorial.jpa.ds1")
                .persistenceUnit("db1")
                .properties(properties)
                .build();
    }


    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
