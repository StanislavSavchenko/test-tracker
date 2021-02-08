package com.test.tracker.config;

import com.test.tracker.SimpleEncryptor;
import com.test.tracker.model.dto.HibernateProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.test.tracker.repository",
        entityManagerFactoryRef = "platformEntityManagerFactory",
        transactionManagerRef = "platformTransactionManager"
)
@Import(CommonPersistenceConfig.class)
@Slf4j
public class PlatformPersistenceConfig {

    @Bean
    @Primary
    public PlatformTransactionManager platformTransactionManager(@Qualifier("platformEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean platformEntityManagerFactory(DataSource dataSource,
                                                                               HibernateProperties hibernateProperties) {
        return getLocalContainerEntityManagerFactoryBean(dataSource, hibernateProperties);
    }

    static LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(DataSource dataSource, HibernateProperties hibernateProperties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.test.tracker");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", hibernateProperties.getDialect());
        jpaProperties.put("hibernate.show_sql", hibernateProperties.isShowSql());
        jpaProperties.put("hibernate.format_sql", hibernateProperties.isFormatSql());
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource getDataSource(ApplicationContext applicationContext) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Environment env = applicationContext.getEnvironment();

        validateEnvProps(env);

        SimpleEncryptor encryptor = new SimpleEncryptor(String.valueOf(System.getenv("DB_KEY")));

        String password = encryptor.decrypt(System.getenv("DB_PASSWORD"));
        String urlWithSchema = System.getenv("DB_URL");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(System.getenv("DB_DRIVER"));
        hikariConfig.setJdbcUrl(urlWithSchema);
        hikariConfig.setUsername(System.getenv("DB_USERNAME"));
        hikariConfig.setPassword(password);
        hikariConfig.setConnectionTimeout(120000);
        hikariConfig.setMaximumPoolSize(Integer.parseInt(System.getenv("DB_CONNECTION_MAXIMUM_POOL_SIZE")));
        hikariConfig.setIdleTimeout(Integer.parseInt(System.getenv("DB_CONNECTION_IDLE_TIMEOUT")));
        return new HikariDataSource(hikariConfig);
    }

    private void validateEnvProps(Environment env) {
        if (StringUtils.isEmpty(System.getenv("DB_KEY"))) {
            throw new IllegalArgumentException("env parameter DB_KEY should be specified");
        }

        if (StringUtils.isEmpty(System.getenv("DB_PASSWORD"))) {
            throw new IllegalArgumentException("env parameter DB_PASSWORD should be specified");
        }

        if (StringUtils.isEmpty(System.getenv("DB_URL"))) {
            throw new IllegalArgumentException("env parameter DB_URL should be specified");
        }

        if (StringUtils.isEmpty(System.getenv("DB_DRIVER"))) {
            throw new IllegalArgumentException("env parameter DB_DRIVER should be specified");
        }

        if (StringUtils.isEmpty(System.getenv("DB_USERNAME"))) {
            throw new IllegalArgumentException("env parameter DB_USERNAME should be specified");
        }

        if (StringUtils.isEmpty(System.getenv("DB_CONNECTION_MAXIMUM_POOL_SIZE"))) {
            throw new IllegalArgumentException("env parameter DB_CONNECTION_MAXIMUM_POOL_SIZE should be specified");
        }

        if (StringUtils.isEmpty(System.getenv("DB_CONNECTION_IDLE_TIMEOUT"))) {
            throw new IllegalArgumentException("env parameter DB_CONNECTION_IDLE_TIMEOUT should be specified");
        }

    }

}
