package com.contentsale.common.error;

public class GeneralException implements CommonError {

    private int errCode;
    private String errMsg;

    public GeneralException(int errCode, String errMsg){
        super();
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
        return null;
    }

    public void setErrCode(int errCode){
        this.errCode = errCode;
    }
}
