package com.ranger.mybootmybatis.user.controller;

import com.github.pagehelper.PageHelper;
import com.ranger.mybootmybatis.user.dao.User;
import com.ranger.mybootmybatis.user.service.UserService;
import com.ranger.mybootmybatis.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name UserController
 * @description
 * @date 2019-07-22
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAllUser")
    @ResponseBody
    List<User> getAllUser(){
        PageHelper.startPage(1, 10);
        return userService.selectAll();
    }
}
