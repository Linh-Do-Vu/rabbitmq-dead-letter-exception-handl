package com.example.rabbitmq.Consumer;
import com.example.rabbitmq.ParamReport;
import com.example.rabbitmq.exception.SPSSCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.example.rabbitmq.RabbitConfig.RabbitMQConfig.DEAD_LETTER_QUEUE_NAME;

@Service
@RequiredArgsConstructor
public class RabbitMQJsonConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void jsonConsumer(ParamReport report) throws SPSSCustomException {
       if (report.getType() ==1L ) {
           System.out.println(report.toString());
       }else throw new SPSSCustomException("lỗi test");
    }
    @RabbitListener(queues = DEAD_LETTER_QUEUE_NAME)
    public void deadConsumer (ParamReport report) {
        System.err.println("đã lỗi vào deafd " + report);
    }
}

