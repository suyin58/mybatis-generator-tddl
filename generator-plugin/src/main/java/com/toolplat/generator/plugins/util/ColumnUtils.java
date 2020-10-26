package com.toolplat.generator.plugins.util;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Column 工具类
 *
 * @author suyin
 */
public class ColumnUtils {

    public static List<IntrospectedColumn> removeIdentityAndGeneratedAlwaysColumns(List<IntrospectedColumn> allColumns) {
        return ListUtilities.removeIdentityAndGeneratedAlwaysColumns(allColumns).stream().filter(it -> !it.isAutoIncrement()).collect(Collectors.toList());
    }
}
