package com.dtzhejiang.irs.res.bill.domain.user.gateway;

import com.dtzhejiang.irs.res.bill.domain.user.valueobject.RoleInfo;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.User;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;

import java.util.List;

public interface UserGateway {
    User getCurrentUser();
    UserInfo getUserInfo(String userName);
    List<RoleInfo> getRolesByPermission(String permissionCode);
}
