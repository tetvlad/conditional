package ru.netology.conditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {

    private final RestTemplate restTemplate = new RestTemplate();

    // Контейнер DEV (порт 8888)
    @Container
    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8888);

    // Контейнер PROD (порт 8887)
    @Container
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8887);

    @Test
    void testDevProfile() {
        Integer devPort = devApp.getMappedPort(8888);
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + devPort + "/profile", String.class);
        System.out.println("Dev Response: " + entity.getBody());
        Assertions.assertEquals("Current profile is dev", entity.getBody());
    }

    @Test
    void testProdProfile() {
        Integer prodPort = prodApp.getMappedPort(8887);
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + prodPort + "/profile", String.class);
        System.out.println("Prod Response: " + entity.getBody());
        Assertions.assertEquals("Current profile is production", entity.getBody());
    }
}