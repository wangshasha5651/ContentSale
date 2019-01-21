package com.contentsale.service.model;

import javafx.scene.layout.BackgroundImage;
import org.omg.PortableInterceptor.INACTIVE;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wss on 2019/1/19.
 */

// 用户下单的交易模型
public class OrderModel {

    // 订单序号
    private Integer id;

    // 交易订单流水号
    private String orderNo;

    // 购买的用户id
    private Integer userId;

    // 订单总额
    private BigDecimal totalPayment;

    // 创建日期
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

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
