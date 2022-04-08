package kittyfacts;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KittyfactsApplicationBot {

	public static void main(String[] args) {
		SpringApplication.run(KittyfactsApplicationBot.class, args);
	}

}
