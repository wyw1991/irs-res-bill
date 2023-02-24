package com.dtzhejiang.irs.res.bill.domain.exception;

import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -2098248518233823159L;

    /**
     * 只会打印在日志里的额外业务信息
     */
    private String extraMsg;

    /**
     * 简明业务异常
     * @param msg 展示给用户的信息
     */
    public BusinessException(String msg) {
        super(msg);
    }

    /**
     * 详细业务异常
     * @param msg 展示给用户的信息
     * @param extraMsg 展示在日志里的额外业务信息
     */
    public BusinessException(String msg, String extraMsg) {
        super(msg);
        this.extraMsg = extraMsg;
    }

}
