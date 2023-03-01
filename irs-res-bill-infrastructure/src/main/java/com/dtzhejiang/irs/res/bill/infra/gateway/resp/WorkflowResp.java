package com.dtzhejiang.irs.res.bill.infra.gateway.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkflowResp<T> implements Serializable {
    private static final long serialVersionUID = -1759345425591680897L;

    private String code;
    private String msg;
    private Boolean success;
    private T data;
}
