package com.wlt.webm.business.dianx.action;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.dianx.bean.TelcomPayBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;

public class TelcomChargeAction  extends DispatchAction{
	
	/** ͬ���� **/
	private static Object lock = new Object();
	
	
	
	/**
	 * ��ѯ���Žɷ��˵�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward queryBill(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TelcomForm phonePayForm = (TelcomForm) form;
		String sepNo=Tools.getSeqNo(phonePayForm.getTradeObject().replaceAll(" ", "").trim());
		phonePayForm.setSeqNo(sepNo);
		phonePayForm.setTradeObject(phonePayForm.getTradeObject().replaceAll(" ", "").trim());
		SysUserForm userForm = (SysUserForm)request.getSession().getAttribute("userSession");
		phonePayForm.setAreaCode(userForm.getSa_zone());
		TelcomPayBean bean = new TelcomPayBean();
		
		int interfaceID=-1;
		DBService dbService =null;
		//�������㶫ʡ�����ţ���ȡ�ӿ�id
		try {
			dbService=new DBService();
			String sql = "SELECT interid FROM sys_gddxinterfacemaping ORDER BY id ASC LIMIT 0,1";
			interfaceID = dbService.getInt(sql);
		} catch (SQLException e) {
			Log.error("���Ų�ѯ�˵����ֽӿ��쳣��"+e);
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		int state=-100;
		if(interfaceID==9){//ѶԴ����
			state = bean.XYAccountQueryBill(phonePayForm,userForm);
			request.setAttribute("bool", false);
		}else if(interfaceID==19){//��������
			state = bean.JunBaoRemainderQueryBill(phonePayForm,userForm);
			request.setAttribute("bool", true);
		}else{
			Log.info("���Ų�ѯ�˵�δ�ҵ�ָ���ӿ�!");
			request.setAttribute("mess","ϵͳ��æ,���Ժ��ѯ!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if(state==1){//��ȡ�ʵ���Ϣ�ɹ�
		Log.info("��ʼ��ѯ���Ź̻��ʵ�....," + phonePayForm.getNumType());
			request.setAttribute("phonePayForm", phonePayForm);
			return new ActionForward("/telcom/phonebillinfo.jsp");
		}else{      
			request.setAttribute("mess",phonePayForm.getMessage());
		 	return new ActionForward("/telcom/telcomPhonepay.jsp");
	    }
	}
	
	/**
	 * ���������˵���ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward queryAccountInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=gbk");
		response.setCharacterEncoding("gbk");
		PrintWriter out=response.getWriter();
		
		String tradeobject=request.getParameter("tradeobject");
		String numbertype=request.getParameter("numbertype");
		
		TelcomForm phonePayForm=new TelcomForm();
		phonePayForm.setNumType(numbertype);
		phonePayForm.setTradeObject(tradeobject);
		
		List<String[]> arryList=new TelcomPayBean().JunBaoAccountQueryBill(phonePayForm,"0");
		if(arryList==null || arryList.size()<=0){
			out.println(-1);
		}else{
			JSONArray json=JSONArray.fromObject(arryList); 
			out.print(json);
		}
		out.flush();
		out.close();
		return null;
	}
	
		
    /**
     * ���ų�ֵ
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward payBill(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50��00:10��������");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		String numType=request.getParameter("numType");
		String phonePid="35";
		String phone=request.getParameter("tradeObject").replaceAll(" ", "");
		String payfee=request.getParameter("payFee");
		if(phone==null || "".equals(phone) ||
			payfee==null || "".equals(payfee))
		{
			Log.info("�㶫���ų�ֵ:ϵͳ�쳣   phone:"+phone+"     payfee:"+payfee);
			request.setAttribute("mess", "ϵͳ�쳣,�����µ�¼!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		
		if(payfee.indexOf("-")!=-1)
		{
			request.setAttribute("mess", "��ֵ�������Ǵ�����!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		
		String isdae=request.getParameter("isdae");//�Ƿ���

		String userPid=loginUser.getUsersite();
		String parentID=loginUser.getParent_id();
		String userno=loginUser.getUserno();
		String seqNo=Tools.getStreamSerial(phone);
		double fee=(int)FloatArith.yun2li(payfee);
		if(fee<=0){
			request.setAttribute("mess", "��ֵ�������Ǵ�����!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		String serialNo= "wh09015" + Tools.getNow3().substring(6)+ Tools.buildRandom(2) + Tools.buildRandom(3); //Tools.getNow3().substring(2)+"110800003243"+Tools.buildRandom(2);
		if("0".equals(isdae) && fee<2000000){
			//������
			request.setAttribute("mess", "��ֵ���С��2000�����ڴ���ֵ!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if(!"0".equals(isdae) && fee>=2000000){
			//������
			request.setAttribute("mess", "��ֵ������500��ѡ�д���ֵ!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		boolean flags=false;
		if("0".equals(isdae) && fee>=2000000){
			//����ֵ
			flags=true;
		}
		
		//һ�����ڲ������ظ�����
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" һ�����ڴ��ڸý���");
				request.setAttribute("mess", "һ�����ڲ������ظ�����");
				return new ActionForward("/telcom/telcomPhonepay.jsp");
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		BiProd bp=new BiProd();
		float fundacctMoney=bp.getFundacctMoney(userno);
		if(fundacctMoney==-1){
			request.setAttribute("mess", "ϵͳ�쳣!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if((fundacctMoney-fee)<0){
			request.setAttribute("mess", "�˻�����!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if(flags){//����ֵ
			
			//�����ֳ���ѭ��ִ�д�����ֵ
			Log.info("��ֵ����:"+phone+",������ֵ"+payfee+"Ԫ,�����߳�!");
			new TelcomChargeThread(parentID,userno,phonePid,userPid,phone,loginUser.getSa_zone(),loginUser.getUsername(),numType,fee).start();
			
			request.setAttribute("mess", "���Ժ�鿴������ϸ!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		
		//��ͨ����ֵ
		int n=bp.XYdxFill(parentID, userno, phonePid, userPid, "0",
				phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),numType,serialNo,null);
        // 0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ  -2 �޶�Ӧ��ֵ�ӿ�  2 ������ 3ʧ��
		//������  ����ʱ��  ���컧�˻�  ��Ʒ����  ��ֵ����  ������   ��ӡСƱ��Ҫ������
		String strA=seqNo+";"+Tools.getNewDate()+";"+loginUser.getUsername()+";���Žɷ�;"+phone+";"+payfee;
		if(n==0){
			request.setAttribute("strA",strA);
			request.setAttribute("mess", "��ֵ�ɹ�");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==1||n==3||n==-2||n==6){
			request.setAttribute("mess", "��ֵʧ��");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==10){
			request.setAttribute("mess", "һ�����ڲ������ظ�����");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==-5){
			request.setAttribute("mess", "��ֵʧ��,����");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==-1){
			request.setAttribute("mess", "ϵͳ��æ,���Ժ��ֵ!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==10){
			request.setAttribute("mess", "Ӷ��Ȳ���������ϵ�ͷ�");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else {
			request.setAttribute("mess", "�����У�����ϵ�ͷ�");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
	}

	 /**
	 * ��ȡ�û��ӿ�
	 * @return �û��ӿ�
	 * @throws Exception
	 */
	public String getInterfaceId(SysUserInterfaceForm sui) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select in_id")
                    .append(" from sys_user_interface ")
                    .append(" where user_id="+sui.getUser_id()+" and charge_type = "+sui.getCharge_type()+"  and province_code = "+sui.getProvince_code());
            System.out.println("slq:"+sql);
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

	
	/**ʡ��ͨ��ѯ�û����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward queryUserMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUserForm userForm = (SysUserForm)request.getSession().getAttribute("userSession");
		if(userForm==null || "".equals(userForm)){
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		
		TelcomForm phoneInfo = (TelcomForm) form;
		
		String seqNo=Tools.getNow()+((int)(Math.random()*10000)+10000)+((char)(new Random().nextInt(26) + (int)'A'));
		String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		
		TradeMsg requestMsg=new TradeMsg();
		requestMsg.setSeqNo(seqNo);
		requestMsg.setServNo("00");
		requestMsg.setMsgFrom2("0103");
		requestMsg.setRsCode("000");
		requestMsg.setTradeNo("09023");
		requestMsg.setFlag("0");
		
		Map<String, String> content=new HashMap<String, String>();
		content.put("phone",phoneInfo.getTradeObject());
	    content.put("numType",phoneInfo.getNumType());
	    content.put("serialNo",serialNo);
	    
		requestMsg.setContent(content);
		if(!PortalSend.getInstance().sendmsg(requestMsg)){
			request.setAttribute("mess","��ѯʧ��!");
			return new ActionForward("/lt/payment.jsp");
		}
		int k=0;
		TradeMsg rsMsg=null;
		while(k<=180){
			k++;
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg=MemcacheConfig.getInstance().get(seqNo);
			if(null==rsMsg){
				continue;
			}else{
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if(null==rsMsg&&k>=180){
			request.setAttribute("mess","��ѯ������!");
			return new ActionForward("/lt/payment.jsp");
		}
		String code=rsMsg.getRsCode();
		if("000".equals(code)){
			Map  rs=rsMsg.getContent();
			String ss=rs.get("fee").toString().replace(" ","");
			boolean bool=false;
			if(Float.parseFloat(ss)>0){
				bool=true;
				request.setAttribute("bool",bool);
			}
			phoneInfo.setTotalFee(ss);
			request.setAttribute("phonePayForm",phoneInfo);
			return new ActionForward("/lt/paymentInfo.jsp");
		}else {
			request.setAttribute("mess","��ѯʧ��!");
			return new ActionForward("/lt/payment.jsp");
		}
	}

	/**ʡ��ͨ�ɷ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward UserPayMent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50��00:10��������");
			return new ActionForward("/lt/payment.jsp");
		}
		String numType=request.getParameter("numType");
		String phonePid="35";
		String phone=request.getParameter("tradeObject").replaceAll(" ", "");
		String payfee=request.getParameter("payFee");
		if(phone==null || "".equals(phone) ||
			payfee==null || "".equals(payfee))
		{
			Log.info("�㶫��ͨ��ֵ:ϵͳ�쳣   phone:"+phone+"     payfee:"+payfee);
			request.setAttribute("mess", "ϵͳ�쳣,�����µ�¼!");
			return new ActionForward("/lt/payment.jsp");
		}
		if(payfee.indexOf("-")!=-1)
		{
			request.setAttribute("mess", "��ֵ�������Ǵ�����!");
			return new ActionForward("/lt/payment.jsp");
		}
		String userPid=loginUser.getUsersite();
		String parentID=loginUser.getParent_id();
		String userno=loginUser.getUserno();
		String seqNo=Tools.getStreamSerial(phone);
		double fee=(int)FloatArith.yun2li(payfee);
		if(fee<=0){
			request.setAttribute("mess", "��ֵ�������Ǵ�����!");
			return new ActionForward("/lt/payment.jsp");
		}
		//һ�����ڲ������ظ�����
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" һ�����ڴ��ڸý���");
				request.setAttribute("mess", "һ�����ڲ������ظ�����");
				return new ActionForward("/lt/payment.jsp");
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		BiProd bp=new BiProd();
		float fundacctMoney=bp.getFundacctMoney(userno);
		if(fundacctMoney==-1){
			request.setAttribute("mess", "��ֵʧ��,ϵͳ�쳣!");
			return new ActionForward("/lt/payment.jsp");
		}
		if((fundacctMoney-fee)<0){
			request.setAttribute("mess", "��ֵʧ��,���㣡");
			return new ActionForward("/lt/payment.jsp");
		}
		/**
		 * �������� 1000
		 
		for(int i=0;i<1000;i++){
			String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			Object[] strs=new Object[]{parentID, userno, phonePid, userPid, "2",
					phone, Tools.getStreamSerial(phone), fee,loginUser.getSa_zone(),loginUser.getUsername(),numType,serialNo};
			new TelcomChargeAction().new Cs(strs).start();
			try{
				Thread.sleep(1000);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		*/
		String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		//��ͨ����ֵ
		int n=bp.LianTongFill(parentID, userno, phonePid, userPid, "2",
				phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),numType,serialNo);
        // 0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ  -2 �޶�Ӧ��ֵ�ӿ�  2 ������ 3ʧ��
		//������  ����ʱ��  ���컧�˻�  ��Ʒ����  ��ֵ����  ������   ��ӡСƱ��Ҫ������
		String strA=seqNo+";"+Tools.getNewDate()+";"+loginUser.getUsername()+";��ͨ�ɷ�;"+phone+";"+payfee;
		if(n==0){
			request.setAttribute("strA",strA);
			request.setAttribute("mess", "��ֵ�ɹ�");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==1){
			request.setAttribute("mess", "��ֵʧ��");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==3){
			request.setAttribute("mess", "��ֵ�쳣,����ϵ�ͷ�");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==4){
			request.setAttribute("mess", "�������ظ���ֵ");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==5){
			request.setAttribute("mess", "��ֵʧ��,����");
			return new ActionForward("/lt/payment.jsp");
		}else {
			request.setAttribute("mess", "�����У�����ϵ�ͷ�");
			return new ActionForward("/lt/payment.jsp");
		}
	}
	
	/**
	 * ʡ��ͨ �˻�����ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward Query_Money(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/text;charset=GBK");  
		PrintWriter out=response.getWriter();
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			out.println("���¼");
			out.flush();
			out.close();
			return null;
		}
		if(!"0".equals(loginUser.getRoleType())){
			out.println("ûȨ��");
			out.flush();
			out.close();
			return null;
		}
		
		String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(serialNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo("09035");
		Map<String, String> content = new HashMap<String, String>();
		content.put("serialNo", serialNo);
		msg.setContent(content);
		
		if (!PortalSend.getInstance().sendmsg(msg)) {
			out.println("�쳣");
			out.flush();
			out.close();
			return null;
		}
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 180) {
			k++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(serialNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(serialNo);
				break;
			}
		}
		if(rsMsg==null || "".equals(rsMsg.getRsCode().trim())){
			out.println("�쳣");
			out.flush();
			out.close();
			return null;
		}
		if(!"000".equals(rsMsg.getRsCode())){
			out.println("�쳣");
			out.flush();
			out.close();
			return null;
		}
		Map<String,String> map=rsMsg.getContent();
		out.println(map.get("fee")+"Ԫ");
		out.flush();
		out.close();
		return null;
	}

	/**�ƶ�ͨ�ɷ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward MobileRecharge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50��00:10��������");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		String phonePid="35";
		String phone=request.getParameter("tradeObject").replaceAll(" ", "");
		String payfee=request.getParameter("payFee");
		if(phone==null || "".equals(phone) ||
			payfee==null || "".equals(payfee) || phone.length()!=11){
			Log.info("�㶫�ƶ���ֵ:ϵͳ�쳣   phone:"+phone+"     payfee:"+payfee);
			request.setAttribute("mess", "ϵͳ�쳣,�����µ�¼!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		if(payfee.indexOf("-")!=-1){
			request.setAttribute("mess", "��ֵ�������Ǵ�����!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		String userPid=loginUser.getUsersite();
		String parentID=loginUser.getParent_id();
		String userno=loginUser.getUserno();
		String seqNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*1000)+1000);
		double fee=(int)FloatArith.yun2li(payfee);
		if(fee<=0){
			request.setAttribute("mess", "��ֵ�������Ǵ�����!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		//һ�����ڲ������ظ�����
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" һ�����ڴ��ڸý���");
				request.setAttribute("mess", "һ�����ڲ������ظ�����");
				return new ActionForward("/Mobile/MobileRecharge.jsp");
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		BiProd bp=new BiProd();
		String[] st=bp.getPhoneInfo(phone.substring(0,7));
		if (null != st && st.length == 3) {
			if(!"35".equals(st[0]) || !"1".equals(st[1])){
				request.setAttribute("mess", "���ֵ�㶫�ƶ�����!");
				return new ActionForward("/Mobile/MobileRecharge.jsp");
			}
		}else{
			request.setAttribute("mess", "���ֵ�㶫�ƶ�����!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		float fundacctMoney=bp.getFundacctMoney(userno);
		if(fundacctMoney==-1){
			request.setAttribute("mess", "��ֵʧ��,ϵͳ�쳣!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		if((fundacctMoney-fee)<0){
			request.setAttribute("mess", "��ֵʧ��,���㣡");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		//��ͨ����ֵ
		int n=1;//bp.MobileFill(parentID, userno, phonePid, userPid, "2",phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),"1");
        // 0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ  -2 �޶�Ӧ��ֵ�ӿ�  2 ������ 3ʧ��
		//������  ����ʱ��  ���컧�˻�  ��Ʒ����  ��ֵ����  ������   ��ӡСƱ��Ҫ������
		String strA=seqNo+";"+Tools.getNewDate()+";"+loginUser.getUsername()+";��ͨ�ɷ�;"+phone+";"+payfee;
		if(n==0){
			request.setAttribute("strA",strA);
			request.setAttribute("mess", "��ֵ�ɹ�");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}else if(n==1||n==3||n==-2){
			request.setAttribute("mess", "��ֵʧ��");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}else if(n==-5){
			request.setAttribute("mess", "��ֵʧ��,����");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}else {
			request.setAttribute("mess", "�����У�����ϵ�ͷ�");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
	}

	
	/**����
	class Cs extends Thread{
		private Object[] strs;
		public Cs(Object[] strs){
			this.strs=strs;
		}
		public void run(){
			try {
				new BiProd().LianTongFill(strs[0].toString(), strs[1].toString(), 
						strs[2].toString(), strs[3].toString(), strs[4].toString(),
						strs[5].toString(), strs[6].toString(),Double.parseDouble(strs[7].toString()),
						strs[8].toString(),strs[9].toString(),strs[10].toString().toString(),strs[11].toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	*/
}
