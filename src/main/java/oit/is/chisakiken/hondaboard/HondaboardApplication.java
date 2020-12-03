package oit.is.chisakiken.hondaboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HondaboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HondaboardApplication.class, args);
	}

}
