package com.contentsale.service.impl;

import com.alibaba.fastjson.JSON;
import com.contentsale.common.error.BusinessException;
import com.contentsale.common.error.EmBusinessError;
import com.contentsale.controller.FinanceController;
import com.contentsale.dao.CartDOMapper;
import com.contentsale.dataobject.CartDO;
import com.contentsale.interceptor.model.HostHolder;
import com.contentsale.service.CartService;
import com.contentsale.service.model.CartModel;
import com.contentsale.service.model.OrderItemModel;
import com.contentsale.utils.CartUtils;
import com.contentsale.utils.JedisAdapter;
import com.contentsale.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wss on 2019/1/19.
 */

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

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

            // 检查数据库购物车里是否已存在该商品
            CartDO cartDOForCheck = cartDOMapper.selectByItemIdAndUserId(cartModel.getItemId(), userId);

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

            //清除缓存中购物车的数据
            String cartKey = RedisKeyUtil.getCartKey(userId);
            Boolean resultFlush = flushCache(cartKey);
            if(resultFlush.equals(Boolean.FALSE)){
                return false;
            }

            return true;
        }catch (Exception e){
            return false;
        }


//        //返回创建完的对象
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

                cartModelList = getCartModelListFromCache(cartKey);

            } else { //当缓存中没有时，才从数据库读取

                cartModelList = getCartModelListFromDb(cartKey);

                //读取出后存入缓存，以便下次查看
                for (CartModel cartModel : cartModelList) {
                    jedisAdapter.lpush(cartKey, JSON.toJSONString(cartModel));
                }
            }
            return cartModelList;
        }catch(Exception e){
            logger.error("查看购物车异常" , e.getMessage());
            return null;
        }

    }

    //删除购物车商品
    @Override
    @Transactional(propagation = Propagation.NESTED)  //在SettleAccount的子事务中进行
    public Boolean deleteCartItem(List<OrderItemModel> orderItemModelList) throws BusinessException {
        try{
            for(OrderItemModel orderItemModel : orderItemModelList){

                Integer itemId = orderItemModel.getItemId();
                try{
                    cartDOMapper.deleteByUserAndItem(hostHolder.getUser().getId(), itemId);
                }catch (Exception e){
                    throw new BusinessException(EmBusinessError.SQL_ERROR);
                }
            }
            return true;
        }catch (Exception e){
            logger.error("删除购物车数据异常" , e.getMessage());
            return false;
        }

    }

    // 将缓存的数据同步到数据库
    @Override
    public Boolean updateCacheToDb(Integer userId) {

        try{
            String cartKey = RedisKeyUtil.getCartKey(userId);

            if (jedisAdapter.exists(cartKey)) {

                List<CartModel> cartModelList = getCartModelListFromCache(cartKey);
                if(cartModelList.size() != 0){
                    List<CartDO> cartDOList = null;
                    cartDOList = cartModelList.stream().map(cartModel -> {
                        CartDO cartDO = CartUtils.convertCartDOFromCartModel(cartModel);
                        return cartDO;
                    }).collect(Collectors.toList());

                    for (CartDO cartDO : cartDOList){
                        cartDOMapper.replaceIntoPreventDup(cartDO);
                    }
                }
            }
            return true;
        }catch (Exception e){
            logger.error("同步购物车缓存数据到数据库异常" , e.getMessage());
            return false;
        }
    }

    //将数据库内容刷新到缓存
    @Override
    public Boolean updateDbToCache(Integer userId) {
        try{
            List<CartModel> cartModelList = null;

            String cartKey = RedisKeyUtil.getCartKey(userId);
            cartModelList = getCartModelListFromDb(cartKey);

            //读取出后存入缓存，以便下次查看
            for (CartModel cartModel : cartModelList) {
                jedisAdapter.lpush(cartKey, JSON.toJSONString(cartModel));
            }
            return true;
        }catch (Exception e){
            logger.error("同步购物车数据库数据到缓存异常" , e.getMessage());
            return false;
        }
    }

    //从缓存得到购物车数据
    @Override
    public List<CartModel> getCartModelListFromCache(String cartKey) {
        int len = (int) jedisAdapter.llen(cartKey);
        List<String> cartItemString = jedisAdapter.lrange(cartKey, 0, len - 1);

        List<CartModel> cartModelList = null;
        cartModelList = cartItemString.stream().map(itemString -> {
            CartModel itemModel = JSON.parseObject(itemString, CartModel.class);
            return itemModel;
        }).collect(Collectors.toList());

        return cartModelList;
    }

    //从数据库得到购物车数据
    @Override
    public List<CartModel> getCartModelListFromDb(String cartKey) {
        List<CartDO> cartDOList = cartDOMapper.listItem(hostHolder.getUser().getId());

        List<CartModel> cartModelList = null;
        cartModelList = cartDOList.stream().map(cartDO -> {
            CartModel itemModel = CartUtils.convertModelFromDataObject(cartDO);
            return itemModel;
        }).collect(Collectors.toList());

        return cartModelList;
    }

    //清空购物车缓存
    @Override
    public Boolean flushCache(String cartKey) {
        try{
            jedisAdapter.del(cartKey);
            return true;
        }catch (Exception e){
            logger.error("清空购物车缓存异常" , e.getMessage());
            return false;
        }

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
