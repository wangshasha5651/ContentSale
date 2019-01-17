package com.contentsale.service;

import com.contentsale.common.error.BusinessException;
import com.contentsale.service.model.UserModel;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by wss on 2019/1/9.
 */
public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel gerUserById(Integer id);
    UserModel validateLogin(String username, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
    void logout(String ticket);
}
