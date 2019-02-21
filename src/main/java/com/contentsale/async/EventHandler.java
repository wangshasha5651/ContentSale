package com.contentsale.async;

import java.util.List;

/**
 * Created by wss on 2019/2/21.
 */
public interface EventHandler {

    void doHandle(EventModel model);

    List<EventType> getSupportEventType();
}
