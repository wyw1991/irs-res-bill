package com.dtzhejiang.irs.res.bill.domain.user.valueobject;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
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
