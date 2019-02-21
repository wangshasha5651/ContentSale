package com.contentsale.service.impl;

import com.contentsale.common.error.BusinessException;
import com.contentsale.dao.UserDOMapper;
import com.contentsale.dao.UserPasswordDOMapper;
import com.contentsale.dataobject.UserDO;
import com.contentsale.dataobject.UserPasswordDO;
import com.contentsale.service.UserService;
import com.contentsale.service.model.UserModel;
import com.contentsale.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wss on 2019/1/9.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel gerUserById(Integer id) {
        //调用userDOMapper获取对应用户的dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null){
            return null;
        }
        //调用userPasswordDOMapper，通过用户id获取对应用户的加密密码信息
//        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        //return convertFromDataObject(userDO, userPasswordDO);

        return new UserModel();
    }


    @Override
    public UserModel validateLogin(String username, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 通过用户名获取用户信息
        UserDO userDO = userDOMapper.selectByName(username);
        if(userDO == null){
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = UserUtils.convertFromDataObject(userDO, userPasswordDO);

        // 比对用户信息内加密的密码是否和传输的密码一致
        String encryptPassword = UserUtils.encodeByMD5(password + userModel.getSalt());

        if(!StringUtils.equals(encryptPassword, userModel.getEncryptPassword())){
            return null;
        }

        return userModel;

    }

}
