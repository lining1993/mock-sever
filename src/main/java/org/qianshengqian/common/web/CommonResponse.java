package org.qianshengqian.common.web;

/**
 * Created by lining on 2017/8/21.
 */
public class CommonResponse extends BaseResponse {

    private boolean success;
    private String message;
    private Object data;

    public CommonResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static CommonResponse ReturnCommonBaseResponse(boolean success, String message, Object data){
        return new CommonResponse(success,message,data);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
