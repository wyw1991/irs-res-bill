package com.dtzhejiang.irs.res.bill.domain.user.valueobject;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
public class UserInfo extends User{
    /**
     * 角色
     */
    private Set<String> roleCodes;
    /**
     * 权限
     */
    private Set<String> permissionList;
    /**
     * 当前权限列表
     */
    private String currentPermission;


    /**
     * 是否是应用管理员
     */
    public boolean isAppManager(){
        return roleCodes.contains(UserRole.APP_MANAGER.getCode());
    }

    /**
     * 是否是合规性确认员
     */
    public boolean isComplianceConfirmer(){
        return roleCodes.contains(UserRole.COMPLIANCE_CONFIRM.getCode());
    }

    /**
     * 是否是合规终审员
     */
    public boolean isComplianceLeader(){
        return roleCodes.contains(UserRole.COMPLIANCE_LEADER.getCode());
    }

    /**
     * 是否是应用审核终审员
     */
    public boolean isBusinessLeader(){
        return roleCodes.contains(UserRole.BUSINESS_LEADER.getCode());
    }

}
