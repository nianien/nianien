package com.nianien.test;

import java.util.List;

import org.dom4j.Document;
import org.junit.Test;

import com.nianien.core.loader.ResourceLoader;
import com.nianien.core.xml.XMLDocument;
import com.nianien.core.xml.XMLNode;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-02-07
 */
public class TestXMLNode {
    @Test
    public void test() {
        Document document = XMLDocument.xmlFromFile(ResourceLoader.getFile("datasource.xml"));
        List<XMLNode> list = XMLDocument.getXMLNodes(document, "/source/config");
        System.out.println(list.size());

        for (XMLNode node : list) {
            System.out.println(node.getAttributes());
        }
    }
}
