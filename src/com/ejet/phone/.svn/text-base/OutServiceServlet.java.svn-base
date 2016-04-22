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
import com.ejet.phone.dao.OutServiceDao;
import com.ejet.phone.dao.PhoneDao;
import com.wlt.webm.business.action.AccountBillAction;
import com.wlt.webm.business.action.OrderAction;
import com.wlt.webm.business.bean.JtfkBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.AcctBillForm;
import com.wlt.webm.business.form.ChildFaactForm;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.mobile.QbMobile;
import com.wlt.webm.mobile.TelPhoneFill;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.PageAttribute;

public class OutServiceServlet extends BaseServlet{
	
	private String     tempKey = publicKey;
	
	public OutServiceServlet()
	{
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doPost(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if( StringUtils.equals("test", method) )
		{
			this.returnResult(response, publicKey, "V2.0.3", "0000", "URL is ok!", "", true);
			return;
		}
		String msgData = request.getParameter("JSON_DATA");
		
		if( StringUtils.isEmpty(msgData) )
			this.returnResult(response, publicKey, "", "0001", "参数错误!", "", true);
		
		String [] jsonArray = msgData.split("\\|");
		if( jsonArray==null || jsonArray.length!=3 )
			this.returnResult(response, publicKey, "", "0001", "参数错误!", "", true);
		
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
		
		if( StringUtils.equals("login", method) )
		{
			userForm = new SysUserForm();
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
	    Log.info("★★★OUT  jsonObj:" + jsonObj.toString());
	    
		if(StringUtils.equals("0001", servicetype) && StringUtils.equals("login", method)){
			
			Log.info("★★★ Login -------------------------");
			userForm.setLogin( jsonObj.getString("user_name") );
			userForm.setPassword( jsonObj.getString("passsec") );
			 
			int state = OutServiceDao.login(userForm, request, response);
			
			if( state!=0 )
			{
				this.returnResult(response, publicKey, userName, state+"", (String)request.getAttribute("mess"), servicetype, true);
				return;
			}
	        //  查询公告
	        List list = OutServiceDao.getSysNoticeListLatest(userForm);
	        //查询银行账号
	        Map temp = OutServiceDao.getAccountData(userForm.getUser_id());
	        //  查询资金帐户
	        ChildFaactForm fms = OutServiceDao.getUserFundAcct(userForm.getUser_id(), "02");
	        //sessionkey
	        String sessionkey = getSessionKey();
	        
	        //判断是否是接口商
	        if( Integer.parseInt(userForm.getRoleType())!= Constant.WLT_AEGENT )
	        {
	        	this.returnResult(response, publicKey, userName, "0001", "登录失败，非接口商!", servicetype, true);  
	        	return;
	        }
	        
	        //  用户信息map
	        Map userDataMap = new HashMap();
	        userDataMap.put("id", wrapObject(userForm.getUser_id()) );
	        userDataMap.put("user_name", URLEncoder.encode(wrapObject(temp.get("user_name")), "UTF-8"));
	        userDataMap.put("name", URLEncoder.encode( wrapObject(userForm.getUserename()), "UTF-8") );
	        userDataMap.put("phone", userForm.getPhone());
	        userDataMap.put("address", URLEncoder.encode( wrapObject(userForm.getAddress()), "UTF-8"));
	        userDataMap.put("mail", wrapObject(userForm.getUsermail()) );
	        userDataMap.put("identity", wrapObject( temp.get("user_icard")) );
	        userDataMap.put("area",    URLEncoder.encode ( wrapObject( OutServiceDao.getSite(userForm.getUsersite()) + userForm.getWltarea()), "UTF-8") );
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
	       
		}else if(StringUtils.equals("0030",servicetype)){ //0030:获取激活短信
			Log.info("★★★ 0030:获取激活短信  ");
			String ret = com.wlt.webm.mobile.SmallRequest.sendValidateCode(userForm);
			//最后返回结果
			if( ret.equalsIgnoreCase("1") )// 1 发送短信失败 
			{
				this.returnResult(response, tempKey, userName, "0001", "发送短信失败", servicetype, true); 
				
			}else if( ret.equalsIgnoreCase("2") )// 1 发送短信失败 
			{
				this.returnResult(response, tempKey, userName, "0002", " Username未获取到", servicetype, true);  
			}else
			{
				this.returnResult(response, tempKey, userName, "0000", "发送短信成功!", servicetype, true); 
			}
			return;
			
		}else //非登录
		{
			JSONObject contentObj	= jsonObj.getJSONObject("content");
			
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
					ret = OutServiceDao.validateAndGetPhone(form, userForm, request, response);
				}
				else
				{
					ret = OutServiceDao.validatePhone(form, userForm, request, response);
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
				
				if(!mytype.equals(type) && !subtype.equals("04"))// 验证号码类型，与交易业务类型是否匹配
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
		        this.returnResult(response, tempKey, userName, "0000", "0002充值验证成功", servicetype, sendJson, true);  
				
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
					ret = OutServiceDao.payGDTelecome(form, userForm, request, response);
//					com.wlt.webm.mobile.TelPhoneFill tel = new com.wlt.webm.mobile.TelPhoneFill();
//					ret = tel.telMobile(form, userForm);
					
				}else if(subtype.equals("04") )//全国充值
				{
					System.out.println("OUT全国充值1:");
					ret = OutServiceDao.tzFill(form, userForm, request, response);
					returnmsg = (String)request.getAttribute("mess");
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
			        this.returnResult(response, tempKey, userName, "0000", "成功", servicetype, sendJson, true);  
			        return;
					
				}else
				{
					this.returnResult(response, tempKey, userName, ret+"", returnmsg + formatRecode(ret), servicetype, true);  
				}
				return;
				
			}else if(StringUtils.equals("0031",servicetype)){ //0031  登录激活
					Log.info("★★★ 0031 登录激活  ");
					String ret = com.wlt.webm.mobile.SmallRequest.sendValidateCode(userForm);
					//最后返回结果
					if( ret.equalsIgnoreCase("1") )// 1 发送短信失败 
					{
						this.returnResult(response, tempKey, userName, "0001", "发送短信失败", servicetype, true); 
						
					}else if( ret.equalsIgnoreCase("2") )// 1 发送短信失败 
					{
						this.returnResult(response, tempKey, userName, "0002", " Username未获取到", servicetype, true);  
					}else
					{
						this.returnResult(response, tempKey, userName, "0000", "发送短信成功!", servicetype, true); 
					}
					return;
			}else{
				this.returnResult(response, tempKey, userName, "0002", " 未找到业务类型", servicetype, true);  
				return;
			}
				
			
		}	
		}catch(Exception e){
			System.out.println(e);
			this.returnResult(response, tempKey, userName, "0001", e.getMessage(), servicetype, true);  
		}
	
		
		
		
		
	}
	
	
	
	
	
	

}
