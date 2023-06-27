package com.ecommerce.rabbitmq.producer;

import com.ecommerce.rabbitmq.model.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProducer {
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.direct}")
    private String exchange;
    @Value("${rabbitmq.key.register}")
    private String keyRegisterUser;

    public void createSendMessage(CreateUser createUser){
        rabbitTemplate.convertAndSend(exchange,keyRegisterUser,createUser);
    }
}
