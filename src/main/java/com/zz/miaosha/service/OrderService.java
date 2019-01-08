package com.zz.miaosha.service;

import com.zz.miaosha.dao.MiaoshaOrderDao;
import com.zz.miaosha.domain.MiaoshaOrder;
import com.zz.miaosha.domain.MiaoshaProduct;
import com.zz.miaosha.domain.MiaoshaUser;
import com.zz.miaosha.redis.OrderKey;
import com.zz.miaosha.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;


public class OrderService {
    @Autowired
    MiaoshaOrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        //return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
    }

    public MiaoshaOrder getOrderById(long orderId) {
        return orderDao.getById(orderId);
    }


    public MiaoshaOrder createOrder(MiaoshaUser user, MiaoshaProduct goods) {
        MiaoshaOrder order = new MiaoshaOrder();
        order.setProduct(goods.getId());
        order.setUser(user.getId());
        orderDao.insertOrder(order);

        redisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+user.getId()+"_"+goods.getId(), order);

        return order;
    }

    public void deleteOrders() {
        orderDao.deleteMiaoshaOrders();
    }
}
