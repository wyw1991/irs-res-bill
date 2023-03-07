package com.dtzhejiang.irs.res.bill.infra.gateway;

import com.bees.shirocas.principal.UserPrincipal;
import com.dtzhejiang.common.entity.resp.JsonResult;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.RoleInfo;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.usercenter.client.RoleClient;
import com.dtzhejiang.usercenter.client.dto.rep.RoleRep;
import com.dtzhejiang.usercenter.client.dto.req.GetRoleByPermissionReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserGatewayImpl implements UserGateway {

    @Autowired
    private RoleClient roleClient;

    @Override
    public UserInfo getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityUtils.getSubject().getPrincipal();
        return UserInfo.builder()
                .userName(userPrincipal.getUserName())
                .displayName(userPrincipal.getDisplayName())
                .roleCodes(userPrincipal.getRoleList())
                .permissionList(userPrincipal.getPermissionList())
                .build();
    }

    @Override
    public List<RoleInfo> getRolesByPermission(String permissionCode) {
        GetRoleByPermissionReq req = new GetRoleByPermissionReq();
        req.setPermissionCode(permissionCode);
        req.setSystemCode("irs-res-bill");
        JsonResult<List<RoleRep>> result = roleClient.filterByPermission(req);
        if(result == null || !result.getSuccess()){
            return new ArrayList<>();
        }
        return result.getData().stream().map(role -> RoleInfo.builder().roleCode(role.getCode()).roleName(role.getName()).build()).collect(Collectors.toList());
    }
}
