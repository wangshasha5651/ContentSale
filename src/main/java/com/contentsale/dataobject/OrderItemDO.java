package com.contentsale.dataobject;

import java.util.Date;

public class OrderItemDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.order_no
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private String orderNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.user_id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.item_id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private Integer itemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.item_name
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private String itemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.current_each_price
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private Double currentEachPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.quantity
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private Integer quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.total_price
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private Double totalPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.create_time
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.id
     *
     * @return the value of order_item.id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.id
     *
     * @param id the value for order_item.id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.order_no
     *
     * @return the value of order_item.order_no
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.order_no
     *
     * @param orderNo the value for order_item.order_no
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.user_id
     *
     * @return the value of order_item.user_id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.user_id
     *
     * @param userId the value for order_item.user_id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.item_id
     *
     * @return the value of order_item.item_id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.item_id
     *
     * @param itemId the value for order_item.item_id
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.item_name
     *
     * @return the value of order_item.item_name
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.item_name
     *
     * @param itemName the value for order_item.item_name
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.current_each_price
     *
     * @return the value of order_item.current_each_price
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public Double getCurrentEachPrice() {
        return currentEachPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.current_each_price
     *
     * @param currentEachPrice the value for order_item.current_each_price
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setCurrentEachPrice(Double currentEachPrice) {
        this.currentEachPrice = currentEachPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.quantity
     *
     * @return the value of order_item.quantity
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.quantity
     *
     * @param quantity the value for order_item.quantity
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.total_price
     *
     * @return the value of order_item.total_price
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.total_price
     *
     * @param totalPrice the value for order_item.total_price
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_item.create_time
     *
     * @return the value of order_item.create_time
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_item.create_time
     *
     * @param createTime the value for order_item.create_time
     *
     * @mbg.generated Mon Jan 21 18:49:05 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}