package com.contentsale.controller;

import com.contentsale.controller.viewobject.FinanceVO;
import com.contentsale.dataobject.FinanceDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.FinanceService;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.utils.FinanceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/20.
 */
@Controller("/finance")
@RequestMapping("/finance")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true")
public class FinanceController {

    private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);

    @Autowired
    private FinanceService financeService;

    @Autowired
    private HostHolder hostHolder;

    // 查看财务所有项目
    @RequestMapping(value="/show", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView listFinance(ModelAndView modelAndView){

        try{
            List<FinanceModel> financeModelList = financeService.listFinanceItem(hostHolder.getUser().getId());

            List<FinanceVO> financeVOList = FinanceUtils.convertVOListFromModelList(financeModelList);

            // 防止上一次查询的财务总价未清零
            FinanceVO.setAllItemPrice(new BigDecimal(0));
            for(FinanceVO financeVOForCalcu : financeVOList){
                // 累加单类商品总额
                FinanceVO.setAllItemPrice(FinanceVO.getAllItemPrice().add(financeVOForCalcu.getTotalPrice()));
            }

            modelAndView.addObject("financeList", financeVOList);
            modelAndView.addObject("allItemPrice", FinanceVO.getAllItemPrice());
            // 对此次查询得到的财务总价清零
            FinanceVO.setAllItemPrice(new BigDecimal(0));

            modelAndView.setViewName("itemBought");
        }catch (Exception e){
            logger.error("查看财务项目异常：" + e.getMessage());
        }

        return modelAndView;
    }
}
