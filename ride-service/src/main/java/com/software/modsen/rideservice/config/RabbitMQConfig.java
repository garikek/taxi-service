package com.software.modsen.rideservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
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
