package vn.unigap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.unigap.controllers.EmployerController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RecruitmentServiceApplicationTests {
	@Autowired
	private EmployerController controller;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
