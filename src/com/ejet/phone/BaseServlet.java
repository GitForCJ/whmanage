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
    	Log.debug("★★★返回JSON: " + orgJson);
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
    	Log.debug("★★★返回JSON: " + orgJson);
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
	      return "交易成功!";
	  if (code == 1)
	      return "转款人资金账号不存在";
	    if (code == 2)
	      return "转款人资金账号不可用";
	    if (code == 3)
	      return "转款人资金账号余额不足";
	    if (code == 4)
	      return "交易密码输入错误";
	    if (code == 5)
	      return "被转款人资金账号不存在";
	    if (code == 6)
	      return " 被转款人资金账号不可用 ";
	    if (code == 8)
	      return "交易异常";
	    
	    return "";
  }
  
  
  public String formatQbRecode(int code)
  {
    if (code == 0)
      return "交易成功!";
    if (code == 1)
      return "数字签名错误（检查密钥是否正确、md5加密是否正确）";
    if (code == 2)
      return "订单重复提交";
    if (code == 3)
      return "用户帐号不存在";
    if (code == 4)
      return "系统错误(指的是非在线卡支付逻辑的所有错误)。如果出现该错误，最多重复尝试充值3次，如果错误依旧，建议放弃充值或人工干预处理";
    if (code == 5)
      return "IP错误";
    if (code == 6)
      return "用户key错误";
    if (code == 7)
      return "参数错误 数据不合法";
    if (code == 8)
      return "库存不足";
    if (code == 9)
      return "用户状态异常";
    if (code == 10)
      return "订单处理中";
    if (code == 11)
      return "余额不足";
    if (code == 12)
      return "网络中断";
    if (code == 13)
      return "处理中";
    if (code == 15)
        return "Q币充值网络异常";
    if (code == 101)
      return "此功能暂时不可用";
    if (code == 102)
      return "该商业号权限不足";
    if (code == 103) {
      return "系统维护中";
    }
    return "";
  }

  public String formatRecode(int code)
  {
    if (code == 0)
      return "交易成功!";
    if (code == 1)
      return "无对应充值接口";
    if (code == 2)
      return "未知用户类型";
    if (code == 3)
      return "无对应佣金组";
    if (code == 4)
      return "押金账号不存在";
    if (code == 5)
      return "佣金子账号不存在 ";
    if (code == 6)
      return "押金账号不可用";
    if (code == 7)
      return "佣金子账号不可用";
    if (code == 8)
      return "押金账号余额不足";
    if (code == 9)
      return "佣金明细不存在";
    if (code == 10)
      return "生成订单失败 ";
    if (code == 11)
      return "数据处理错误";
    if (code == 12)
      return "充值异常,请联系客服";
    if (code == -1)
      return "充值失败";
    if (code == -2) {
      return "充值无响应";
    }
    return "未找到返回码!!!";
  }
}