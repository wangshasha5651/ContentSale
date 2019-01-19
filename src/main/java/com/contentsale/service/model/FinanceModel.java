package com.contentsale.service.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wss on 2019/1/19.
 */
public class FinanceModel {

    // 财务项目id
    private Integer id;

    // 所售商品id
    private Integer itemId;

    // 所售商品名称
    private String itemName;

    // 所售商品图片url
    private String itemImgUrl;

    // 购买时间
    private Date paymentTime;

    // 购买数量
    private Integer quantity;

    // 购买时的单价
    private BigDecimal eachPrice;

    // 购买总价
    private BigDecimal totalPrice;

    // 购买者id
    private Integer buyerId;

    // 出售者id
    private Integer sellerId;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImgUrl() {
        return itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.itemImgUrl = itemImgUrl;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getEachPrice() {
        return eachPrice;
    }

    public void setEachPrice(BigDecimal eachPrice) {
        this.eachPrice = eachPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }
}
