package ir.sharifi.soroush.soroush_test_project.config;

import ir.sharifi.soroush.soroush_test_project.utils.jwtUtils.JwtProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name = "ModelMapper")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    @ConfigurationProperties(prefix = "app")
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

}
