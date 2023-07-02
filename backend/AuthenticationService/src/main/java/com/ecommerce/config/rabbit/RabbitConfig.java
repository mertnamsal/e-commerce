package com.ecommerce.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //exchange
    @Value("${rabbitmq.exchange.direct}")
    private String exchangeDirectAuth;

    //key
    @Value("${rabbitmq.key.register}")
    private String keyAuth = "key-auth";

    //queue
    @Value("${rabbitmq.queue.register}")
    private String queueAuthCreateCustomer;


    //exchange
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchangeDirectAuth);
    }

    //queue
    @Bean
    Queue createCustomerQueue(){
        return new Queue(queueAuthCreateCustomer);
    }

    //binding

    @Bean
    public Binding bindingCreateAuthCustomer(final Queue createAuthCustomerQueue,final DirectExchange directExchange){
        return BindingBuilder.bind(createAuthCustomerQueue).to(directExchange).with(keyAuth);
    }


}
