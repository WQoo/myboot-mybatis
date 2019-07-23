package com.ranger.mybootmybatis.user.service.impl;

import com.ranger.mybootmybatis.user.dao.User;
import com.ranger.mybootmybatis.user.mapper.UserMapper;
import com.ranger.mybootmybatis.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type User service.
 *
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name UserServiceImpl
 * @description
 * @date 2019 -07-22
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }


    @Override
    public User selectByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
