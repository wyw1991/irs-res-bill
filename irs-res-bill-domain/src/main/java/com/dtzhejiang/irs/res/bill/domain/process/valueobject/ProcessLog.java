package com.dtzhejiang.irs.res.bill.domain.process.valueobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程日志
 */
@Data
public class ProcessLog implements Serializable{

    private static final long serialVersionUID = -8008250610666058694L;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 操作时间
     */
    private Date createTime;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 角色
     */
    private String roleName;
    /**
     * 部门名
     */
    private String deptName;
    /**
     * 区划名
     */
    private String regionName;
    /**
     * 操作名
     */
    private String opName;
    /**
     * 代理人信息
     */
    private List<AssigneeInfoResp> assignees;
    /**
     * 业务信息
     */
    private Map<String, Object> bizInfo;

    @Data
    public static class AssigneeInfoResp implements Serializable{
        private static final long serialVersionUID = 8179891332468112259L;
        /**
         * 代理人
         */
        private String assignee;
        /**
         * 部门id
         */
        private Long deptId;
        /**
         * 部门名
         */
        private String deptName;
        /**
         * 区划码
         */
        private String regionCode;
        /**
         * 区划名
         */
        private String regionName;
    }
}
