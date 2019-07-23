package com.ranger.mybootmybatis.user.service;

import com.ranger.mybootmybatis.user.dao.User;

import java.util.List;

/**
 * The interface User service.
 *
 * @author qiang.wang01 @hand-china.com
 * @version 1.0
 * @name UserService
 * @description
 * @date 2019 -07-22
 */
public interface UserService {

    /**
     * Delete by primary key int.
     *
     * @param id the id
     * @return the int
     */
    int deleteByPrimaryKey(String id);

    /**
     * Insert int.
     *
     * @param record the record
     * @return the int
     */
    int insert(User record);

    /**
     * Select by primary key user.
     *
     * @param id the id
     * @return the user
     */
    User selectByPrimaryKey(String id);

    /**
     * Update by primary key int.
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(User record);

    /**
     *
     * @return
     */
    List<User> selectAll();
}
