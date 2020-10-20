package com.toolplat.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.Properties;

/**
 * 注释生成
 * @author suyin
 */
public class CommentGenerator extends DefaultCommentGenerator {

    private Properties properties;

    public CommentGenerator(){
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.properties.putAll(properties);
    }

    /**
     * set model comment from table remark
     * @param topLevelClass
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        String auth = properties.getProperty("author");
        String tableDesc = introspectedTable.getRemarks();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + tableDesc);
        topLevelClass.addJavaDocLine(" * @author " + auth);
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * set field comment from field remark
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remark = introspectedColumn.getRemarks();
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + remark);
        field.addJavaDocLine(" */");
    }


    /**
     * remove java mapping coment
     * @param method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
       // super.addGeneralMethodComment(method, introspectedTable);
    }

}
