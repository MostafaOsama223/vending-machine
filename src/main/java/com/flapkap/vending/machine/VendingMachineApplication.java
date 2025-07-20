package com.flapkap.vending.machine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VendingMachineApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VendingMachineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Vending Machine Application is running!");
	}
}
