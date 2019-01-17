package com.contentsale.service.impl;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.dao.LoginTicketDOMapper;
import com.contentsale.dao.UserDOMapper;
import com.contentsale.dao.UserPasswordDOMapper;
import com.contentsale.dataobject.LoginTicketDO;
import com.contentsale.dataobject.UserDO;
import com.contentsale.dataobject.UserPasswordDO;
import com.contentsale.service.UserService;
import com.contentsale.service.model.UserModel;
import com.contentsale.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.validation.Validator;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wss on 2019/1/9.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private LoginTicketDOMapper loginTicketDOMapper;


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
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = UserUtils.convertFromDataObject(userDO, userPasswordDO);

        // 比对用户信息内加密的密码是否和传输的密码一致
        String encryptPassword = UserUtils.encodeByMD5(password + userModel.getSalt());

        if(!StringUtils.equals(encryptPassword, userModel.getEncryptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        // 若登录密码正确，生成ticket
        userModel.setTicket(addLoginTicket(userDO.getId()));

        return userModel;

    }

    @Override
    public void logout(String ticket) {
        LoginTicketDO loginTicketDO = loginTicketDOMapper.selectByTicket(ticket);
        loginTicketDO.setStatus((byte)1);
        loginTicketDOMapper.updateByPrimaryKeySelective(loginTicketDO);
    }

    // 为用户生成ticket
    private String addLoginTicket(int userId){
        LoginTicketDO loginTicketDO = new LoginTicketDO();
        loginTicketDO.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600+1);
        loginTicketDO.setExpired(date);
        loginTicketDO.setStatus((byte)0);
        loginTicketDO.setTicket(UUID.randomUUID().toString().replaceAll("-",""));

        loginTicketDOMapper.insertSelective(loginTicketDO);

        return loginTicketDO.getTicket();
    }

}
