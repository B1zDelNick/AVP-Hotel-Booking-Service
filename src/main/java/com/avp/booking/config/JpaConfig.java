package com.avp.booking.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.avp.booking.model.repositories", "com.avp.booking.auth.model"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class JpaConfig
{
    @Autowired
    private Environment environment;

    /*public JpaConfig(Environment environment)
    {
        this.environment = environment;
    }*/

    //@Value("${datasource.sampleapp.maxPoolSize:10}")
    //private int maxPoolSize;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.booking")
    public DataSourceProperties dataSourceProperties()
    {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource()
    {
        /*Properties dsProps = new Properties();
        dsProps.put("url", environment.getProperty("datasource.booking.url"));
        dsProps.put("user", environment.getProperty("datasource.booking.user"));
        dsProps.put("password", environment.getProperty("datasource.booking.password"));
        dsProps.put("prepStmtCacheSize",250);
        dsProps.put("prepStmtCacheSqlLimit",2048);
        dsProps.put("cachePrepStmts",Boolean.TRUE);
        dsProps.put("useServerPrepStmts",Boolean.TRUE);

        Properties configProps = new Properties();
        configProps.put("dataSourceClassName", environment.getProperty("datasource.booking.dataSourceClassName"));
        configProps.put("poolName", environment.getProperty("datasource.booking.poolName"));
        configProps.put("maximumPoolSize", environment.getProperty("datasource.booking.maximumPoolSize", Integer.class, 10));
        configProps.put("minimumIdle", environment.getProperty("datasource.booking.minimumIdle"));
        configProps.put("maxLifetime", environment.getProperty("datasource.booking.maxLifetime"));
        configProps.put("connectionTimeout", environment.getProperty("datasource.booking.connectionTimeout"));
        configProps.put("idleTimeout", environment.getProperty("datasource.booking.idleTimeout"));
        configProps.put("dataSourceProperties", dsProps);

        HikariConfig hc = new HikariConfig(configProps);
        HikariDataSource ds = new HikariDataSource(hc);

        return ds;*/

        DataSourceProperties dataSourceProperties = dataSourceProperties();

        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
                .create(dataSourceProperties.getClassLoader())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .type(HikariDataSource.class)
                .build();

        dataSource.setMaximumPoolSize(
                environment.getProperty("datasource.booking.maximumPoolSize", Integer.class, 10));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException
    {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(
                new String[] {
                        "com.avp.booking.auth.model",
                        "com.avp.booking.model.entities" });
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());

        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter()
    {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
    }

    private Properties jpaProperties()
    {
        Properties properties = new Properties();

        properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.booking.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.booking.hibernate.ddl-auto"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.booking.hibernate.show-sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.booking.hibernate.format-sql"));

        if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.booking.defaultSchema")))
        {
            properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.booking.defaultSchema"));
        }

        return properties;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
    {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}

