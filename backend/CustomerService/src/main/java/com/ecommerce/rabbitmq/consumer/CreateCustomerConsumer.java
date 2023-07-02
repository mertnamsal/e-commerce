package com.ecommerce.rabbitmq.consumer;

import com.ecommerce.rabbitmq.model.CreateCustomer;
import com.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCustomerConsumer {
    private final CustomerService customerService;

    @RabbitListener(queues = "queue-auth-create-customer")
    public void createCustomerConsumerListener(CreateCustomer createCustomer){
        customerService.save(createCustomer);
    }
}
