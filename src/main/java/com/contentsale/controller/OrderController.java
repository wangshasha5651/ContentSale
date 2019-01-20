package com.contentsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.controller.viewobject.FinanceVO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.CartService;
import com.contentsale.service.FinanceService;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.utils.FinanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wss on 2019/1/19.
 */

@Controller("/order")
@RequestMapping("/")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true")
public class OrderController extends BaseController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private CartService cartService;


    // 结算
    @RequestMapping(value="/buy", method = {RequestMethod.POST})
    @ResponseBody
    public String SettleAccount(@RequestBody JSONObject json, RedirectAttributesModelMap modelMap) throws BusinessException {

        List<Map<String, String>> paramList = json.getObject("cartList", List.class);

        // 添加财务项目
        List<FinanceModel> financeModelList = financeService.createFinaceItem(paramList);
        if(financeModelList == null ||financeModelList.size() == 0){
            throw new BusinessException(EmBusinessError.ORDER_FINANCE_ERROR);
        }

        // 清空该用户购物车
        Boolean DeleteResult = cartService.deleteCartItem(paramList);
        if(DeleteResult.equals(Boolean.FALSE)){
            throw new BusinessException(EmBusinessError.ORDER_CART_ERROR);
        }

        return "success";
    }
}
