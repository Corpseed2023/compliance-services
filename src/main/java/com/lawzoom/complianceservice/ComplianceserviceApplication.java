package com.lawzoom.complianceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComplianceserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComplianceserviceApplication.class, args);
	}

}
