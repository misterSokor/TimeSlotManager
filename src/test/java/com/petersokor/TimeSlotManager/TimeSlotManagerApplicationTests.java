//this class created to check if the application context loads successfully.
package com.petersokor.TimeSlotManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TimeSlotManagerApplicationTests {

	@Test
	void contextLoads() {
		// to ensure that the application context loads successfully
		TimeSlotManagerApplication.main(new String[]{});
	}
}
