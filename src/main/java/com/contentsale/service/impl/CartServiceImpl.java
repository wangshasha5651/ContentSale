package com.contentsale.service.impl;

import com.alibaba.fastjson.JSON;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.controller.viewobject.CartVO;
import com.contentsale.dao.CartDOMapper;
import com.contentsale.dataobject.CartDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.CartService;
import com.contentsale.service.model.CartModel;
import com.contentsale.service.model.OrderItemModel;
import com.contentsale.utils.CartUtils;
import com.contentsale.utils.JedisAdapter;
import com.contentsale.utils.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/19.
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDOMapper cartDOMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    @Transactional // 在事务中进行
    public Boolean addToCart(CartModel cartModel) {

        try{
            Integer userId = hostHolder.getUser().getId();

            String cartKey = RedisKeyUtil.getCartKey(userId);

            Boolean existFlag = false;
            // 检查缓存中的购物车列表是否已有相同商品
            if(jedisAdapter.exists(cartKey)){
                int len = (int)jedisAdapter.llen(cartKey);
                if(len != 0) {
                    List<String> cartItemString = jedisAdapter.lrange(cartKey, 0, len - 1);

                    int index = 0;
                    for (String itemString : cartItemString) {
                        CartModel item = JSON.parseObject(itemString, CartModel.class);

                        //若缓存中购物车已存在该商品，则增加其数量
                        if (item.getItemId().equals(cartModel.getItemId())) {
                            existFlag = true;
                            item.setQuantity(item.getQuantity() + cartModel.getQuantity());
                            //更新到缓存中
                            jedisAdapter.lset(cartKey, index, JSON.toJSONString(item));
                        }
                        index++;
                    }
                }
            }

            //若不存在，则存入缓存
            if(existFlag.equals(Boolean.FALSE)){
                jedisAdapter.lpush(cartKey, JSON.toJSONString(cartModel));
            }
            return true;
        }catch (Exception e){
            return false;
        }


//        // 检查数据库购物车里是否已存在该商品
//        CartDO cartDOForCheck = cartDOMapper.selectByItemIdAndUserId(cartModel.getItemId(), userId);
//
//        // 如果购物车不存在该商品，则添加记录
//        if(cartDOForCheck == null){
//            // 转化cartModel->cartDO
//            CartDO cartDO = CartUtils.convertCartDOFromCartModel(cartModel);
//
//            // 单独设置创建时间
//            cartDO.setCreateTime(new Date());
//
//            // 写入数据库
//            cartDOMapper.insertSelective(cartDO);
//            cartModel.setId(cartDO.getId());
//
//        }else{
//            // 如果购物车存在该商品，则在和原来的基础上增加新添加的数量
//            cartDOForCheck.setQuantity(cartDOForCheck.getQuantity() + cartModel.getQuantity());
//            cartDOMapper.updateByPrimaryKeySelective(cartDOForCheck);
//
//            cartModel.setId(cartDOForCheck.getId());
//        }

        //返回创建完的对象
//        return this.getCartItemById(cartModel.getId());
    }

    // 查看购物车
    @Override
    public List<CartModel> listCartItem(Integer userId) {

        try {
            List<CartModel> cartModelList = null;

            //先查找缓存中的数据
            String cartKey = RedisKeyUtil.getCartKey(userId);
            if (jedisAdapter.exists(cartKey)) {
                int len = (int) jedisAdapter.llen(cartKey);
                List<String> cartItemString = jedisAdapter.lrange(cartKey, 0, len - 1);

                cartModelList = cartItemString.stream().map(itemString -> {
                    CartModel itemModel = JSON.parseObject(itemString, CartModel.class);
                    return itemModel;
                }).collect(Collectors.toList());

            } else { //当缓存中没有时，才从数据库读取
                List<CartDO> cartDOList = cartDOMapper.listItem(userId);

                cartModelList = cartDOList.stream().map(cartDO -> {
                    CartModel itemModel = CartUtils.convertModelFromDataObject(cartDO);
                    return itemModel;
                }).collect(Collectors.toList());

                //读取出后存入缓存，以便下次查看
                for (CartModel cartModel : cartModelList) {
                    jedisAdapter.lpush(cartKey, JSON.toJSONString(cartModel));
                }

            }
            return cartModelList;
        }catch(Exception e){
            return null;
        }

    }

    @Override
    @Transactional(propagation = Propagation.NESTED)  //在SettleAccount的子事务中进行
    public Boolean deleteCartItem(List<OrderItemModel> orderItemModelList) throws BusinessException {


        for(OrderItemModel orderItemModel : orderItemModelList){

            Integer itemId = orderItemModel.getItemId();
            try{
                cartDOMapper.deleteByUserAndItem(hostHolder.getUser().getId(), itemId);
            }catch (Exception e){
                throw new BusinessException(EmBusinessError.SQL_ERROR);
            }
        }
        return true;
    }


    @Override
    public CartModel getCartItemById(Integer id) {
        CartDO cartDO = cartDOMapper.selectByPrimaryKey(id);
        if(cartDO == null){
            return null;
        }

        CartModel cartModel = CartUtils.convertModelFromDataObject(cartDO);

        return cartModel;
    }
}
