package com.zz.miaosha.service;

import com.zz.miaosha.dao.MiaoshaProductDao;
import com.zz.miaosha.domain.MiaoshaProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    MiaoshaProductDao goodsDao;


    public List<MiaoshaProduct> list(){return goodsDao.list();}

    public MiaoshaProduct getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getById(goodsId);
    }

    public boolean reduceStock(MiaoshaProduct goods) {
        MiaoshaProduct g = new MiaoshaProduct();
        g.setId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }

    public void resetStock(List<MiaoshaProduct> goodsList) {
        for(MiaoshaProduct goods : goodsList ) {
            MiaoshaProduct g = new MiaoshaProduct();
            g.setId(goods.getId());
            g.setNumber(goods.getNumber());
            goodsDao.resetStock(g);
        }
    }

}