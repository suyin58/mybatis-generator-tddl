package com.toolplat.generator.plugins;

import com.toolplat.generator.plugins.util.JavaElementGeneratorTools;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * 分页插件
 * @author suyin
 */
public class LimitPlugin extends  BasePlugin {

    @Override
    public boolean validate(List<String> warnings) {
        // 只支持MYSQL的limit 处理
        if ("com.mysql.jdbc.Driver".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false
                && "com.mysql.cj.jdbc.Driver".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false) {
            return false;
        }
        return super.validate(warnings);
    }

    /**
     * ModelExample Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();
        // 添加offset和rows字段
        Field offsetField = JavaElementGeneratorTools.generateField(
                "start",
                JavaVisibility.PROTECTED,
                integerWrapper,
                null
        );
        // commentGenerator.addFieldComment(offsetField, introspectedTable);
        topLevelClass.addField(offsetField);

        Field rowsField = JavaElementGeneratorTools.generateField(
                "limit",
                JavaVisibility.PROTECTED,
                integerWrapper,
                null
        );
//        commentGenerator.addFieldComment(rowsField, introspectedTable);
        topLevelClass.addField(rowsField);
        // 增加getter && setter 方法
        Method mSetOffset = JavaElementGeneratorTools.generateSetterMethod(offsetField);
//        commentGenerator.addGeneralMethodComment(mSetOffset, introspectedTable);
        topLevelClass.getMethods().add(mSetOffset);

        Method mGetOffset = JavaElementGeneratorTools.generateGetterMethod(offsetField);
//        commentGenerator.addGeneralMethodComment(mGetOffset, introspectedTable);
        topLevelClass.getMethods().add(mGetOffset);

        Method mSetRows = JavaElementGeneratorTools.generateSetterMethod(rowsField);
//        commentGenerator.addGeneralMethodComment(mSetRows, introspectedTable);
        topLevelClass.getMethods().add(mSetRows);

        Method mGetRows = JavaElementGeneratorTools.generateGetterMethod(rowsField);
//        commentGenerator.addGeneralMethodComment(mGetRows, introspectedTable);
        topLevelClass.getMethods().add(mGetRows);


        // 提供几个快捷方法
        Method setLimit = JavaElementGeneratorTools.generateMethod(
                "limit",
                JavaVisibility.PUBLIC,
                topLevelClass.getType(),
                false,
                new Parameter(integerWrapper, "limit")
        );
//        commentGenerator.addGeneralMethodComment(setLimit, introspectedTable);
        setLimit = JavaElementGeneratorTools.generateMethodBody(
                setLimit,
                "this.limit = limit;",
                "return this;"
        );
        topLevelClass.getMethods().add(setLimit);

        Method setLimit2 = JavaElementGeneratorTools.generateMethod(
                "limit",
                JavaVisibility.PUBLIC,
                topLevelClass.getType(),
                false,
                new Parameter(integerWrapper, "start"),
                new Parameter(integerWrapper, "limit")
        );
//        commentGenerator.addGeneralMethodComment(setLimit2, introspectedTable);
        setLimit2 = JavaElementGeneratorTools.generateMethodBody(
                setLimit2,
                "this.start = start;",
                "this.limit = limit;",
                "return this;"
        );
        topLevelClass.getMethods().add(setLimit2);


        // !!! clear 方法增加 offset 和 rows的清理
        List<Method> methodList = topLevelClass.getMethods();
        for (Method method : methodList) {
            if (method.getName().equals("clear")) {
                method.addBodyLine("start = null;");
                method.addBodyLine("limit = null;");
            }
        }
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.generateLimitElement(element);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.generateLimitElement(element);
        return true;
    }

    /**
     * 生成limit节点
     * @param element
     */
    private void generateLimitElement(XmlElement element) {
        XmlElement ifLimitNotNullElement = new XmlElement("if");
        ifLimitNotNullElement.addAttribute(new Attribute("test", "limit != null"));

        XmlElement ifOffsetNotNullElement = new XmlElement("if");
        ifOffsetNotNullElement.addAttribute(new Attribute("test", "start != null"));
        ifOffsetNotNullElement.addElement(new TextElement("limit ${start}, ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNotNullElement);

        XmlElement ifOffsetNullElement = new XmlElement("if");
        ifOffsetNullElement.addAttribute(new Attribute("test", "start == null"));
        ifOffsetNullElement.addElement(new TextElement("limit ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNullElement);

        element.addElement(ifLimitNotNullElement);
    }

}
