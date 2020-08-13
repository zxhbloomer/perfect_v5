package com.perfect.core.config.mybatis.typehandlers;

import com.alibaba.fastjson.JSON;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationFunctionInfoVo;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: JsonHandler
 * @Description: mybatis处理JSON类型
 * @Author: zxh
 * @date: 2020/4/13
 * @Version: 1.0
 */
@MappedTypes(value = {OperationFunctionInfoVo.class})
@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class OperationFunctionInfoVoTypeHandler<T extends Object> extends BaseTypeHandler<T> {

    private Class<T> clazz;

    public OperationFunctionInfoVoTypeHandler(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T obj, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(obj));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if(rs.getString(columnName) == null){
            return null;
        }else {
            return JSON.parseObject(rs.getString(columnName), clazz);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JSON.parseObject(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JSON.parseObject(cs.getString(columnIndex), clazz);
    }
}
