package com.contentsale.dataobject;

import java.util.Date;

public class OrderAllDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_all.id
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_all.order_no
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    private String orderNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_all.user_id
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_all.total_payment
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    private Double totalPayment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_all.create_time
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_all.id
     *
     * @return the value of order_all.id
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_all.id
     *
     * @param id the value for order_all.id
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_all.order_no
     *
     * @return the value of order_all.order_no
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_all.order_no
     *
     * @param orderNo the value for order_all.order_no
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_all.user_id
     *
     * @return the value of order_all.user_id
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_all.user_id
     *
     * @param userId the value for order_all.user_id
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_all.total_payment
     *
     * @return the value of order_all.total_payment
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public Double getTotalPayment() {
        return totalPayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_all.total_payment
     *
     * @param totalPayment the value for order_all.total_payment
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_all.create_time
     *
     * @return the value of order_all.create_time
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_all.create_time
     *
     * @param createTime the value for order_all.create_time
     *
     * @mbg.generated Tue Jan 22 09:14:59 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}