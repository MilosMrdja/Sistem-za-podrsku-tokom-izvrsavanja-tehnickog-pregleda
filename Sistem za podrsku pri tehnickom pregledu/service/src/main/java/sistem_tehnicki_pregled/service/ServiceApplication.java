package sistem_tehnicki_pregled.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EntityScan(basePackages = {
		"sistem_tehnicki_pregled.model.entities"
})
@EnableJpaRepositories(basePackages = {
		"sistem_tehnicki_pregled.service.repositories"
})
@EnableJpaAuditing
@EnableKafka
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

}
