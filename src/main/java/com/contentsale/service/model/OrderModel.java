package com.contentsale.service.model;

import javafx.scene.layout.BackgroundImage;
import org.omg.PortableInterceptor.INACTIVE;

import java.math.BigDecimal;

/**
 * Created by wss on 2019/1/19.
 */

// 用户下单的交易模型
public class OrderModel {

    // 交易订单号
    private String id;

    // 购买的用户id
    private Integer userId;

    // 商家id
    private Integer sellerId;

    // 购买时的单价
    private BigDecimal itemPrice;

    // 购买的商品id
    private Integer itemId;

    // 购买的数量
    private Integer amount;

    // 交易金额
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
}
