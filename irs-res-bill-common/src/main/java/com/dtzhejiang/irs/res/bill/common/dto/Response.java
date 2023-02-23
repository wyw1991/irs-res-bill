package com.dtzhejiang.irs.res.bill.common.dto;

/**
 * Response to caller
 */
public class Response {

    /**
     * 响应是否成功
     */
    private boolean success;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;


    public static Response buildSuccess() {
        Response response = new Response();
        response.setCode("200");
        response.setSuccess(true);
        return response;
    }

    public static Response buildFailure(String code, String msg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

    @Override
    public String toString() {
        return "Response [success=" + this.success + ", errCode=" + this.code + ", errMessage=" + this.message +"]";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
