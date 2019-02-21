package com.contentsale.async;

/**
 * Created by wss on 2019/2/21.
 */
public enum EventType {

    ORDER(0);

    private int value;

    EventType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
