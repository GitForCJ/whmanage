package com.wlt.webm.business.bean.trafficfines;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.Tools;

/**
 * Υ�´�����Ϣ��ѯ��
 * @author 1989
 *
 */
public class SynchronousDatas {
	
	/**
	 * ��ѯΥ����ϸ
	 * @param carNum  ���ƺ�
	 * @param ph      ��ϵ�绰
	 * @return   BreakRulesInfoResp
	 */
    public BreakRulesInfoResp synchronousByCarPh(String carNum,String ph){
    	BreakRulesInfoReq req=new BreakRulesInfoReq(carNum, ph, 1, 200, PublicMethod.TOKEN_ID);
    	try {
			return (BreakRulesInfoResp)PublicMethod.breakrulesMethod(BreakRulesInfoResp.class,req,PublicMethod.GET_INFO);
		} catch (Exception e) {
			Log.error("��ѯ "+carNum+"Υ�½����Ϣ����"+e.toString());
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * ���ݶ��� ��ѯΥ����ϸ�޸�״̬
     * @param order
     */
    public void dealOrder(PeccancyWOModel order){
    	DBService db=null;
    	List<PeccancyInfoModel> breakRules=order.getPeccs();
    	long orderid=order.getId();
    	String orderstate=order.getWoState();
    	int n=breakRules.size();
    	try {
			db=new DBService();
			db.setAutoCommit(false);
			db.update("UPDATE wht_bruleorder SET woState="+orderstate+"  WHERE id="+orderid);
	    	for(int j=0;j<n;j++){
	    		PeccancyInfoModel breakRule=breakRules.get(j);
	    		db.update("UPDATE wht_breakrules SET woState="+breakRule.getWoState()+"  WHERE id="+breakRule.getId());
	    	}
	    	db.commit();
		} catch (SQLException e) {
			db.rollback();
			Log.error("�޸Ķ�����"+orderid+"����"+e.toString());
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
			}
		}
    }
    
	/**
	 * ��ȡ��Ҫͬ���Ķ���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String[]> getDates() {
		ArrayList<String[]> datas = null;
		String sql = "SELECT id,userno,contactNum,carnum,dealserial,woState FROM  wht_bruleorder WHERE woState=3";
		DBService db = null;
		try {
			db = new DBService();
			datas = (ArrayList<String[]>) db.getList(sql);
		} catch (Exception e) {
			Log.error("ͬ����ͨ���������쳣:" + e.toString());
			return null;
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return datas;
	}
	
	/**
	 * ���ݹ������µ�
	 * @param id
	 * @return 0 �����ݿ��µ���1�ɹ� ��2 ��������ʧ�ܣ�3�ڲ��쳣ʧ��
	 */
	public int submitOrder(long id){
	String sqlbyid="SELECT a.contactNum,a.NAME,a.carnum,a.exp1,a.carFrameNum,a.engineNumber,a.payMethod,"
		+" SUM(b.corpus),SUM(b.lateFee),SUM(b.replaceMoney)  FROM wht_bruleorder a" 
        +" INNER JOIN  wht_breakrules b ON a.id=b.gdid WHERE a.id="+id;
	String sqlbygdid="SELECT id,pecNum,pecCode,pecDesc,pecAddr,pecDate,pecScore,corpus,lateFee,replaceMoney,agent,pecState,"
		+" createDate,updateDate,updateWorkerNo,woState,areaCode,illegalType FROM wht_breakrules WHERE gdid="+id;
	DBService db=null;
	try{
		db=new DBService();
		String[] order=db.getStringArray(sqlbyid);
		List<String[]> lists=db.getList(sqlbygdid);
		if(null==order||null==lists||lists.size()<1){
			//�����ݿ����µ�
			return 0;
		}else{
		SubmitBreakRulesReq req=new SubmitBreakRulesReq();
		//���û�������
		req.setContactNum(order[0]);
		req.setName(order[1]);
		req.setPlateNumber(order[2]);
		req.setPlateNumberType(order[3]);
		req.setCarFrameNum(order[4]);
		req.setEngineNumber(order[5]);
		req.setCorpus(Float.parseFloat(order[6])/1000);
		req.setLateFee(Float.parseFloat(order[7])/1000);
		req.setServiceCharge(lists.size()*25);
		req.setTokenId(PublicMethod.TOKEN_ID);
		//����Υ����ϸ  List<PeccancyInfoModel>
			List<PeccancyInfoModel> breakRules=new ArrayList<PeccancyInfoModel>();
			for(String[] str:lists){
				PeccancyInfoModel model=new PeccancyInfoModel();
				model.setId(str[0]);
				model.setPecNum(str[1]);
				model.setPlateNumber(order[2]);
				model.setPecCode(str[2]);
				model.setPecDesc(str[3]);
				model.setPecAddr(str[4]);
				model.setPecDate(Tools.string2timestamp(str[5])+"");
				model.setPecScore(str[6]);
				model.setCorpus(Float.parseFloat(str[7])/1000+"");
				model.setLateFee(Float.parseFloat(str[8])/1000+"");
				model.setAgent(str[10]);
				model.setPecState(str[11]);
				model.setCreateDate(Tools.string2timestamp(str[12])+"");
				model.setUpdateDate(Tools.string2timestamp(("".equals(str[13]))?Tools.getNewDate():str[13])+"");
				model.setUpdateWorkerNo(str[14]);
//				model.setWoState(str[15]);
				model.setAreaCode(str[16]);
				model.setIllegalType(str[17]);
				breakRules.add(model);
			}
			req.setPeccs(breakRules);
			SubmitBreakRulesResp resp=(SubmitBreakRulesResp)PublicMethod.breakrulesMethod(
					SubmitBreakRulesResp.class, req, PublicMethod.SUBMIT_BREAKRULES);
			if(resp!=null&&resp.getCode()==0){
				PeccancyWOModel wo=resp.getOrder();
				long orderid=wo.getId();
				String workerNo=(null==wo.getWorkerNo()?"":wo.getWorkerNo());
				String woState=wo.getWoState();
				long spid=wo.getSpID();
				String resultCode=" ";//wo.getResultCode();
				List<PeccancyInfoModel> info=wo.getPeccs();
				String sql0="update wht_bruleorder set id=?,workerNo=?,woState=?,spID=?,resultCode=? where id=?";
				db.setAutoCommit(false);
				db.update(sql0, new Object[]{orderid,workerNo,woState,spid,resultCode,id});
				for(PeccancyInfoModel del:info){
					db.update("update wht_breakrules set pecState='"+(null==del.getPecState()?"a":del.getPecState())+
							"',updateDate='"+Tools.timestamp2String(Long.parseLong(del.getUpdateDate()))+
							"',woState="+del.getWoState()+
							" where id="+del.getId());
				}
				db.commit();
				return 1;
			}else{
				//ʧ��
				return 2;
			}
		}
	}catch (Exception e) {
		Log.error("�µ��ڲ��쳣:"+e.toString());
		db.rollback();
		return 3;
	}finally{
		if(null!=db){
			db.close();
		}
	}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		String  str0="{\\"code\\":0,\\"desc\\":\\"����Υ�´���ɹ�\\",\\"peccancyInfo\\":null,\\"order\\":{\\"id\\":2000407,\\"woNum\\":null,\\"logisticsNum\\":null,\\"logisticsState\\":null,\\"peccs\\":[{\\"id\\":2001791,\\"pecNum\\":\\"441220141013153000CJ0346\\",\\"plateNumber\\":\\"��A2CJ03\\",\\"pecCode\\":\\"\\",\\"pecDesc\\":\\"��ʻ���������ؿ��ػ�����Σ����Ʒ���䳵�������������������ʻ�����涨ʱ��10δ��20��\\",\\"pecAddr\\":\\"��������67����400��\\",\\"pecDate\\":1413185400000,\\"pecScore\\":3,\\"corpus\\":150.0,\\"lateFee\\":0.0,\\"replaceMoney\\":25.0,\\"agent\\":1,\\"pecState\\":null,\\"pecChanl\\":1,\\"createDate\\":1414208425000,\\"updateDate\\":1414218581000,\\"updateWorkerNo\\":\\"\\",\\"woState\\":\\"1\\",\\"areaCode\\":\\"\\",\\"couponNum\\":null,\\"couponMoney\\":0.0,\\"remark\\":null,\\"exInfo\\":null,\\"illegalType\\":\\"\\"},{\\"id\\":2001792,\\"pecNum\\":\\"441220141007105400CJ0346\\",\\"plateNumber\\":\\"��A2CJ03\\",\\"pecCode\\":\\"\\",\\"pecDesc\\":\\"��ʻ���������ؿ��ػ�����Σ����Ʒ���䳵�������������������ʻ�����涨ʱ��10δ��20��\\",\\"pecAddr\\":\\"��������67����400��\\",\\"pecDate\\":1412650440000,\\"pecScore\\":3,\\"corpus\\":150.0,\\"lateFee\\":0.0,\\"replaceMoney\\":25.0,\\"agent\\":1,\\"pecState\\":null,\\"pecChanl\\":1,\\"createDate\\":1414208425000,\\"updateDate\\":1414218581000,\\"updateWorkerNo\\":\\"\\",\\"woState\\":\\"1\\",\\"areaCode\\":\\"\\",\\"couponNum\\":null,\\"couponMoney\\":0.0,\\"remark\\":null,\\"exInfo\\":null,\\"illegalType\\":\\"\\"},{\\"id\\":2001794,\\"pecNum\\":\\"445320140711151700CJ0346\\",\\"plateNumber\\":\\"��A2CJ03\\",\\"pecCode\\":\\"\\",\\"pecDesc\\":\\"��ʻ���������ؿ��ػ�����Σ����Ʒ���䳵�������������������ʻ�����涨ʱ��10δ��20��\\",\\"pecAddr\\":\\"��������85km110m\\",\\"pecDate\\":1405063020000,\\"pecScore\\":3,\\"corpus\\":150.0,\\"lateFee\\":0.0,\\"replaceMoney\\":25.0,\\"agent\\":1,\\"pecState\\":null,\\"pecChanl\\":1,\\"createDate\\":1414208425000,\\"updateDate\\":1414218581000,\\"updateWorkerNo\\":\\"\\",\\"woState\\":\\"1\\",\\"areaCode\\":\\"\\",\\"couponNum\\":null,\\"couponMoney\\":0.0,\\"remark\\":null,\\"exInfo\\":null,\\"illegalType\\":\\"\\"}],\\"contactNum\\":\\"12345678901\\",\\"name\\":\\"cxaisj\\",\\"autoCarID\\":2009680,\\"payMethod\\":4,\\"isVisit\\":2,\\"visitDate\\":null,\\"factVisitDate\\":null,\\"visitAddr_city\\":null,\\"visitAddr_district\\":null,\\"visitAddr\\":null,\\"postCode\\":null,\\"remark\\":null,\\"corpus\\":450.0,\\"lateFee\\":0.0,\\"serviceCharge\\":75.0,\\"fastCharge\\":0.0,\\"totalCharge\\":525.0,\\"fromCanal\\":12,\\"createDate\\":1414218000943,\\"workerNo\\":\\"api_wanheng\\",\\"woState\\":\\"1\\",\\"spID\\":1,\\"resultCode\\":null,\\"carFrameNum\\":\\"144058\\",\\"engineNumber\\":\\"8296\\",\\"couponMoney\\":0.0,\\"updateStateTime\\":1414218000943,\\"updateWorkerNo\\":null,\\"timeOut":false},\\"sps\\":null,\\"cars\\":null,\\"orders\\":null,\\"ordersTotal\\":0,\\"peccancyInfos\\":null,\\"peccancyInfosTotal\\":0}";
		
		
		
		PeccancyInfoModel del=new PeccancyInfoModel();
//		del.setPecState("1");
		del.setUpdateDate("1397459397000");
		del.setId(12+"");
		del.setWoState("1");
String str="update wht_breakrules set pecState='"+(null==del.getPecState()?"aa":del.getPecState())+
"',updateDate='"+Tools.timestamp2String(Long.parseLong(del.getUpdateDate()))+
"',woState="+del.getWoState()+
" where id="+del.getId();
System.out.println(str);
	}

}
