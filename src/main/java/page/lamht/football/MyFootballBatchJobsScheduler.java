package page.lamht.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyFootballBatchJobsScheduler {

	public static void main(String[] args) {
		SpringApplication.run(MyFootballBatchJobsScheduler.class, args);
	}

}
