package com.contentsale.controller;


import com.contentsale.common.Const;
import com.contentsale.controller.viewobject.UserVO;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.common.responese.CommonReturnType;
import com.contentsale.dao.UserDOMapper;
import com.contentsale.dao.UserPasswordDOMapper;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.CartService;
import com.contentsale.service.UserService;
import com.contentsale.service.model.UserModel;
import com.contentsale.utils.JedisAdapter;
import com.contentsale.utils.RedisKeyUtil;
import com.contentsale.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;


import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by wss on 2019/1/9.
 */

@Controller("user")
@RequestMapping("/")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true") //Access-Control-Allow-Origin
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Autowired
    private CartService cartService;

    @Autowired
    private HostHolder hostHolder;



    @RequestMapping(value="/user/login",method = {RequestMethod.POST})
    public String sessionLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session,
                               RedirectAttributesModelMap modelMap) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //入参校验
        if (StringUtils.isEmpty(username)) {
            throw new BusinessException(EmBusinessError.LOGIN_USERNAME_EMPTY);
        }
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.LOGIN_PASSWORD_EMPTY);
        }

        //用户登录服务，验证登录是否成功
        UserModel userModel = userService.validateLogin(username, password);

        // 若登录失败
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        session.setAttribute(Const.CURRENT_USER, userModel);

        //向缓存中存储具有一定有效期LOGIN_EXPIRE_TIME的token
        String loginKey = RedisKeyUtil.getLoginKey(userModel.getId());
        jedisAdapter.setex(loginKey, Const.LOGIN_EXPIRE_TIME, session.getId());

        //清除购物车残留缓存
        Boolean resultFlush = cartService.flushCache(RedisKeyUtil.getCartKey(userModel.getId()));
        if(resultFlush.equals(Boolean.FALSE)){
            throw new BusinessException(EmBusinessError.CART_CACHE_ERROR);
        }

        UserVO userVO = UserUtils.convertVOFromModel(userModel);
        modelMap.addFlashAttribute("viewInfo", CommonReturnType.create(userVO));

        return "redirect:/";
    }

    @RequestMapping(value="/user/logout",method = {RequestMethod.GET})
    public String logout(HttpSession session) throws BusinessException {
        UserVO user = UserUtils.convertVOFromModel((UserModel)session.getAttribute(Const.CURRENT_USER));
        session.removeAttribute(Const.CURRENT_USER);

        Integer userId = user.getId();
        jedisAdapter.del(RedisKeyUtil.getLoginKey(userId));

        //清除购物车缓存
        Boolean resultFlush = cartService.flushCache(RedisKeyUtil.getCartKey(userId));
        if(resultFlush.equals(Boolean.FALSE)){
            throw new BusinessException(EmBusinessError.CART_CACHE_ERROR);
        }

        //清除用户主页缓存
        jedisAdapter.del(RedisKeyUtil.getHomeKey());
        Const.afterLoginCacheFlag = false;

        hostHolder.clear();

        return "redirect:/login";
    }
}





