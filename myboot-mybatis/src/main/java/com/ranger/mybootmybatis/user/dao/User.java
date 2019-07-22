package com.ranger.mybootmybatis.user.dao;

import lombok.*;

import java.util.Date;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name User
 * @description
 * @date 2019-06-26
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseDao{

    private Long id;

    private String name;

    private Long age;
}
