package com.hatc.common.servicestub.util;

import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ConfigParser extends DefaultHandler {

    ////����һ��Properties �����������ֵ
    private Properties props;

    private String currentSet;
    private String currentName;
    private StringBuffer currentValue = new StringBuffer();

    //��������ʼ��props
    public ConfigParser() {

        this.props = new Properties();
    }

    public Properties getProps() {
        return this.props;
    }

    //���忪ʼ����Ԫ�صķ���. �����ǽ�<xxx>�е�����xxx��ȡ����.
    @Override
	public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        currentValue.delete(0, currentValue.length());
        this.currentName = qName;
    }

    //�����ǽ�<xxx></xxx>֮���ֵ���뵽currentValue
    @Override
	public void characters(char[] ch, int start, int length) throws
            SAXException {
        currentValue.append(ch, start, length);
    }

    //������</xxx>������,��֮ǰ�����ƺ�ֵһһ��Ӧ������props��
    @Override
	public void endElement(String uri, String localName, String qName) throws
            SAXException {
        props.put(qName.toLowerCase(), currentValue.toString().trim());
    }

}
