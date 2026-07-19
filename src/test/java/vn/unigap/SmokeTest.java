package vn.unigap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.unigap.api.controller.EmployerController;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private EmployerController employerController;

    @Test
    void contextLoads() throws Exception {
        assertThat(employerController).isNotNull();
    }
}
