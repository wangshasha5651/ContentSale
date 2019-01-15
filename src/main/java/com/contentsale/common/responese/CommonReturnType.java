package com.contentsale.common.responese;

/**
 * Created by wss on 2019/1/10.
 */
public class CommonReturnType {
    // 对应请求的返回处理结果状态：sucess或fail
    private String status;

    // 若status=success，则data内返回前端需要的jason数据
    // 若status=fail，则data内使用通用的错误码信息
    private Object data;

    // 一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setData(result);
        commonReturnType.setStatus(status);
        return commonReturnType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
