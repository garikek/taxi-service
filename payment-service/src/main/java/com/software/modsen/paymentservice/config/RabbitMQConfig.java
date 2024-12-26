package com.software.modsen.paymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
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
    public TopicExchange ridePaymentExchange() {
        return new TopicExchange("ride-payment-exchange");
    }

    @Bean
    public Queue ridePaymentQueue() {
        return QueueBuilder.durable("ride-payment-queue").build();
    }

    @Bean
    public Binding ridePaymentBinding(Queue ridePaymentQueue, TopicExchange ridePaymentExchange) {
        return BindingBuilder
                .bind(ridePaymentQueue)
                .to(ridePaymentExchange)
                .with("ride-payment");
    }

    @Bean
    public TopicExchange paymentRideExchange() {
        return new TopicExchange("payment-ride-exchange");
    }

    @Bean
    public Queue paymentRideQueue() {
        return QueueBuilder.durable("payment-ride-queue").build();
    }

    @Bean
    public Binding paymentRideBinding(Queue paymentRideQueue, TopicExchange paymentRideExchange) {
        return BindingBuilder
                .bind(paymentRideQueue)
                .to(paymentRideExchange)
                .with("payment-ride");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
