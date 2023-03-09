package com.dtzhejiang.irs.res.bill.domain.process.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 流程任务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessTask implements Serializable {
    private static final long serialVersionUID = 6689875044527880132L;

    private String id;
    private String processId;
    private String name;
    private String category;
    private String currentHandler;
    private String currentHandlerName;
    private List<String> currentGroups;
    private List<String> currentGroupNames;
}
