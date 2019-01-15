package com.contentsale.common.error;

/**
 * Created by wss on 2019/1/10.
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);

}
