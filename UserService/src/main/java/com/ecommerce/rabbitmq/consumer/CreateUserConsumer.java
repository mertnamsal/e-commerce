package com.ecommerce.rabbitmq.consumer;

import com.ecommerce.rabbitmq.model.CreateUser;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {
    private final UserService userService;

    @RabbitListener(queues = "queue-auth-create-user")
    public void createUserConsumerListener(CreateUser createUser){
        userService.save(createUser);
    }
}
