package com.dtzhejiang.irs.res.bill.domain.process.valueobject;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * 流程实例
 */
@Getter
@Builder
public class ProcessInstance implements Serializable {

    private static final long serialVersionUID = -4337552607986958934L;

    /**
     * 流程id
     */
    private final String processId;
    /**
     * 状态
     */
    private final String status;

}
