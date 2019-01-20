package com.contentsale.controller.viewobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wss on 2019/1/20.
 */
public class FinanceVO {

    // 财务项目id
    private Integer id;

    // 所售商品id
    private Integer itemId;

    // 所售商品名称
    private String itemName;

    // 所售商品图片url
    private String itemImgUrl;

    // 购买时间
    private String paymentTime;

    // 购买数量
    private Integer quantity;

    // 购买时的单价
    private BigDecimal eachPrice;

    // 单类商品购买总价
    private BigDecimal totalPrice;

    // 财务中所有商品购买的总价
    private static BigDecimal allItemPrice = new BigDecimal(0);

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

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
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

    public static BigDecimal getAllItemPrice() {
        return allItemPrice;
    }

    public static void setAllItemPrice(BigDecimal allItemPrice) {
        FinanceVO.allItemPrice = allItemPrice;
    }
}
