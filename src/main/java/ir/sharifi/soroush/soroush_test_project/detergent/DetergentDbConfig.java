package ir.sharifi.soroush.soroush_test_project.detergent;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "detergentEntityManagerFactory",
        transactionManagerRef = "detergentTransactionManager",
        basePackages = { "ir.sharifi.soroush.soroush_test_project.detergent.repo" }
)
public class DetergentDbConfig {

    @Bean(name = "detergentDataSource")
    @ConfigurationProperties(prefix = "detergent.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "detergentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    detergentEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("detergentDataSource") DataSource dataSource
    ) {
        return
                builder
                        .dataSource(dataSource)
                        .packages("ir.sharifi.soroush.soroush_test_project.detergent.model")
                        .persistenceUnit("detergent")
                        .build();
    }  @Bean(name = "detergentTransactionManager")
    public PlatformTransactionManager detergentTransactionManager(
            @Qualifier("detergentEntityManagerFactory") EntityManagerFactory
                    detergentEntityManagerFactory
    ) {
        return new JpaTransactionManager(detergentEntityManagerFactory);
    }
}
