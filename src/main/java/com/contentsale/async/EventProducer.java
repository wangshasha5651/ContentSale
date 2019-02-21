package com.contentsale.async;

import com.alibaba.fastjson.JSONObject;
import com.contentsale.controller.CartController;
import com.contentsale.utils.JedisAdapter;
import com.contentsale.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by wss on 2019/2/21.
 */

@Service
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    //发生事件后将事件模型EventModel放入异步队列里
    public boolean fireEvent(EventModel model){
        try{
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        }catch (Exception e){
            logger.error("事件触发时推入队列异常：" + e.getMessage());
            return false;
        }

    }

}
