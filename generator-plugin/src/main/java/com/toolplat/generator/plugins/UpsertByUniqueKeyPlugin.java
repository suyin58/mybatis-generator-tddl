/*
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * ---------------------------------------------------------------------------
 * 存在即更新插件
 * ---------------------------------------------------------------------------
 */
public class UpsertByUniqueKeyPlugin extends BasePlugin {
    public static final String METHOD = "upsertByUniqueKey";  // 方法名

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
        if(null == uniqueKey || uniqueKey.size() == 0){
            System.err.println("generator UpsertByUniqueKeyPlugin break ,cause by no unique can be found");
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
        if(null == uniqueKey || uniqueKey.size() == 0){
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
        List<IntrospectedColumn> nonUniqueKeyColumns = new ArrayList<>(introspectedTable.getAllColumns());
        nonUniqueKeyColumns.removeAll(uniqueKey);
        for (VisitableElement where :XmlElementGeneratorTools.generateSets(nonUniqueKeyColumns,null, false)) {
            insertEle.addElement(where);
        }


        document.getRootElement().addElement(insertEle);
    }


}