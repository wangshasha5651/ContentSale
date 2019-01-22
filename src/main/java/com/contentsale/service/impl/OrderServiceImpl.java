package com.contentsale.service.impl;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.dao.OrderAllDOMapper;
import com.contentsale.dao.OrderItemDOMapper;
import com.contentsale.dao.SequenceDOMapper;
import com.contentsale.dataobject.OrderAllDO;
import com.contentsale.dataobject.OrderItemDO;
import com.contentsale.dataobject.SequenceDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.OrderService;
import com.contentsale.service.model.OrderItemModel;
import com.contentsale.service.model.OrderAllModel;
import com.contentsale.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Created by wss on 2019/1/21.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private OrderItemDOMapper orderItemDOMapper;

    @Autowired
    private OrderAllDOMapper orderAllDOMapper;

    @Autowired
    private HostHolder hostHolder;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderAllModel createOrder(List<OrderItemModel> orderItemModelList) throws BusinessException {

        String orderNo = generateOrderNo();

        BigDecimal totalPayment = createOrderItem(orderItemModelList, orderNo);

        OrderAllModel orderAllModel = new OrderAllModel();
        orderAllModel.setOrderNo(orderNo);
        orderAllModel.setUserId(hostHolder.getUser().getId());
        orderAllModel.setTotalPayment(totalPayment);
        orderAllModel.setCreateTime(new Date());

        //插入数据库
        OrderAllDO orderAllDO = OrderUtils.convertDOFromModel(orderAllModel);
        try{
            orderAllDOMapper.insertSelective(orderAllDO);

        }catch(Exception e){
            throw new BusinessException(EmBusinessError.SQL_ERROR);
        }

        orderAllModel.setId(orderAllDO.getId());

        return this.getOrderModelById(orderAllModel.getId());
    }


    @Override
    @Transactional
    public BigDecimal createOrderItem(List<OrderItemModel> orderItemModelList, String orderNo) throws BusinessException {

        BigDecimal totalPayment = new BigDecimal(0);
        for(OrderItemModel orderItemModel : orderItemModelList){
            orderItemModel.setOrderNo(orderNo);
            orderItemModel.setUserId(hostHolder.getUser().getId());
            BigDecimal currentEachPrice = orderItemModel.getCurrenrEachPrice();
            BigDecimal quantity = new BigDecimal(orderItemModel.getQuantity());
            BigDecimal totalPrice = currentEachPrice.multiply(quantity);
            orderItemModel.setTotalPrice(totalPrice);
            orderItemModel.setCreateTime(new Date());

            // 计算订单总价
            totalPayment = totalPayment.add(totalPrice);

            // 插入数据库
            OrderItemDO orderItemDO = OrderUtils.convertItemDOFromModel(orderItemModel);
            try {
                orderItemDOMapper.insertSelective(orderItemDO);
            }catch(Exception e){
                throw new BusinessException(EmBusinessError.SQL_ERROR);
            }

            orderItemModel.setId(orderItemDO.getId());

        }

        return totalPayment;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 保证订单流水号的自增序列全局唯一
    public String generateOrderNo(){
        // 订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        // 前8位时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        // 中间为自增序列
        // 获取当前sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        // 取出序号
        sequence = sequenceDO.getCurrentValue();
        // 跳增步长，以便下次取出
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        // 拼接
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6-sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        // 最后2位为扩展位，暂时写死
        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    @Override
    public OrderAllModel getOrderModelById(Integer id) {

        OrderAllDO orderAllDO = orderAllDOMapper.selectByPrimaryKey(id);
        if(orderAllDO == null){
            return null;
        }

        OrderAllModel orderAllModel = OrderUtils.convertModelFromDO(orderAllDO);

        return orderAllModel;
    }
}
