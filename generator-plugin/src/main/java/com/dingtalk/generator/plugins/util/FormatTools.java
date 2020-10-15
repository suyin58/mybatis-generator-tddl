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

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * ---------------------------------------------------------------------------
 * 格式化工具，优化输出
 * ---------------------------------------------------------------------------
 * @author: hewei
 * @time:2017/6/30 10:53
 * ---------------------------------------------------------------------------
 */
public class FormatTools {
    /**
     * 在最佳位置添加方法
     * @param innerClass
     * @param method
     */
    public static void addMethodWithBestPosition(InnerClass innerClass, Method method) {
        addMethodWithBestPosition(method, innerClass.getMethods());
    }



    /**
     * 在最佳位置添加方法
     * @param innerEnum
     * @param method
     */
    public static void addMethodWithBestPosition(InnerEnum innerEnum, Method method) {
        addMethodWithBestPosition(method, innerEnum.getMethods());
    }

    /**
     * 在最佳位置添加方法
     * @param topLevelClass
     * @param method
     */
    public static void addMethodWithBestPosition(TopLevelClass topLevelClass, Method method) {
        addMethodWithBestPosition(method, topLevelClass.getMethods());
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

    /**
     * 获取最佳添加位置
     * @param method
     * @param methods
     * @return
     */
    private static void addMethodWithBestPosition(Method method, List<Method> methods) {
        int index = -1;
        for (int i = 0; i < methods.size(); i++) {
            Method m = methods.get(i);
            if (m.getName().equals(method.getName())) {
                if (m.getParameters().size() <= method.getParameters().size()) {
                    index = i + 1;
                } else {
                    index = i;
                }
            } else if (m.getName().startsWith(method.getName())) {
                if (index == -1) {
                    index = i;
                }
            } else if (method.getName().startsWith(m.getName())) {
                index = i + 1;
            }
        }
        if (index == -1 || index >= methods.size()) {
            methods.add(methods.size(), method);
        } else {
            methods.add(index, method);
        }
    }

    /**
     * 替换已有方法注释
     * @param commentGenerator
     * @param method
     * @param introspectedTable
     */
    public static void replaceGeneralMethodComment(CommentGenerator commentGenerator, Method method, IntrospectedTable introspectedTable) {
        method.getJavaDocLines().clear();
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
    }



    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
