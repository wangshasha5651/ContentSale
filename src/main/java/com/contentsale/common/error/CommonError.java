package com.contentsale.common.error;

/**
 * Created by wss on 2019/1/10.
 */
public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);

}
