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
	 * �����ħ����
	 */
	public static final String groups="20002";
	/**
	 * �û���ѯaction
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
//		//����Ȩ���ж�
		if(userSession==null){
			request.setAttribute("mess","��û�в鵥��Ȩ��!");
			return new ActionForward("/car/query.jsp");
		}
		if(!"3".equals(userSession.getRoleType())){
			request.setAttribute("mess","��û�в鵥��Ȩ��!");
			return new ActionForward("/car/query.jsp");
		}
		
		CarInfo car=(CarInfo)form;
		//������֤
		if(car.getProvince_name()==null || "".equals(car.getProvince_name())||
				car.getCar_number()==null || "".equals(car.getCar_number()) ||
				car.getCar_type()==null || "".equals(car.getCar_type())||
				car.getCar_code()==null || "".equals(car.getCar_code()) ||
				car.getCar_engine()==null || "".equals(car.getCar_engine())||
				car.getCar_username()==null || "".equals(car.getCar_username()) ||
				car.getCar_userphone()==null || "".equals(car.getCar_userphone())){
			Log.info("CarQuery Υ�´�������������㣬�����쳣������");
			request.setAttribute("mess","��������,�쳣����");
			return new ActionForward("/car/query.jsp");
		}
		//�������ƺ�
		String carsNumber=car.getProvince_name()+car.getCar_number();
		request.setAttribute("carInfo",car);
		
		List arry=getQueryList(carsNumber,groups,car);
		if(arry==null || arry.size()<=0){
			request.setAttribute("mess","δ��ѯ��Υ�¼�¼!");
			return new ActionForward("/car/query.jsp");
			
		}
		request.setAttribute("arry",arry);
		return new ActionForward("/car/CarPlaceOrder.jsp");
	}
	
	/**
	 * ��ͨ���� �µ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward carOrderAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
//		//����Ȩ���ж�
		if(userSession==null){
			request.setAttribute("mess","��û�в鵥��Ȩ��!");
			return new ActionForward("/car/query.jsp");
		}
		if(!"3".equals(userSession.getRoleType())){
			request.setAttribute("mess","��û�в鵥��Ȩ��!");
			return new ActionForward("/car/query.jsp");
		}
		CarInfo car=(CarInfo)form;
		//money#id
		String[] ids=request.getParameterValues("ids");
		if(ids==null || ids.length<=0){
			request.setAttribute("mess","��������,ϵͳ�쳣");
			return CarQuery(mapping,form,request,response);
		}
		//�������ƺ�
		String carsNumber=car.getProvince_name()+car.getCar_number();
		//ҳ�������µ��ܶ�
		float pageTotal=0;
		
		//��Ҫ�µ��ύ�����ε���Ϣ ����
		List<String[]> arryList=new ArrayList<String[]>();
		for(int i=0;i<ids.length;i++){
			float one_money=Float.parseFloat(ids[i].split("#")[0]);
			
			//����Υ����Ϣ
			String[] s1=(String[])getYQuery(carsNumber,groups,ids[i].split("#")[1],null).get(0);
			
			//����Υ�·���
			float wh_Zmoney=Float.parseFloat(s1[4])+Float.parseFloat(s1[5])+Float.parseFloat(s1[6])+Float.parseFloat(s1[13]);
			
			//ҳ���µ��ܶ��̨����
			pageTotal=pageTotal+wh_Zmoney;
			
			//����Υ����Ϣ �۷ѽ�� �Ա���֤
			if(one_money!=(wh_Zmoney)){
				request.setAttribute("mess","�µ�ʧ��,�µ�����!");
				return CarQuery(mapping,form,request,response);
			}
			//����״̬��֤ �Ƿ�ɴ���(1�����Դ��� ,2�������Դ���)   102��ʼ��״̬
			if(!"1".equals(s1[7]) || !"102".equals(s1[10])){
				request.setAttribute("mess","�µ�ʧ��,��Υ�²����µ�!");
				return CarQuery(mapping,form,request,response);
			}
			//��̨ÿ���µ�Υ�£���ӵ��µ�������
			arryList.add(s1);
		}
		//���� dealserial ��ˮ��
		String dealserial="Order"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
		
		//0�ɹ�  1ʧ�� 2����  3�쳣 
		int bool=DownOrder(arryList,userSession.getUserno(),pageTotal,car,dealserial,"");
		if(bool==0){
			request.setAttribute("mess","�µ��ɹ�!");
			return new ActionForward("/car/query.jsp");
		}else if(bool==1){
			request.setAttribute("mess","�µ�ʧ��!");
			return new ActionForward("/car/query.jsp");
		}else if(bool==2){
			request.setAttribute("mess","�µ�ʧ��,�˻�����!");
			return new ActionForward("/car/query.jsp");
		}else if(bool==4){
			request.setAttribute("mess","�µ�ʧ��,�ظ��µ�!");
			return new ActionForward("/car/query.jsp");
		}else{
			request.setAttribute("mess","�µ��쳣,��ϵ�ͷ�!");
			return new ActionForward("/car/query.jsp");
		}
	}
	
	/**
	 * Υ�²�ѯ list
	 * @param carsNumber �������ƺ�
	 * @param gr ħ��id
	 * @param car carInfo 
	 * @return list
	 */
	public List getQueryList(String carsNumber,String gr,CarInfo car){
		//Ԥ��ѯ�� ȡ����
		List arry=getYQuery(carsNumber,groups,null,car);
		if(arry!=null && arry.size()>0){
			//Ԥ��ѯ��������
			return arry;
		}else{
			DBService db=null;
			try {
				//Ԥ��ѯ��������
				List<HashMap> arr=Interface.Query(car.getCar_username(), car.getCar_type(), car.getCar_code(), car.getCar_engine(), carsNumber);
				if(arr==null || arr.size()<=0){
					return arr;
				}
				db=new DBService();
				db.setAutoCommit(false);
				String ownerInfo_id=db.getString("SELECT id FROM wht_yquery_ownerinfo WHERE carNum='"+carsNumber+"'");
				if(ownerInfo_id==null || "".equals(ownerInfo_id)){
					//����
					String sql="insert into wht_yquery_ownerinfo(contactNum,name,carNum,carFrameNum,engineNumber,carType,address,exp1) " +
							"values('"+car.getCar_userphone()+"','"+car.getCar_username()+"','"+carsNumber+"','"+car.getCar_code()+"','"+car.getCar_engine()+"','"+car.getCar_type()+"','','"+Tools.getNow()+"');";
					ownerInfo_id=db.getGeneratedKey(sql)+"".trim();
				}else{
					//����
					String sql="UPDATE wht_yquery_ownerinfo SET contactNum='"+car.getCar_userphone()+"',name='"+car.getCar_username()+"',carFrameNum='"+car.getCar_code()+"',engineNumber='"+car.getCar_engine()+"',carType='"+car.getCar_type()+"',exp1='"+Tools.getNow()+"'  WHERE id="+ownerInfo_id+"  AND carNum='"+carsNumber+"'";
					db.update(sql);
					//�ɵ����û���ʷΥ����Ϣ
					db.update("DELETE FROM wht_yquery_wz WHERE ownerinfoId="+ownerInfo_id);
				}
				//��Ԥ��ѯ������
				for(int i=0;i<arr.size();i++){
					HashMap mm=arr.get(i);
					StringBuffer buf=new StringBuffer();
					buf.append("INSERT INTO wht_yquery_wz(ownerinfoId,insertTime,carId,pecNum,peccode,pecDesc,pecAddr,pecDate,pecScore," +
							"corpus,lateFee,replaceMoney,agent,woState) VALUES(");
					buf.append(ownerInfo_id);
					buf.append(",'"+Tools.getNow()+"'");
					buf.append(",'"+mm.get("ViolationId")+"'");//Υ�²�ID
					buf.append(",'"+mm.get("Wsh")+"'");//Υ�������(�����)
					buf.append(",'"+mm.get("Wzdm")+"'");//Υ�´���
					buf.append(",'"+mm.get("ViloationDetail")+"'");//Υ����Ϊ����
					buf.append(",'"+mm.get("ViloationLocation")+"'");//Υ�µص�
					buf.append(",'"+mm.get("ViolationTime")+"'");//Υ��ʱ��
					//�۷�
					String koufen=mm.get("Wzdm")==null?"-1":mm.get("Wzdm")+"";
					if("-1".equals(koufen)){
						koufen="0";
					}else if("7".equals(koufen)){
						koufen="12";
					}else{
						koufen=koufen.substring(1,2);
					}
					buf.append(","+koufen);//�۷�	 pecScore
					buf.append(","+(mm.get("FineFee")==null?"0":Integer.parseInt(mm.get("FineFee")+"".trim())*10));//���� corpus
					buf.append(","+(mm.get("LateFee")==null?"0":Integer.parseInt(mm.get("LateFee")+"".trim())*10));//���ɽ�  lateFee
					buf.append(","+(mm.get("DealFee")==null?"0":Integer.parseInt(mm.get("DealFee")+"".trim())*10));//����ͨ����� replaceMoney
					buf.append(",'"+("1".equals(mm.get("DealFlag")+"".trim())?1:2)+"'");//�Ƿ�ɴ���(1�����Դ��� ,2�������Դ���) agent
					buf.append(",'102'");//��ʼ��״̬
					buf.append(")");
					db.update(buf.toString());
				}
				db.commit();
				//Ԥ��ѯ�� ȡ����
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
	 * Ԥ��ѯ���ݿ�  ȡ����
	 * @param carNumber ���ƺ�
	 * @param gr  ħ���� ��Ӧ sys_tpemploy_jf groups 
	 * @param id Υ�¼�¼id wht_yquery_ownerinfo id
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
				//ͨ��Υ�µ�ַ��ȡ���б��
				String AddsCode=getAddrCode(strs[2]);
				strs[17]=AddsCode;
				//��ȡӶ��ֵ(�����)  ���� ���õ�ħ���ţ����б�ţ��������� 
				String fee=db.getString("SELECT val FROM sys_tpemploy_jf WHERE groups="+gr+" AND pid="+AddsCode+" AND carid="+strs[12]);
				
				//����(��) * Ӷ���(Ӷ��(Ԫ)/100) 
				//float f=Float.parseFloat(strs[4]) * (Float.parseFloat(fee)/100);
				
				float f=Float.parseFloat(fee)*1000;
				//���ͨӶ��
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
	 *ͨ��Υ�µ�ַ��ȡ���б��
	 * @param address
	 * @return String 
	 */
	public String getAddrCode(String address){
		/**
		 * sa_id	sa_name


371	��Զ
372	�Ƹ�
374	��ݸ
375	��ɽ
387	��ͷ
388	����
393	������
394	������
		 */
		String str="381";
		if(address.indexOf("��β")!=-1){
			str="74";
		}else if(address.indexOf("����")!=-1){
			str="77";	
		}else if(address.indexOf("����")!=-1){
			str="78";	
		}else if(address.indexOf("�麣")!=-1){
			str="101";	
		}else if(address.indexOf("����")!=-1){
			str="102";	
		}else if(address.indexOf("տ��")!=-1){
			str="103";	
		}else if(address.indexOf("�ع�")!=-1){
			str="104";	
		}else if(address.indexOf("��ɽ")!=-1){
			str="105";	
		}else if(address.indexOf("ï��")!=-1){
			str="366";	
		}else if(address.indexOf("����")!=-1){
			str="367";	
		}else if(address.indexOf("����")!=-1){
			str="369";	
		}else if(address.indexOf("��Զ")!=-1){
			str="371";	
		}else if(address.indexOf("�Ƹ�")!=-1){
			str="372";	
		}else if(address.indexOf("��ݸ")!=-1){
			str="374";	
		}else if(address.indexOf("��ɽ")!=-1){
			str="375";	
		}else if(address.indexOf("��ͷ")!=-1){
			str="387";	
		}else if(address.indexOf("����")!=-1){
			str="388";	
		}else if(address.indexOf("����")!=-1){
			str="393";	
		}else if(address.indexOf("����")!=-1){
			str="394";	
		}else if(address.indexOf("÷��")!=-1){
			str="368";	
		}else if(address.indexOf("��Դ")!=-1){
			str="395";
		}
		return str;
	}
	
	/**
	 * Υ���µ�
	 * @param arryList Ԥ��ѯ�� wht_yquery_wz ��Ҫ�µ�Υ�¼�¼����
	 * @param userno ϵͳ���
	 * @param pageTotal ���ͨƽ̨���׽��
	 * @param car ������Ϣ
	 * @param dealserial ������
	 * @return  0�ɹ�  1ʧ�� 2����  3�쳣   4�ظ��µ�
	 */
	public int DownOrder(List<String[]> arryList,String userno,float pageTotal,CarInfo car,String dealserial,String backURL){
		//�������ƺ�
		String carsNumber=car.getProvince_name()+car.getCar_number();
		DBService dbs=null;
		try {
			dbs=new DBService();
			String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+userno+"')";
			String[] s2=dbs.getStringArray(getSql);
			if(s2==null || s2.length<=0){
				return 1;//�û��˻�������
			}
			float accountMoney=Float.parseFloat(s2[1])-pageTotal;
			if(accountMoney<0){
				return 2;//�˻�����
			}
			String inString="";//�����̲��Υ��ID�����ID�ԡ�|���ָ�
			float submitMoney=0;//�ύ�������ܷ��ã���λΪ�֣������ʷ�
			for(int i=0;i<arryList.size();i++){
				String[] s3=arryList.get(i);
				inString=inString+s3[14]+"|";
				//�ύ�����εķ���
				submitMoney=submitMoney+Float.parseFloat(s3[4])+Float.parseFloat(s3[5])+Float.parseFloat(s3[6]);//��
			}
			inString=inString.substring(0,inString.length()-1);
			//�ظ��µ��ж�
			List arrLists=dbs.getList("SELECT 1 FROM wht_breakrules WHERE id IN ("+inString.replace("|",",")+")");
			if(arrLists!=null && arrLists.size()>0){
				return 4;
			}
			
			//Ԥ��ѯ
			HashMap<String,String> map=Interface.PreQuery(inString, submitMoney/10,car);
			if(map==null || map.size()<=0){
				return 1;//���ѯʧ��
			}
			
			//���
			boolean bool=CreateOrder(car,userno,submitMoney,pageTotal,dealserial,arryList);
			if(!bool){
				return 1;//��⣬��¼����ʧ��
			}
			
			//0�ɹ� 1ʧ�� �����°��µ�״̬
			int flag=Interface.PlaceOrder(dealserial,map.get("SpTransId"),map.get("ViolationId"),submitMoney/10);
			
			//��㴦��״̬
			isBaiShiBang(dealserial,(int)pageTotal,flag,inString,backURL);
			
			if(flag==0){//�µ��ɹ�������Ϊ������״̬ 
//				String s9="���ƺ�:"+carsNumber+","+arryList.size()+"��Υ��,����ɹ�,Ψһ���ߣ�400-9923-988";
//				if(YMmessage.qxtSendSMS(car.getCar_userphone(),s9)){
//					Log.info("Υ���µ����ŷ��ͳɹ�,�ļ����,���ͺ���:"+car.getCar_userphone()+",����:"+s9);
//				}
				return 0;//�µ��ɹ�
			}else{
				return 1;//�µ�ʧ�ܣ��˷ѳɹ�
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
	 * ��⣬��¼���������� 
	 * @param carInfo 
	 * @param userno �û�ϵͳ���
	 * @param submitMoney �ύ���
	 * @param orderMoney ����ʵ�ʽ��
	 * @param dealserial ������
	 * @param strsList ÿ��Υ�� 
	 * @return boolean
	 */
	public boolean CreateOrder(CarInfo carInfo,String userno,float submitMoney,float orderMoney,String dealserial,List<String[]> strsList){
		//����id
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
						""+submitMoney+"," +//totalCharge //��+�����+��ݷ�  ���ύ�����ε��ܶ����������ƽ̨ �����
						"5," +//fromCanal
						"'"+Tools.getNewDate()+"'," +//createDate
						"'"+dealserial+"'," +//workerNo Ĭ��Ϊ�գ����������� �µ�������
						"100," +//woState
						"111," +//spID
						"'02'," +//resultCode
						"'"+carInfo.getCar_code()+"'," +//carFrameNum
						"'"+carInfo.getCar_engine()+"'," +//engineNumber
						"'"+carInfo.getCar_type()+"'," +//exp1 ��������
						"'2'," +//exp2 �µ����� ����1  ������2   ���0 
						"''," +//exp2 ��������
						"''," +//��ϵ�˵�ַ
						"" +orderMoney+")";//����ƽ̨�۷��ܶ����+���ɽ�+���δ����+���ͨ�����
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
					buf.append("'"+arr[14]+"',");//Υ����Ϣ  id
					buf.append("'"+maxid+"',");//wht_bruleorder  id  gdid
					buf.append("'"+arr[15]+"',");//Υ�������(�����) pecNum
					buf.append("'"+arr[16]+"',");//Υ�´���  pecCode
					buf.append("'"+arr[3]+"',");//Υ����Ϊ����  pecDesc
					buf.append("'"+arr[2]+"',");//Υ�µص�  pecAddr
					buf.append("'"+arr[1]+"',");//Υ��ʱ��  pecDate
					buf.append(arr[9]+",");//�۷����  pecScore
					buf.append(arr[4]+",");//����  corpus
					buf.append(arr[5]+",");//���ɽ�  lateFee
					buf.append(arr[6]+",");//����ͨ�����  replaceMoney
					buf.append(arr[13]+",");//���ͨ�����  ownMoney
					buf.append(arr[7]+",");// �Ƿ�ɴ���(1�����Դ��� ,2�������Դ���)  
					buf.append("1,");//״̬(1�����ͨ�� ,2����˲�ͨ��)   pecState
					buf.append("1,");//�ɼ�����(1���������ӿ� ,2���ֹ��ɼ�) pecChanl
					buf.append("'',");//createDate
					buf.append("'',");//updateDate
					buf.append("'',");//updateWorkerNo
					buf.append("'',");//woState
					buf.append("'"+arr[17]+"',");//areaCode
					buf.append("'',");//illegalType
					buf.append("2,");//�µ�����(1���ţ�2�����0���) Exp1
					buf.append("'"+one_zMoney+"',");//���ͨ�����ۿ��ܶ� Exp4
					buf.append("'"+one_submitMoney+"',");//�ύ�����εĵ��ܶ�
					buf.append("'"+arr[0]+"'");//��Ӧ wht_yquery_wz �е� id �ֶ�
					buf.append(")");
					
					dbs.update(buf.toString());
				}
				float accountMoney=Float.parseFloat(funs[1])-orderMoney;
				//������Ϣ¼��
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
						"'��ͨ�����µ�'," +
						"0," +
						Tools.getNow3()+","+
						accountMoney+"," +
						"'"+dealserial+"'," +
						"'"+funs[0]+"'," +
						"1)";
				//�޸��˻����
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
	 * ������ �µ�
	 * @param dealserial ������ �µ����񶩵���
	 * @param orderMoney  ����ƽ̨���׽��  �� 
	 * @param bool �����µ�״̬ 0�ɹ�  1 ʧ��
	 * @param wzId Υ��id  ��ʽ xxxx|xxxx|xxxx
	 * @param backURL 
	 * @return int 1ʧ��   0�ɹ�  -1�쳣
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
					//�޸�Ԥ��ѯ��Υ��  ״̬
					db.update("UPDATE wht_yquery_wz SET woState=3 WHERE carId IN ("+wzId.replace("|",",")+")");
					db.commit();
				return 0;
			}else{
				//�˷�
				String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
				//�µ� ��tradeaccount �û���¼�˺ţ�������childfacct �����˺�
				String[] acc=db.getStringArray(" SELECT tradeaccount,childfacct FROM "+tableName+" WHERE tradeserial='"+dealserial+"' AND dealserial='"+dealserial+"'");
				//���ݰ����� �µ���¼�˻���  �û��˺ţ����Ҵ��û����˻������
				String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+acc[0]+"')";
				String[] funs=db.getStringArray(getSql);
				if(!acc[1].equals(funs[0])){
					return -1;
				}
				int accountMoney=Integer.parseInt(funs[1])+orderMoney;
				//�˷����� ��ˮ��
				String serial="resBSB"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
				db.setAutoCommit(false);
					//������Ϣ¼��
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
							"'��ͨ����(ʧ���˷�)'," +
							"0," +
							Tools.getNow3()+","+accountMoney+"," +
							"'"+dealserial+"'," +
							"'"+acc[1]+"'," +
							"2)";
					//�޸��µ�״̬
					db.update("UPDATE wht_bruleorder SET resultCode='03',woState='101' WHERE workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
					db.update("UPDATE wht_breakrules SET woState=101 WHERE gdid=(SELECT id FROM wht_bruleorder  WHERE workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
					//�޸�Ԥ��ѯ��Υ��  ״̬
					db.update("UPDATE wht_yquery_wz SET woState=101 WHERE carId IN ("+wzId.replace("|",",")+")");
					//�޸��˻����
					String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+acc[1]+"'";
					db.update(acctSql);
					db.update(updatesql);
				db.commit();
				return 1;
			}
		}catch(Exception e){
			db.rollback();
			Log.error("����ͨ��ͨ�����µ��쳣����"+e);
			return -1;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * ��Ӧ �ͻ�������
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
			Log.info("��ӿ��̷��ؽ���ɹ�:" + rs);
		} catch (IOException e) {
			e.printStackTrace();
			Log.error("����������ͽ��ʧ�ܣ�" + rs + "  �ٴη���...");
			Send(rs, response);
		}
	}
	
	/**
	 * ��ͨ�����ѯ ��Ӧ
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
				w.setCarid(strs[14]);//carid	50	�����̲��Υ�µ���
				w.setTime(strs[1]);//time	32	Υ��ʱ�䣬��ʽ�磺2011-12-01 11:03:05
				w.setAddress(strs[2]);//address	200	Υ�µص�
				w.setCorpus(strs[4]);//corpus	10	�������λΪ� 1Ԫ = 1000��
				w.setLateFee(strs[5]);//lateFee	10	���ɽ𣨵�λΪ�
				w.setReplaceMoney((Float.parseFloat(strs[6])+Float.parseFloat(strs[13]))+"");//replaceMoney	10	�������λΪ�
				w.setPecDesc(strs[3]);//pecDesc	200	Υ��ϸ��
				w.setAgent(strs[7]);//agent	10	�Ƿ�ɴ���(1�����Դ��� ,2�������Դ���)
				w.setWoState(strs[10]);//woState	10  wanheng ���쵥״̬ 
				w.setWsh(strs[15]);//wsh	20	�����
				w.setWzdm(strs[16]);//wzdm	10	Υ�´���
				w.setPecScore(strs[9]);//pecScore	10	Υ�¿۷�
				list.add(w);
			}
			car.setData(list);
		}else{
			car.setData(null);
		}
		
		if("json".equals(format)){//json
//			return JSON.toJSONString(car);
			JsonConfig config = new JsonConfig();
	        config.setJsonPropertyFilter(new IgnoreFieldProcessorImpl(true, new String[]{"servletWrapper","servlet"})); // ���Ե�name���Լ����϶���
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
	 * �µ���Ӧ 
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
	 * Υ�¶�����ѯ״̬
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
	 * �ӿ�Υ�� ��ѯ
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
		 * 23:50 �� 00:20 ��������
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
			// �ӻ����л�ȡ�ӿ�����Ϣ
			TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
			if (tp == null) {
				Send(QueryResult("108",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
				return null;
			}
			// ��֤ip�����ն˺��Ƿ�Ϸ�
			String ip = Tools.getIpAddr(request);
			if (tp.getIp().indexOf(ip) == -1) {
				Send(QueryResult("105",ptOrderNo,car_number,car_type,car_code,car_engine,car_username,car_userphone,reseponseFormat,null),response);
				return null;
			}
			//md5ǩ����֤
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
	 * ��ͨ���� �µ�
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
		 * 23:50 �� 00:20 ��������
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
		String notifyUrl=request.getParameter("notifyUrl");//�ص���ַ
		if(userSysNo==null || "".equals(userSysNo) ||
				ptOrderNo==null || "".equals(ptOrderNo)||
				ptPayTime==null || "".equals(ptPayTime) ||
				ids_arry==null || "".equals(ids_arry) ||
				sign==null || "".equals(sign)){
			Send(OrderResult("101",ptOrderNo,reseponseFormat),response);
			return null;
		}
		// �ӻ����л�ȡ�ӿ�����Ϣ
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			Send(OrderResult("108",ptOrderNo,reseponseFormat),response);
			return null;
		}
		// ��֤ip�����ն˺��Ƿ�Ϸ�
		String ip = Tools.getIpAddr(request);
		if (tp.getIp().indexOf(ip) == -1) {
			Send(OrderResult("105",ptOrderNo,reseponseFormat),response);
			return null;
		}
		//md5ǩ����֤
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
			
			//�������ƺ�
			String carsNumber=car.getProvince_name()+car.getCar_number();
			//ҳ�������µ��ܶ�
			float pageTotal=0;
			
			//��Ҫ�µ��ύ�����ε���Ϣ ����
			List<String[]> arryList=new ArrayList<String[]>();
			for(int i=0;i<ids.length;i++){
				//����Υ����Ϣ
				String[] s1=(String[])getYQuery(carsNumber,tp.getJfgroups(),ids[i],null).get(0);
				
				//����Υ�·���
				float wh_Zmoney=Float.parseFloat(s1[4])+Float.parseFloat(s1[5])+Float.parseFloat(s1[6])+Float.parseFloat(s1[13]);
				
				//ҳ���µ��ܶ��̨����
				pageTotal=pageTotal+wh_Zmoney;
				
				//����״̬��֤ �Ƿ�ɴ���(1�����Դ��� ,2�������Դ���)   102��ʼ��״̬
				if(!"1".equals(s1[7]) || !"102".equals(s1[10])){
					Send(OrderResult("302",ptOrderNo,reseponseFormat),response);
					return null;
				}
				//��̨ÿ���µ�Υ�£���ӵ��µ�������
				arryList.add(s1);
			}
			//���� dealserial ��ˮ��
			String dealserial=ptOrderNo;
	
			//0�ɹ�  1ʧ�� 2����  3�쳣  4�ظ��µ�
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
	 * Υ�¶�����ѯ�ӿ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return out
	 */
	public ActionForward QueryOrderState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 23:50 �� 00:20 ��������
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
		// �ӻ����л�ȡ�ӿ�����Ϣ
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			result.setRs_code("108");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		}
		// ��֤ip�����ն˺��Ƿ�Ϸ�
		String ip = Tools.getIpAddr(request);
		if (tp.getIp().indexOf(ip) == -1) {
			result.setRs_code("105");
			Send(QueryOrderStateResult(result,reseponseFormat),response);
			return null;
		}
		//md5ǩ����֤
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
	 * ͨ��Υ�±��е� ownerinfoId ȡ���û���Ϣ
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
	 * car �����б�
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
				//�����б�
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
				//���б�
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

