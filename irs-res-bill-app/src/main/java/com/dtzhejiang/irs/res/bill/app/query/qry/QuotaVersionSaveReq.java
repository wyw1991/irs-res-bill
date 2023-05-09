package com.dtzhejiang.irs.res.bill.app.query.qry;

import lombok.Data;

/**
 * @date 2022/5/6 23:13
 */
@Data
public class QuotaVersionSaveReq {
    /**
     * 版本id
     */
    private Long versionId;

    /**
     * 指标id
     */
    private Long quotaId;

    /**
     * 版本说明
     */
    private String versionDesc;

    /**
     * 是否要下发通知
     */
    private Boolean notifyFlag;

}
