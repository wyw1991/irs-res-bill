package com.dtzhejiang.irs.res.bill.domain.user.valueobject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户姓名
     */
    private String displayName;
}
