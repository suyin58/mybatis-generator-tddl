package com.toolplat.generator.plugins;

import com.toolplat.generator.plugins.delegate.MyRules;
import com.toolplat.generator.plugins.util.JavaElementGeneratorTools;
import com.toolplat.generator.plugins.util.XmlElementGeneratorTools;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * update by uniqueKey, 根据唯一索引更新数据
 * @author suyin
 */
public class UpdateByUniqueKeySelectivePlugin extends BasePlugin {
    /**
     * Mapper.java 方法名
     */
    public static final String METHOD = "updateByUniqueKeySelective";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        MyRules myRules = new MyRules(introspectedTable.getRules());
        introspectedTable.setRules(myRules);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if(null == uniqueKey || uniqueKey.size() == 0){
            System.err.println("generator updateByUniqueKeySelective break ,cause by no unique can be found");
            return true;
        }
        // 方法生成
        Method method = JavaElementGeneratorTools.generateMethod(
                METHOD,
                JavaVisibility.PUBLIC,
                new FullyQualifiedJavaType("int"),
                true,
                new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record")
        );

        // 注释生成
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        // 构造interface
        JavaElementGeneratorTools.addMethodToInterface(interfaze, method);

        return super.clientGenerated(interfaze, introspectedTable);
    }



    /**
     * 生成XML文件
     * @see  org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeySelectiveElementGenerator
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {


        if(null == uniqueKey || uniqueKey.size() == 0){
            return true;
        }
        // 增加update标签
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$
        context.getCommentGenerator().addComment(answer);
        answer.addAttribute(new Attribute(
                "id", METHOD)); //$NON-NLS-1$

        // 增加update标签的parameter
        String parameterType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = introspectedTable.getRecordWithBLOBsType();
        } else {
            parameterType = introspectedTable.getBaseRecordType();
        }

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType));

        // 增加update语句
        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        List<IntrospectedColumn> nonUniqueKeyColumns = new ArrayList<>(introspectedTable.getAllColumns());
        nonUniqueKeyColumns.removeAll(uniqueKey);
        for (IntrospectedColumn introspectedColumn : nonUniqueKeyColumns) {
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            sb.append(',');

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        // 增加where条件语句
        for (VisitableElement where : XmlElementGeneratorTools.generateWheres(uniqueKey)) {
            answer.addElement(where);
        }

        XmlElementGeneratorTools.addElementWithBestPosition(document.getRootElement(), answer);
        return true;
    }
}
