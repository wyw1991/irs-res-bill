package com.dtzhejiang.irs.res.bill.app.dto;

import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AppInfoDTO {




    /**
     * AppInfo
     */
    private AppInfo appInfo;

    /**
     *
     */
    private Map<SubTypeEnum,String> map;


}