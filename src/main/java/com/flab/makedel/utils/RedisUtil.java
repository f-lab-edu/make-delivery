package com.flab.makedel.utils;

public class RedisUtil {

    private static final String cartKey = ":CART";

    private RedisUtil() {
    }

    public static String generateCartKey(String id) {
        return id + cartKey;
    }

}
