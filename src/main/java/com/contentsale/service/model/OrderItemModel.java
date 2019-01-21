package com.contentsale.service.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wss on 2019/1/21.
 */
public class OrderItemModel {

    private Integer id;

    // 所关联的订单流水号
    private String orderNo;

    // 购买者id
    private Integer userId;

    // 商品id
    private Integer itemId;

    // 商品名称
    private String itemName;

    // 当时购买的单价
    private BigDecimal currenrEachPrice;

    // 购买数量
    private Integer quantity;

    // 该类别商品的总价
    private BigDecimal totalPrice;

    // 创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public BigDecimal getCurrenrEachPrice() {
        return currenrEachPrice;
    }

    public void setCurrenrEachPrice(BigDecimal currenrEachPrice) {
        this.currenrEachPrice = currenrEachPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
