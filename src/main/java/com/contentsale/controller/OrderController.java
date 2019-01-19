package com.contentsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.contentsale.service.model.FinanceModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    // 查看购物车
    @RequestMapping(value="/buy")
    public String getTest(@RequestBody JSONObject json, HttpServletRequest request, HttpServletResponse response){

        List<Map<String, String>> paramList = json.getObject("cartList", List.class);
        System.out.println(paramList.size());

        List<FinanceModel> financeModelList = new ArrayList<>();
        for(Map<String, String> param : paramList){
            FinanceModel financeModel = new FinanceModel();
            financeModel.setId(Integer.valueOf(param.get("id")));
            financeModel.setItemName(param.get("title"));
            financeModel.setQuantity(Integer.valueOf(param.get("quantity")));
            financeModel.setEachPrice(BigDecimal.valueOf(Double.valueOf(param.get("price"))));
            financeModelList.add(financeModel);
        }


        return "success";
    }
}
