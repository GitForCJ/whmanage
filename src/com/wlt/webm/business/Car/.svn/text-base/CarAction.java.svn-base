package com.wlt.webm.business.Car;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.thoughtworks.xstream.XStream;
import com.wlt.webm.business.Car.bean.CarInfo;
import com.wlt.webm.business.Car.bean.IgnoreFieldProcessorImpl;
import com.wlt.webm.business.Car.bean.WzList;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.YMmessage;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.tool.Tools;

/**
 * @author admin
 */
public class CarAction  extends DispatchAction {
	
	/**
	 * 代理店魔板编号
	 */
	public static final String groups="20002";
	/**
	 * 用户查询action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward CarQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
//		//操作权限判断
		if(userSession==null){
			request.setAttribute("mess","您没有查单的权限!");
			return new ActionForward("/car/query.jsp");
		}
		if(!"3".equals(userSession.getRoleType())){
			request.setAttribute("mess","您没有查单的权限!");
			return new ActionForward("/car/query.jsp");
		}
		
		CarInfo car=(CarInfo)form;
		//参数验证
		if(car.getProvince_name()==null || "".equals(car.getProvince_name())||
				car.getCar_number()==null || "".equals(car.getCar_number()) ||
				car.getCar_type()==null || "".equals(car.getCar_type())||
				car.getCar_code()==null || "".equals(car.getCar_code()) ||
				car.getCar_engine()==null || "".equals(car.getCar_engine())||
				car.getCar_username()==null || "".equals(car.getCar_username()) ||
				car.getCar_userphone()==null || "".equals(car.getCar_userphone())){
			Log.info("CarQuery 违章处理，请求参数不足，，，异常操作，");
			request.setAttribute("mess","参数不足,异常操作");
			return new ActionForward("/car/query.jsp");
		}
		//完整车牌号
		String carsNumber=car.getProvince_name()+car.getCar_number();
		request.setAttribute("carInfo",car);
		
		List arry=getQueryList(carsNumber,groups,car);
		if(arry==null || arry.size()<=0){
			request.setAttribute("mess","未查询到违章记录!");
			return new ActionForward("/car/query.jsp");
			
		}
		request.setAttribute("arry",arry);
		return new ActionForward("/car/CarPlaceOrder.jsp");
	}
	
	/**
	 * 交通罚款 下单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward carOrderAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
//		//操作权限判断
		if(userSession==null){
			request.setAttribute("mess","您没有查单的权限!");
			return new ActionForward("/car/query.jsp");
		}
		if(!"3".equals(userSession.getRoleType())){
			request.setAttribute("mess","您没有查单的权限!");
			return new ActionForward("/car/query.jsp");
		}
		CarInfo car=(CarInfo)form;
		//money#id
		String[] ids=request.getParameterValues("ids");
		if(ids==null || ids.length<=0){
			request.setAttribute("mess","参数不足,系统异常");
			return CarQuery(mapping,form,request,response);
		}
		//完整车牌号
		String carsNumber=car.getProvince_name()+car.getCar_number();
		//页面计算的下单总额
		float pageTotal=0;
		
		//需要下单提交到上游的信息 集合
		List<String[]> arryList=new ArrayList<String[]>();
		for(int i=0;i<ids.length;i++){
			float one_money=Float.parseFloat(ids[i].split("#")[0]);
			
			//单条违章信息
			String[] s1=(String[])getYQuery(carsNumber,groups,ids[i].split("#")[1],null).get(0);
			
			//单条违章费用
			float wh_Zmoney=Float.parseFloat(s1[4])+Float.parseFloat(s1[5])+Float.parseFloat(s1[6])+Float.parseFloat(s1[13]);
			
			//页面下单总额，后台计算
			pageTotal=pageTotal+wh_Zmoney;
			
			//单条违章信息 扣费金额 对比验证
			if(one_money!=(wh_Zmoney)){
				request.setAttribute("mess","下单失败,下单金额不符!");
				return CarQuery(mapping,form,request,response);
			}
			//订单状态验证 是否可代办(1：可以代办 ,2：不可以代办)   102初始化状态
			if(!"1".equals(s1[7]) || !"102".equals(s1[10])){
				request.setAttribute("mess","下单失败,此违章不能下单!");
				return CarQuery(mapping,form,request,response);
			}
			//后台每条下单违章，添加到下单集合中
			arryList.add(s1);
		}
		//账务 dealserial 流水号
		String dealserial="Order"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
		
		//0成功  1失败 2余额不足  3异常 
		int bool=DownOrder(arryList,userSession.getUserno(),pageTotal,car,dealserial,"");
		if(bool==0){
			request.setAttribute("mess","下单成功!");
			return new ActionForward("/car/query.jsp");
		}else if(bool==1){
			request.setAttribute("mess","下单失败!");
			return new ActionForward("/car/query.jsp");
		}else if(bool==2){
			request.setAttribute("mess","下单失败,账户余额不足!");
			return new ActionForward("/car/query.jsp");
		}else if(bool==4){
			request.setAttribute("mess","下单失败,重复下单!");
			return new ActionForward("/car/query.jsp");
		}else{
			request.setAttribute("mess","下单异常,联系客服!");
			return new ActionForward("/car/query.jsp");
		}
	}
	
	/**
	 * 违章查询 list
	 * @param carsNumber 完整车牌号
	 * @param gr 魔板id
	 * @param car carInfo 
	 * @return list
	 */
	public List getQueryList(String carsNumber,String gr,CarInfo car){
		//预查询库 取数据
		List arry=getYQuery(carsNumber,groups,null,car);
		if(arry!=null && arry.size()>0){
			//预查询库有数据
			return arry;
		}else{
			DBService db=null;
			try {
				//预查询库无数据
				List<HashMap> arr=Interface.Query(car.getCar_username(), car.getCar_type(), car.getCar_code(), car.getCar_engine(), carsNumber);
				if(arr==null || arr.size()<=0){
					return arr;
				}
				db=new DBService();
				db.setAutoCommit(false);
				String ownerInfo_id=db.getString("SELECT id FROM wht_yquery_ownerinfo WHERE carNum='"+carsNumber+"'");
				if(ownerInfo_id==null || "".equals(ownerInfo_id)){
					//插入
					String sql="insert into wht_yquery_ownerinfo(contactNum,name,carNum,carFrameNum,engineNumber,carType,address,exp1) " +
							"values('"+car.getCar_userphone()+"','"+car.getCar_username()+"','"+carsNumber+"','"+car.getCar_code()+"','"+car.getCar_engine()+"','"+car.getCar_type()+"','','"+Tools.getNow()+"');";
					ownerInfo_id=db.getGeneratedKey(sql)+"".trim();
				}else{
					//更新
					String sql="UPDATE wht_yquery_ownerinfo SET contactNum='"+car.getCar_userphone()+"',name='"+car.getCar_username()+"',carFrameNum='"+car.getCar_code()+"',engineNumber='"+car.getCar_engine()+"',carType='"+car.getCar_type()+"',exp1='"+Tools.getNow()+"'  WHERE id="+ownerInfo_id+"  AND carNum='"+carsNumber+"'";
					db.update(sql);
					//干掉此用户历史违章信息
					db.update("DELETE FROM wht_yquery_wz WHERE ownerinfoId="+ownerInfo_id);
				}
				//入预查询库数据
				for(int i=0;i<arr.size();i++){
					HashMap mm=arr.get(i);
					StringBuffer buf=new StringBuffer();
					buf.append("INSERT INTO wht_yquery_wz(ownerinfoId,insertTime,carId,pecNum,peccode,pecDesc,pecAddr,pecDate,pecScore," +
							"corpus,lateFee,replaceMoney,agent,woState) VALUES(");
					buf.append(ownerInfo_id);
					buf.append(",'"+Tools.getNow()+"'");
					buf.append(",'"+mm.get("ViolationId")+"'");//违章侧ID
					buf.append(",'"+mm.get("Wsh")+"'");//违法受理号(文书号)
					buf.append(",'"+mm.get("Wzdm")+"'");//违章代码
					buf.append(",'"+mm.get("ViloationDetail")+"'");//违章行为描述
					buf.append(",'"+mm.get("ViloationLocation")+"'");//违章地点
					buf.append(",'"+mm.get("ViolationTime")+"'");//违章时间
					//扣分
					String koufen=mm.get("Wzdm")==null?"-1":mm.get("Wzdm")+"";
					if("-1".equals(koufen)){
						koufen="0";
					}else if("7".equals(koufen)){
						koufen="12";
					}else{
						koufen=koufen.substring(1,2);
					}
					buf.append(","+koufen);//扣分	 pecScore
					buf.append(","+(mm.get("FineFee")==null?"0":Integer.parseInt(mm.get("FineFee")+"".trim())*10));//本金 corpus
					buf.append(","+(mm.get("LateFee")==null?"0":Integer.parseInt(mm.get("LateFee")+"".trim())*10));//滞纳金  lateFee
					buf.append(","+(mm.get("DealFee")==null?"0":Integer.parseInt(mm.get("DealFee")+"".trim())*10));//车主通代办费 replaceMoney
					buf.append(",'"+("1".equals(mm.get("DealFlag")+"".trim())?1:2)+"'");//是否可代办(1：可以代办 ,2：不可以代办) agent
					buf.append(",'102'");//初始化状态
					buf.append(")");
					db.update(buf.toString());
				}
				db.commit();
				//预查询库 取数据
				List list=getYQuery(carsNumber,groups,null,null);
				return list;
			} catch (Exception e) {
				if(db!=null){
					db.rollback();
				}
				e.printStackTrace();
			}finally{
				if(db!=null){
					db.close();
					db=null;
				}
			}
			return null;
		}
	}
	/**
	 * 预查询数据库  取数据
	 * @param carNumber 车牌号
	 * @param gr  魔板编号 对应 sys_tpemploy_jf groups 
	 * @param id 违章记录id wht_yquery_ownerinfo id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getYQuery(String carNumber,String gr,String id,CarInfo car){
		DBService db=null;
		try {
			db=new DBService();
			if(car!=null){
				db.update("UPDATE wht_yquery_ownerinfo SET contactNum='"+car.getCar_userphone()+"',name='"+car.getCar_username()+"',carFrameNum='"+car.getCar_code()+"',engineNumber='"+car.getCar_engine()+"',carType='"+car.getCar_type()+"',exp1='"+Tools.getNow()+"' WHERE carNum='"+carNumber+"'");
			}
			String sql="SELECT " +
					"id,pecDate,pecAddr,pecDesc,corpus,lateFee," +
					"replaceMoney,agent,pecState,pecScore,woState,areacode," +
					"(SELECT carType FROM wht_yquery_ownerinfo WHERE id=ownerinfoId) cartypes," +
					"0 as wh_fee," +
					"carId,pecNum,peccode,0 as areaCode " +
					"FROM " +
					"wht_yquery_wz WHERE ownerinfoId=(SELECT id FROM wht_yquery_ownerinfo " +
					"WHERE carNum='"+carNumber+"') ";
			if(id!=null){
				sql=sql+" AND id="+id;
			}else{
				sql=sql+" AND insertTime>DATE_FORMAT(DATE_SUB(SYSDATE(),INTERVAL 1 DAY),'%Y%m%d%H%i%s')";
			}
			List arryList=db.getList(sql);
			if(arryList==null || arryList.size()<=0){
				return null;
			}
			List arry=new ArrayList();
			for(int i=0;i<arryList.size();i++){
				String[] strs=(String[])arryList.get(i);
				//通过违章地址获取地市编号
				String AddsCode=getAddrCode(strs[2]);
				strs[17]=AddsCode;
				//获取佣金值(服务费)  根据 配置的魔板编号，地市编号，车辆类型 
				String fee=db.getString("SELECT val FROM sys_tpemploy_jf WHERE groups="+gr+" AND pid="+AddsCode+" AND carid="+strs[12]);
				
				//本金(里) * 佣金比(佣金(元)/100) 
				//float f=Float.parseFloat(strs[4]) * (Float.parseFloat(fee)/100);
				
				float f=Float.parseFloat(fee)*1000;
				//万汇通佣金
				strs[13]=f+"";
				arry.add(strs);
			}
			return arry;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 *通过违章地址获取地市编号
	 * @param address
	 * @return String 
	 */
	public String getAddrCode(String address){
		/**
		 * sa_id	sa_name


371	清远
372	云浮
374	东莞
375	佛山
387	汕头
388	潮州
393	揭阳市
394	阳江市
		 */
		String str="381";
		if(address.indexOf("汕尾")!=-1){
			str="74";
		}else if(address.indexOf("深圳")!=-1){
			str="77";	
		}else if(address.indexOf("广州")!=-1){
			str="78";	
		}else if(address.indexOf("珠海")!=-1){
			str="101";	
		}else if(address.indexOf("惠州")!=-1){
			str="102";	
		}else if(address.indexOf("湛江")!=-1){
			str="103";	
		}else if(address.indexOf("韶关")!=-1){
			str="104";	
		}else if(address.indexOf("中山")!=-1){
			str="105";	
		}else if(address.indexOf("茂名")!=-1){
			str="366";	
		}else if(address.indexOf("江门")!=-1){
			str="367";	
		}else if(address.indexOf("肇庆")!=-1){
			str="369";	
		}else if(address.indexOf("清远")!=-1){
			str="371";	
		}else if(address.indexOf("云浮")!=-1){
			str="372";	
		}else if(address.indexOf("东莞")!=-1){
			str="374";	
		}else if(address.indexOf("佛山")!=-1){
			str="375";	
		}else if(address.indexOf("汕头")!=-1){
			str="387";	
		}else if(address.indexOf("潮州")!=-1){
			str="388";	
		}else if(address.indexOf("揭阳")!=-1){
			str="393";	
		}else if(address.indexOf("阳江")!=-1){
			str="394";	
		}else if(address.indexOf("梅州")!=-1){
			str="368";	
		}else if(address.indexOf("河源")!=-1){
			str="395";
		}
		return str;
	}
	
	/**
	 * 违章下单
	 * @param arryList 预查询表 wht_yquery_wz 需要下单违章记录集合
	 * @param userno 系统编号
	 * @param pageTotal 万汇通平台交易金额
	 * @param car 车辆信息
	 * @param dealserial 订单号
	 * @return  0成功  1失败 2余额不足  3异常   4重复下单
	 */
	public int DownOrder(List<String[]> arryList,String userno,float pageTotal,CarInfo car,String dealserial,String backURL){
		//完整车牌号
		String carsNumber=car.getProvince_name()+car.getCar_number();
		DBService dbs=null;
		try {
			dbs=new DBService();
			String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+userno+"')";
			String[] s2=dbs.getStringArray(getSql);
			if(s2==null || s2.length<=0){
				return 1;//用户账户不存在
			}
			float accountMoney=Float.parseFloat(s2[1])-pageTotal;
			if(accountMoney<0){
				return 2;//账户余额不足
			}
			String inString="";//代办商侧的违章ID，多个ID以“|”分隔
			float submitMoney=0;//提交到上游总费用（单位为分），含邮费
			for(int i=0;i<arryList.size();i++){
				String[] s3=arryList.get(i);
				inString=inString+s3[14]+"|";
				//提交到上游的费用
				submitMoney=submitMoney+Float.parseFloat(s3[4])+Float.parseFloat(s3[5])+Float.parseFloat(s3[6]);//里
			}
			inString=inString.substring(0,inString.length()-1);
			//重复下单判断
			List arrLists=dbs.getList("SELECT 1 FROM wht_breakrules WHERE id IN ("+inString.replace("|",",")+")");
			if(arrLists!=null && arrLists.size()>0){
				return 4;
			}
			
			//预查询
			HashMap<String,String> map=Interface.PreQuery(inString, submitMoney/10,car);
			if(map==null || map.size()<=0){
				return 1;//与查询失败
			}
			
			//入库
			boolean bool=CreateOrder(car,userno,submitMoney,pageTotal,dealserial,arryList);
			if(!bool){
				return 1;//入库，记录订单失败
			}
			
			//0成功 1失败 ，百事帮下单状态
			int flag=Interface.PlaceOrder(dealserial,map.get("SpTransId"),map.get("ViolationId"),submitMoney/10);
			
			//万恒处理状态
			isBaiShiBang(dealserial,(int)pageTotal,flag,inString,backURL);
			
			if(flag==0){//下单成功，，改为办理中状态 
//				String s9="车牌号:"+carsNumber+","+arryList.size()+"条违章,受理成功,唯一热线：400-9923-988";
//				if(YMmessage.qxtSendSMS(car.getCar_userphone(),s9)){
//					Log.info("违章下单短信发送成功,文件输出,发送号码:"+car.getCar_userphone()+",内容:"+s9);
//				}
				return 0;//下单成功
			}else{
				return 1;//下单失败，退费成功
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}finally{
			if(null!=dbs){
				dbs.close();
				dbs=null;
			}
		}
	}
	
	/**
	 * 入库，记录订单，账务 
	 * @param carInfo 
	 * @param userno 用户系统编号
	 * @param submitMoney 提交金额
	 * @param orderMoney 交易实际金额
	 * @param dealserial 订单号
	 * @param strsList 每条违章 
	 * @return boolean
	 */
	public boolean CreateOrder(CarInfo carInfo,String userno,float submitMoney,float orderMoney,String dealserial,List<String[]> strsList){
		//工单id
		String maxid=Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		DBService dbs=null;
		try {
			dbs=new DBService();
			String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+userno+"')";
			String[] funs=dbs.getStringArray(getSql);
			
			dbs.setAutoCommit(false);
				String sql="INSERT INTO wht_bruleorder(id,woNum,userno,contactNum,name,autoCarID,carnum,dealserial,payMethod,isVisit,totalCharge," +
						"fromCanal,createDate,workerNo,woState,spID,resultCode,carFrameNum,engineNumber,Exp1,Exp2,Exp3,Exp4,Exp5) " +
						"VALUES(" +
						"'"+maxid+"'," +  //id
						"'"+maxid+"'," + //woNum
						"'"+userno+"'," +//userno
						"'"+carInfo.getCar_userphone()+"'," +//contactNum
						"'"+carInfo.getCar_username()+"'," +//name
						"123," +//autoCarID
						"'"+carInfo.getProvince_name()+carInfo.getCar_number()+"'," + //carnum    
						"'"+dealserial+"'," +//dealserial
						"4," +//payMethod
						"2," +//isVisit
						""+submitMoney+"," +//totalCharge //金+服务费+快递费  （提交到上游的总额，不包括我们平台 服务费
						"5," +//fromCanal
						"'"+Tools.getNewDate()+"'," +//createDate
						"'"+dealserial+"'," +//workerNo 默认为空，百世邦则是 下单订单号
						"100," +//woState
						"111," +//spID
						"'02'," +//resultCode
						"'"+carInfo.getCar_code()+"'," +//carFrameNum
						"'"+carInfo.getCar_engine()+"'," +//engineNumber
						"'"+carInfo.getCar_type()+"'," +//exp1 汽车类型
						"'2'," +//exp2 下单类型 电信1  白世邦2   入库0 
						"''," +//exp2 车辆名称
						"''," +//联系人地址
						"" +orderMoney+")";//我们平台扣费总额，本金+滞纳金+上游代办费+万汇通代办费
				dbs.update(sql);
				for(int i=0;i<strsList.size();i++){
					String[] arr=(String[])strsList.get(i);
					
					float one_zMoney=Float.parseFloat(arr[4])+Float.parseFloat(arr[5])+Float.parseFloat(arr[6])+Float.parseFloat(arr[13]);
					float one_submitMoney=Float.parseFloat(arr[4])+Float.parseFloat(arr[5])+Float.parseFloat(arr[6]);
					
					StringBuffer buf=new StringBuffer("INSERT INTO wht_breakrules(" +
							"id,gdid,pecNum,pecCode,pecDesc,pecAddr," +
							"pecDate,pecScore,corpus,lateFee,replaceMoney,ownMoney,agent,pecState,pecChanl,createDate,updateDate,updateWorkerNo," +
							"woState,areaCode,illegalType,Exp1,Exp4,Exp5,Exp2" +
							") " +
							"VALUES(");
					buf.append("'"+arr[14]+"',");//违章信息  id
					buf.append("'"+maxid+"',");//wht_bruleorder  id  gdid
					buf.append("'"+arr[15]+"',");//违法受理号(文书号) pecNum
					buf.append("'"+arr[16]+"',");//违章代码  pecCode
					buf.append("'"+arr[3]+"',");//违章行为描述  pecDesc
					buf.append("'"+arr[2]+"',");//违章地点  pecAddr
					buf.append("'"+arr[1]+"',");//违章时间  pecDate
					buf.append(arr[9]+",");//扣分情况  pecScore
					buf.append(arr[4]+",");//本金  corpus
					buf.append(arr[5]+",");//滞纳金  lateFee
					buf.append(arr[6]+",");//车主通代办费  replaceMoney
					buf.append(arr[13]+",");//万汇通代办费  ownMoney
					buf.append(arr[7]+",");// 是否可代办(1：可以代办 ,2：不可以代办)  
					buf.append("1,");//状态(1：审核通过 ,2：审核不通过)   pecState
					buf.append("1,");//采集渠道(1：公安厅接口 ,2：手工采集) pecChanl
					buf.append("'',");//createDate
					buf.append("'',");//updateDate
					buf.append("'',");//updateWorkerNo
					buf.append("'',");//woState
					buf.append("'"+arr[17]+"',");//areaCode
					buf.append("'',");//illegalType
					buf.append("2,");//下单类型(1电信，2百世邦，0入库) Exp1
					buf.append("'"+one_zMoney+"',");//万汇通单条扣款总额 Exp4
					buf.append("'"+one_submitMoney+"',");//提交到上游的的总额
					buf.append("'"+arr[0]+"'");//对应 wht_yquery_wz 中的 id 字段
					buf.append(")");
					
					dbs.update(buf.toString());
				}
				float accountMoney=Float.parseFloat(funs[1])-orderMoney;
				//账务信息录入
				String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
				String acctSql="INSERT INTO "+tableName+"(childfacct,dealserial," +
						"tradeaccount,tradetime,tradefee,infacctfee,tradetype,expl,state,distime," +
						"accountleft,tradeserial,other_childfacct,pay_type) VALUES(" +
						"'"+funs[0]+"'," +
						"'"+dealserial+"'," +
						"'"+userno+"'," +
						"'"+Tools.getNow3()+"'," +
						""+orderMoney+"," +
						""+orderMoney+"," +
						"8," +
						"'交通罚款下单'," +
						"0," +
						Tools.getNow3()+","+
						accountMoney+"," +
						"'"+dealserial+"'," +
						"'"+funs[0]+"'," +
						"1)";
				//修改账户余额
				String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+funs[0]+"'";
				dbs.update(acctSql);
				dbs.update(updatesql);
			dbs.commit();
			return true;
		} catch (Exception e) {
			if(dbs!=null){
				dbs.rollback();
			}
			e.printStackTrace();
			return false;
		}finally{
			if(null!=dbs){
				dbs.close();
				dbs=null;
			}
		}
	}
	
	/**
	 * 百世邦 下单
	 * @param dealserial 白世邦 下单账务订单号
	 * @param orderMoney  我们平台交易金额  里 
	 * @param bool 上游下单状态 0成功  1 失败
	 * @param wzId 违章id  格式 xxxx|xxxx|xxxx
	 * @param backURL 
	 * @return int 1失败   0成功  -1异常
	 */
	public int isBaiShiBang(String dealserial,int orderMoney,int bool,String wzId,String backURL){
		DBService db=null;
		try{
			db=new DBService();
			if(backURL!=null && !"".equals(backURL)){
				db.update("UPDATE wht_bruleorder SET Exp4='"+backURL+"' WHERE workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
			}
			if(bool==0){
				db.setAutoCommit(false);
					db.update("UPDATE wht_bruleorder SET resultCode='00',woState='3' WHERE workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
					db.update("UPDATE wht_breakrules SET woState=3 WHERE gdid=(SELECT id FROM wht_bruleorder  WHERE workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
					//修改预查询表违章  状态
					db.update("UPDATE wht_yquery_wz SET woState=3 WHERE carId IN ("+wzId.replace("|",",")+")");
					db.commit();
				return 0;
			}else{
				//退费
				String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
				//下单 ：tradeaccount 用户登录账号，，，，childfacct 交易账号
				String[] acc=db.getStringArray(" SELECT tradeaccount,childfacct FROM "+tableName+" WHERE tradeserial='"+dealserial+"' AND dealserial='"+dealserial+"'");
				//根据白世邦 下单记录账户的  用户账号，查找此用户的账户，余额
				String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+acc[0]+"')";
				String[] funs=db.getStringArray(getSql);
				if(!acc[1].equals(funs[0])){
					return -1;
				}
				int accountMoney=Integer.parseInt(funs[1])+orderMoney;
				//退费账务 流水号
				String serial="resBSB"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
				db.setAutoCommit(false);
					//账务信息录入
					String acctSql="INSERT INTO "+tableName+"(childfacct,dealserial," +
							"tradeaccount,tradetime,tradefee,infacctfee,tradetype,expl,state,distime," +
							"accountleft,tradeserial,other_childfacct,pay_type) VALUES(" +
							"'"+acc[1]+"'," +
							"'"+serial+"'," +
							"'"+acc[0]+"'," +
							"'"+Tools.getNow3()+"'," +
							""+orderMoney+"," +
							""+orderMoney+"," +
							"8," +
							"'交通罚款(失败退费)'," +
							"0," +
							Tools.getNow3()+","+accountMoney+"," +
							"'"+dealserial+"'," +
							"'"+acc[1]+"'," +
							"2)";
					//修改下单状态
					db.update("UPDATE wht_bruleorder SET resultCode='03',woState='101' WHERE workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
					db.update("UPDATE wht_breakrules SET woState=101 WHERE gdid=(SELECT id FROM wht_bruleorder  WHERE workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
					//修改预查询表违章  状态
					db.update("UPDATE wht_yquery_wz SET woState=101 WHERE carId IN ("+wzId.replace("|",",")+")");
					//修改账户余额
					String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+acc[1]+"'";
					db.update(acctSql);
					db.update(updatesql);
				db.commit();
				return 1;
			}
		}catch(Exception e){
			db.rollback();
			Log.error("百事通交通罚款下单异常，，"+e);
			return -1;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * 响应 客户端请求
	 * @param rs
	 * @param response
	 */
	public void Send(String rs, HttpServletResponse response) {
		PrintWriter out;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(rs);
			out.flush();
			out.close();
			Log.info("向接口商返回结果成功:" + rs);
		} catch (IOException e) {
			e.printStackTrace();
			Log.error("向第三方发送结果失败：" + rs + "  再次发送...");
			Send(rs, response);
		}
	}
	
	/**
	 * 交通罚款查询 响应
	 * @param rsCode
	 * @param ptOrderNo
	 * @param car_number
	 * @param car_type
	 * @param car_code
	 * @param car_engine
	 * @param car_username
	 * @param car_userphone
	 * @param format
	 * @param arry
	 * @return null;
	 * @throws Exception 
	 */
	public String QueryResult(
			String rsCode,
			String ptOrderNo,
			String car_number,
			String car_type,
			String car_code,
			String car_engine,
			String car_username,
			String car_userphone,
			String format,
			List arry) throws Exception{
		if(!"xml".equals(format)){
			format="json";
		}
		
		CarInfo car=new CarInfo();
		car.setRsCode(rsCode);
		car.setPtOrderNo(ptOrderNo);
		car.setCar_number(car_number);
		car.setCar_code(car_code);
		car.setCar_engine(car_engine);
		car.setCar_type(car_type);
		car.setCar_username(car_username);
		car.setCar_userphone(car_userphone);
		if(arry!=null && arry.size()>0){
			List<WzList> list=new ArrayList<WzList>();
			for(int i=0;i<arry.size();i++){
				String[] strs=(String[])arry.get(i);
				WzList w=new WzList();
				w.setWhid(strs[0]);//wanheng id
				w.setCarid(strs[14]);//carid	50	代办商侧的违章单号
				w.setTime(strs[1]);//time	32	违章时间，格式如：2011-12-01 11:03:05
				w.setAddress(strs[2]);//address	200	违章地点
				w.setCorpus(strs[4]);//corpus	10	罚款金额（单位为里） 1元 = 1000里
				w.setLateFee(strs[5]);//lateFee	10	滞纳金（单位为里）
				w.setReplaceMoney((Float.parseFloat(strs[6])+Float.parseFloat(strs[13]))+"");//replaceMoney	10	代办金额（单位为里）
				w.setPecDesc(strs[3]);//pecDesc	200	违章细节
				w.setAgent(strs[7]);//agent	10	是否可代办(1：可以代办 ,2：不可以代办)
				w.setWoState(strs[10]);//woState	10  wanheng 代办单状态 
				w.setWsh(strs[15]);//wsh	20	文书号
				w.setWzdm(strs[16]);//wzdm	10	违章代码
				w.setPecScore(strs[9]);//pecScore	10	违章扣分
				list.add(w);
			}
			car.setData(list);
		}else{
			car.setData(null);
		}
		
		if("json".equals(format)){//json
//			return JSON.toJSONString(car);
			JsonConfig config = new JsonConfig();
	        config.setJsonPropertyFilter(new IgnoreFieldProcessorImpl(true, new String[]{"servletWrapper","servlet"})); // 忽略掉name属性及集合对象
	        JSONObject fromObject = JSONObject.fromObject(car, config );
	        return fromObject.toString();
		}else{//xml
			XStream xstream = new XStream(); 
			xstream.alias("CarInfo", car.getClass()); 
			xstream.alias("wz", WzList.class);
			String xml = xstream.toXML(car); 
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml;
		}
	}
	
	/**
	 * 下单响应 
	 * @param rsCode
	 * @param ptOrderNo
	 * @param format
	 * @return string 
	 */
	public String OrderResult(String rsCode,String ptOrderNo,String format){
		String strs="";
		if(!"xml".equals(format)){
			strs="{\"rsCode\":\""+rsCode+"\",\"ptOrderNo\":\""+ptOrderNo+"\"}";
		}else{
			StringBuffer sf = new StringBuffer();
			sf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><rsCode>").append(rsCode).append("</rsCode>").append("<ptOrderNo>").append(ptOrderNo).append("</ptOrderNo>").append("</response>");
			strs=sf.toString();
		}
		return strs;
	}
	/**
	 * 违章订单查询状态
	 * @param result 
	 * @param format
	 * @return null
	 */
	public String QueryOrderStateResult(CarQueryResult result,String format){
		String strs="";
		if(!"xml".equals(format)){
			strs=JSON.toJSONString(result);
		}else{
			XStream xstream = new XStream(); 
			xstream.alias("rs", CarQueryInfo.class); 
			xstream.alias("root",CarQueryResult.class);
			String xml = xstream.toXML(result); 
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml;
		}
		return strs;
	}

	/**
	 * 接口违章 查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward InterCarQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/**
		 * 23:50 到 00:20 不处理交易
		 */
		if(!Tools.validateTime()){
			return null;
		}
		String reseponseFormat=request.getParameter("reseponseFormat");
		
		String userSysNo=request.getParameter("userSysNo");
		String ptOrderNo=request.getParameter("ptOrderNo");
		String ptPayTime=request.getParameter("ptPayTime");
		String car_number=request.getParameter("car_number");
		String car_type=request.getParameter("car_type");
		String car_code=request.getParameter("car_code");
		String car_engine=request.getParameter("car_engine");
		String car_username=request.getParameter("car_username");
		String car_userphone=request.getParameter("car_userphone");
		String sign=request.getParameter("sign");
		try {
			if(userSysNo==null || "".equals(userSysNo) ||
					ptOrderNo==null || "".equals(ptOrderNo)||
					ptPayTime==null || "".equals(ptPayTime) ||
					car_number==null || "".equals(car_number) ||
					car_type==null || "".equals(car_type) ||
					car_code==null || "".equals(car_code) ||
					car_engine==null || "".equals(car_engine) ||
					car_username==null || "".equals(car_username) ||
					car_userphone==null || "".equals(car_userphone)||
					sign==null || "".equals(sign)){
				Send(QueryResult("101",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
				return null;
			}
			car_number=URLDecoder.decode(new String(car_number.getBytes("iso-8859-1"),"utf-8"),"utf-8");
			car_username=URLDecoder.decode(new String(car_username.getBytes("iso-8859-1"),"utf-8"),"utf-8");
			// 从缓存中获取接口商信息
			TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
			if (tp == null) {
				Send(QueryResult("108",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
				return null;
			}
			// 验证ip或者终端号是否合法
			String ip = Tools.getIpAddr(request);
			if (tp.getIp().indexOf(ip) == -1) {
				Send(QueryResult("105",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
				return null;
			}
			//md5签名验证
			String signString=MD5Util.MD5Encode(
					userSysNo +
					ptOrderNo+
					ptPayTime +
					car_type+
					car_code+
					car_engine+ 
					car_userphone+ tp.getKeyvalue(),"utf-8");
			
			if (!signString.equals(sign)) {
				Send(QueryResult("102",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
				return null;
			}
			
			CarInfo car=new CarInfo();
			car.setCar_number(car_number);
			car.setCar_code(car_code);
			car.setCar_engine(car_engine);
			car.setCar_type(car_type);
			car.setCar_username(car_username);
			car.setCar_userphone(car_userphone);
			
			List arry=getQueryList(car_number,tp.getJfgroups(),car);
			if(arry==null || arry.size()<=0){
				Send(QueryResult("301",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
				return null;
			}else{
				Send(QueryResult("000",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,arry),response);
				return null;
			}
		} catch (Exception e) {
			try {
				Send(QueryResult("106",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 交通罚款 下单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws UnsupportedEncodingException 
	 */
	public ActionForward InterCarOrderAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 23:50 到 00:20 不处理交易
		 */
		if(!Tools.validateTime()){
			return null;
		}
		String reseponseFormat=request.getParameter("reseponseFormat");
		String userSysNo=request.getParameter("userSysNo");
		String ptOrderNo=request.getParameter("ptOrderNo");
		String ptPayTime=request.getParameter("ptPayTime");
		String ids_arry=request.getParameter("ids");
		String sign=request.getParameter("sign");
		String notifyUrl=request.getParameter("notifyUrl");//回调地址
		if(userSysNo==null || "".equals(userSysNo) ||
				ptOrderNo==null || "".equals(ptOrderNo)||
				ptPayTime==null || "".equals(ptPayTime) ||
				ids_arry==null || "".equals(ids_arry) ||
				sign==null || "".equals(sign)){
			Send(OrderResult("101",ptOrderNo,reseponseFormat),response);
			return null;
		}
		// 从缓存中获取接口商信息
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			Send(OrderResult("108",ptOrderNo,reseponseFormat),response);
			return null;
		}
		// 验证ip或者终端号是否合法
		String ip = Tools.getIpAddr(request);
		if (tp.getIp().indexOf(ip) == -1) {
			Send(OrderResult("105",ptOrderNo,reseponseFormat),response);
			return null;
		}
		//md5签名验证
		String signString = userSysNo +  ptOrderNo + ptPayTime + tp.getKeyvalue();
		if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
			Send(OrderResult("102",ptOrderNo,reseponseFormat),response);
			return null;
		}
		try{
			ids_arry=URLDecoder.decode(ids_arry,"utf-8");
			
			if("#".equals(ids_arry.substring(ids_arry.length()-1,ids_arry.length()))){
				ids_arry=ids_arry.substring(0,ids_arry.length()-1);	
			}
			//id#id2#id3
			String[] ids=ids_arry.split("#");
			if(ids==null || ids.length<=0){
				Send(OrderResult("501",ptOrderNo,reseponseFormat),response);
				return null;
			}
			CarInfo car=getCarUserInfo(ids[0]);
			if(car==null){
				Send(OrderResult("501",ptOrderNo,reseponseFormat),response);
				return null;
			}
			
			//完整车牌号
			String carsNumber=car.getProvince_name()+car.getCar_number();
			//页面计算的下单总额
			float pageTotal=0;
			
			//需要下单提交到上游的信息 集合
			List<String[]> arryList=new ArrayList<String[]>();
			for(int i=0;i<ids.length;i++){
				//单条违章信息
				String[] s1=(String[])getYQuery(carsNumber,tp.getJfgroups(),ids[i],null).get(0);
				
				//单条违章费用
				float wh_Zmoney=Float.parseFloat(s1[4])+Float.parseFloat(s1[5])+Float.parseFloat(s1[6])+Float.parseFloat(s1[13]);
				
				//页面下单总额，后台计算
				pageTotal=pageTotal+wh_Zmoney;
				
				//订单状态验证 是否可代办(1：可以代办 ,2：不可以代办)   102初始化状态
				if(!"1".equals(s1[7]) || !"102".equals(s1[10])){
					Send(OrderResult("302",ptOrderNo,reseponseFormat),response);
					return null;
				}
				//后台每条下单违章，添加到下单集合中
				arryList.add(s1);
			}
			//账务 dealserial 流水号
			String dealserial=ptOrderNo;
	
			//0成功  1失败 2余额不足  3异常  4重复下单
			int bool=DownOrder(arryList,tp.getUser_no(),pageTotal,car,dealserial,notifyUrl);
			if(bool==0){
				Send(OrderResult("000",ptOrderNo,reseponseFormat),response);
				return null;
			}else if(bool==1){
				Send(OrderResult("208",ptOrderNo,reseponseFormat),response);
				return null;
			}else if(bool==2){
				Send(OrderResult("201",ptOrderNo,reseponseFormat),response);
				return null;
			}else if(bool==4){
				Send(OrderResult("502",ptOrderNo,reseponseFormat),response);
				return null;
			}else{
				Send(OrderResult("106",ptOrderNo,reseponseFormat),response);
				return null;
			}
		}catch (Exception e) {
			Send(OrderResult("106",ptOrderNo,reseponseFormat),response);
			return null;
		}finally{
			
		}
	}
	
	/**
	 * 违章订单查询接口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return out
	 */
	public ActionForward QueryOrderState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 23:50 到 00:20 不处理交易
		 */
		if(!Tools.validateTime()){
			return null;
		}
		String reseponseFormat=request.getParameter("reseponseFormat");
		String userSysNo=request.getParameter("userSysNo");
		String ptOrderNo=request.getParameter("ptOrderNo");
		String ptPayTime=request.getParameter("ptPayTime");
		String sign=request.getParameter("sign");
		CarQueryResult result=new CarQueryResult();
		if(userSysNo==null || "".equals(userSysNo) ||
				ptOrderNo==null || "".equals(ptOrderNo)||
				ptPayTime==null || "".equals(ptPayTime) ||
				sign==null || "".equals(sign) ){
			result.setRs_code("101");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		}
		// 从缓存中获取接口商信息
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			result.setRs_code("108");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		}
		// 验证ip或者终端号是否合法
		String ip = Tools.getIpAddr(request);
		if (tp.getIp().indexOf(ip) == -1) {
			result.setRs_code("105");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		}
		//md5签名验证
		String signString = userSysNo +  ptOrderNo + ptPayTime + tp.getKeyvalue();
		if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
			result.setRs_code("102");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		}
		DBService db=null;
		try {
			db=new DBService();
			List<String[]> arrys=db.getList("select es.Exp2,er.carnum,es.pecAddr,es.Exp4,es.woState,er.createDate from wht_bruleorder er,wht_breakrules es where er.dealserial='"+ptOrderNo+"' and er.workerNo='"+ptOrderNo+"' and er.id=es.gdid");
			if(arrys==null || arrys.size()<=0){
				result.setRs_code("501");
				Send(QueryOrderStateResult(result,reseponseFormat),response);
				return null;
			}
			
			List<CarQueryInfo> infoList=new ArrayList<CarQueryInfo>();
			for(String[] s:arrys){
				CarQueryInfo c=new CarQueryInfo();
				c.setWzId(s[0]);
				c.setWzNum(s[1]);
				c.setWzAddress(s[2]);
				c.setWzMoney(s[3]);
				c.setWzState(s[4]);
				c.setOrderTime(s[5]);
				infoList.add(c);
			}
			result.setResult(infoList);
			result.setRs_code("000");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		} catch (Exception e) {
			result.setRs_code("106");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * 通过违章表中的 ownerinfoId 取得用户信息
	 * @param wz_table_id
	 * @return CarInfo
	 */
	public CarInfo getCarUserInfo(String wz_table_id){
		DBService db=null;
		try{
			db=new DBService();
			
			String[] strs=db.getStringArray("SELECT carNum,carType,carFrameNum,engineNumber,NAME,contactNum FROM wht_yquery_ownerinfo WHERE id = (SELECT ownerinfoId FROM wht_yquery_wz WHERE id="+wz_table_id+")");
			if(strs!=null && strs.length>0){
				CarInfo info=new CarInfo();
				info.setProvince_name("");
				info.setCar_number(strs[0]);
				info.setCar_type(strs[1]);
				info.setCar_code(strs[2]);
				info.setCar_engine(strs[3]);
				info.setCar_username(strs[4]);
				info.setCar_userphone(strs[5]);
				return info;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return null;
	}

	/**
	 * car 处理列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list
	 */
	public ActionForward CarList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		if(userSession==null){
			return null;
		}
		PrintWriter pWriter = null;
		String requestType=request.getParameter("requestType");
		String wzid=request.getParameter("wzid");
		
		DBService dbService = null;
		try {
			pWriter = response.getWriter();
			dbService = new DBService();
			if("1".equals(requestType)){
				//处理列表
				int index=1;
				int lastIndex=1;
			    int pagesize=15;
				
				if(request.getParameter("index")!=null && !"".equals(request.getParameter("index")))
				{
					index=Integer.parseInt(request.getParameter("index"));
				}
				if(index<=0)
					index=1;
				int count=dbService.getInt("select count(*) con from wht_bruleorder  WHERE userno='"+userSession.getUserno()+"'");
				if(count>0)
					lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
				
				if(index>=lastIndex)
					index=lastIndex;
				List arry=dbService.getList("SELECT id,carnum,NAME,contactNum,Exp5,woState,createDate FROM wht_bruleorder WHERE userno='"+userSession.getUserno()+"' order by id desc limit "+(index-1)*pagesize+" , "+pagesize, null);
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("index",index);
				map.put("lastindex",lastIndex);
				map.put("arry",arry);
				JSONArray json = JSONArray.fromObject(map);
				pWriter.print(json.toString());
				pWriter.flush();
				pWriter.close();
			}else if("2".equals(requestType)){
				//子列表
				List arryList=dbService.getList("SELECT a1.pecDate,a1.pecAddr,a1.pecDesc,a1.corpus,a1.lateFee,a1.replaceMoney,a1.ownMoney,a1.pecScore,a1.woState,a1.Exp4 FROM wht_breakrules a1 WHERE gdid='"+wzid+"'", null);
				JSONArray json = JSONArray.fromObject(arryList);
				pWriter.print(json);
				pWriter.flush();
				pWriter.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		return null;
	}
}

