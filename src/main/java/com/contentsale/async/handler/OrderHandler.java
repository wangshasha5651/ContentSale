package com.contentsale.async.handler;

import com.contentsale.async.EventHandler;
import com.contentsale.async.EventModel;
import com.contentsale.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wss on 2019/2/21.
 */

@Component
public class OrderHandler implements EventHandler {

    @Override
    public void doHandle(EventModel model) {
        System.out.println("ordered");
    }

    @Override
    public List<EventType> getSupportEventType() {
        //只关注Order下单的行为
        return Arrays.asList(EventType.ORDER);
    }
}
