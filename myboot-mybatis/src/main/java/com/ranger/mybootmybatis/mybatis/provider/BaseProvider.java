package com.ranger.mybootmybatis.mybatis.provider;

import lombok.Data;

import java.lang.reflect.ParameterizedType;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name BaseProvider
 * @description 用于获取范性的Class属性
 * @date 2019-07-22
 */
@Data
public class BaseProvider<T> {

    /*范型的类型*/
    private Class<T> entityClass;

    private int index = 0;

    public BaseProvider(){
        GetEntity();
    }

    /**
     * 获取实体类的方法
     */
    public void GetEntity(){
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[index];
    }


}
