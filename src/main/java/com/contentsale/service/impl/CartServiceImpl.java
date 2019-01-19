package com.contentsale.service.impl;

import com.contentsale.controller.viewobject.CartVO;
import com.contentsale.dao.CartDOMapper;
import com.contentsale.dataobject.CartDO;
import com.contentsale.service.CartService;
import com.contentsale.service.model.CartModel;
import com.contentsale.utils.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/19.
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDOMapper cartDOMapper;

    @Override
    @Transactional // 在事务中进行
    public CartModel addToCart(CartModel cartModel) {

        // 先验证购物车里是否已存在该商品
        CartDO cartDOForCheck = cartDOMapper.selectByItemId(cartModel.getItemId());

        // 如果购物车不存在该商品，则添加记录
        if(cartDOForCheck == null){
            // 转化cartModel->cartDO
            CartDO cartDO = CartUtils.convertCartDOFromCartModel(cartModel);

            // 单独设置创建时间
            cartDO.setCreateTime(new Date());

            // 写入数据库
            cartDOMapper.insertSelective(cartDO);
            cartModel.setId(cartDO.getId());

        }else{
            // 如果购物车存在该商品，则在和原来的基础上增加新添加的数量
            cartDOForCheck.setQuantity(cartDOForCheck.getQuantity() + cartModel.getQuantity());
            cartDOMapper.updateByPrimaryKeySelective(cartDOForCheck);

            cartModel.setId(cartDOForCheck.getId());
        }

        // 返回创建完的对象
        return this.getCartItemById(cartModel.getId());
    }

    @Override
    public List<CartModel> listCartItem(Integer userId) {

        List<CartDO> cartDOList = cartDOMapper.listItem(userId);

        List<CartModel> cartModelList = cartDOList.stream().map(cartDO -> {
            CartModel itemModel = CartUtils.convertModelFromDataObject(cartDO);
            return itemModel;
        }).collect(Collectors.toList());


        return cartModelList;
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