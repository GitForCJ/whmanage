package com.ejet.phone;


import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.commsoft.epay.util.logging.Log;
import com.ejet.phone.dao.PhoneCountDao;
import com.ejet.phone.dao.PhoneDao;
import com.wlt.webm.business.action.OrderAction;
import com.wlt.webm.business.bean.JtfkBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.AcctBillForm;
import com.wlt.webm.business.form.ChildFaactForm;
import com.wlt.webm.business.form.CityReportForm;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.mobile.QbMobile;
import com.wlt.webm.mobile.SmallRequest;
import com.wlt.webm.mobile.TelPhoneFill;
import com.wlt.webm.rights.form.SysLoginUserForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.PageAttribute;

public class AdPhoneServlet extends BaseServlet{
	
//	private String     tempKey = publicKey;
	
	public AdPhoneServlet()
	{
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doPost(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String     tempKey = publicKey;
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if( StringUtils.equals("test", method) )
		{
			this.returnResult(response, publicKey, "V2.0.4", "0000", "URL is ok!", "", true);
			return;
		}
		String msgData = request.getParameter("JSON_DATA");
		
		if( StringUtils.isEmpty(msgData) )
			this.returnResult(response, publicKey, "", "0001", "参数错误!", "", false);
		
		String [] jsonArray = msgData.split("\\|");
		if( jsonArray==null || jsonArray.length!=3 )
			this.returnResult(response, publicKey, "", "0001", "参数错误!", "", false);
		
		//未解析数据
		String userName   = "";
		String rsultjson  = "";
		String servicetype= "";
		SysUserForm userForm = null;
		String returnmsg = "";
	try {
		servicetype = jsonArray[0];      //业务
		userName = Mac.getNameDecrypt(publicKey, jsonArray[1]).trim();
		userForm = keyMap.get(userName);
		
		if( StringUtils.equals("login", method))
		{
			if( StringUtils.equals("0031",servicetype) )
			{
				
			}else {
				userForm = new SysUserForm();
			}
			rsultjson= Mac.getLoginDecrypt(publicKey, userName, jsonArray[2]);//获取json报文
			
		}else
		{
			if( userForm==null )
			{
				this.returnResult(response, publicKey, userName, "0002", "请先登录!", servicetype, true);
				return;
			}
			tempKey = userForm.getEndDate();//置换密钥
			rsultjson= Mac.getLoginDecrypt(tempKey, userName, jsonArray[2]);//获取json报文
		}	
			
		 //开始解析json报文
	    JSONObject jsonResult = new JSONObject(rsultjson);
	    JSONObject jsonObj	= jsonResult.getJSONObject("server");
	    Log.info("★★★ jsonObj:" + jsonObj.toString());
	    
		if(StringUtils.equals("0001", servicetype) && StringUtils.equals("login", method)){
			
			Log.info("★★★ Login -------------------------");
			userForm.setLogin( jsonObj.getString("user_name") );
			userForm.setPassword( jsonObj.getString("passsec") );
			keyMap.remove(userName);
			int state = PhoneDao.login(userForm, request, response);
			
			if( state!=0 )
			{
				this.returnResult(response, publicKey, userName, state+"", (String)request.getAttribute("mess"), servicetype, true);
				return;
			}
	        //查询公告
	        List list = PhoneDao.getSysNoticeListLatest(userForm);
	        //查询银行账号
	        Map temp = PhoneDao.getAccountData(userForm.getUser_id());
	        //查询资金帐户
	        ChildFaactForm fms = PhoneDao.getUserFundAcct(userForm.getUser_id(), "02");
	        //sessionkey
	        String sessionkey = getSessionKey();
	        //用户信息map
	        Map userDataMap = new HashMap();
	        userDataMap.put("id", wrapObject(userForm.getUser_id()) );
	        userDataMap.put("user_name", URLEncoder.encode(wrapObject(temp.get("user_name")), "UTF-8"));
	        userDataMap.put("name", URLEncoder.encode( wrapObject(userForm.getUserename()), "UTF-8") );
	        userDataMap.put("phone", userForm.getPhone());
	        userDataMap.put("address", URLEncoder.encode( wrapObject(userForm.getAddress()), "UTF-8"));
	        userDataMap.put("mail", wrapObject(userForm.getUsermail()) );
	        userDataMap.put("identity", wrapObject( temp.get("user_icard")) );
	        userDataMap.put("area",    URLEncoder.encode ( wrapObject(PhoneDao.getSite(userForm.getUsersite()) + userForm.getWltarea()), "UTF-8") );
	       
	        //
	        Map bankDataMap = new HashMap();
	        bankDataMap.put("balance", fms.getUsableleft());
	        bankDataMap.put("accountno", fms.getChildfacct());
	        bankDataMap.put("bankno", wrapObject( temp.get("bankno")) );
	       
	        Map sendJson = new HashMap();
	        sendJson.put("consultation_list", list);
	        sendJson.put("user_data", userDataMap);
	        sendJson.put("bank_data", bankDataMap);
	        sendJson.put("sessionkey", sessionkey);
	       
	        userForm.setStartDate(Tools.getNow3());
	        userForm.setEndDate(sessionkey);
	       
	        keyMap.put(userName, userForm);
	        this.returnResult(response, publicKey, userName, "0000", "登录成功!", servicetype, sendJson, false);  
	       
		}else //非登录
		{
			
			JSONObject contentObj	= jsonObj.getJSONObject("content");
			ActionMapping mapping = new ActionMapping();
			
			if(StringUtils.equals("0002",servicetype)){//0002:(充值验证)
				
				Log.info("★★★ 0002充值验证------------------------");
				JSONObject chargeObj = contentObj.getJSONObject("charge_msg");
				String tradeobject   =  chargeObj.getString("phone");
				String subtype       =  chargeObj.getString("subtype");
				String payfee 		 =  chargeObj.getString("money");
				String type 		 =  chargeObj.getString("type");
				String seqno         = Tools.getSeqNo(type+userForm.getLogin());
				
				TelcomForm form = new TelcomForm();
				form.setTradeObject(tradeobject);
				form.setPayFee( Tools.fen2Yuan(payfee) );
				form.setSeqNo(seqno);
				int ret = -1;
				if( subtype.equals("08") )//q币
				{
					ret = PhoneDao.validateAndGetPhone(form, userForm, request, response);
				}
				else
				{
					ret = PhoneDao.validatePhone(form, userForm, request, response);
				}
				//最后返回结果
				if( ret!=0 )
				{
					this.returnResult(response, tempKey, userName, ret+"",formatRecode(ret), servicetype, true); 
					return;
				}
				String mytype = "";
				String rsType = request.getAttribute("phone_type")+"";
				if(rsType.equals("2"))//表示联通
				{
					mytype = "2";
					
				}else if(rsType.equals("1"))//表示移动
				{
					mytype = "3";
					
				}else if(rsType.equals("3"))//表示电信
				{
					mytype = "1";
				}
				
				if(!mytype.equals(type) && !subtype.equals("04"))
				{
					this.returnResult(response, tempKey, userName, "0002", "充值号码与业务类型不匹配!", servicetype, true); 
					return;
				}
				
				Map ordermsg = new HashMap();
				ordermsg.put("balance",  wrapObject(request.getAttribute("balance")));
				ordermsg.put("dedmoney", wrapObject(request.getAttribute("dedmoney")));
				ordermsg.put("money",    wrapObject(request.getAttribute("money")));
				ordermsg.put("phone_type", wrapObject(mytype));
				ordermsg.put("area_msg",  URLEncoder.encode(wrapObject(request.getAttribute("area_msg")), "utf-8"));
				ordermsg.put("phone_msg", URLEncoder.encode(wrapObject(request.getAttribute("phone_msg")), "utf-8"));
				Map sendJson = new HashMap();
		        sendJson.put("ordermsg", ordermsg);
		        this.returnResult(response, tempKey, userName, "0000", "0002充值验证成功", servicetype, sendJson, false);  
				
			}else if(StringUtils.equals("0003",servicetype)){//0003:充值订单(充值验证)
				
				Log.info("★★★  0003充值订单------------------------");
				JSONObject chargeObj = contentObj.getJSONObject("charge_msg");
				String tradeobject   =  chargeObj.getString("phone");
				String subtype       =  chargeObj.getString("subtype");
				String payfee 		 =  chargeObj.getString("money");
				String type 		 =  chargeObj.getString("type");
				String seqno         = Tools.getSeqNo(type+userForm.getLogin());
				
				int ret = -2;
				TelcomForm form = new TelcomForm();
				form.setTradeObject(tradeobject);
				form.setPayFee( Tools.fen2Yuan(payfee) );
				form.setSeqNo(seqno);
				
				if( subtype.equals("01") )//广东电信
				{
					form.setNumType(chargeObj.getString("phonenotype"));// 01 单一缴费 02 宽带缴费 03 综合缴费 
					com.wlt.webm.mobile.TelPhoneFill tel = new com.wlt.webm.mobile.TelPhoneFill();
					ret = tel.telMobile(form, userForm);
					
				}else if(subtype.equals("02") )//广东联通 
				{
					com.wlt.webm.mobile.UnicomFill tel = new com.wlt.webm.mobile.UnicomFill();
					ret = tel.telMobile(form, userForm);
					
				}else if(subtype.equals("03") )//广东移动
				{
					com.wlt.webm.mobile.CmccFill tel = new com.wlt.webm.mobile.CmccFill();
					ret = tel.telMobile(form, userForm);
					
				}else if(subtype.equals("04") )//全国充值
				{
					System.out.println("---------全国充值1:");
					ret = PhoneDao.tzFill(form, userForm, request, response);
					returnmsg = (String)request.getAttribute("mess");
					System.out.println("---------全国充值2:" + request.getAttribute("mess"));
					
				}else if(subtype.equals("05") )// 05湖北电信
				{
					form.setNumType("2");//表示号码类型（湖北电信接口）
					com.wlt.webm.mobile.XjFill tel = new com.wlt.webm.mobile.XjFill();
					ret = tel.telMobile(form, userForm);
				}else if(subtype.equals("06") )//湖北联通 
				{
					form.setNumType("1");//表示号码类型（湖北联通接口）
					com.wlt.webm.mobile.XjFill tel = new com.wlt.webm.mobile.XjFill();
					ret = tel.telMobile(form, userForm);
					
				}else if ( subtype.equals("07") )
				{
					form.setNumType("0");//表示号码类型（湖北移动接口）
					com.wlt.webm.mobile.XjFill tel = new com.wlt.webm.mobile.XjFill();
					ret = tel.telMobile(form, userForm);
					
				}else if ( subtype.equals("08") ) //Q币
				{
					com.wlt.webm.mobile.QbMobile q = new QbMobile();
					request.setAttribute("posid", userForm.getUser_id());
					ret = q.MobileQb(userForm.getUser_id(), tradeobject, Integer.parseInt(payfee), request, response);
					
				}else if ( subtype.equals("09") ) //交通罚款
				{
					JtfkForm jtform = new JtfkForm();
					JSONObject trafficObj = contentObj.getJSONObject("traffic_msg");
					request.setAttribute("violationId", trafficObj.getString("phone"));
					request.setAttribute("isMail", trafficObj.getString("isMail"));
					request.setAttribute("mailAddr", URLDecoder.decode(trafficObj.getString("mailAddr"), "utf-8"));
					request.setAttribute("totalFee", trafficObj.getString("totalFee"));
					ret = PhoneDao.payOrder(mapping,jtform,userForm, request,response);
					if(ret==0)
					{
				        this.returnResult(response, tempKey, userName, "0000", "0023成功", servicetype, false);  
					}else
					{
						this.returnResult(response, tempKey, userName, "0001", "查询交通罚款失败", servicetype, true);  
					}
					return;
					
				}
				//最后返回结果
				if( ret==0 )
				{

					Map payMsgMap = new HashMap();
					payMsgMap.put("orderno", seqno);
					payMsgMap.put("dedmoney", payfee);			//实际扣费金额	dedmoney
					payMsgMap.put("phone", tradeobject);		//扣款号码	phone
					payMsgMap.put("money", payfee); 			//应付金额	money
					payMsgMap.put("balance", PhoneDao.getLeftFee(form, userForm, request, response)); 			//缴费后余额	balance
					
					Map sendJson = new HashMap();
			        sendJson.put("pay_msg", payMsgMap);
			        this.returnResult(response, tempKey, userName, "0000", "0014成功", servicetype, sendJson, false);  
			        return;
					
				}else
				{
					this.returnResult(response, tempKey, userName, ret+"", returnmsg + formatRecode(ret), servicetype, true);  
				}
			
				return;
			}else if(StringUtils.equals("0008",servicetype))//0008:银联转款
			{
				//查询银行账号
			    Map   temp = PhoneDao.getAccountData( userForm.getUser_id() );
			    //调用短信接口
			    String fee = contentObj.getString("fee");//金额
			    String sec = contentObj.getString("sec");//交易密码
//			    int ret = PhoneDao.transFoward(mapping, userForm, request, response);
			    
			    
			    
			    
			    //
				
			    return;
			}else if(StringUtils.equals("0009",servicetype))//0009:银联转款
			{
				//查询银行账号
			    Map   temp = PhoneDao.getAccountData( userForm.getUser_id() );
			    //调用短信接口
			    String fee = contentObj.getString("fee");//金额
			    String sec = contentObj.getString("sec");//交易密码
			    int ret = PhoneDao.transFoward(fee, sec, userForm, request, response);
			    if(ret!=0)
			    {
			    	this.returnResult(response, tempKey, userName, "0001", (String)request.getAttribute("mess"), servicetype, true);  
			    	return;
			    }
			    ret = PhoneDao.trans(fee, userForm, request, response);
			    if(ret!=0)
			    {
			    	this.returnResult(response, tempKey, userName, "0001", (String)request.getAttribute("mess"), servicetype, true); 
			    	return;
			    }else
			    {
			    	Map payMsgMap = new HashMap();
					payMsgMap.put("orderno", request.getAttribute("serial"));
					payMsgMap.put("dedmoney", fee);			//实际扣费金额	dedmoney
					payMsgMap.put("money", fee); 			//应付金额	money
					payMsgMap.put("balance", PhoneDao.getLeftFee(null, userForm, request, response)); 			//缴费后余额	balance
					
					Map sendJson = new HashMap();
			        sendJson.put("pay_msg", payMsgMap);
			        this.returnResult(response, tempKey, userName, "0000", "0009成功", servicetype, sendJson, false);  
			    }
			    //
				
			    return;
			}else if(StringUtils.equals("0014",servicetype)) //0014:查询交易记录
			{
				 OrderAction action = new OrderAction();
				 request.getSession().setAttribute("userSession", userForm);
				 OrderForm form = new OrderForm();
				 form.setCurPage( Integer.parseInt(contentObj.getString("pagenum")) );
				 String orderFee = contentObj.getString("fee");
				 if(null != orderFee && !"".equals(orderFee)){
					 form.setFee(String.valueOf(Integer.parseInt(orderFee)/100) );
				 }
				 form.setTradeobject( contentObj.getString("phone"));
				 form.setStartDate( contentObj.getString("starttime"));
				 form.setEndDate( contentObj.getString("endtime"));
				 form.setService( contentObj.getString("type"));
				 form.setState(contentObj.getString("state"));
				 form.setTradeserial(contentObj.getString("order_no"));
				 int ret = action.phoneList(mapping, form, request, response);
				 Map dealmsgMap = new HashMap();
				 List list =  (List)request.getAttribute("orderList");
				 List dealmsg   = new ArrayList();
				 
				 for(Object tmp : list){
					 String[] temp = (String[])tmp;
					 Map map = new HashMap();
					 map.put("time", 		temp[6]); 				//日期
					 map.put("phone", 		temp[2]); 				//号码
					 map.put("phone_type", 	URLEncoder.encode(wrapObject(temp[10]), "utf-8")); 		   //号码类型	
					 map.put("area", 		URLEncoder.encode(wrapObject(temp[0]), "utf-8"));    		//区域 	
					 map.put("fee", 		temp[5]);    			//金额
					 map.put("state", 		URLEncoder.encode(wrapObject(temp[8]),"utf-8"));   			//状态
					 map.put("orderno", 	temp[1]);  				//订单号
					 map.put("type", 		URLEncoder.encode(wrapObject(temp[12]),"utf-8"));     			//类型
					 map.put("balance", 	temp[14]);	 			//帐号余额
					 dealmsg.add(map);
				 }
				 PageAttribute page = (PageAttribute)request.getAttribute("page");
				 Map pagemsg = new HashMap();
				 pagemsg.put("pagenum", page.getCurPage()); 		//返回页码
				 pagemsg.put("itemnum", page.getPageSize());		//返回条数
				 pagemsg.put("totalpagenum", page.getPageCount());   //返回总页
				 pagemsg.put("totalitemnum", page.getRsCount());   //总条数
				 
				Map sendJson = new HashMap();
		        sendJson.put("dealmsg", dealmsg);
		        sendJson.put("pagemsg", pagemsg);
		        this.returnResult(response, tempKey, userName, "0000", "0014成功", servicetype, sendJson, true);  
		        return;
			}else if(StringUtils.equals("0018",servicetype)) //0018:账务明细
			{
				 request.getSession().setAttribute("userSession", userForm);
				 
				 AcctBillForm form = new AcctBillForm();
				 form.setTradetype( contentObj.getString("type")); //交易类型	type
				 form.setAcctType( contentObj.getString("account_type") );                      //资金账号类型	account_type
				 form.setPay_type(	contentObj.getString("fee_type")	);						//支出类别	fee_type
				 String orderFee = contentObj.getString("fee");
				 if(null != orderFee && !"".equals(orderFee)){
					 form.setTradefee(String.valueOf(Integer.parseInt(orderFee)/100) );
				 }
				 form.setTradeserial(  contentObj.getString("orderno")  );              		//订单号	orderno
				 form.setState( "0" );//订单状态	state
				 form.setStartDate( contentObj.getString("starttime"));
				 form.setEndDate( contentObj.getString("endtime"));
				 
				 form.setCurPage( Integer.parseInt(contentObj.getString("pagenum")) );  		//请求页码
				 request.setAttribute("itemnum", contentObj.getString("itemnum"));//请求条数	itemnum

				 int ret = PhoneCountDao.countAcctBill( form, userForm, request, response);
				 Map dealmsgMap = new HashMap();
				 List list =  (List)request.getAttribute("orderList");
				 List dealmsg   = new ArrayList();
				 
				 for(Object tmp : list){
					 String[] temp = (String[])tmp;
					 Map map = new HashMap();
					 map.put("time", 			wrapObject(temp[3])); 													// 日期
					 map.put("account", 		wrapObject(temp[0])); 													// 交易账号
					 map.put("account_sub_type", URLEncoder.encode(wrapObject(temp[13]),"utf-8"));
					 map.put("account_sub", URLEncoder.encode(wrapObject(temp[0]),"utf-8"));
					 map.put("type", 			URLEncoder.encode(wrapObject(temp[5]),"utf-8"));     		// 交易类型	type	
					 map.put("area", 			URLEncoder.encode(wrapObject(temp[14]),"utf-8"));
					 map.put("sub_name", 			URLEncoder.encode(wrapObject(temp[17]),"utf-8")); 
					 map.put("fee", 			wrapObject(temp[4]));    												// 金额	fee	
					 map.put("state", 			URLEncoder.encode(wrapObject(temp[7]),"utf-8"));   			// 状态
					 map.put("orderno", 		wrapObject(temp[1]));  													// 订单号	orderno	
					 map.put("in_out", 			wrapObject(temp[11]));     		// 转出或转入账号	in_out
					 map.put("fee_type", URLEncoder.encode(wrapObject(temp[12]),"utf-8"));
					 map.put("balance", 		wrapObject(temp[9]));	 												// 帐号余额
					 dealmsg.add(map);
				 }
				 PageAttribute page = (PageAttribute)request.getAttribute("page");
				 Map pagemsg = new HashMap();
				 pagemsg.put("pagenum", page.getCurPage()); 		//返回页码
				 pagemsg.put("itemnum", page.getPageSize());		//返回条数
				 pagemsg.put("totalpagenum", page.getPageCount());  //返回总页
				 pagemsg.put("totalitemnum", page.getRsCount());    //总条数
				 
				Map sendJson = new HashMap();
		        sendJson.put("dealmsg", dealmsg);
		        sendJson.put("pagemsg", pagemsg);
		        this.returnResult(response, tempKey, userName, "0000", "0014成功", servicetype, sendJson, true);  
		        return;
			}else if(StringUtils.equals("0017",servicetype)) //0017:地市统计
			{
				 request.getSession().setAttribute("userSession", userForm);
				 
				 CityReportForm form = new CityReportForm();
				 form.setService( contentObj.getString("type")); //交易类型	type
				 form.setState( contentObj.getString("state") );                      //资金账号类型	account_type
				 form.setStartDate( contentObj.getString("starttime"));
				 form.setEndDate( contentObj.getString("endtime"));
				 form.setUser_id(userForm.getUser_id());
				 
//				 form.setCurPage( Integer.parseInt(contentObj.getString("pagenum")) );  		//请求页码
//				 request.setAttribute("itemnum", contentObj.getString("itemnum"));//请求条数	itemnum

				 int ret = PhoneCountDao.query( form, request, response);
				 Map dealmsgMap = new HashMap();
				 List list =  (List)request.getAttribute("cityCount");
				 List dealmsg   = new ArrayList();
				 
				 for(Object tmp : list){
					 String[] temp = (String[])tmp;
					 Map map = new HashMap();
					 map.put("type", 			URLEncoder.encode(wrapObject(temp[1]),"utf-8")); 													
					 map.put("num", 		wrapObject(temp[5])); 													
					 map.put("fee", wrapObject(temp[4]));
					 map.put("state", URLEncoder.encode(wrapObject(temp[3]),"utf-8"));
					 map.put("dudfee", 			wrapObject(temp[4]));     		
					 map.put("area", 			URLEncoder.encode(wrapObject(temp[6]),"utf-8"));
					 map.put("term_type", 			temp[2]); 
					 dealmsg.add(map);
				 }
				 PageAttribute page = new PageAttribute(1,Constant.PAGE_SIZE);
				 Map pagemsg = new HashMap();
				 pagemsg.put("pagenum", page.getCurPage()); 		//返回页码
				 pagemsg.put("itemnum", page.getPageSize());		//返回条数
				 pagemsg.put("totalpagenum", page.getPageCount());  //返回总页
				 pagemsg.put("totalitemnum", page.getRsCount());    //总条数
				 
				Map sendJson = new HashMap();
		        sendJson.put("dealmsg", dealmsg);
		        sendJson.put("pagemsg", pagemsg);
		        this.returnResult(response, tempKey, userName, "0000", "0017成功", servicetype, sendJson,true);  
		        return;
			}else if(StringUtils.equals("0019",servicetype))	//0019:佣金明细
			{
//				 AccountBillAction action = new AccountBillAction();
				 Log.info("★★★0019佣金明细查询");
				 request.getSession().setAttribute("userSession", userForm);
				 
				 AcctBillForm form = new AcctBillForm();
				 form.setTradetype( contentObj.getString("type")); //交易类型	type
				 form.setTradeaccount( contentObj.getString("account"));
				 form.setTradeserial(  contentObj.getString("streamno")  );              //交易流水号
				 form.setStartDate( contentObj.getString("starttime"));
				 form.setEndDate( contentObj.getString("endtime"));
				 form.setState("0");

				 form.setCurPage( Integer.parseInt(contentObj.getString("pagenum")) );  		//请求页码
				 request.setAttribute("itemnum", contentObj.getString("itemnum"));//请求条数	itemnum
				 
				 int ret = PhoneCountDao.commList( form, userForm, request, response);
				 Map dealmsgMap = new HashMap();
				 List list =  (List)request.getAttribute("orderList");
				 List dealmsg   = new ArrayList();
				 
				 for(Object tmp : list){
					 String[] temp = (String[])tmp;
					 Map map = new HashMap();
					 //a.childfacct,a.dealserial,a.tradeaccount,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),a.tradefee,b.name ,a.explain,a.state,DATE_FORMAT(a.distime,'%Y-%m-%d %k:%i:%s')
//					,a.accountleft,a.tradeserial,a.other_childfacct,a.pay_type,c.accttypeid,g.sa_name,e.user_ename,e.user_site_city,a.tradeaccount
					 map.put("time", 			wrapObject(temp[3])); 													// 日期
					 map.put("account", 		wrapObject(temp[0])); 													// 交易账号
					 map.put("account_type", URLEncoder.encode(wrapObject(temp[13]),"utf-8"));
					 map.put("type", 			URLEncoder.encode(wrapObject(temp[5]),"utf-8"));     		// 交易类型	type	
					 map.put("fee", 			wrapObject(temp[4]));    												// 金额	fee	
					 map.put("state", 			URLEncoder.encode(wrapObject(temp[7]),"utf-8"));   			// 状态
					 map.put("orderno", 		wrapObject(temp[1]));  													// 订单号	orderno	
					 map.put("in_out", 			wrapObject(temp[11]));     		// 转出或转入账号	in_out
					 map.put("fee_type", URLEncoder.encode(wrapObject(temp[12]),"utf-8"));
					 map.put("balance", 		wrapObject(temp[9]));	 												// 帐号余额
					 map.put("deal_account", wrapObject(temp[2]));
					 
//					 map.put("account_sub_type", URLEncoder.encode(wrapObject(temp[10]), "utf-8")); 		// 资金子账号类型
//					 map.put("account_sub", 	wrapObject(temp[6])); 											// 资金子账号	account_sub	
//					 map.put("area", 			URLEncoder.encode(wrapObject(temp[0]), "utf-8"));    		// 区域	area	
//					 map.put("type", 			URLEncoder.encode(wrapObject(temp[12]),"utf-8"));     		// 支出类型	fee_type
					 dealmsg.add(map);
				 }
				 PageAttribute page = (PageAttribute)request.getAttribute("page");
				 Map pagemsg = new HashMap();
				 pagemsg.put("pagenum", page.getCurPage()); 		//返回页码
				 pagemsg.put("itemnum", page.getPageSize());		//返回条数
				 pagemsg.put("totalpagenum", page.getPageCount());  //返回总页
				 pagemsg.put("totalitemnum", page.getRsCount());    //总条数
				 
				Map sendJson = new HashMap();
		        sendJson.put("dealmsg", dealmsg);
		        sendJson.put("pagemsg", pagemsg);
		        this.returnResult(response, tempKey, userName, "0000", "0014成功", servicetype, sendJson, true);  
				return;
				
			}else if(StringUtils.equals("0020",servicetype))	//0020:收益明细
			{
//				 AccountBillAction action = new AccountBillAction();
				 request.getSession().setAttribute("userSession", userForm);
				 
				 AcctBillForm form = new AcctBillForm();
				 form.setTradetype( contentObj.getString("type")); //交易类型	type
				 form.setTradeaccount( contentObj.getString("account"));
				 form.setTradeserial(  contentObj.getString("streamno")  );              //交易流水号
				 form.setStartDate( contentObj.getString("starttime"));
				 form.setEndDate( contentObj.getString("endtime"));
				 form.setState("0");

				 form.setCurPage( Integer.parseInt(contentObj.getString("pagenum")) );  		//请求页码
				 request.setAttribute("itemnum", contentObj.getString("itemnum"));//请求条数	itemnum
				 
				 int ret = PhoneCountDao.incomeList( form, userForm, request, response);
				 Map dealmsgMap = new HashMap();
				 List list =  (List)request.getAttribute("orderList");
				 List dealmsg   = new ArrayList();
				 
				 for(Object tmp : list){
					 String[] temp = (String[])tmp;
					 Map map = new HashMap();
					 map.put("time", 			wrapObject(temp[3])); 													// 日期
					 map.put("account", 		wrapObject(temp[0])); 													// 交易账号
					 map.put("account_type", URLEncoder.encode(wrapObject(temp[13]),"utf-8"));
					 map.put("type", 			URLEncoder.encode(wrapObject(temp[5]),"utf-8"));     		// 交易类型	type	
					 map.put("sub_name", 			URLEncoder.encode(wrapObject(temp[17]),"utf-8")); 
					 map.put("fee", 			wrapObject(temp[4]));    												// 金额	fee	
					 map.put("state", 			URLEncoder.encode(wrapObject(temp[7]),"utf-8"));   			// 状态
					 map.put("orderno", 		wrapObject(temp[1]));  													// 订单号	orderno	
					 map.put("in_out", 			wrapObject(temp[11]));     		// 转出或转入账号	in_out
					 map.put("fee_type", URLEncoder.encode(wrapObject(temp[12]),"utf-8"));
					 map.put("balance", 		wrapObject(temp[9]));	 												// 帐号余额
					 map.put("deal_account", wrapObject(temp[2]));
					 dealmsg.add(map);
				 }
				 PageAttribute page = (PageAttribute)request.getAttribute("page");
				 Map pagemsg = new HashMap();
				 pagemsg.put("pagenum", page.getCurPage()); 		//返回页码
				 pagemsg.put("itemnum", page.getPageSize());		//返回条数
				 pagemsg.put("totalpagenum", page.getPageCount());  //返回总页
				 pagemsg.put("totalitemnum", page.getRsCount());    //总条数
				 
				Map sendJson = new HashMap();
		        sendJson.put("dealmsg", dealmsg);
		        sendJson.put("pagemsg", pagemsg);
		        this.returnResult(response, tempKey, userName, "0000", "0014成功", servicetype, sendJson, true);  
				return;
				
			}else if(StringUtils.equals("0021",servicetype))	//service_type	0021:押金明细
			{
//				 AccountBillAction action = new AccountBillAction();
				 request.getSession().setAttribute("userSession", userForm);
				 
				 AcctBillForm form = new AcctBillForm();
				 form.setTradetype( contentObj.getString("type")); //交易类型	type
				 form.setTradeaccount( contentObj.getString("account"));
				 form.setTradeserial(  contentObj.getString("streamno")  );              //交易流水号
				 
				 form.setStartDate( contentObj.getString("starttime"));
				 form.setEndDate( contentObj.getString("endtime"));
				 form.setState("0");

				 form.setCurPage( Integer.parseInt(contentObj.getString("pagenum")) );  		//请求页码
				 request.setAttribute("itemnum", contentObj.getString("itemnum"));//请求条数	itemnum
				 
				 int ret =  PhoneCountDao.commyjList( form, userForm, request, response);
				 Map dealmsgMap = new HashMap();
				 List list =  (List)request.getAttribute("orderList");
				 List dealmsg   = new ArrayList();
				 
				 for(Object tmp : list){
					 String[] temp = (String[])tmp;
					 Map map = new HashMap();
					 map.put("time", 			wrapObject(temp[3])); 													// 日期
					 map.put("account", 		wrapObject(temp[0])); 													// 交易账号
					 map.put("account_type", URLEncoder.encode(wrapObject(temp[13]),"utf-8"));
					 map.put("type", 			URLEncoder.encode(wrapObject(temp[5]),"utf-8"));     		// 交易类型	type	
					 map.put("fee", 			wrapObject(temp[4]));    												// 金额	fee	
					 map.put("state", 			URLEncoder.encode(wrapObject(temp[7]),"utf-8"));   			// 状态
					 map.put("orderno", 		wrapObject(temp[1]));  													// 订单号	orderno	
					 map.put("in_out", 			wrapObject(temp[11]));     		// 转出或转入账号	in_out
					 map.put("fee_type", URLEncoder.encode(wrapObject(temp[12]),"utf-8"));
					 map.put("balance", 		wrapObject(temp[9]));	 												// 帐号余额
					 map.put("deal_account", wrapObject(temp[2]));
					 dealmsg.add(map);
				 }
				 PageAttribute page = (PageAttribute)request.getAttribute("page");
				 Map pagemsg = new HashMap();
				 pagemsg.put("pagenum", page.getCurPage()); 		//返回页码
				 pagemsg.put("itemnum", page.getPageSize());		//返回条数
				 pagemsg.put("totalpagenum", page.getPageCount());  //返回总页
				 pagemsg.put("totalitemnum", page.getRsCount());    //总条数
				 
				Map sendJson = new HashMap();
		        sendJson.put("dealmsg", dealmsg);
		        sendJson.put("pagemsg", pagemsg);
		        this.returnResult(response, tempKey, userName, "0000", "0014成功", servicetype, sendJson, true);  
				return;
				
			}else if(StringUtils.equals("0022",servicetype)){ 			//交通罚款
				
				JtfkForm form = new JtfkForm();
				form.setVehicle( URLDecoder.decode(contentObj.getString("carno"),"utf-8") ); 		//车牌号	"carno
				form.setVehicleType( contentObj.getString("cartype") ); //车辆类型	cartype
				form.setFrameNo( contentObj.getString("carnum")  ); 	//车架号	carnum
				//form.setVehicleType( contentObj.getString("area") ); 	/*违章区域	area*/
				System.out.println(URLDecoder.decode(contentObj.getString("carno"),"utf-8") );
				int ret = PhoneDao.queryJtfk(mapping,form,request,response);
				if(ret==0)
				{
					List rs = new ArrayList();
					
					List list = (List)request.getAttribute("jtfkList");
					for(int i=0; i<list.size(); i++)
					{
						JtfkBean bean = (JtfkBean)list.get(i);
						Map item  = new HashMap();
						item.put("time", wrapObject(bean.getViolationTime()));  //time	违规时间
						item.put("money", bean.getDealFee());  //money	缴费金额
						item.put("area", URLEncoder.encode(wrapObject(bean.getViloationLocation()),"utf-8") );  //area	违章地点
						item.put("trafficdetail", URLEncoder.encode(bean.getViloationDetail(), "utf-8") );  //trafficdetail	违章细节
						item.put("fileno",  wrapObject( bean.getWsh() )  );  //fileno	文件号
						item.put("isdeal",  wrapObject( bean.getDealFlag() ) );  //isdeal	是否可以处理
						item.put("ismail", wrapObject("") );  //ismail	是否邮递
						item.put("isnow", wrapObject( bean.getLiveBill() ) );  //isnow	是否现场单
						item.put("trafficid", wrapObject( bean.getViolationId() ) );  //trafficid	违章id
						rs.add(item);
					}
					Map sendJson = new HashMap();
			        sendJson.put("dealmsg", rs);
			        this.returnResult(response, tempKey, userName, "0000", "0023成功", servicetype, sendJson, true);  
			        
				}else
				{
					this.returnResult(response, tempKey, userName, "0001", "查询交通罚款失败", servicetype, true);  
				}
				return;
			}else if(StringUtils.equals("0023",servicetype)){ //电信手机端查询服务类  com.wlt.webm.mobile.TelPhoneFill
				Log.info("★★★0023电信手机端查询");
				TelcomForm   form = new TelcomForm ();
				form.setTradeObject(contentObj.getString("phone"));
				form.setNumType(contentObj.getString("type"));
				com.wlt.webm.mobile.TelPhoneFill fill = new TelPhoneFill();
				int ret  = fill.telcomQuery(form, userForm);
				if( ret==1 )
				{
					Map billmsgMap = new HashMap();
					billmsgMap.put("name", URLEncoder.encode(wrapObject(form.getUserName()), "utf-8"));      //name	用户姓名
					billmsgMap.put("money", Tools.yuanToFen(form.getTotalFee()));     //money	用户金额
					billmsgMap.put("dealtime", " ");  //dealtime	交易时间
					billmsgMap.put("billtime", " ");  //billtime	计费周期
					billmsgMap.put("streamno", wrapObject(form.getSeqNo()));  	//streamno	流水号
					List lists = form.getBillList();
					Log.info("★★★0023电信手机端查询：" + lists.toString());
					List rs = new ArrayList();
					for(int i=0; i<lists.size(); i++)
					{
						Map<String, String> it = (Map<String, String>)lists.get(i);
						
						for(String m : it.keySet())
						{
							Map item = new HashMap();
							item.put("name", URLEncoder.encode(wrapObject(m),"UTF-8"));
							item.put("value", URLEncoder.encode(wrapObject(it.get(m)),"UTF-8"));
							rs.add(item);
						}
					}
					billmsgMap.put("billList", rs );  //明细
					Map sendJson = new HashMap();
			        sendJson.put("billmsg", billmsgMap);
			        this.returnResult(response, tempKey, userName, "0000", "0023成功", servicetype, sendJson, true);  
					
				}else
				{
					this.returnResult(response, tempKey, userName, "0001", "查询账单失败", servicetype, true);  
				}
				
				return;
			}else if(StringUtils.equals("0024",servicetype)){ //额度转移
				Log.info("★★★  0024额度转移");
				com.wlt.webm.mobile.FeeTransfer tran = new com.wlt.webm.mobile.FeeTransfer();
				int ret = tran.feeTransfer(userForm,  Integer.parseInt( contentObj.getString("fee") ), contentObj.getString("id"), contentObj.getString("sec"));
				//最后返回结果
				if( ret==0 )
				{
					Map paymsg = new HashMap();
					
					paymsg.put("orderno", wrapObject( "" ));//订单号	orderno	
					paymsg.put("dedmoney", wrapObject( "" ));//实际转账金额	dedmoney	
					paymsg.put("money", wrapObject( contentObj.getString("fee")  ));//转账金额	"money
					paymsg.put("balance", wrapObject( "" ));//余额	balance	
					
					Map sendJson = new HashMap();
			        sendJson.put("pay_msg", paymsg);
					this.returnResult(response, tempKey, userName, "0000", formatRecode(ret), servicetype, sendJson, true); 
					
					
				}else
				{
					this.returnResult(response, tempKey, userName, ret+"", formatFeeTransfer(ret), servicetype, true);  
				}
				return;
			}else if(StringUtils.equals("0025",servicetype)){ //冲正查询
				
				OrderAction action = new OrderAction();
				 request.getSession().setAttribute("userSession", userForm);
				 OrderForm form = new OrderForm();
				 form.setCurPage( Integer.parseInt(contentObj.getString("pagenum")) );
				 form.setFee( contentObj.getString("fee"));
				 form.setTradeobject( contentObj.getString("phone"));
				 form.setStartDate( contentObj.getString("starttime"));
				 form.setEndDate( contentObj.getString("endtime"));
				 form.setService( contentObj.getString("type"));
				 form.setState("0");
				 
				 int ret = action.phoneList(mapping, form, request, response);
				 Map dealmsgMap = new HashMap();
				 List list =  (List)request.getAttribute("orderList");
				 List dealmsg   = new ArrayList();
				 for(Object tmp : list){
					 String[] temp = (String[])tmp;
					 Map map = new HashMap();
					 map.put("time", 		wrapObject(temp[6])); 				//日期
					 map.put("phone", 		wrapObject(temp[2])); 				//号码
					 map.put("phone_type", 	URLEncoder.encode(wrapObject(temp[10]), "utf-8")); 		   //号码类型	
					 map.put("area", 		URLEncoder.encode(wrapObject(temp[0]), "utf-8"));    		//区域 	
					 map.put("fee", 		wrapObject(temp[5]));    			//金额
					 map.put("state", 		URLEncoder.encode(wrapObject(temp[8]),"utf-8"));   			//状态
					 map.put("orderno", 	wrapObject(temp[1]));  				//订单号
					 map.put("type", 		URLEncoder.encode(wrapObject(temp[12]),"utf-8"));     		//类型
					 map.put("balance", 	wrapObject(temp[14]));	 			//帐号余额
					 dealmsg.add(map);
				 }
				 PageAttribute page = (PageAttribute)request.getAttribute("page");
				 Map pagemsg = new HashMap();
				 pagemsg.put("pagenum", page.getCurPage()); 		//返回页码
				 pagemsg.put("itemnum", page.getPageSize());		//返回条数
				 pagemsg.put("totalpagenum", page.getPageCount());   //返回总页
				 pagemsg.put("totalitemnum", page.getRsCount());   //总条数
				 
				Map sendJson = new HashMap();
		        sendJson.put("dealmsg", dealmsg);
		        sendJson.put("pagemsg", pagemsg);
		        this.returnResult(response, tempKey, userName, "0000", "0014成功", servicetype, sendJson, true);  
		        return;
				
			}else if(StringUtils.equals("0026",servicetype)){ //冲正
				Log.info("★★★0026冲正");
				com.wlt.webm.mobile.ReverseFill fill = new com.wlt.webm.mobile.ReverseFill();
				int ret = fill.reverse(contentObj.getString("orderno"), userForm);
				//最后返回结果
				if( ret==0 )
				{
					this.returnResult(response, tempKey, userName, "0000", formatRecode(ret), servicetype, true);  
				}else
				{
					this.returnResult(response, tempKey, userName, ret+"", formatFeeTransfer(ret), servicetype, true);  
				}
				
				return;
			}else if(StringUtils.equals("0027",servicetype)){ //修改密码
				Log.info("★★★0027修改密码");
				SysLoginUserForm userLoginForm = new SysLoginUserForm();
				userLoginForm.setUser_id(userForm.getUser_id());
				userLoginForm.setUsername(userName);
				userLoginForm.setUserpassword(contentObj.getString("newsec"));
				
				int ret = PhoneDao.updatePass(contentObj.getString("type"), contentObj.getString("oldsec"), userLoginForm, request);
				
				//最后返回结果
				if( ret==0 )//修改成功
				{
					this.returnResult(response, tempKey, userName, "0000", formatRecode(ret), servicetype, false);  
				}else
				{
					this.returnResult(response, tempKey, userName, ret+"", (String)request.getAttribute("mess"), servicetype, false);  
				}
				return;
			}else if(StringUtils.equals("0028",servicetype)){ //0028:查询账号余额
			//	Log.info("★★★0028查询账号余额");
				int ret = PhoneDao.getAccountInfo(userForm, request);
				//最后返回结果
				if( ret==0 )//修改成功
				{
					 Map paymsg = new HashMap();
					 paymsg.put("yongjinbalance",  	 request.getAttribute("yongjinbalance")); 
					 paymsg.put("yajingbalance",        request.getAttribute("yajingbalance")); 
					 paymsg.put("dongjiebalance",       request.getAttribute("dongjiebalance")); 
					 
					Map sendJson = new HashMap();
			        sendJson.put("pay_msg", paymsg);
					this.returnResult(response, tempKey, userName, "0000", "0028查询账号余额成功", servicetype, sendJson, true);  
				}else
				{
					this.returnResult(response, tempKey, userName, ret+"", (String)request.getAttribute("mess"), servicetype, true);  
				}
				return;
				
			}else if(StringUtils.equals("0029",servicetype)){ //0029:佣金转押金
				Log.info("★★★0029佣金转押金");
				request.setAttribute("transFee", contentObj.getString("fee"));
//				int ret = PhoneDao.transChild(userForm, request);
				int ret =SmallRequest.transChild(userForm, ""+request.getAttribute("transFee"));
				//最后返回结果
				if( ret==0 )//修改成功
				{
					 Map paymsg = new HashMap();
					 paymsg.put("orderno",  	 		request.getAttribute("orderno")); 
					 
					//查询资金帐户
				    ChildFaactForm fms = PhoneDao.getUserFundAcct(userForm.getUser_id(), "02");
					 
					 paymsg.put("dedmoney",    request.getAttribute("transFee")); 
					 paymsg.put("money",       request.getAttribute("transFee")); 
					 paymsg.put("balance",     fms.getUsableleft()); 
					 
					Map sendJson = new HashMap();
			        sendJson.put("pay_msg", paymsg);
					this.returnResult(response, tempKey, userName, "0000", "0029佣金转押金成功", servicetype, sendJson, true);  
				}else
				{
					this.returnResult(response, tempKey, userName, ret+"", (String)request.getAttribute("mess"), servicetype, true);  
				}
				return;
			}else if(StringUtils.equals("0030",servicetype)){ //0030:获取激活短信
				Log.info("★★★ 0030:获取激活短信  ");
				userForm = new SysUserForm();
				userForm.setUsername(userName);
				String ret = com.wlt.webm.mobile.SmallRequest.sendValidateCode(userForm);
				//最后返回结果
				if( ret.equalsIgnoreCase("1") )// 1 发送短信失败 
				{
					this.returnResult(response, publicKey, userName, "0001", "发送短信失败", servicetype, true); 
					
				}else if( ret.equalsIgnoreCase("2") )// 1 发送短信失败 
				{
					this.returnResult(response, publicKey, userName, "0002", " Username未获取到", servicetype, true);  
				}else
				{
					userForm.setMmccode(ret); 
					keyMap.put(userName, userForm);
					this.returnResult(response, publicKey, userName, "0000", "发送短信成功!", servicetype, true); 
				}
				return;
			}else if(StringUtils.equals("0031",servicetype)){ //0031  登录激活
				Log.info("★★★ 0031 登录激活  ");
				String mmcString = jsonObj.getString("passsec");
				if(!mmcString.equals(userForm.getMmccode()))
				{
					this.returnResult(response, publicKey, userName, "0001", "验证码不匹配!", servicetype, true); 
					return;
				}
				userForm.setLogin(userForm.getUsername());
				int ret = com.wlt.webm.mobile.SmallRequest.updateState(userForm);
				//最后返回结果
				if( ret==1)// 1 发送短信失败 
				{
					this.returnResult(response, publicKey, userName, "0001", "激活失败", servicetype, true); 
					
				}else if( ret==0 )// 
				{
					userForm.setLogin( jsonObj.getString("user_name") );
					userForm.setMmccode("");
					int state = PhoneDao.login1(userForm, request, response);
					if( state!=0 )
					{
						this.returnResult(response, publicKey, userName, state+"", (String)request.getAttribute("mess"), servicetype, true);
						return;
					}
					 //查询公告
			        List list = PhoneDao.getSysNoticeListLatest(userForm);
			        //查询银行账号
			        Map temp = PhoneDao.getAccountData(userForm.getUser_id());
			        //查询资金帐户
			        ChildFaactForm fms = PhoneDao.getUserFundAcct(userForm.getUser_id(), "02");
			        //sessionkey
			        String sessionkey = getSessionKey();
			        //用户信息map
			        Map userDataMap = new HashMap();
			        userDataMap.put("id", wrapObject(userForm.getUser_id()) );
			        userDataMap.put("user_name", URLEncoder.encode(wrapObject(temp.get("user_name")), "UTF-8"));
			        userDataMap.put("name", URLEncoder.encode( wrapObject(userForm.getUserename()), "UTF-8") );
			        userDataMap.put("phone", userForm.getPhone());
			        userDataMap.put("address", URLEncoder.encode( wrapObject(userForm.getAddress()), "UTF-8"));
			        userDataMap.put("mail", wrapObject(userForm.getUsermail()) );
			        userDataMap.put("identity", wrapObject( temp.get("user_icard")) );
			        userDataMap.put("area",    URLEncoder.encode ( wrapObject(PhoneDao.getSite(userForm.getUsersite()) + userForm.getWltarea()), "UTF-8") );
			       
			        //
			        Map bankDataMap = new HashMap();
			        bankDataMap.put("balance", fms.getUsableleft());
			        bankDataMap.put("accountno", fms.getChildfacct());
			        bankDataMap.put("bankno", wrapObject( temp.get("bankno")) );
			       
			        Map sendJson = new HashMap();
			        sendJson.put("consultation_list", list);
			        sendJson.put("user_data", userDataMap);
			        sendJson.put("bank_data", bankDataMap);
			        sendJson.put("sessionkey", sessionkey);
			       
			        userForm.setStartDate(Tools.getNow3());
			        userForm.setEndDate(sessionkey);
			       
			        keyMap.put(userName, userForm);
			        this.returnResult(response, publicKey, userName, "0000", "登录成功!", servicetype, sendJson, true);  
				}
				return;
			}else
			{
				return;
			}
				
			
		}	
		}catch(Exception e){
			System.out.println(e);
			this.returnResult(response, tempKey, userName, "0001", e.getMessage(), servicetype, true);  
		}
	
		
		
		
		
	}
	
	
	
	
	
	

}
