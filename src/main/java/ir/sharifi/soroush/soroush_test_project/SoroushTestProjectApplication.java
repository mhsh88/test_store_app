package ir.sharifi.soroush.soroush_test_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"ir.sharifi.soroush.soroush_test_project.detergent.service",
		"ir.sharifi.soroush.soroush_test_project.user.service",
		"ir.sharifi.soroush.soroush_test_project.food.service",
		"ir.sharifi.soroush.soroush_test_project.detergent",
		"ir.sharifi.soroush.soroush_test_project.food",
		"ir.sharifi.soroush.soroush_test_project.user",
		"ir.sharifi.soroush.soroush_test_project.config"


})
public class SoroushTestProjectApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SoroushTestProjectApplication.class, args);
	}

}
