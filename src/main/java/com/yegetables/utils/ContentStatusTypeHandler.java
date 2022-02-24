package com.yegetables.utils;

import com.yegetables.pojo.ContentStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContentStatusTypeHandler extends BaseTypeHandler<ContentStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ContentStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public ContentStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return StringTools.Content.valueOf(rs.getString(columnName));
    }

    @Override
    public ContentStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return StringTools.Content.valueOf(rs.getString(columnIndex));

    }

    @Override
    public ContentStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return StringTools.Content.valueOf(cs.getString(columnIndex));
    }
}
