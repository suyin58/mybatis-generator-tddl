package com.dingtalk.generator.plugins;

import com.dingtalk.generator.plugins.util.JavaElementGeneratorTools;
import com.dingtalk.generator.plugins.util.XmlElementGeneratorTools;
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

import java.util.stream.Collectors;

/**
 * select by uniqueKey, should assign unique key name
 * @author suyin
 */
public class DeleteByUniqueKeyPlugin extends BasePlugin {


    /**
     * Mapper.java 方法名
     */
    public static final String METHOD_DELETE_BY_UNIQUE_KEY = "deleteByUniqueKey";


    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if(null == uniqueKey || uniqueKey.size() == 0){
            System.err.println("generator deleteByUniqueKey break ,cause by no unique can be found");
            return false;
        }
        // 方法生成
        Method selectMethod = JavaElementGeneratorTools.generateMethod(
                METHOD_DELETE_BY_UNIQUE_KEY,
                JavaVisibility.PUBLIC,
                new FullyQualifiedJavaType("int"),
                true,
                uniqueKey.stream().map(it -> new Parameter(it.getFullyQualifiedJavaType(),it.getJavaProperty(),
                        "@Param(\""+it.getJavaProperty()+"\")"
                )).collect(Collectors.toList())
        );

        // 注释生成
        // commentGenerator.addGeneralMethodComment(selectOneMethod, introspectedTable);

        // 构造interface
        JavaElementGeneratorTools.addMethodToInterface(interfaze, selectMethod);
        return super.clientGenerated(interfaze, introspectedTable);
    }

    /**
     * 生成XML文件
     * @see  org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.DeleteByPrimaryKeyElementGenerator
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        if(null == uniqueKey || uniqueKey.size() == 0){
            return false;
        }
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", METHOD_DELETE_BY_UNIQUE_KEY)); //$NON-NLS-1$

        // 添加参数类型
        String parameterType;
        if (uniqueKey.size() == 1) {
            parameterType = uniqueKey.get(0).getFullyQualifiedJavaType().getFullyQualifiedName();
        } else {
            // PK fields are in the base class. If more than on PK
            // field, then they are coming in a map.
            parameterType = "map"; //$NON-NLS-1$
        }
        answer.addAttribute(new Attribute("parameterType", parameterType));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        // where
        for (VisitableElement where :XmlElementGeneratorTools.generateWheres(uniqueKey)) {
            answer.addElement(where);
        }
        XmlElementGeneratorTools.addElementWithBestPosition(document.getRootElement(), answer);
        return true;
    }

}
