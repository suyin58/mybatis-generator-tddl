package com.toolplat.generator.plugins;

import com.toolplat.generator.plugins.delegate.MyRules;
import com.toolplat.generator.plugins.util.JavaElementGeneratorTools;
import com.toolplat.generator.plugins.util.XmlElementGeneratorTools;
import org.mybatis.generator.api.IntrospectedTable;
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

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * select by uniqueKey, 根据唯一索引查询数据
 * @author suyin
 */
public class SelectByUniqueKeyPlugin extends BasePlugin {

    /**
     * Mapper.java 方法名
     */
    public static final String METHOD = "selectByUniqueKey";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        MyRules myRules = new MyRules(introspectedTable.getRules());
        introspectedTable.setRules(myRules);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if(null == uniqueKey || uniqueKey.size() == 0){
            System.err.println("generator SelectByUniqueKeyPlugin break ,cause by no unique can be found");
            return true;
        }
        // 方法生成
        Method method = JavaElementGeneratorTools.generateMethod(
                METHOD,
                JavaVisibility.PUBLIC,
                JavaElementGeneratorTools.getModelTypeWithBLOBs(introspectedTable),
                true,
                uniqueKey.stream().map(it -> new Parameter(it.getFullyQualifiedJavaType(), it.getJavaProperty(),
                        "@Param(\"" + it.getJavaProperty() + "\")"
                )).collect(Collectors.toList())
        );

        // 注释生成
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        // 构造interface
        JavaElementGeneratorTools.addMethodToInterface(interfaze, method);
        return super.clientGenerated(interfaze, introspectedTable);
    }

    /**
     * 生成XML文件
     * @see  org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByPrimaryKeyElementGenerator
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        if(null == uniqueKey || uniqueKey.size() == 0){
            return true;
        }
        // 生成查询语句
        XmlElement answer = new XmlElement("select");
        context.getCommentGenerator().addComment(answer);
        // 添加ID
        answer.addAttribute(new Attribute("id", METHOD));
        // 添加返回类型
        if (introspectedTable.hasBLOBColumns()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }

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
        answer.addElement(new TextElement("select"));

        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable.getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,");
        }
        answer.addElement(new TextElement(sb.toString()));

        answer.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));
        if (introspectedTable.hasBLOBColumns()) {
            answer.addElement(new TextElement(",")); //$NON-NLS-1$
            answer.addElement(XmlElementGeneratorTools.getBlobColumnListElement(introspectedTable));
        }
        // 添加from tableName
        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        // 添加where语句
        for (VisitableElement where :XmlElementGeneratorTools.generateWheres(uniqueKey)) {
            answer.addElement(where);
        }

        XmlElementGeneratorTools.addElementWithBestPosition(document.getRootElement(), answer);
        return true;
    }
}
