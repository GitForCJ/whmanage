package com.wlt.webm.business.action;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.*;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.commsoft.epay.util.logging.Log;

public class ContentNotAllowedInProlog {
  private static void parse(InputStream stream) throws SAXException,
      ParserConfigurationException, IOException {
    SAXParserFactory.newInstance().newSAXParser().parse(stream,
        new DefaultHandler());
  }

  public static void main(String[] args) {
    String[] encodings = { "UTF-8", "UTF-16", "ISO-8859-1" };
    for (String actual : encodings) {
      for (String declared : encodings) {
        if (actual != declared) {
        	String result="<?xml version='1.0' encoding='GBK'?><request><head><cmdid></cmdid><sversion></sversion><cversion></cversion><spid></spid><time>2015-8-24 14:35:29</time><userip>127.0.0.1</userip><uin>3331663323</uin><token></token><seqno></seqno></head><body><paipai_dealid>1217189101201515245430381001</paipai_dealid><cardid>gdlt100</cardid><num>1</num><customer>18682033916</customer><pay>10000</pay><section1>5000</section1><section2>1</section2><price>10000</price><deal_time>2015-8-24 14:35:29</deal_time></body><sign>2f6b04ffff4a1718ea9bbd437e616edd</sign></request>";
          byte[] encoded = result.getBytes(Charset.forName(actual));
          try {
            parse(new ByteArrayInputStream(encoded));
          } catch (Exception e) {
            System.out.println(e.getMessage() + " actual:" + actual + " xml:"
                + result);
          }
        }
      }
    }
  }
}