package com.contentsale.interceptor.model;

import com.contentsale.controller.viewobject.FinanceVO;
import com.contentsale.controller.viewobject.ItemVO;
import com.contentsale.controller.viewobject.UserVO;
import com.contentsale.dataobject.UserDO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class HostHolder {

    // 为每一线程单独存取当前登录的user变量
    private static ThreadLocal<UserVO> users = new ThreadLocal<>();

    public UserVO getUser(){
        return users.get();
    }

    public void setUsers(UserVO user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }

    // 为每一线程存取买家已买的商品
    private static ThreadLocal<List<Integer>> itemBoughtList = new ThreadLocal<>();

    public List<Integer> getItemBoughtList(){
        return itemBoughtList.get();
    }

    public void setItemBoughtList(List<Integer> list){
        itemBoughtList.set(list);
    }

    // 价格映射
    private static ThreadLocal<Map<String, String>> priceMap = new ThreadLocal<>();

    public Map<String, String> getPriceMap(){
        return priceMap.get();
    }

    public void setPriceMap(Map<String, String> map){
        priceMap.set(map);
    }


    // 为每一线程存取卖家已售的商品
    private static ThreadLocal<List<Integer>> itemSoldList = new ThreadLocal<>();

    public List<Integer> getItemSoldList(){
        return itemSoldList.get();
    }

    public void setItemSoldList(List<Integer> list){
        itemSoldList.set(list);
    }
}
