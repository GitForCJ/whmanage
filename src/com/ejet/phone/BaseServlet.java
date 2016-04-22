package com.ejet.phone;

import com.commsoft.epay.util.logging.Log;
import com.google.gson.Gson;
import com.wlt.webm.rights.form.SysUserForm;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet
{
  protected static Map<String, SysUserForm> keyMap = new HashMap();
  protected static final String publicKey = "jiaofeib";

  private String generateWord()
  {
    String[] beforeShuffle = { "2", "3", "4", "5", "6", "7", 
      "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
      "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", 
      "W", "X", "Y", "Z" };
    List list = Arrays.asList(beforeShuffle);
    Collections.shuffle(list);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      sb.append(list.get(i));
    }
    String afterShuffle = sb.toString();
    String result = afterShuffle.substring(5, 9);
    return result;
  }

  public String getSessionKey()
  {
    String t = Tools.getNow3().substring(8, 12) + generateWord();
    return t;
  }

  public boolean bigger(String src, String dest)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    try {
      Date ds = sdf.parse(src);
      Date ds2 = sdf.parse(dest);
      ds.before(ds2);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return true;
  }

  protected SysUserForm getUserForm(HttpServletRequest request)
  {
    SysUserForm user = new SysUserForm();
    return user;
  }

  protected void returnResult(HttpServletResponse response, String sessionKey, String name, String result, String returnmsg, String servertype, 
		  Object sendJson, boolean isDebug)
    throws IOException
  {
    Map data = new HashMap();
    Map rep = new HashMap();
    data.put("result", result);
    data.put("returnmsg", URLEncoder.encode(wrapObject(returnmsg), "UTF-8"));
    data.put("INFO", wrapObject(returnmsg));
    data.put("content", sendJson);
    rep.put("response", data);

    String orgJson = new Gson().toJson(rep);
    if( isDebug )
    	Log.debug("���ﷵ��JSON: " + orgJson);
    String content = Mac.getLoginEncryt(sessionKey, name, orgJson, servertype);
    simpleResponse(content, response);
  }

  protected void returnResult(HttpServletResponse response, String key, String name, String result, String returnmsg, String servertype, boolean isDebug)
    throws UnsupportedEncodingException
  {
    Map data = new HashMap();
    Map rep = new HashMap();
    data.put("result", result);
    data.put("returnmsg", URLEncoder.encode(wrapObject(returnmsg), "UTF-8"));
    data.put("INFO",  URLEncoder.encode(wrapObject(returnmsg), "UTF-8"));
    rep.put("response", data);
    String orgJson = new Gson().toJson(rep);
    if( isDebug )
    	Log.debug("���ﷵ��JSON: " + orgJson);
    String content = Mac.getLoginEncryt(key, name, orgJson, servertype);
    simpleResponse(content, response);
  }

  public void simpleResponse(String content, HttpServletResponse response)
  {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
      out.print(content);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      if (out != null) {
        out.flush();
        out.close();
      }
    }
  }

  public static String wrapObject(Object obj) {
    if (obj == null)
      return "";
    if ((obj instanceof String)) {
      return obj.toString().equalsIgnoreCase("null") ? "" : obj.toString();
    }
    return obj.toString();
  }

  
  public String formatFeeTransfer(int code)
  {
	  if (code == 0)
	      return "���׳ɹ�!";
	  if (code == 1)
	      return "ת�����ʽ��˺Ų�����";
	    if (code == 2)
	      return "ת�����ʽ��˺Ų�����";
	    if (code == 3)
	      return "ת�����ʽ��˺�����";
	    if (code == 4)
	      return "���������������";
	    if (code == 5)
	      return "��ת�����ʽ��˺Ų�����";
	    if (code == 6)
	      return " ��ת�����ʽ��˺Ų����� ";
	    if (code == 8)
	      return "�����쳣";
	    
	    return "";
  }
  
  
  public String formatQbRecode(int code)
  {
    if (code == 0)
      return "���׳ɹ�!";
    if (code == 1)
      return "����ǩ�����󣨼����Կ�Ƿ���ȷ��md5�����Ƿ���ȷ��";
    if (code == 2)
      return "�����ظ��ύ";
    if (code == 3)
      return "�û��ʺŲ�����";
    if (code == 4)
      return "ϵͳ����(ָ���Ƿ����߿�֧���߼������д���)��������ָô�������ظ����Գ�ֵ3�Σ�����������ɣ����������ֵ���˹���Ԥ����";
    if (code == 5)
      return "IP����";
    if (code == 6)
      return "�û�key����";
    if (code == 7)
      return "�������� ���ݲ��Ϸ�";
    if (code == 8)
      return "��治��";
    if (code == 9)
      return "�û�״̬�쳣";
    if (code == 10)
      return "����������";
    if (code == 11)
      return "����";
    if (code == 12)
      return "�����ж�";
    if (code == 13)
      return "������";
    if (code == 15)
        return "Q�ҳ�ֵ�����쳣";
    if (code == 101)
      return "�˹�����ʱ������";
    if (code == 102)
      return "����ҵ��Ȩ�޲���";
    if (code == 103) {
      return "ϵͳά����";
    }
    return "";
  }

  public String formatRecode(int code)
  {
    if (code == 0)
      return "���׳ɹ�!";
    if (code == 1)
      return "�޶�Ӧ��ֵ�ӿ�";
    if (code == 2)
      return "δ֪�û�����";
    if (code == 3)
      return "�޶�ӦӶ����";
    if (code == 4)
      return "Ѻ���˺Ų�����";
    if (code == 5)
      return "Ӷ�����˺Ų����� ";
    if (code == 6)
      return "Ѻ���˺Ų�����";
    if (code == 7)
      return "Ӷ�����˺Ų�����";
    if (code == 8)
      return "Ѻ���˺�����";
    if (code == 9)
      return "Ӷ����ϸ������";
    if (code == 10)
      return "���ɶ���ʧ�� ";
    if (code == 11)
      return "���ݴ������";
    if (code == 12)
      return "��ֵ�쳣,����ϵ�ͷ�";
    if (code == -1)
      return "��ֵʧ��";
    if (code == -2) {
      return "��ֵ����Ӧ";
    }
    return "δ�ҵ�������!!!";
  }
}