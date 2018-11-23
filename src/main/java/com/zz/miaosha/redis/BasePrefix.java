package com.zz.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {
    private String prefix;

    private int expireSecond;

    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSecond,String prefix){
        this.prefix = prefix;
        this.expireSecond = expireSecond;
    }

    @Override
    public int expiireSeconds() {
        return expireSecond;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
