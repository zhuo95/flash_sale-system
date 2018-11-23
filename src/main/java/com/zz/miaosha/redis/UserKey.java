package com.zz.miaosha.redis;

public class UserKey extends BasePrefix {

    private UserKey( String prefix) {
        super(prefix);
    }

    private UserKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static UserKey getById = new UserKey("id");

    public static UserKey getByNmae = new UserKey("name");


}
