package com.toolplat.generator.plugins.util;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class BeanUtils {

    public static XmlElement cloneXmlElement(XmlElement ele) {
        XmlElement answer = new XmlElement(ele.getName());
        if (ele.getElements() != null && ele.getElements().size() > 0) {
            for (VisitableElement e : ele.getElements()) {
                answer.addElement(e);
            }
        }
        if (ele.getAttributes() != null && ele.getAttributes().size() > 0) {
            for (Attribute attribute : ele.getAttributes()) {
                answer.addAttribute(attribute);
            }
        }
        return answer;
    }
}
