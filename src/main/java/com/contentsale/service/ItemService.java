package com.contentsale.service;

import com.contentsale.common.error.BusinessException;
import com.contentsale.service.model.ItemModel;
import com.contentsale.service.model.OrderItemModel;

import java.util.List;
import java.util.Map;


/**
 * Created by wss on 2019/1/16.
 */

public interface ItemService {

    // 创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    // 商品列表浏览
    List<ItemModel> listItem();

    // 未购买商品的列表浏览
    List<ItemModel> listNotBoughtItem(List<Integer> itemBoughtIdList);

    // 获取商品详情
    ItemModel getItemById(Integer id);

    // 修改商品信息
    Boolean editItem(ItemModel itemModel) throws BusinessException;

    // 更改商品销量
    Boolean changeSales(List<OrderItemModel> orderItemModelList) throws BusinessException;

    // 删除商品
    Boolean deleteItem(Integer id) throws BusinessException;

    //未登录时 获取主页静态页面
    String getIndexHtmlTextNotLogin() throws Exception;

    //已登录后 获取主页静态页面
    String getIndexHtmlTextIfLogined(Map<String, Object> map) throws Exception;

}
