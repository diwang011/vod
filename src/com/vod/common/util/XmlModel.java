package com.vod.common.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
 * @author vod
 * 
 */
public class XmlModel
{

    private String nodeName;

    private String content;

    private Map<String, String> attr = new LinkedHashMap<String, String>();

    private List<XmlModel> child = new ArrayList<XmlModel>();

    public XmlModel()
    {
    }

    public XmlModel(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public XmlModel(String nodeName, String content)
    {
        this.nodeName = nodeName;
        this.content = content;
    }

    public void addttribute(String key, String value)
    {
        this.attr.put(key, value);
    }

    public Map<String, String> getAttr()
    {
        return this.attr;
    }

    public void setAttr(Map<String, String> attr)
    {
        this.attr = attr;
    }

    public String getNodeName()
    {
        return this.nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public List<XmlModel> getChild()
    {
        return this.child;
    }

    public void setChild(List<XmlModel> child)
    {
        this.child = child;
    }

    public void addChild(XmlModel child)
    {
        this.child.add(child);
    }

    public String toXml()
    {
        Document doc = DocumentHelper.createDocument();
        Element elment = doc.addElement(this.nodeName);
        this.makeElement(this, elment);
        return doc.asXML();
    }

    private void makeElement(XmlModel xmlbean, Element element)
    {
        Map<String, String> map = xmlbean.getAttr();
        for (String key : map.keySet())
        {
            element.addAttribute(key, map.get(key));
        }
        List<XmlModel> child = xmlbean.getChild();
        if (child.size() > 0)
        {
            for (XmlModel childBean : child)
            {
                Element el = element.addElement(childBean.getNodeName());
                this.makeElement(childBean, el);
            }
        }
        else
        {
            if (xmlbean.getContent() != null && !xmlbean.getContent().equals(""))
            {
                element.setText(xmlbean.getContent());
            }
        }
    }

    /**
     * 
     * @param xml
     * @return XMLBean
     * @throws DocumentException
     */
    public static XmlModel parser(String xml) throws DocumentException
    {
        XmlModel xmlbean = new XmlModel();
        Document doc = DocumentHelper.parseText(xml);
        Element elment = doc.getRootElement();
        XmlModel.markXMLBean(xmlbean, elment);
        return xmlbean;
    }

    private static void markXMLBean(XmlModel xmlbean, Element el)
    {
        @SuppressWarnings("unchecked")
        List<Attribute> atr = el.attributes();
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < atr.size(); i++)
        {
            map.put(atr.get(i).getName(), atr.get(i).getValue());
        }
        xmlbean.setAttr(map);
        xmlbean.setNodeName(el.getName());
        if (el.getText() != null && !el.getText().trim().equals(""))
        {
            xmlbean.setContent(el.getText());
        }
        else
        {
            @SuppressWarnings("unchecked")
            List<Element> list = el.elements();
            List<XmlModel> child = new ArrayList<XmlModel>();
            for (Element element : list)
            {
                XmlModel b = new XmlModel();
                child.add(b);
                XmlModel.markXMLBean(b, element);
            }
            xmlbean.setChild(child);
        }
    }

    public static void main(String[] args)
    {
        /************************ create ********************************/
        XmlModel xml = new XmlModel("OrdersPrint");
        xml.addttribute("Date", "2011-09-15");
        xml.addttribute("Account", "123");
        xml.addttribute("Secure", "3");
        xml.addttribute("OrderCount", "123");
        xml.addttribute("CopyCount", "2");

        XmlModel bean1 = new XmlModel("Order");
        bean1.addttribute("DispatchNumber", "2894484");
        bean1.addttribute("Number", "634686069092845559");
        bean1.addttribute("Date", "2012-03-29");
        XmlModel bean2 = new XmlModel("oo");
        bean2.addttribute("t", "2894484");
        bean2.addttribute("e", "634686069092845559");
        bean2.addttribute("w", "2012-03-29");

        bean1.addChild(bean2);
        xml.addChild(bean1);

        System.out.println(xml.toXml());
        /************************ paraser ********************************/

        try
        {
            XmlModel bean = XmlModel.parser(xml.toXml());
            List<XmlModel> list = bean.getChild();
            for (XmlModel xmlBean : list)
            {
                Map<String, String> map = xmlBean.getAttr();
                System.out.println(map.get("Number"));
                System.out.println(xmlBean.getNodeName() + "\t" + map.get("t"));
            }
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }
}
