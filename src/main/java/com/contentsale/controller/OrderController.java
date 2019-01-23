package com.contentsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;

import com.contentsale.service.CartService;
import com.contentsale.service.FinanceService;
import com.contentsale.service.ItemService;
import com.contentsale.service.impl.OrderServiceImpl;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.service.model.OrderItemModel;

import com.contentsale.service.model.OrderAllModel;
import com.contentsale.utils.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;


import java.util.List;
import java.util.Map;

/**
 * Created by wss on 2019/1/19.
 */

@Controller("/order")
@RequestMapping("/")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true")
public class OrderController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private FinanceService financeService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderServiceImpl orderService;

    // 结算
    @RequestMapping(value="/buy", method = {RequestMethod.POST})
    @ResponseBody
    @Transactional
    public String SettleAccount(@RequestBody JSONObject json, RedirectAttributesModelMap modelMap) throws BusinessException {

        try{
            List<Map<String, String>> paramList = json.getObject("cartList", List.class);

            List<OrderItemModel> orderItemModelList = OrderUtils.convertModelfromJSON(paramList);

            // 更改商品销量
            Boolean editResult = itemService.changeSales(orderItemModelList);
            if(editResult.equals(Boolean.FALSE)){
                throw new BusinessException(EmBusinessError.ORDER_CHANGE_SALES_ERROR);
            }

            // 添加财务项目
            List<FinanceModel> financeModelList = financeService.createFinaceItem(orderItemModelList);
            if(financeModelList == null ||financeModelList.size() == 0){
                throw new BusinessException(EmBusinessError.ORDER_FINANCE_ERROR);
            }

            // 清空该用户购物车
            Boolean deleteResult = cartService.deleteCartItem(orderItemModelList);
            if(deleteResult.equals(Boolean.FALSE)){
                throw new BusinessException(EmBusinessError.ORDER_CART_ERROR);
            }

            // 创建订单
            OrderAllModel orderAllModel = orderService.createOrder(orderItemModelList);
            if(orderAllModel == null){
                throw new BusinessException(EmBusinessError.ORDER_CREATE_ERROR);
            }
        }catch (Exception e){
            logger.error("创建订单失败：" + e.getMessage());
        }

        return "success";
    }

}
