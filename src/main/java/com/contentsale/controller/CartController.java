package com.contentsale.controller;

import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.controller.viewobject.CartVO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.impl.CartServiceImpl;
import com.contentsale.service.model.CartModel;
import com.contentsale.utils.CartUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/19.
 */

@Controller("/cart")
@RequestMapping("/cart")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "true")
public class CartController extends BaseController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CartServiceImpl cartService;

    // 添加到购物车
    @RequestMapping(value="/add",method = {RequestMethod.POST})
    @ResponseBody
    public String createItem(@RequestParam("itemTitle") String itemTitle,
                                   @RequestParam("itemId") Integer itemId,
                                   @RequestParam("quantity") Integer quantity,
                                   @RequestParam("currentPrice") BigDecimal currentPrice,
                                   ModelAndView modelAndView) throws BusinessException {

        if (StringUtils.isEmpty(itemTitle) || itemId == null || quantity == null || currentPrice == null) {
            throw new BusinessException(EmBusinessError.ADD_TO_CART_ERROR);
        }

        CartModel cartModel = new CartModel();
        cartModel.setItemId(itemId);
        cartModel.setItemTitle(itemTitle);
        cartModel.setUserId(hostHolder.getUser().getId());
        cartModel.setQuantity(quantity);
        cartModel.setCurrentPrice(currentPrice);
        cartModel.setCreateTime(new Date());

        Boolean result = cartService.addToCart(cartModel);
        if(result.equals(Boolean.FALSE)){
            throw new BusinessException(EmBusinessError.ADD_TO_CART_ERROR);
        }

//            CartModel cartModelForReturn = cartService.addToCart(cartModel);
//            CartVO cartVO = CartUtils.convertVOFromModel(cartModelForReturn);
//
//            modelAndView.addObject("cartVO", cartVO);

        return "success";
    }

    // 查看购物车
    @RequestMapping(value="/show", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView listCart(ModelAndView modelAndView) throws BusinessException {

        List<CartModel> cartModelList = cartService.listCartItem(hostHolder.getUser().getId());

        if(cartModelList == null){
            throw new BusinessException(EmBusinessError.CHECK_CART_ERROR);
        }

        // 判断购物车是否为空
        if(cartModelList.size() == 0){
            modelAndView.setViewName("settleAccount");
            return modelAndView;
        }else{
            // 使用stream api将list内的itemModel转化为itemVO
            List<CartVO> cartVOList = cartModelList.stream().map(cartModel -> {
                CartVO cartVO = CartUtils.convertVOFromModel(cartModel);
                return cartVO;
            }).collect(Collectors.toList());

            modelAndView.addObject("cartList", cartVOList);
            modelAndView.setViewName("settleAccount");
        }

        return modelAndView;
    }

}
