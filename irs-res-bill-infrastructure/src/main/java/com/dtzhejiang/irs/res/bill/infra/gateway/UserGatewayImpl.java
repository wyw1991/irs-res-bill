package com.dtzhejiang.irs.res.bill.infra.gateway;

import com.bees.shirocas.principal.UserPrincipal;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserGatewayImpl implements UserGateway {

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
}
