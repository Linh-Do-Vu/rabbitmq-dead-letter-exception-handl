package com.example.rabbitmq.Producer;
import com.example.rabbitmq.ParamReport;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;
    private final RabbitTemplate rabbitTemplate ;
    public void sendJsonMessage (ParamReport report) {
        rabbitTemplate.convertAndSend(exchange,routingJsonKey,report);
    }

}
