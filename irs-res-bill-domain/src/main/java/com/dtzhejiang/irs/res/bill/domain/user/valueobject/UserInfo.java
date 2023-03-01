package com.dtzhejiang.irs.res.bill.domain.user.valueobject;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserInfo {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户姓名
     */
    private String displayName;
    /**
     * 角色
     */
    private Set<String> roleCodes;
    /**
     * 权限
     */
    private Set<String> permissionList;
}
