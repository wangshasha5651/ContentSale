package com.contentsale.interceptor.model;

import com.contentsale.dataobject.UserDO;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    // 为每一线程单独存取当前登录的user变量
    private static ThreadLocal<UserDO> users = new ThreadLocal<>();

    public UserDO getUser(){
        return users.get();
    }

    public void setUsers(UserDO user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
