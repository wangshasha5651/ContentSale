package com.contentsale.common.error;

/**
 * Created by wss on 2019/1/10.
 */
public enum EmBusinessError implements CommonError{

    // 通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),


    // 20000开头为登录相关错误定义
    LOGIN_USERNAME_EMPTY(20001,"用户名不为空"),
    LOGIN_PASSWORD_EMPTY(20002,"密码不为空"),
    USER_LOGIN_FAIL(20003,"登录失败"),


    // 30000开头为创建item相关错误定义
    ITEM_TITLE_EMPTY(30001,"商品名称不为空"),
    ITEM_SUMMARY_EMPTY(30002,"商品摘要不为空"),
    ITEM_IMG_EMPTY(30003,"商品图片不为空"),
    ITEM_DESCRIPTION_EMPTY(30004,"商品正文不为空"),
    ITEM_PRICE_EMPTY(30005,"商品价格不为空");


  //  USER_NOT_EXIST(20004,"用户不存在");


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
