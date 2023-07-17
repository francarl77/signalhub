package it.pagopa.interop.performancetest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PerformanceTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceTestApplication.class, args);
	}

}
