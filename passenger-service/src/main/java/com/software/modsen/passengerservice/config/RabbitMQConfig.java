package com.software.modsen.passengerservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange passengerRatingExchange() {
        return new TopicExchange("passenger-rating-exchange");
    }

    @Bean
    public Queue passengerRatingQueue() {
        return QueueBuilder.durable("passenger-rating-queue").build();
    }

    @Bean
    public Binding passengerRatingBinding(Queue passengerRatingQueue, TopicExchange passengerRatingExchange) {
        return BindingBuilder
                .bind(passengerRatingQueue)
                .to(passengerRatingExchange)
                .with("passenger-rating");
    }

    @Bean
    public TopicExchange passengerRideExchange() {
        return new TopicExchange("passenger-ride-exchange");
    }

    @Bean
    public Queue passengerRideQueue() {
        return QueueBuilder.durable("passenger-ride-queue").build();
    }

    @Bean
    public Binding passengerRideBinding(Queue passengerRideQueue, TopicExchange passengerRideExchange) {
        return BindingBuilder
                .bind(passengerRideQueue)
                .to(passengerRideExchange)
                .with("passenger-ride");
    }

    @Bean
    public TopicExchange passengerPaymentExchange() {
        return new TopicExchange("passenger-payment-exchange");
    }

    @Bean
    public Queue passengerPaymentQueue() {
        return QueueBuilder.durable("passenger-payment-queue").build();
    }

    @Bean
    public Binding passengerPaymentBinding(Queue passengerPaymentQueue, TopicExchange passengerPaymentExchange) {
        return BindingBuilder
                .bind(passengerPaymentQueue)
                .to(passengerPaymentExchange)
                .with("passenger-payment");
    }

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
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
