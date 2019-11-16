package ir.sharifi.soroush.soroush_test_project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DataJpaTest(excludeAutoConfiguration = HibernateJpaAutoConfiguration.class)
class SoroushTestProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
