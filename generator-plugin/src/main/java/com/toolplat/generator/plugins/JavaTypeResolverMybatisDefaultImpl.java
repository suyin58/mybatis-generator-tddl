package com.toolplat.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;


/**
 * JDBC 类型特殊转化
 * @author suyin
 */
public class JavaTypeResolverMybatisDefaultImpl extends JavaTypeResolverDefaultImpl {

    @Override
    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer = defaultType;

        switch (column.getJdbcType()) {
            case Types.BIT:
                answer = this.calculateBitReplacement(column, defaultType);
                break;
            case Types.NUMERIC:
            case Types.DECIMAL:
                answer = this.calculateBigDecimalReplacement(column, defaultType);
                break;
            case Types.TINYINT:
                answer = new FullyQualifiedJavaType(Integer.class.getName());
                break;
            default:
                break;
        }

        return answer;
    }
}
