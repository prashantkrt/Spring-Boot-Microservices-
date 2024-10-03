package com.mylearning.test;

import com.mylearning.test.dto.Customer;
import com.mylearning.test.service.KafkaMessagePublisher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;

import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class KafkaProducerTestApplicationTests {

//    @Container
//    public static ZookeeperContainer zookeeper = new ZookeeperContainer("zookeeper:3.6.0");
   @Container
   public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest").asCompatibleSubstituteFor("apache/kafka")).withStartupTimeout(Duration.ofMinutes(5));  // Optional:Timeout for starting the container;


    @DynamicPropertySource
    public static void initKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    @Test
    public void testSendMessage() throws ExecutionException, InterruptedException {
       kafkaMessagePublisher.sendMessage(new Customer(1234,"DummyName","dummyEmail","12345678980"));

       await().pollInterval(Duration.ofSeconds(3)).atMost(10, TimeUnit.MINUTES).untilAsserted(()->{
           //assert statements we can add
       });
    }

}
