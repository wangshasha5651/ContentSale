package com.contentsale.utils;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.dataobject.OrderDO;
import com.contentsale.dataobject.OrderItemDO;
import com.contentsale.service.model.OrderItemModel;
import com.contentsale.service.model.OrderModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wss on 2019/1/21.
 */
public class OrderUtils {


    public static List<OrderItemModel> convertModelfromJSON(List<Map<String, String>> paramList) throws BusinessException {

        List<OrderItemModel> orderItemModelList = new ArrayList<>();

        for(Map<String, String> param : paramList){
            if(param.get("id") == null || StringUtils.isEmpty(param.get("id"))){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            if(param.get("title") == null || StringUtils.isEmpty(param.get("title"))){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            if(param.get("quantity") == null || StringUtils.isEmpty(param.get("quantity"))){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            if(param.get("price") == null || StringUtils.isEmpty(param.get("price"))){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }

            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setItemId(Integer.valueOf(param.get("id")));
            orderItemModel.setItemName(param.get("title"));
            orderItemModel.setQuantity(Integer.valueOf(param.get("quantity")));
            orderItemModel.setCurrenrEachPrice(new BigDecimal(param.get("price")));
            orderItemModelList.add(orderItemModel);
        }
        return orderItemModelList;
    }

    public static OrderItemDO convertItemDOFromModel(OrderItemModel orderItemModel){
        if(orderItemModel == null){
            return null;
        }
        OrderItemDO orderItemDO = new OrderItemDO();
        BeanUtils.copyProperties(orderItemModel, orderItemDO);

        // 由于数据库中price是double类型，model中定义为bigdecimal，直接用BeanUtils的copyProperties方法的可能会有精度损失
        orderItemDO.setCurrentEachPrice(orderItemModel.getCurrenrEachPrice().doubleValue());
        orderItemDO.setTotalPrice(orderItemModel.getTotalPrice().doubleValue());

        return orderItemDO;
    }

    public static OrderDO convertDOFromModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);

        // 由于数据库中price是double类型，model中定义为bigdecimal，直接用BeanUtils的copyProperties方法的可能会有精度损失
        orderDO.setTotalPayment(orderModel.getTotalPayment().doubleValue());


        return orderDO;
    }

    public static OrderModel convertModelFromDO(OrderDO orderDO){
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDO, orderModel);

        orderModel.setTotalPayment(new BigDecimal(orderDO.getTotalPayment()));

        return orderModel;
    }
}
