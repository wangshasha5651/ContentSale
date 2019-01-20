package com.contentsale.utils;

import com.contentsale.controller.viewobject.FinanceVO;
import com.contentsale.dataobject.FinanceDO;
import com.contentsale.service.model.FinanceModel;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by wss on 2019/1/20.
 */
public class FinanceUtils {

    public static FinanceModel convertModelFromDataObject(FinanceDO financeDO){
        FinanceModel financeModel = new FinanceModel();
        BeanUtils.copyProperties(financeDO, financeModel);
        financeModel.setEachPrice(new BigDecimal(financeDO.getEachPrice()));
        financeModel.setTotalPrice(new BigDecimal(financeDO.getTotalPrice()));

        return financeModel;
    }

    public static FinanceVO convertVOFromModel(FinanceModel financeModel){
        if(financeModel == null){
            return null;
        }

        FinanceVO financeVO = new FinanceVO();
        BeanUtils.copyProperties(financeModel, financeVO);

        // 要显示的付款时间是String类型
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(financeModel.getPaymentTime());
        financeVO.setPaymentTime(dateString);

        return financeVO;
    }

    public static List<FinanceModel> convertModelListFromDOList(List<FinanceDO> financeDOList){
        List<FinanceModel> financeModelList = financeDOList.stream().map(financeDO -> {
            FinanceModel financeModel = FinanceUtils.convertModelFromDataObject(financeDO);
            return financeModel;
        }).collect(Collectors.toList());

        return financeModelList;
    }

    public static List<FinanceVO> convertVOListFromModelList(List<FinanceModel> financeModelList){
        // 使用stream api将list内的itemModel转化为itemVO
        List<FinanceVO> financeVOList = financeModelList.stream().map(financeModel -> {
            FinanceVO financeVO = FinanceUtils.convertVOFromModel(financeModel);
            return financeVO;
        }).collect(Collectors.toList());

        return financeVOList;
    }

    public static List<Integer> getItemIDListFromDOList(List<FinanceDO> financeDOList){
        List<Integer> itemIDList = financeDOList.stream().map(financeDO -> {
            Integer itemId = financeDO.getItemId();
            return itemId;
        }).collect(Collectors.toList());

        return itemIDList;
    }
}
