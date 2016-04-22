package com.wlt.webm.business.dianx.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.pccommon.SCPReCode;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scputil.Tools;


/**
 * 
 * @author caiSJ
 *
 */
public class TelcomPayBean {
	
	private int fee = 0;
	private Map rsMap = new HashMap();
	private Object[] qryOb = null;	
	private int num = 0;
	
	/**
	 * ��ѯ���Ź̻��˵�
	 * @param form �̻��ɷ����ݱ� 
	 * @param user �û���Ϣ
	 * @return ���Ź̻��˵���Ϣ
	 * @throws Exception
	 */
	public int queryBill(TelcomForm form,String time,SysUserForm user) {
		String seqNo=form.getSeqNo();
		TradeMsg requestMsg=new TradeMsg();
		requestMsg.setSeqNo(seqNo);
		requestMsg.setServNo("00");
		requestMsg.setMsgFrom2("0103");
		requestMsg.setRsCode("000");
		requestMsg.setTradeNo("04001");
		requestMsg.setFlag("0");
	    Map<String, String> content=new HashMap<String, String>(); 
	    content.put("tradeTime", time);
	    content.put("phone", form.getTradeObject());
	    content.put("phoneType", form.getNumType());
	    content.put("areaCode", form.getAreaCode());
		requestMsg.setContent(content);
			if(!PortalSend.getInstance().sendmsg(requestMsg)){
				form.setMessage("��ѯʧ��"); 
			    return 3;//ʧ��
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
			form.setMessage("��ѯ������"); 
			return 2;//������
		}
		String code=rsMsg.getRsCode();
		if("000".equals(code)){
			Map  rs=rsMsg.getContent();
			form.setBillList((String)rs.get("circle"));
			form.setUserName((String)rs.get("userName"));
			form.setTotalFee((String)rs.get("userFee"));
			form.setSerial((String)rs.get("serial"));
			return 1;//�ɹ�
		}else if("110".equals(code)){
			form.setMessage("���벻����"); 
		    return 3;//ʧ��
		}
		else {
			form.setMessage("��ѯʧ��"); 
		    return 3;//
		}
	}
	
	
	/**
	 * ѶԴ �ͻ���������²�ѯ
	 * @param form
	 * @param user
	 * @return int
	 */
	public int XYAccountQueryBill(TelcomForm form,SysUserForm user) {
		String seqNo=form.getSeqNo();
		TradeMsg requestMsg=new TradeMsg();
		requestMsg.setSeqNo(seqNo);
		requestMsg.setServNo("00");
		requestMsg.setMsgFrom2("0103");
		requestMsg.setRsCode("000");
		requestMsg.setTradeNo("09017");
		requestMsg.setFlag("0");
		
		Map<String, String> content=new HashMap<String, String>();
		if("01".equals(form.getNumType()))
		{
			form.setNumType("000001");
		}
		if("02".equals(form.getNumType()))
		{
			form.setNumType("000003");
		}
		if("03".equals(form.getNumType()))
		{
			form.setNumType("000004");
		}
		content.put("phone",form.getTradeObject());
	    content.put("phoneType", form.getNumType());
		requestMsg.setContent(content);
			if(!PortalSend.getInstance().sendmsg(requestMsg)){
				form.setMessage("��ѯʧ��"); 
			    return 3;//ʧ��
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
			form.setMessage("��ѯ������"); 
			return 2;//������
		}
		String code=rsMsg.getRsCode();
		if("000".equals(code)){
			Map  rs=rsMsg.getContent();
			form.setBillList(form.getAreaCode());
			form.setUserName((String)rs.get("username"));
			form.setTotalFee((String)rs.get("fee"));
			form.setPayType((String)rs.get("paytype"));
			
			//�����û���ѯ���� �ͻ�����
			MemcacheConfig.getInstance().setAccountName(form.getTradeObject(), rs.get("username"));
			return 1;//�ɹ�
		}else if("110".equals(code)){
			form.setMessage("���벻����"); 
		    return 3;//ʧ��
		}
		else {
			form.setMessage("��ѯʧ��"); 
		    return 3;//
		}
	}
	
	/**
	 * ������������ѯ
	 * @param form
	 * @param user
	 * @return int
	 */
	public int JunBaoRemainderQueryBill(TelcomForm form,SysUserForm user) {
		String seqNo=form.getSeqNo();
		TradeMsg requestMsg=new TradeMsg();
		requestMsg.setSeqNo(seqNo);
		requestMsg.setServNo("00");
		requestMsg.setMsgFrom2("0103");
		requestMsg.setRsCode("000");
		requestMsg.setTradeNo("09030");
		requestMsg.setFlag("0");
		
		String rechargeType="";
		if("01".equals(form.getNumType())){//��һ
			rechargeType="000001";
		}
		if("02".equals(form.getNumType())){//���
			rechargeType="000003";
		}
		if("03".equals(form.getNumType())){//�ۺ�
			rechargeType="000004";
		}
		String phone=form.getTradeObject();
		
		Map<String, String> content=new HashMap<String, String>();
		content.put("phone",phone);
	    content.put("rechargeType",rechargeType);
	    
		requestMsg.setContent(content);
		if(!PortalSend.getInstance().sendmsg(requestMsg)){
			form.setMessage("��ѯʧ��"); 
			return 3;//ʧ��
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
			form.setMessage("��ѯ������"); 
			return 2;//������
		}
		String code=rsMsg.getRsCode();
		if("000".equals(code)){
			Map  rs=rsMsg.getContent();
			form.setUserName((rs.get("clintName")+"").replace(" ",""));
			form.setTotalFee(Float.parseFloat((rs.get("clintPhoneMoney")+"").replace(" ","").trim())*100+"");
			return 1;//�ɹ�
		}else if("110".equals(code)){
			form.setMessage("���벻����"); 
		    return 3;//ʧ��
		}
		else {
			form.setMessage("��ѯʧ��"); 
		    return 3;//
		}
	}
	
	/**
	 * �����󸶷��˵���ѯ
	 * @param phonePayForm
	 * @param phone
	 * @return list
	 */
	public List<String[]> JunBaoAccountQueryBill(TelcomForm phonePayForm,String str) {
		String phone=phonePayForm.getTradeObject();
		
		String seqNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		TradeMsg requestMsg=new TradeMsg();
		requestMsg.setSeqNo(seqNo);
		requestMsg.setServNo("00");
		requestMsg.setMsgFrom2("0103");
		requestMsg.setRsCode("000");
		requestMsg.setTradeNo("09029");
		requestMsg.setFlag("0");
		
		String accountType="";
		if("01".equals(phonePayForm.getNumType())){//��һ
			accountType="0";
		}
		if("02".equals(phonePayForm.getNumType())){//���
			accountType="0";
		}
		if("03".equals(phonePayForm.getNumType())){//�ۺ�
			accountType="1";
		}
		String areacode="";
		Map<String, String> content=new HashMap<String, String>();
		if("020".equals(phone.trim().substring(0,3))){
			areacode="020";
			phone=phone.substring(3,phone.length()).trim();
		}else if("0".equals(phone.substring(0,1))){
			areacode=phone.substring(0,4);
			phone=phone.substring(4,phone.length()).trim();
		}else{
			//�ֻ����� (�����ֻ�����ǰ��λ��ȡ����)
			if(phone.length()!=11){
				return null;
			}
			DBService dbService =null;
			//�������㶫ʡ�����ţ���ȡ�ӿ�id
			try {
				dbService=new DBService();
				String sql="SELECT b.sa_zone FROM sys_phone_area a,sys_area b  WHERE a.province_code=35 AND a.phone_num='"+phone.substring(0,7)+"' AND a.cart_type = b.sa_name ";
				areacode=dbService.getString(sql);
				if(areacode==null){
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;//ϵͳ��æ
			}finally{
				if(dbService!=null){
					dbService.close();
					dbService=null;
				}
			}
		}
		content.put("phone",phone);
	    content.put("areacode",areacode);
	    content.put("accountType",accountType);
	    
		requestMsg.setContent(content);
		if(!PortalSend.getInstance().sendmsg(requestMsg)){
		    return null;//ʧ��
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
			return null;//������
		}
		String code=rsMsg.getRsCode();
		if("000".equals(code)){
			Map  rs=rsMsg.getContent();
			String CustomerName=rs.get("CustomerName").toString().trim();
			String CustomerBalance=rs.get("CustomerBalance").toString().trim();
			String CustomerMx=rs.get("CustomerMx").toString().trim();
			
			phonePayForm.setUserName(CustomerName);
			phonePayForm.setTotalFee(CustomerBalance);
			phonePayForm.setMessage("".equals(CustomerMx)?"":CustomerMx.substring(0,CustomerMx.length()-1));
			
			ArrayList<String[]> arrlist=null;
			if("1".equals(str)){//1�ӿڲ�ѯ  0������ѯ
				arrlist=new ArrayList<String[]>();
				arrlist.add(new String[]{});
				return arrlist;
			}
			String[] ss=new String[]{"abc","-1","-1","-1","-1","-1","01;"+CustomerName};
			//�����û���ѯ���� �ͻ�����
			MemcacheConfig.getInstance().setAccountName(phonePayForm.getTradeObject(),CustomerName);
			if("".equals(CustomerMx)){//���˵���ϸ
				 arrlist=new ArrayList<String[]>();
				 arrlist.add(ss);
				 return arrlist;
			}else{//���˵���ϸ
				if(CustomerMx!=null && !"".equals(CustomerMx)){
					CustomerMx=CustomerMx.substring(0,CustomerMx.length()-1);
					String[] strs=CustomerMx.split("#");
					arrlist=new ArrayList<String[]>(); 
					for(int i=0;i<strs.length;i++){
						String[] s=strs[i].split("\\|");
						s[3]=(Float.parseFloat(s[3])/100)+"";
						s[6]=s[6]+";"+CustomerName;
						arrlist.add(s);
					}
				}
			}
			//��ӡСƱ��Ҫ����Ϣ
			MemcacheConfig.getInstance().setAccountName(phonePayForm.getTradeObject()+"_Acc",arrlist);
			
			return arrlist;//�ɹ�
		}else {
			return null;//
		}
	}
	/**
	 * ѶԴ��ѯ���Ź̻��˵�
	 * @param form �̻��ɷ����ݱ� 
	 * @param user �û���Ϣ
	 * @return ���Ź̻��˵���Ϣ
	 */
	public int XYQueryBill(TelcomForm form,SysUserForm user) {
		String seqNo=form.getSeqNo();
		TradeMsg requestMsg=new TradeMsg();
		requestMsg.setSeqNo(seqNo);
		requestMsg.setServNo("00");
		requestMsg.setMsgFrom2("0103");
		requestMsg.setRsCode("000");
		requestMsg.setTradeNo("09014");
		requestMsg.setFlag("0");
		
		Map<String, String> content=new HashMap<String, String>();
		if("01".equals(form.getNumType()))
		{
			form.setNumType("000001");
		}
		if("02".equals(form.getNumType()))
		{
			form.setNumType("000003");
		}
		if("03".equals(form.getNumType()))
		{
			form.setNumType("000004");
		}
		content.put("phone",form.getTradeObject());
		
	    content.put("phoneType", form.getNumType());
		requestMsg.setContent(content);
			if(!PortalSend.getInstance().sendmsg(requestMsg)){
				form.setMessage("��ѯʧ��"); 
			    return 3;//ʧ��
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
			form.setMessage("��ѯ������"); 
			return 2;//������
		}
		String code=rsMsg.getRsCode();
		if("000".equals(code)){
			Map  rs=rsMsg.getContent();
			form.setBillList(form.getAreaCode());
			form.setUserName((String)rs.get("username"));
			form.setTotalFee((String)rs.get("fee"));
			form.setSerial((String)rs.get("serial"));
			return 1;//�ɹ�
		}else if("110".equals(code)){
			form.setMessage("���벻����"); 
		    return 3;//ʧ��
		}
		else {
			form.setMessage("��ѯʧ��"); 
		    return 3;//
		}
	}
	
	
	
	/**
	 * 
	 * @param userNo
	 * @return
	 */
    public String getSeqNo(String userNo) {
        return Tools.getNow().substring(2)+userNo+UniqueNo.getInstance().getNoTwo();
    }
	
    
    /**
     * 
     * @param contentMap
     * @param mon
     * @return
     * @throws Exception
     */
	private List setQryPhoneBill(Map contentMap,int mon)
	{
		List payInfo = new ArrayList();
		int k = 1;
		
		for(int i=1;i<=mon;i++)
		{
			String flag = "RsMon"+i;
			Map cont = (Map)contentMap.get(flag);
			fee = fee + Integer.parseInt((String)cont.get("TotalFee"));
			int subNum = Integer.parseInt((String)cont.get("Num"));
			num += subNum;

			//�ɷ�������Ϣ
			String payInf = (String)cont.get("PayInfo"+i);
			payInfo.add(payInf);
			
			for(int j=1;j<=subNum;j++)
			{
				String rsFlag = "Rst"+i+"_"+j;
				String rst = (String)cont.get(rsFlag);
				
				String subRst = "Rst"+k;
				//��ǩ��,��ǩ��Ӧ��ֵ
				rsMap.put(subRst,rst);
				k++;
			}
		}
		
		return payInfo;
	}
	
	
    /**
     * �����˵��б�
     * @param billList �˵��б�ÿ��Ԫ��Ϊ�˵���ϸ��
     * @param bill �˵��ַ���
     */
    private void addBilldetail(List billList, String bill){
        Map billMap = new LinkedHashMap();
        String[] str=bill.split("\\|");
        for(int i=0;i<str.length;i++){
            String[] detail=str[i].split("\\$",-1);
            //if(!detail[0].equals("�˺����")){
            billMap.put(detail[0],detail[1]);
            //}
            
        }
        billList.add(billMap);
    }
    
	/**
	 *���Žɷ�
	 * @param form �̻��ɷ����ݱ�
	 * @param  user �û���Ϣ
	 * @return -1���Ź̻��ʽ��˻��ɷ�ʧ��
	 * 			1 ���Ź̻��ʽ��˻��ɷѳɹ�
	 * 			-2�ɷѳ�ʱ,���ױ�ȡ����
	 * @throws Exception
	 */
	public Map payBill(TelcomForm form, SysUserForm user,String serialNo) {
        Map rs00=new HashMap();
		String numType=form.getNumType();
		String payNo=form.getTradeObject();
		//Log.info("----------------------"+numType);
    	StringBuffer logMessage = new StringBuffer();
    	logMessage.append(" ������ˮ�� : ").append(form.getSeqNo())
	    	      .append(", ���� : ").append(form.getTradeObject())
	    	      .append(",���׽�� : ").append(form.getPayFee())
	    	      .append(", ��ǰʱ��: ").append(Tools.getNow());
    	
    	 Log.info("���Žɷ�---��ʼ�ʽ��˻��ɷ�....," + logMessage);
         String seqNo=form.getSeqNo();
         String payFee=form.getPayFee();
         payFee=Math.round(Double.parseDouble(payFee)*100)+"";

        String billInfo="04075582025590";
        FillBusiness fillBus=new FillBusiness(seqNo,numType,payFee,payNo,user.getSa_zone().trim()
				,"04075582025590",serialNo,0);
        List lists=fillBus.deal();
        String code=(String)lists.get(0);
        if(code.equals("503")){//��·�Ͽ��޷���ȡ������Ϣ
        	rs00.put("code", 2);
            return rs00;
        }
        else if(code.equals("000")){
        Map<String,String> maps=(HashMap<String, String>)lists.get(1);
       String  num=maps.get("Num");
       String  payfee="";//���컧���
		Map ctx = new HashMap();
		ctx.put("Num",num);
		ctx.put("TermNo", "");//�ն˱��
		ctx.put("Fee", payfee);
		ctx.put("response", maps.get("response"));
		ctx.put("Rst1", maps.get("Rst1"));
		int rsNum =  Integer.parseInt(num);
		for(int i=1;i<=rsNum;i++)
		{
			String flag = "Rst"+i;
			String value = (String)ctx.get("Rst"+i);
			ctx.put(flag,value);
		}

		String status = (String)ctx.get("response");
         String fee=(String) ctx.get("Fee");
         String fee2="";
         if(null==fee ||"".equals(fee)){
        	 Log.info("���Žɷ�---���컧���Ϊ��");
        	 fee2="";
         }else{
         fee2="  ���컧��"+Tools.fenToYuan(fee);
         }
         form.setMessage(SCPReCode.getSCPreMsg(status)+fee2);
         //form.setMessage(SCPReCode.getSCPreMsg(status));
         Log.info("���Žɷ�---SCP�����룺"+status);
         String billnums="0";
         String result="";//�˵���ϸ�ַ���
         List billList=new ArrayList();
         if(status.equals("000")||status.equals("027")){
                 billnums = (String) ctx.get("Num");
                 //form.setTotalFee((String) context.get("TotalFee"));
                 for(int j=0;j<Integer.parseInt(billnums);j++){
                     result=ctx.get("Rst"+(j+1)).toString();
                     addBilldetail(billList, result);
                 }
               //  form.setBillList(billList);
             }
             Log.info("���Žɷѳɹ���" + logMessage);
         	rs00.put("code", 1);
         	rs00.put("CheckBill", maps.get("CheckBill"));
         	rs00.put("RollBack", maps.get("RollBack"));
            return rs00;
         }else{
             Log.info("���Žɷ�ʧ�ܣ�" + logMessage);
         	rs00.put("code", -1);
            return rs00;
         }
	}
}
