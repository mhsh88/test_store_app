package ir.sharifi.soroush.soroush_test_project.food;


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
        entityManagerFactoryRef = "foodEntityManagerFactory",
        transactionManagerRef = "foodTransactionManager",
        basePackages = { "ir.sharifi.soroush.soroush_test_project.food.repo" }
)
public class FoodDbConfig {

    private final Environment environment;

    @Autowired
    public FoodDbConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "foodDataSource")
    @ConfigurationProperties(prefix = "food.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(environment.getProperty("food.datasource.driver-class-name"))
                .url(environment.getProperty("food.datasource.url"))
                .username(environment.getProperty("food.datasource.username"))
                .password(environment.getProperty("food.datasource.password"))
                .build();
    }

    @Bean(name = "foodEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    foodEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("foodDataSource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        return
                builder
                        .dataSource(dataSource)
                        .packages("ir.sharifi.soroush.soroush_test_project.food.model")
                        .persistenceUnit("food")
                        .properties(properties)
                        .build();
    }  @Bean(name = "foodTransactionManager")
    public PlatformTransactionManager foodTransactionManager(
            @Qualifier("foodEntityManagerFactory") EntityManagerFactory
                    foodEntityManagerFactory
    ) {
        return new JpaTransactionManager(foodEntityManagerFactory);
    }
}
