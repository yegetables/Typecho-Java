package com.yegetables.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

import static com.yegetables.utils.TimeTools.string10ToTimestamp;
import static com.yegetables.utils.TimeTools.timestampToString10;


@MappedTypes({Timestamp.class})
public class TimestampTypeHandler extends BaseTypeHandler<Timestamp> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Timestamp parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, timestampToString10(parameter));
    }


    @Override
    public Timestamp getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return string10ToTimestamp(rs.getString(columnName));
    }

    @Override
    public Timestamp getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return string10ToTimestamp(rs.getString(columnIndex));
    }

    @Override
    public Timestamp getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return string10ToTimestamp(cs.getString(columnIndex));
    }


}
