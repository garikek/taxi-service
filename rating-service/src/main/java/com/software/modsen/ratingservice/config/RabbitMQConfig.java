package com.software.modsen.ratingservice.config;

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
    public Binding passengerBinding(Queue passengerRatingQueue, TopicExchange passengerRatingExchange) {
        return BindingBuilder
                .bind(passengerRatingQueue)
                .to(passengerRatingExchange)
                .with("passenger-rating");
    }

    @Bean
    public TopicExchange driverRatingExchange() {
        return new TopicExchange("driver-rating-exchange");
    }

    @Bean
    public Queue driverRatingQueue() {
        return QueueBuilder.durable("driver-rating-queue").build();
    }

    @Bean
    public Binding driverBinding(Queue driverRatingQueue, TopicExchange driverRatingExchange) {
        return BindingBuilder
                .bind(driverRatingQueue)
                .to(driverRatingExchange)
                .with("driver-rating");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
