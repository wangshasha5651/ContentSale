package com.contentsale.common.error;

/**
 * Created by wss on 2019/1/10.
 */
public enum EmBusinessError implements CommonError{

    // 通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    SQL_ERROR(10002,"数据库操作异常"),


    // 20000开头为登录相关错误定义
    LOGIN_USERNAME_EMPTY(20001,"用户名不为空"),
    LOGIN_PASSWORD_EMPTY(20002,"密码不为空"),
    USER_LOGIN_FAIL(20003,"登录失败"),


    // 30000开头为创建item相关错误定义
    ITEM_TITLE_EMPTY(30001,"商品名称不为空"),
    ITEM_SUMMARY_EMPTY(30002,"商品摘要不为空"),
    ITEM_IMG_EMPTY(30003,"商品图片不为空"),
    ITEM_DESCRIPTION_EMPTY(30004,"商品正文不为空"),
    ITEM_PRICE_EMPTY(30005,"商品价格不为空"),
    DOWNLOAD_IMG_FAIL(30003,"图片下载失败"),


    // 40000开头为创建order相关错误定义
    ORDER_CHANGE_SALES_ERROR(40001,"下单时商品销量更改服务出错"),
    ORDER_FINANCE_ERROR(40002,"下单时财务服务出错"),
    ORDER_CART_ERROR(40003,"下单时购物车服务出错"),
    USER_NOT_EXIST(40004, "用户不存在"),
    ORDER_CREATE_ERROR(40003,"创建订单出错"),

    // 50000开头为购物车错误
    ADD_TO_CART_ERROR(50001, "添加购物车失败"),
    CHECK_CART_ERROR(50002, "查看购物车失败");


    private int errCode;
    private String errMsg;

    private EmBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }}
