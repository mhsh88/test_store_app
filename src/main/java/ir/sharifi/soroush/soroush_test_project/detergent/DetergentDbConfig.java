package ir.sharifi.soroush.soroush_test_project.detergent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "detergentEntityManagerFactory",
        transactionManagerRef = "detergentTransactionManager",
        basePackages = { "ir.sharifi.soroush.soroush_test_project.detergent.repo" }
)
public class DetergentDbConfig {

    private final Environment environment;

    @Autowired
    public DetergentDbConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "detergentDataSource")
    @ConfigurationProperties(prefix = "detergent.datasource")
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                .driverClassName(environment.getProperty("detergent.datasource.driver-class-name"))
                .url(environment.getProperty("detergent.datasource.url"))
                .username(environment.getProperty("detergent.datasource.username"))
                .password(environment.getProperty("detergent.datasource.password"))

                .build();
    }

    @Bean(name = "detergentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    detergentEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("detergentDataSource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        return
                builder
                        .dataSource(dataSource)
                        .packages("ir.sharifi.soroush.soroush_test_project.detergent.model")
                        .persistenceUnit("detergent")
                        .properties(properties)
                        .build();
    }  @Bean(name = "detergentTransactionManager")
    public PlatformTransactionManager detergentTransactionManager(
            @Qualifier("detergentEntityManagerFactory") EntityManagerFactory
                    detergentEntityManagerFactory
    ) {
        return new JpaTransactionManager(detergentEntityManagerFactory);
    }
}
