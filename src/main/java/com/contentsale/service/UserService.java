package com.contentsale.service;

import com.contentsale.service.model.UserModel;

/**
 * Created by wss on 2019/1/9.
 */
public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel gerUserById(Integer id);
}
