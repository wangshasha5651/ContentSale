package com.contentsale.interceptor;

import com.contentsale.common.Const;
import com.contentsale.controller.viewobject.FinanceVO;
import com.contentsale.controller.viewobject.UserVO;
import com.contentsale.dao.*;
import com.contentsale.dataobject.*;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.service.model.UserModel;
import com.contentsale.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wss on 2019/1/15.
 */

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private FinanceDOMapper financeDOMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 当访问的非主页时，清除主页页面缓存
        String url = request.getRequestURI();
        if(!StringUtils.equals(url, "/")){
            jedisAdapter.del(RedisKeyUtil.getHomeKey());
        }

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute(Const.CURRENT_USER) != null){

            UserVO user = UserUtils.convertVOFromModel((UserModel)session.getAttribute(Const.CURRENT_USER));

            //验证缓存中session信息
            String loginSessionId = jedisAdapter.get(RedisKeyUtil.getLoginKey(user.getId()));
            if(loginSessionId != null && loginSessionId.equals(session.getId())){

                //若用户存在正确登录信息

                // 保存用户到线程本地变量中
                hostHolder.setUsers(user);

            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        //在渲染前将已登录的用户传给页面
        if(modelAndView != null && hostHolder.getUser() != null){
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
