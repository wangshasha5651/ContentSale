package com.contentsale.service;

import com.contentsale.common.error.BusinessException;
import com.contentsale.service.model.FinanceModel;
import com.contentsale.service.model.OrderItemModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Created by wss on 2019/1/20.
 */
public interface FinanceService {

    List<FinanceModel> createFinaceItem(List<OrderItemModel> orderItemModelList) throws BusinessException;

    List<FinanceModel> listFinanceItem(Integer userId);

    List<Integer> getBoughtIdList(Integer userId);

    Map<String, BigDecimal> getBoughtPriceMap(Integer userId);

    List<Integer> getSoldIdList(Integer userId);
}
