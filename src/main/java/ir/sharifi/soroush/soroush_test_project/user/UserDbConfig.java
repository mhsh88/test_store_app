package ir.sharifi.soroush.soroush_test_project.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "userEntityManagerFactory",
        basePackages = { "ir.sharifi.soroush.soroush_test_project.user.repo" }
)
public class UserDbConfig {

    private final Environment environment;

    @Autowired
    public UserDbConfig(Environment environment) {
        this.environment = environment;
    }

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "user.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(environment.getProperty("user.datasource.driver-class-name"))
                .url(environment.getProperty("user.datasource.url"))
                .username(environment.getProperty("user.datasource.username"))
                .password(environment.getProperty("user.datasource.password"))
                .build();
    }

    @Primary
    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto",environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        return builder
                .dataSource(dataSource)
                .packages("ir.sharifi.soroush.soroush_test_project.user.model")
                .persistenceUnit("user")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("userEntityManagerFactory") EntityManagerFactory
                    entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
