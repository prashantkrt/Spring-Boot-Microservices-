package com.mylearning.rabbitmqpubsub.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mylearning.rabbitmqpubsub.entity.Payment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Service;

@Service
public class PaytmClientApp1 {

    @RabbitListener(queues = "paytm_queue")
    public void subscribe(Payment payment) throws JsonProcessingException {
        System.out.println("consumer App-1 consumes : " + new ObjectMapper().writeValueAsString(payment));
    }
}
