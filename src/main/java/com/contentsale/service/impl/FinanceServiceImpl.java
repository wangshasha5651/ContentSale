package com.contentsale.service.impl;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.dao.FinanceDOMapper;
import com.contentsale.dao.ItemDOMapper;
import com.contentsale.dataobject.FinanceDO;
import com.contentsale.dataobject.ItemDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.FinanceService;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.service.model.ItemModel;
import com.contentsale.service.model.OrderItemModel;
import com.contentsale.utils.FinanceUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/20.
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FinanceDOMapper financeDOMapper;

    @Autowired
    private ItemServiceImpl itemService;

    // 新增财务条目
    @Override
    @Transactional(propagation = Propagation.REQUIRED) // 在事务中进行
    public List<FinanceModel> createFinaceItem(List<OrderItemModel> orderItemModelList) throws BusinessException {

        List<FinanceDO> financeDOList = new ArrayList<>();
        for(OrderItemModel orderItemModel : orderItemModelList){

            //校验商品是否合法
            ItemModel itemModel = itemService.getItemById(orderItemModel.getItemId());
            if(itemModel == null){
                throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
            }

            FinanceDO financeDO = new FinanceDO();

            financeDO.setItemId(itemModel.getId());
            financeDO.setItemName(itemModel.getTitle());
            financeDO.setItemImgUrl(itemModel.getImgUrl());
            financeDO.setPaymentTime(new Date());
            Integer quantity = orderItemModel.getQuantity();
            Double eachPrice = orderItemModel.getCurrenrEachPrice().doubleValue();
            Double totalPrice = eachPrice * quantity;
            financeDO.setQuantity(quantity);
            financeDO.setEachPrice(eachPrice);
            financeDO.setTotalPrice(totalPrice);
            financeDO.setBuyerId(hostHolder.getUser().getId());
            financeDO.setSellerId(itemModel.getSellerId());

            financeDOList.add(financeDO);
        }

        List<FinanceModel> financeModelList = new ArrayList<>();
        for (FinanceDO financeDOForSave : financeDOList){
            financeDOMapper.insertSelective(financeDOForSave);
            FinanceModel financeModel = FinanceUtils.convertModelFromDataObject(financeDOForSave);
            financeModelList.add(financeModel);
        }

        return financeModelList;
    }

    //列出用户所有已买商品
    @Override
    public List<FinanceModel> listFinanceItem(Integer buyerId) {

        List<FinanceDO> financeDOList = financeDOMapper.listItem(buyerId);

        List<FinanceModel> financeModelList = FinanceUtils.convertModelListFromDOList(financeDOList);

        return financeModelList;
    }
}
