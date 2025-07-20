package com.flapkap.vending.machine;

import org.springframework.boot.SpringApplication;

public class TestVendingMachineApplication {

	public static void main(String[] args) {
		SpringApplication.from(VendingMachineApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
