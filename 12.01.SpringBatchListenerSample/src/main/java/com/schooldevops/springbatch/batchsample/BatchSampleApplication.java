package com.schooldevops.springbatch.batchsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchSampleApplication {

	public static void main(String[] args) {

		SpringApplication.run(BatchSampleApplication.class, args);

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
	}

}
