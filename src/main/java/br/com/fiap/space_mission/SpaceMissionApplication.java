package br.com.fiap.space_mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("br.com.fiap.space_mission.model")
@EnableJpaRepositories("br.com.fiap.space_mission.repository")
public class SpaceMissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceMissionApplication.class, args);
	}

}
