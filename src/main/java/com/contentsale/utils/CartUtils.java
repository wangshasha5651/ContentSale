package com.contentsale.utils;

import com.contentsale.controller.viewobject.CartVO;
import com.contentsale.dataobject.CartDO;
import com.contentsale.service.model.CartModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by wss on 2019/1/19.
 */

@Component
public class CartUtils {

    public static CartDO convertCartDOFromCartModel(CartModel cartModel){
        if(cartModel == null){
            return null;
        }
        CartDO cartDO = new CartDO();
        BeanUtils.copyProperties(cartModel, cartDO);

        // 由于数据库中price是double类型，model中定义为bigdecimal，直接用BeanUtils的copyProperties方法的可能会有精度损失
        cartDO.setCurrentPrice(cartModel.getCurrentPrice().doubleValue());

        return cartDO;
    }

    public static CartModel convertModelFromDataObject(CartDO cartDO){
        CartModel cartModel = new CartModel();
        BeanUtils.copyProperties(cartDO, cartModel);
        cartModel.setCurrentPrice(new BigDecimal(cartDO.getCurrentPrice()));

        return cartModel;
    }

    public static CartVO convertVOFromModel(CartModel cartModel){
        if(cartModel == null){
            return null;
        }

        CartVO cartVO = new CartVO();
        BeanUtils.copyProperties(cartModel, cartVO);

        return cartVO;
    }

}
