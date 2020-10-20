package com.toolplat.generator.plugins;

import com.toolplat.generator.plugins.constant.PluginConstants;
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
import org.mybatis.generator.api.dom.xml.XmlElement;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 *
 * @author suyin
 */
public class SelectByExampleForUpdate extends BasePlugin {
    /**
     * Mapper.java 方法名
     */
    public static final String METHOD_SELECT_BY_EXAMPLE_FOR_UPDATE = "selectByExampleForUpdate";

    /**
     * Mapper.java 方法名
     */
    public static final String METHOD_SELECT_BY_EXAMPLE_WITH_BLOBS_FOR_UPDATE = "selectByExampleWithBlobsForUpdate";


    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String useExample = introspectedTable.getTableConfigurationProperty(PluginConstants.USE_EXAMPLE);
        if(null != useExample && !Boolean.valueOf(useExample)){
            return false;
        }

        // 方法生成
        Method selectMethod = JavaElementGeneratorTools.generateMethod(
                METHOD_SELECT_BY_EXAMPLE_FOR_UPDATE,
                JavaVisibility.PUBLIC,
                JavaElementGeneratorTools.getModelTypeWithBLOBs(introspectedTable),
                true,
                new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()), "example")
        );
        // 构造interface
        JavaElementGeneratorTools.addMethodToInterface(interfaze, selectMethod);
        return super.clientGenerated(interfaze, introspectedTable);
    }


    /**
     * 生成XML文件
     * @see  org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithoutBLOBsElementGenerator
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String useExample = introspectedTable.getTableConfigurationProperty("useExample");
        if(null != useExample && !Boolean.valueOf(useExample)){
            return false;
        }

        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                METHOD_SELECT_BY_EXAMPLE_FOR_UPDATE));
        // 添加返回类型
        if (introspectedTable.hasBLOBColumns()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getExampleType())); //$NON-NLS-1$
        context.getCommentGenerator().addComment(answer);
        answer.addElement(new TextElement("select")); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable
                .getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            answer.addElement(new TextElement(sb.toString()));
        }
        answer.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(XmlElementGeneratorTools.getExampleIncludeElement(introspectedTable));

        answer.addElement(new TextElement("for update"));

        XmlElementGeneratorTools.addElementWithBestPosition(document.getRootElement(), answer);
        return true;
    }

}
