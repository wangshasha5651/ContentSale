package com.contentsale.service;

import com.contentsale.common.error.BusinessException;
import com.contentsale.service.model.OrderItemModel;
import com.contentsale.service.model.OrderModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wss on 2019/1/21.
 */
public interface OrderService {
    // 创建订单
    public OrderModel createOrder(List<OrderItemModel> orderItemModelList) throws BusinessException;

    // 创建订单中所有的商品项目记录
    public BigDecimal createOrderItem(List<OrderItemModel> orderItemModelList, String orderNo)throws BusinessException;

    // 生成订单流水号
    public String generateOrderNo();

    public OrderModel getOrderModelById(Integer id);
}
