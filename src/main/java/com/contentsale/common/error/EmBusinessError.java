package com.contentsale.common.error;

/**
 * Created by wss on 2019/1/10.
 */
public enum EmBusinessError implements CommonError{

    // 通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),


    // 20000开头为登录相关错误定义
    LOGIN_USERNAME_EMPTY(20001,"用户名为空"),
    LOGIN_PASSWORD_EMPTY(20002,"密码为空"),
    USER_LOGIN_FAIL(20003,"登录失败");

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
