package com.ranger.mybootmybatis.mybatis.select;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name selectMapper
 * @description
 * @date 2019-07-22
 */
public interface selectMapper<T> {

    /**
     * 通用查询api
     * 通过privider可以生成自定义的sql
     * @param record
     * @return
     */
    @SelectProvider(type = SelectProvider.class, method = "dynamicSQL")
    List<T> select(T record);
}
