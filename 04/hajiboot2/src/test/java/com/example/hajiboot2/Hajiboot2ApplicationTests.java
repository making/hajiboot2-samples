package com.example.hajiboot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // (1)
public class Hajiboot2ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate; // (2)

    @Test
    public void contextLoads() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class); // (3)
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // (4)
        assertThat(response.getBody()).isEqualTo("Hello World!"); // (5)
    }

}
