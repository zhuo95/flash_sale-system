package com.zz.miaosha.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MQReceiver {

    @RabbitListener(queues = MQConfig.NAME)
    public void receive(String msg){
        ;log.info(msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receive1(String msg){
        ;log.info("queue1, {}",msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receive2(String msg){
        ;log.info("queue2, {}",msg);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
    public void receive3(byte[] msg){
        log.info("header_queue, {}",new String(msg));
    }
}
