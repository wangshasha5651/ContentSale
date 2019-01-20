package com.contentsale.service;

import com.contentsale.service.model.FinanceModel;

import java.util.List;
import java.util.Map;

/**
 * Created by wss on 2019/1/20.
 */
public interface FinanceService {

    List<FinanceModel> createFinaceItem(List<Map<String, String>> paramList);

    List<FinanceModel> listFinanceItem(Integer userId);
}
