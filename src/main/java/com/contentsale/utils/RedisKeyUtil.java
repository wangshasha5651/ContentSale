package com.contentsale.utils;

/**
 * Created by wss on 2019/2/21.
 */
public class RedisKeyUtil {

    private static String SPLIT = ":";

    private static String LOGIN_PREFIX = "loginUser";

    private static String CART_PREFIX = "cartForUser";

    private static String EVENT_PREFIX = "EVENT";

    private static String HOME_KEY = "homeHtml";


    public static String getLoginKey(Integer userId){
        return LOGIN_PREFIX + SPLIT + userId;
    }

    public static String getCartKey(Integer userId){
        return CART_PREFIX + SPLIT + userId;
    }

    public static String getEventQueueKey(){

        return EVENT_PREFIX;
    }

    public static String getHomeKey(){
        return HOME_KEY;
    }
}
