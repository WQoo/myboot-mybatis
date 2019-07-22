package com.ranger.mybootmybatis.user.dao;

import lombok.*;

import java.util.Date;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name BaseDao
 * @description
 * @date 2019-06-26
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDao {

    private Date creationDate;

    private Date lastUpdateDate;

    private Long versionNumber;
}
