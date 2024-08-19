# Read Me First

The original idea is from  
https://medium.com/@sharatnaik1996/connect-and-use-multiple-datasources-in-spring-boot-java-1192286ab361

- Read DataSourceProperties with given @ConfigurationProperties
- Create a DataSource with DataSourceProperties
- Create LocalContainerEntityManagerFactoryBean with DataSource. Specify entity packages
- Create PlatformTransactionManager with EntityManagerFactory
- @EnableJpaRepositories points to primary or secondary database repository package
