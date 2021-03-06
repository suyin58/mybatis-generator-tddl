package com.toolplat.generator.plugins;

import com.toolplat.generator.plugins.util.JavaElementGeneratorTools;
import com.toolplat.generator.plugins.util.XmlElementGeneratorTools;
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
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

/**
 * batchInsert Plugin（批量insert插件）
 * @author suyin
 */
public class BatchInsertPlugin extends BasePlugin {
    /**
     * 方法名
     */
    public static final String METHOD = "batchInsert";
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {

        // 1. batchInsert
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
        listType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        Method method = JavaElementGeneratorTools.generateMethod(
                METHOD,
                JavaVisibility.DEFAULT,
                FullyQualifiedJavaType.getIntInstance(),
                true,
                new Parameter(listType, "list", "@Param(\"list\")")

        );
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        // interface 增加方法
        JavaElementGeneratorTools.addMethodToInterface(interfaze, method);
        return super.clientGenerated(interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        // 1. batchInsert
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", METHOD));
        // 参数类型
        answer.addAttribute(new Attribute("parameterType", "map"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        context.getCommentGenerator().addComment(answer);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
//        XmlElementGeneratorTools.useGeneratedKeys(answer, introspectedTable);

        answer.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        for (VisitableElement element :
                XmlElementGeneratorTools.generateKeys(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(allColumns),
                        true)) {
            answer.addElement(element);
        }

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ","));


        XmlElement valuesTrimElement = new XmlElement("trim");
        foreachElement.addElement(valuesTrimElement);
        valuesTrimElement.addElement(XmlElementGeneratorTools.generateValuesSelective(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(allColumns), "item.", true) );


        // values 构建
        answer.addElement(new TextElement("values"));
        answer.addElement(foreachElement);

        XmlElementGeneratorTools.addElementWithBestPosition(document.getRootElement(), answer);
        return true;
    }
}
