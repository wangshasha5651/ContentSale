package com.contentsale.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wss on 2019/2/21.
 */
public class EventModel {

    private EventType type;

    private int entityType;

    private int entityId;

    private int entityOwnerId;

    private Map<String, String> contents = new HashMap<>();

    public EventModel(EventType type){
        this.type = type;
    }

    public String getContentItem(String key){
        return contents.get(key);
    }

    public EventModel setContentItem(String key, String value){
        contents.put(key, value);
        return this;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getContents() {
        return contents;
    }

    public EventModel setContents(Map<String, String> contents) {
        this.contents = contents;
        return this;
    }
}
