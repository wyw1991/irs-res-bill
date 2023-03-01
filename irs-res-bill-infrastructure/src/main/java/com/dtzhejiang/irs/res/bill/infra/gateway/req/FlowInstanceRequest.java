package com.dtzhejiang.irs.res.bill.infra.gateway.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowInstanceRequest implements Serializable {
    private static final long serialVersionUID = 43760884904146534L;

    /** 流程定义key，必须 */
    private String processDefinitionKey;
    /** 业务key，必须 */
    private String businessKey;
    /** 表单变量 */
//    private StartFormVariables startFormVariables;
    /** 操作类型 */
    private Map<String,Object> variables;

}
