package com.toolplat.generator.plugins.delegate;

import com.toolplat.generator.plugins.constant.PluginConstants;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.rules.Rules;
import org.mybatis.generator.internal.rules.RulesDelegate;

import java.util.List;

/**
 * @author suyin
 */
public class MyRules extends RulesDelegate {

    public MyRules(Rules rules) {
        super(rules);
    }


    @Override
    public boolean generateBaseResultMap() {
        boolean answer =  super.generateBaseResultMap();
        IntrospectedTable introspectedTable = super.getIntrospectedTable();
        Object uk = introspectedTable.getAttribute(PluginConstants.TABLE_PROPERTY_UNIQUE_KEY);
        if(null != uk){
            return true;
        }
        return answer;
    }


    @Override
    public boolean generateResultMapWithBLOBs() {

        boolean answer =  super.generateResultMapWithBLOBs();
        IntrospectedTable introspectedTable = super.getIntrospectedTable();
        Object uk = introspectedTable.getAttribute(PluginConstants.TABLE_PROPERTY_UNIQUE_KEY);
        if(null != uk){
            return true;
        }
        return answer;
    }

    @Override
    public boolean generateBaseColumnList() {
        boolean answer =  super.generateBaseColumnList();
        IntrospectedTable introspectedTable = super.getIntrospectedTable();
        Object uk = introspectedTable.getAttribute(PluginConstants.TABLE_PROPERTY_UNIQUE_KEY);
        if(null != uk){
            return true;
        }
        return answer;
    }

    @Override
    public boolean generateBlobColumnList() {
        boolean answer =  super.generateBlobColumnList();
        IntrospectedTable introspectedTable = super.getIntrospectedTable();
        Object uk = introspectedTable.getAttribute(PluginConstants.TABLE_PROPERTY_UNIQUE_KEY);
        if(null != uk){
            return true;
        }
        return answer;
    }
}
