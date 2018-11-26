package com.zz.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {

    private MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    private MiaoshaUserKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(6000,"tk");
}
