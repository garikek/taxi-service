package com.software.modsen.authservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange authPassengerExchange() {
        return new TopicExchange("auth-passenger-exchange");
    }

    @Bean
    public Queue authPassengerQueue() {
        return QueueBuilder.durable("auth-passenger-queue").build();
    }

    @Bean
    public Binding authPassengerBinding(Queue authPassengerQueue, TopicExchange authPassengerExchange) {
        return BindingBuilder
                .bind(authPassengerQueue)
                .to(authPassengerExchange)
                .with("auth-passenger");
    }

    @Bean
    public TopicExchange authDriverExchange() {
        return new TopicExchange("auth-driver-exchange");
    }

    @Bean
    public Queue authDriverQueue() {
        return QueueBuilder.durable("auth-driver-queue").build();
    }

    @Bean
    public Binding authDriverBinding(Queue authPassengerQueue, TopicExchange authPassengerExchange) {
        return BindingBuilder
                .bind(authPassengerQueue)
                .to(authPassengerExchange)
                .with("auth-driver");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
