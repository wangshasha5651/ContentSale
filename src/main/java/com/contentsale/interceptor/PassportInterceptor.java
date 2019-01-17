package com.contentsale.interceptor;

import com.contentsale.dao.LoginTicketDOMapper;
import com.contentsale.dao.UserDOMapper;
import com.contentsale.dataobject.LoginTicketDO;
import com.contentsale.dataobject.UserDO;
import com.contentsale.interceptor.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by wss on 2019/1/15.
 */

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDOMapper loginTicketDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ticket = null;

        // 查找客户端请求中是否包含ticket
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }


        // 若包含，则验证是否在服务端数据库中有对应ticket
        if(ticket != null){
            LoginTicketDO loginTicket = loginTicketDOMapper.selectByTicket(ticket);

            // 如果tciket无效
            if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0){
                return true;
            }

            // 如果tciket有效
            UserDO user = userDOMapper.selectByPrimaryKey(loginTicket.getUserId());
            // 保存用户到线程本地变量中
            hostHolder.setUsers(user);

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
