package com.zz.miaosha.rabbitMQ;

import com.zz.miaosha.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    RedisService redisService;


    public void send(Object message){
        String msg = redisService.beanTostr(message);
        amqpTemplate.convertAndSend(MQConfig.NAME, msg);
    }


    public void sendTopic(Object message){
        String msg = redisService.beanTostr(message);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1", msg +"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2", msg +"2");
    }

    public void sendFanout(Object message){
        String msg = redisService.beanTostr(message);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"" ,msg);
    }


    public void sendHeader(Object message){
        String msg = redisService.beanTostr(message);
        MessageProperties messageProperties = new MessageProperties();
        //只有header 一样才能放进去
        messageProperties.setHeader("header1", "value1");
        messageProperties.setHeader("header2", "value2");
        Message obj = new Message(msg.getBytes(),messageProperties );
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE,"", obj);
    }


    public void sendMiaoshaMessage(MiaoshaMessage mm) {
        String msg = redisService.beanTostr(mm);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }


}
