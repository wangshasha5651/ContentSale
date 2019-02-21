package com.contentsale.async;

import com.contentsale.controller.CartController;
import com.contentsale.utils.JedisAdapter;
import com.contentsale.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wss on 2019/2/21.
 */

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;


    //记录每一个event需要哪些handler来处理
    @Override
    public void afterPropertiesSet() throws Exception {

        //找到所有实现了EventHandler的对象(找到所有注册过的handler)
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null){
            for(Map.Entry<String, EventHandler> entry : beans.entrySet()){
                //去遍历每一个handler，找到它要关注哪一个事件，然后反向获得每一个EventType对应哪些handler去处理
                List<EventType> eventTypes = entry.getValue().getSupportEventType();
                for (EventType type : eventTypes){
                    if(!config.containsKey(type)){
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        //新开一个线程去守着异步事件队列，不断查看是否有新的事件未处理
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key = RedisKeyUtil.getEventQueueKey();

                    List<String> events = jedisAdapter.brpop(0, key);

                    //遍历事件
                    for (String message : events){

                    }


                }
            }
        });
        thread.start();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
