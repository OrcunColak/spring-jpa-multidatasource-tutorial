package com.colak.springtutorial.config.ds2config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager",
        basePackages = {"com.colak.springjpamultidatasourcetutorial.repository.ds2"})
public class Datasource2Configuration {

    @Bean
    @ConfigurationProperties("spring.datasource2")
    public DataSourceProperties dataSource2Properties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSource2")
    public DataSource dataSource2() {
        return dataSource2Properties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource2") DataSource dataSource) {

        HashMap<String, Object> properties = new HashMap<>();
        // Create the schema and columns if they don't exist
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.show-sql", "true");

        return builder
                .dataSource(dataSource)
                .packages("com.colak.springjpamultidatasourcetutorial.jpa.ds2")
                .persistenceUnit("db2")
                .properties(properties)
                .build();
    }

    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager(
            @Qualifier("secondEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
