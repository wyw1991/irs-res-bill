package com.dtzhejiang.irs.res.bill.common.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Response with batch page record to return, usually use in page query
 */
public class PageResponse<T> {

    /**
     * 总数
     */
    private long totalCount = 0;

    /**
     * 分页大小
     */
    private long pageSize = 10;

    /**
     * 分页页码
     */
    private long pageIndex = 1;

    private Collection<T> data;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageSize() {
        return Math.max(pageSize, 10);
    }

    public void setPageSize(long pageSize) {
        this.pageSize = Math.max(pageSize, 10);
    }

    public long getPageIndex() {
        return Math.max(pageIndex, 1);
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = Math.max(pageIndex, 1);
    }

    public List<T> getData() {
        return null == data ? Collections.emptyList() : new ArrayList<>(data);
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public long getTotalPages() {
        return this.totalCount % this.pageSize == 0 ? this.totalCount
                / this.pageSize : (this.totalCount / this.pageSize) + 1;
    }

    public boolean isEmpty() {
        return data == null || data.size() == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public static <T> PageResponse<T> of(int pageSize, int pageIndex) {
        PageResponse<T> response = new PageResponse<>();
        response.setData(Collections.emptyList());
        response.setTotalCount(0);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }

    public static <T> PageResponse<T> of(Collection<T> data, long totalCount, long pageSize, long pageIndex) {
        PageResponse<T> response = new PageResponse<>();
        response.setData(data);
        response.setTotalCount(totalCount);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }
}
