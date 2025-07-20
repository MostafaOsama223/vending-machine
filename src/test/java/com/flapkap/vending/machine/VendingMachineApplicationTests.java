package com.flapkap.vending.machine;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class VendingMachineApplicationTests {

	@Test
	void contextLoads() {
	}

}
