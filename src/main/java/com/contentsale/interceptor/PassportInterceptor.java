package com.contentsale.interceptor;

import com.contentsale.controller.viewobject.FinanceVO;
import com.contentsale.controller.viewobject.UserVO;
import com.contentsale.dao.*;
import com.contentsale.dataobject.*;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.service.model.UserModel;
import com.contentsale.utils.FinanceUtils;
import com.contentsale.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private LoginTicketDOMapper loginTicketDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private FinanceDOMapper financeDOMapper;

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

            UserDO userDO = userDOMapper.selectByPrimaryKey(loginTicket.getUserId());
            UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(loginTicket.getUserId());
            UserModel userModel = UserUtils.convertFromDataObject(userDO, userPasswordDO);
            UserVO user = UserUtils.convertVOFromModel(userModel);
            // 保存用户到线程本地变量中
            hostHolder.setUsers(user);

            // 每个买家已买的商品集合
            if(userDO.getType() == 1){
                List<FinanceDO> buyerFinanceDOList = financeDOMapper.listItem(loginTicket.getUserId());
                if(buyerFinanceDOList != null && buyerFinanceDOList.size() != 0){
                    List<Integer> buyerItemIdList = FinanceUtils.getItemIDListFromDOList(buyerFinanceDOList);
                    hostHolder.setItemBoughtList(buyerItemIdList);
                }
                // 买家所有商品购买时的价格map
                Map<String, String> priceMap = new HashMap<>();
                for(FinanceDO financeDO : buyerFinanceDOList){
                    priceMap.put(financeDO.getItemId().toString(), new BigDecimal(financeDO.getEachPrice()).toString());
                }
                hostHolder.setPriceMap(priceMap);
            }else{
                // 每个卖家已卖的商品集合
                List<FinanceDO> sellerFinanceDOList = financeDOMapper.listItemBySeller(loginTicket.getUserId());
                if(sellerFinanceDOList != null && sellerFinanceDOList.size() != 0){
                    List<Integer> sellerItemIdList = FinanceUtils.getItemIDListFromDOList(sellerFinanceDOList);
                    hostHolder.setItemSoldList(sellerItemIdList);
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //在渲染前将已登录的用户传给页面
        if(modelAndView != null && hostHolder.getUser() != null){
            modelAndView.addObject("user", hostHolder.getUser());
            if(hostHolder.getUser().getType() == 1){ // 如果是买家
                modelAndView.addObject("boughtList", hostHolder.getItemBoughtList());
                modelAndView.addObject("priceMap", hostHolder.getPriceMap());
            }else{
                modelAndView.addObject("soldList", hostHolder.getItemSoldList());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
