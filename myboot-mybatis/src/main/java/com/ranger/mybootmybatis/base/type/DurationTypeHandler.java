package com.ranger.mybootmybatis.base.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

/**
 * @author qiang.wang01@hand-china.com
 * @version 1.0
 * @name DurationTypeHandler
 * @description 空值转换
 * @date 2019-07-01
 */
public class DurationTypeHandler extends BaseTypeHandler<Duration> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement,
                                    int index, Duration duration, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(index,duration.toString());
    }

    @Override
    public Duration getNullableResult(ResultSet resultSet, String column) throws SQLException {
        String duration = resultSet.getString(column);
        if (StringUtils.isEmpty(duration)) {
            return null;
        }
        return Duration.parse(duration);
    }

    @Override
    public Duration getNullableResult(ResultSet resultSet, int index) throws SQLException {
        String duration = resultSet.getString(index);
        if (StringUtils.isEmpty(duration)) {
            return null;
        }
        return Duration.parse(duration);
    }

    @Override
    public Duration getNullableResult(CallableStatement callableStatement, int index) throws SQLException {
        String duration = callableStatement.getString(index);
        if (StringUtils.isEmpty(duration)) {
            return null;
        }
        return Duration.parse(duration);
    }
}
