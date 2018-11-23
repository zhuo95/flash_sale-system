package com.zz.miaosha.redis;

public class OrderKey extends BasePrefix {

    private OrderKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }
}
