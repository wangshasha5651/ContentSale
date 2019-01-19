package com.contentsale.service;

import com.contentsale.controller.viewobject.CartVO;
import com.contentsale.service.model.CartModel;

import java.util.List;

/**
 * Created by wss on 2019/1/19.
 */
public interface CartService {

    //添加到购物车
    CartModel addToCart(CartModel cartModel);

    List<CartModel> listCartItem(Integer userId);

    CartModel getCartItemById(Integer id);
}
