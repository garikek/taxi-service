package com.software.modsen.driverservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange driverRatingExchange() {
        return new TopicExchange("driver-rating-exchange");
    }

    @Bean
    public Queue driverRatingQueue() {
        return QueueBuilder.durable("driver-rating-queue").build();
    }

    @Bean
    public Binding binding(Queue driverRatingQueue, TopicExchange driverRatingExchange) {
        return BindingBuilder
                .bind(driverRatingQueue)
                .to(driverRatingExchange)
                .with("driver-rating");
    }

    @Bean
    public TopicExchange rideDriverExchange() {
        return new TopicExchange("ride-driver-exchange");
    }

    @Bean
    public Queue rideDriverQueue() {
        return QueueBuilder.durable("ride-driver-queue").build();
    }

    @Bean
    public Binding rideDriverBinding(Queue rideDriverQueue, TopicExchange rideDriverExchange) {
        return BindingBuilder
                .bind(rideDriverQueue)
                .to(rideDriverExchange)
                .with("ride-driver");
    }

    @Bean
    public TopicExchange driverRideExchange() {
        return new TopicExchange("driver-ride-exchange");
    }

    @Bean
    public Queue driverRideQueue() {
        return QueueBuilder.durable("driver-ride-queue").build();
    }

    @Bean
    public Binding driverRideBinding(Queue driverRideQueue, TopicExchange driverRideExchange) {
        return BindingBuilder
                .bind(driverRideQueue)
                .to(driverRideExchange)
                .with("driver-ride");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
