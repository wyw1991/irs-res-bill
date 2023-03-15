package com.dtzhejiang.irs.res.bill.infra.gateway;

import com.bees.shirocas.principal.UserPrincipal;
import com.dtzhejiang.common.entity.resp.JsonResult;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.RoleInfo;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.User;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.usercenter.client.RoleClient;
import com.dtzhejiang.usercenter.client.UserClient;
import com.dtzhejiang.usercenter.client.dto.rep.RoleRep;
import com.dtzhejiang.usercenter.client.dto.rep.UserDetailRep;
import com.dtzhejiang.usercenter.client.dto.req.GetRoleByPermissionReq;
import com.dtzhejiang.usercenter.client.dto.req.UserQueryReq;
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

    @Autowired
    private UserClient userClient;

    @Override
    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityUtils.getSubject().getPrincipal();
        return User.builder()
                .userName(userPrincipal.getUserName())
                .displayName(userPrincipal.getDisplayName())
                .build();
    }

    @Override
    public UserInfo getUserInfo() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityUtils.getSubject().getPrincipal();
        UserQueryReq userReq = new UserQueryReq();
        userReq.setUserName(userPrincipal.getUserName());
        userReq.setSystemCode("irs-res-bill");
        JsonResult<UserDetailRep> jsonResult = userClient.queryUser(userReq);
        UserDetailRep userDetailRep = jsonResult.getData();
        return UserInfo.builder()
                .userName(userDetailRep.getUserName())
                .displayName(userDetailRep.getDisplayName())
                .roleCodes(userDetailRep.getRoles())
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
