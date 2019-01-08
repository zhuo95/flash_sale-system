package com.zz.miaosha.rabbitMQ;

import com.zz.miaosha.domain.MiaoshaOrder;
import com.zz.miaosha.domain.MiaoshaProduct;
import com.zz.miaosha.domain.MiaoshaUser;
import com.zz.miaosha.redis.RedisService;
import com.zz.miaosha.service.GoodsService;
import com.zz.miaosha.service.MiaoshaService;
import com.zz.miaosha.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MQReceiver {

    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        MiaoshaMessage mm  = redisService.strToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        MiaoshaProduct goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getNumber();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }

//    @RabbitListener(queues = MQConfig.NAME)
//    public void receive(String msg){
//        ;log.info(msg);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receive1(String msg){
//        ;log.info("queue1, {}",msg);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receive2(String msg){
//        ;log.info("queue2, {}",msg);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
//    public void receive3(byte[] msg){
//        log.info("header_queue, {}",new String(msg));
//    }
}
