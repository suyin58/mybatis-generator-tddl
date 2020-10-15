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

package com.dingtalk.generator.plugins.util;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * ---------------------------------------------------------------------------
 * Xml 节点生成工具
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator
 * ---------------------------------------------------------------------------
 * @author suyin
 */
public class XmlElementGeneratorTools {

    public static XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBaseColumnListId()));
        return answer;
    }

    public static XmlElement getExampleIncludeElement(IntrospectedTable introspectedTable) {
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "_parameter != null")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
        includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getExampleWhereClauseId()));
        ifElement.addElement(includeElement);

        return ifElement;
    }

    public static XmlElement getBlobColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBlobColumnListId()));
        return answer;
    }

    /**
     * 在最佳位置添加节点
     * @param rootElement
     * @param element
     */
    public static void addElementWithBestPosition(XmlElement rootElement, XmlElement element) {
        // sql 元素都放在sql后面
        if (element.getName().equals("sql")) {
            int index = 0;
            for (VisitableElement ele : rootElement.getElements()) {
                if (ele instanceof XmlElement && ((XmlElement) ele).getName().equals("sql")) {
                    index++;
                }
            }
            rootElement.addElement(index, element);
        } else {
            // 根据id 排序
            String id = getIdFromElement(element);
            if (id == null) {
                rootElement.addElement(element);
            } else {
                List<VisitableElement> elements = rootElement.getElements();
                int index = -1;
                for (int i = 0; i < elements.size(); i++) {
                    VisitableElement ele = elements.get(i);
                    if (ele instanceof XmlElement) {
                        String eleId = getIdFromElement((XmlElement) ele);
                        if (eleId != null) {
                            if (eleId.startsWith(id)) {
                                if (index == -1) {
                                    index = i;
                                }
                            } else if (id.startsWith(eleId)) {
                                index = i + 1;
                            }
                        }
                    }
                }

                if (index == -1 || index >= elements.size()) {
                    rootElement.addElement(element);
                } else {
                    elements.add(index, element);
                }
            }
        }
    }
    /**
     * 找出节点ID值
     * @param element
     * @return
     */
    private static String getIdFromElement(XmlElement element) {
        for (Attribute attribute : element.getAttributes()) {
            if (attribute.getName().equals("id")) {
                return attribute.getValue();
            }
        }
        return null;
    }
}
