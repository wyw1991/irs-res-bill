package com.dtzhejiang.irs.res.bill.common.dto;

import com.dtzhejiang.irs.res.bill.common.enums.BillPermissionEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Page Query Param
 */
@Getter
@Setter
public class PageQuery {

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 分页大小，默认值：10
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 分页页码，大于或等于1，默认值：1
     */
    private int pageIndex = 1;

    /**
     * 排序依据，默认按创建时间
     */
    private String orderBy = "createTime";

    /**
     * 升降序，DESC-倒序，ASC-升序，默认值：DESC
     */
    private String orderDirection = "DESC";

    /**
     * 当前查询列表权限  VALID_CONFIRM-合规性确认,VALID_PASS-合规性出具,GENERATE-报告生成,REVIEW-报告初审,CONFIRM-报告确认,AUDIT-报告审核,PASS-报告出具
     */
    @NonNull
    private BillPermissionEnum billPermission;

    /**
     * 是否已审核列表：true:是、false:否；默认否
     */
    private Boolean myAudit=false;

    public int getPageIndex() {
        return Math.max(pageIndex, 1);
    }

    public PageQuery setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public PageQuery setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public int getOffset() {
        return (getPageIndex() - 1) * getPageSize();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public PageQuery setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public PageQuery setOrderDirection(String orderDirection) {
        if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
            this.orderDirection = orderDirection;
        }
        return this;
    }

}
