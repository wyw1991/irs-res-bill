package com.dtzhejiang.irs.res.bill.infra.util;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.baomidou.mybatisplus.core.enums.SqlKeyword.*;
@Component
public class PageUtilPlus{


    private static Method tryInitCache;
    private static Method getColumnCache;
    private static Method appendSqlSegments;

    static {
        LambdaQueryWrapper<Object> queryWrapper = new LambdaQueryWrapper<>();
        try {
            Class<?> superclass = queryWrapper.getClass().getSuperclass();
            tryInitCache = superclass.getDeclaredMethod("tryInitCache", Class.class);
            getColumnCache = superclass.getDeclaredMethod("getColumnCache", String.class, Class.class);
            appendSqlSegments = superclass.getSuperclass().getDeclaredMethod("appendSqlSegments", ISqlSegment[].class);
            tryInitCache.setAccessible(true);
            getColumnCache.setAccessible(true);
            appendSqlSegments.setAccessible(true);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置排序字段,如果设置了排序字段,实体类中的orderBy注解将失效
     *
     * @param queryWrapper {@link LambdaQueryWrapper}
     * @param pageQuery     {@link PageQuery}
     * @param clazz        排序的对象
     * @param <T>          泛型
     */
    public static <T> void setOrderBy(LambdaQueryWrapper<T> queryWrapper, PageQuery pageQuery, Class<?> clazz) {
        String orderBy=pageQuery.getOrderBy();
        boolean desc="desc".equalsIgnoreCase(pageQuery.getOrderDirection());
        if (null == orderBy) {
            return;
        }
        setOrderOne(queryWrapper,orderBy,desc,clazz);

    }

    /**
     * @param queryWrapper {@link LambdaQueryWrapper}
     * @param column       排序字段
     * @param desc         降序
     * @param clazz        排序的对象
     * @param <T>          泛型
     */
    public static <T> void setOrderOne(LambdaQueryWrapper<T> queryWrapper, String column, boolean desc, Class<?> clazz) {
        try {
            tryInitCache.invoke(queryWrapper, clazz);
            ColumnCache columnCache = (ColumnCache) getColumnCache.invoke(queryWrapper, column, clazz);
            if (columnCache == null) {
                throw new RuntimeException("数据库中找不到 " + column + " 映射字段");
            }
            String sqlColumn = columnCache.getColumn();

            ISqlSegment[] sqlSegments = {ORDER_BY, () -> sqlColumn, desc ? DESC : ASC};
            appendSqlSegments.invoke(queryWrapper, (Object) sqlSegments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
