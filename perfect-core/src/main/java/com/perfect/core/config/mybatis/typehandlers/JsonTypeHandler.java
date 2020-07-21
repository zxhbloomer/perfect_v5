//package com.perfect.core.config.mybatis.typehandlers;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * @ClassName: JsonHandler
// * @Description: mybatis处理JSON类型
// * @Author: zxh
// * @date: 2020/4/13
// * @Version: 1.0
// */
//@MappedTypes(value = {SModuleInfoVo.class})
//@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
//public class JsonTypeHandler<T extends Object> extends BaseTypeHandler<T> {
//
//    private Class<T> clazz;
//
//    public JsonTypeHandler(Class<T> clazz) {
//        if (clazz == null) {
//            throw new IllegalArgumentException("Type argument cannot be null");
//        }
//        this.clazz = clazz;
//    }
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, T obj, JdbcType jdbcType) throws SQLException {
//        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
//        ps.setString(i, JSON.toJSONString(obj));
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        return JSON.parseObject(rs.getString(columnName), clazz);
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        return JSON.parseObject(rs.getString(columnIndex), clazz);
//    }
//
//    @Override
//    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return JSON.parseObject(cs.getString(columnIndex), clazz);
//    }
//}
