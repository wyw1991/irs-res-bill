package com.dtzhejiang.irs.res.bill.common.dto;

/**
 * Response with single record to return
 */
public class SingleResponse<T> extends Response {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static SingleResponse<?> buildSuccess() {
        SingleResponse<?> response = new SingleResponse<>();
        response.setSuccess(true);
        return response;
    }

    public static SingleResponse<?> buildFailure(String errCode, String errMessage) {
        SingleResponse<?> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setMessage(errMessage);
        response.setCode(errCode);
        return response;
    }

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setCode("200");
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

}
