package com.contentsale.service.impl;

import com.contentsale.dao.FinanceDOMapper;
import com.contentsale.dao.ItemDOMapper;
import com.contentsale.dataobject.FinanceDO;
import com.contentsale.dataobject.ItemDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.FinanceService;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.utils.FinanceUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private ItemDOMapper itemDOMapper;

    @Autowired
    private FinanceDOMapper financeDOMapper;


    // 新增财务条目
    @Override
    @Transactional // 在事务中进行
    public List<FinanceModel> createFinaceItem(List<Map<String, String>> paramList) {



        List<FinanceDO> financeDOList = new ArrayList<>();
        for(Map<String, String> param : paramList){
            // 入参校验
            if(param.get("id") == null || StringUtils.isEmpty(param.get("id"))){
                return null;
            }
            if(param.get("quantity") == null || StringUtils.isEmpty(param.get("quantity"))){
                return null;
            }
            if(param.get("price") == null || StringUtils.isEmpty(param.get("price"))){
                return null;
            }

            //转成DataObject
            ItemDO itemDO = itemDOMapper.selectByPrimaryKey(Integer.valueOf(param.get("id")));

            FinanceDO financeDO = new FinanceDO();

            financeDO.setItemId(itemDO.getId());
            financeDO.setItemName(itemDO.getTitle());
            financeDO.setItemImgUrl(itemDO.getImgUrl());
            financeDO.setPaymentTime(new Date());
            Integer quantity = Integer.valueOf(param.get("quantity"));
            Double eachPrice = Double.valueOf(param.get("price"));
            Double totalPrice = eachPrice * quantity;
            financeDO.setQuantity(quantity);
            financeDO.setEachPrice(eachPrice);
            financeDO.setTotalPrice(totalPrice);
            financeDO.setBuyerId(hostHolder.getUser().getId());
            financeDO.setSellerId(itemDO.getSellerId());

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
