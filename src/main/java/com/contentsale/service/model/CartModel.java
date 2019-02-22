package com.contentsale.service.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wss on 2019/1/19.
 */
public class CartModel {

    //购物车项目id
    private Integer id;

    // 商品id
    private Integer itemId;

    // 商品名称
    private String itemTitle;

    // 购买者id
    private Integer userId;

    // 购物车中的数量
    private Integer quantity;

    // 商品当前单价
    private BigDecimal currentPrice;

    // 创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
