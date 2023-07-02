package com.ecommerce.rabbitmq.producer;

import com.ecommerce.rabbitmq.model.CreateCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCustomerProducer {
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.direct}")
    private String exchange;
    @Value("${rabbitmq.key.register}")
    private String keyRegisterCustomer;

    public void createSendMessage(CreateCustomer createCustomer){
        rabbitTemplate.convertAndSend(exchange,keyRegisterCustomer,createCustomer);
    }
}
