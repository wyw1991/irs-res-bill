package com.dtzhejiang.irs.res.bill.domain.user.valueobject;

import com.dtzhejiang.irs.res.bill.common.enums.BillPermissionEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    /**
     * 当前权限列表
     */
    private String currentPermission;


    public List<String> getPermissionList(BillPermissionEnum currentPermission){
        return roleCodes.stream().map(s -> "<irs-res-bill_"+currentPermission+">-"+s).collect(Collectors.toList());
    }

}
