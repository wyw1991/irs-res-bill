package com.dtzhejiang.irs.res.bill.domain.user.valueobject;

import lombok.Builder;
import lombok.Data;

/**
 * 角色信息
 * @Company: 数字浙江
 * @Author: zhangming
 * @Date 2023/3/7 19:47
 */
@Data
@Builder
public class RoleInfo {
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
}
