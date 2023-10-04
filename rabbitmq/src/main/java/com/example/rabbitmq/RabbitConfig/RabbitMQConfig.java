package com.example.rabbitmq.RabbitConfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    public  static final String DEAD_LETTER_QUEUE_NAME = "dead_letter_queue" ;
    public  static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange" ;
    public  static final String DEAD_LETTER_ROUTING_KEY = "dead_letter" ;
    /**
     * Lưu ý :
     * x-dead-letter-exchange : điền exchange của dead messege
     * x-dead-letter-routing-key : điền routing key cua dead messeger
     * Nếu không chạy được thì xóa sạch queue và exchange rồi trong localhost 15672 đi là được
          */
    @Bean
    public Queue jsonQueue() {
       return QueueBuilder.durable(jsonQueue)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME).build();
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }

}

