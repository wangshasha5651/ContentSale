package com.contentsale.service;

import com.contentsale.common.error.BusinessException;
import com.contentsale.controller.viewobject.CartVO;
import com.contentsale.service.model.CartModel;
import com.contentsale.service.model.OrderItemModel;

import java.util.List;
import java.util.Map;

/**
 * Created by wss on 2019/1/19.
 */
public interface CartService {

    //添加到购物车
    CartModel addToCart(CartModel cartModel);

    List<CartModel> listCartItem(Integer userId);

    Boolean deleteCartItem(List<OrderItemModel> orderItemModelList) throws BusinessException;

    CartModel getCartItemById(Integer id);
}
