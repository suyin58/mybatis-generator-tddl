package com.toolplat.generator.plugins;

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
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

import java.util.ArrayList;
import java.util.List;

public class UpsertByPrimaryKeyPlugin extends BasePlugin {
    public static final String METHOD = "upsertByPrimaryKey";  // 方法名

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {

        // 插件使用前提是数据库为MySQL
        if ("com.mysql.jdbc.Driver".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false
                && "com.mysql.cj.jdbc.Driver".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false) {
            return false;
        }
        return super.validate(warnings);
    }

    /**
     * Java Client Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if (null == introspectedTable.getPrimaryKeyColumns() || introspectedTable.getPrimaryKeyColumns().size() == 0) {
            System.err.println("generator UpsertByPrimaryKeyPlugin break ,cause by no unique can be found");
            return true;
        }
        // ====================================== upsert ======================================
        Method method = JavaElementGeneratorTools.generateMethod(
                METHOD,
                JavaVisibility.DEFAULT,
                FullyQualifiedJavaType.getIntInstance(),
                true,
                new Parameter(JavaElementGeneratorTools.getModelTypeWithoutBLOBs(introspectedTable), "record")
        );
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        // interface 增加方法
        JavaElementGeneratorTools.addMethodToInterface(interfaze, method);
        return super.clientGenerated(interfaze, introspectedTable);
    }

    /**
     * SQL Map Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (null == introspectedTable.getPrimaryKeyColumns() || introspectedTable.getPrimaryKeyColumns().size() == 0) {
            return true;
        }
        this.generateXmlElement(document, introspectedTable);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 生成xml
     * @param document
     * @param introspectedTable
     */
    private void generateXmlElement(Document document, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns = ListUtilities.removeGeneratedAlwaysColumns( introspectedTable.getAllColumns());
        // ====================================== upsert ======================================
        XmlElement insertEle = new XmlElement("insert");
        context.getCommentGenerator().addComment(insertEle);
        insertEle.addAttribute(new Attribute("id", METHOD));

        // 增加update标签的parameter
        String parameterType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = introspectedTable.getRecordWithBLOBsType();
        } else {
            parameterType = introspectedTable.getBaseRecordType();
        }

        insertEle.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType));


        // insert
        insertEle.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        for (VisitableElement element :
                XmlElementGeneratorTools.generateKeys(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(allColumns),
                        true)) {
            insertEle.addElement(element);
        }

        insertEle.addElement(new TextElement("values"));
        insertEle.addElement(XmlElementGeneratorTools.generateValuesSelective(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(allColumns), null, true) );

        insertEle.addElement(new TextElement("on duplicate key update "));
        // set
        List<IntrospectedColumn> nonPrimaryKeyColumns = new ArrayList<>(introspectedTable.getAllColumns());
        nonPrimaryKeyColumns.removeAll(introspectedTable.getPrimaryKeyColumns());
        for (VisitableElement where :XmlElementGeneratorTools.generateSets(nonPrimaryKeyColumns,null, false)) {
            insertEle.addElement(where);
        }


        document.getRootElement().addElement(insertEle);
    }
}