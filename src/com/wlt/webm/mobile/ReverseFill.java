package com.wlt.webm.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.InnerProcessDeal;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.cmcc.CMPayPayBusiness;
import com.wlt.webm.business.cmcc.CMPayUndoPayBusiness;
import com.wlt.webm.business.dianx.bean.RevertBusiness;
import com.wlt.webm.business.dianx.bean.TelcomPayBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

	/**
	 * �ֻ�����
	 * @author 
	 *
	 */
public class ReverseFill {
	
	   
	   /**
	    * app �������� com.wlt.webm.mobile.ReverseFill.reverse(,)
	    * @param tradeserial String���� ��Ҫ�������ǱȽ��׵ĳ�ֵ��ˮ��
	    *                                 
	    * @param user  session�л�ȡ��SysUserForm
	    * 
	    * @return     0:�����ɹ�  1;�������������� -1;//����ʧ�� -2;//���ֽ������Ͳ��ܳ���   -3;//��������
	    *          
	    * @throws Exception
	    */
	   public static int reverse(String tradeserial, SysUserForm user) throws Exception{
		   
		    Log.info("�յ��ֻ���������"+tradeserial+"----"+user.getUser_id());
			//�жϳ��������Ƿ�����
			int n=SysUserInterface.revertCount(user.getUser_id());
	        if(0!=n){
	    		return 1;//��������������	
	        }		
			try{
				//��ȡ��ǰʱ��
				String nowTime=Tools.getNow();
				OrderBean orderBean = new OrderBean();
				//��ȡ������Ϣ
				OrderForm orderForm = orderBean.getOrderInfotwo(tradeserial, "wlt_orderForm_"+nowTime.substring(2, 6));
				//������������
				String rollBack = orderForm.getWriteoff();
				//�绰����
				String payNo = orderForm.getTradeobject();
				//������ˮ��26λ
				String seqNo = Tools.getSeqNo(payNo)+"9";
				//
				String fee = orderForm.getFee();
				String tableName = nowTime.substring(2, 6);
				if("0001".equals(orderForm.getBuyid())){//�㶫����
					Log.info("------------�ֻ����ŷ���-----------:"+payNo);
					//���÷����ӿ�
					RevertBusiness rb = new RevertBusiness(rollBack,payNo,seqNo);
					List list = rb.deal();
					if(null != list && list.size() > 0 && "000".equals(list.get(0))){//�����ɹ�
						//��ѯ�˻���ϸ��¼
						List acctList = orderBean.listAcct(tableName, orderForm.getTradeserial());
						if(null != acctList && acctList.size() > 0){
							//���׽��
							int payFee = 0;
							//�ʽ����˺�
							String fundAcct02 = "";
							//����Ӷ
							String empFee = "";
							//�ϼ��ʽ��˺�
							String empAcctLevlOne = "";
							//�ϼ�Ӷ��
							String empFeeLevlOne = "";
							for(Object tmp : acctList){
								String[] temp = (String[])tmp;
								if(null != temp[3] && !"".equals(temp[3])){
									if("1".equals(temp[3])){//֧��
										payFee = Integer.parseInt(temp[2]);
										fundAcct02 = temp[0];
									}else if("2".equals(temp[3])){//����
										empFee = temp[2];
									}else if("3".equals(temp[3])){//һ������
										empAcctLevlOne = temp[0];
										empFeeLevlOne = temp[2];
									}
								}
							}
							MobileChargeService service = new MobileChargeService();
							//������,�˻�����Ӷ�𲢸����˻���ϸ
							service.updAccountForReverse(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,orderForm.getTradeserial());
							//�ϼ�����Ӷ�𲢸����˻���ϸ
							if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
								service.updEmpAccountForReverse(empAcctLevlOne, empFeeLevlOne, tableName,orderForm.getTradeserial());
							}
						}
						SysUserInterface.revertCountAdd(user.getUser_id());
						return 0;
					}else{//����ʧ��
						return -1;
					}
				}else if("0007".equals(orderForm.getBuyid())){//��ͨ����
					Log.info("-----------�ֻ���ͨ����----------:"+payNo);
					String sqlNo=Constant.FACTORYID_UNICOM+DateParser.getNowDateTime().substring(4)+UniqueNo.getInstance().getNoTwo();
					com.wlt.webm.business.unicom.RevertBusiness rBusiness = new com.wlt.webm.business.unicom.RevertBusiness(payNo,fee,rollBack,sqlNo);
					rBusiness.deal();
					String mainKey=null;
					for(int i=0;i<40;i++){
						mainKey=(String)MsgCache.unicom.get("unicomRePay"+sqlNo);
						if(null != mainKey){
							if(mainKey.equals("0")||mainKey.equals("1")||mainKey.equals("2")){
								break;
							}
						}
						Thread.sleep(5000);
					}
					if(null != mainKey){
						if(mainKey.equals("0")){
							//��ѯ�˻���ϸ��¼
							List acctList = orderBean.listAcct(tableName, orderForm.getTradeserial());
							if(null != acctList && acctList.size() > 0){
								//���׽��
								int payFee = 0;
								//�ʽ����˺�
								String fundAcct02 = "";
								//����Ӷ
								String empFee = "";
								//�ϼ��ʽ��˺�
								String empAcctLevlOne = "";
								//�ϼ�Ӷ��
								String empFeeLevlOne = "";
								for(Object tmp : acctList){
									String[] temp = (String[])tmp;
									if(null != temp[3] && !"".equals(temp[3])){
										if("1".equals(temp[3])){//֧��
											payFee = Integer.parseInt(temp[2]);
											fundAcct02 = temp[0];
										}else if("2".equals(temp[3])){//����
											empFee = temp[2];
										}else if("3".equals(temp[3])){//һ������
											empAcctLevlOne = temp[0];
											empFeeLevlOne = temp[2];
										}
									}
								}
								MobileChargeService service = new MobileChargeService();
								//������,�˻�����Ӷ�𲢸����˻���ϸ
								service.updAccountForReverse(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,orderForm.getTradeserial());
								//�ϼ�����Ӷ�𲢸����˻���ϸ
								if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
									service.updEmpAccountForReverse(empAcctLevlOne, empFeeLevlOne, tableName,orderForm.getTradeserial());
								}
							}
							SysUserInterface.revertCountAdd(user.getUser_id());
							return 0;
						}
					}else{
						return -1;
					}
				}else if("0008".equals(orderForm.getBuyid())){//�㶫�ƶ�
					Log.info("-----------�ֻ��ƶ�����-----------:"+payNo);
					String sqlNo=Tools.getCMPaySeq(com.wlt.webm.uictool.Constant.CMPaySUPhone.trim(),com.wlt.webm.uictool.Constant.CMPayBeginSeq);
					CMPayUndoPayBusiness cmupb = new CMPayUndoPayBusiness(payNo,fee,rollBack,sqlNo);
					cmupb.deal();
					String mainKey=null;
					MobileChargeService service = new MobileChargeService();
					for(int i=0;i<40;i++){
						//mainKey=(String)MsgCache.cmcc.get("cmccRepay"+sqlNo);
						 mainKey = service.selectState("cmccRepay"+sqlNo);
						if(null != mainKey){
							if(mainKey.equals("0")||mainKey.equals("1")||mainKey.equals("2")){
								service.deleteState("cmccRepay"+sqlNo);
								break;
							}
						}
						Thread.sleep(5000);
					}
					if(null != mainKey){
						if(mainKey.equals("0")){
							//��ѯ�˻���ϸ��¼
							List acctList = orderBean.listAcct(tableName, orderForm.getTradeserial());
							if(null != acctList && acctList.size() > 0){
								//���׽��
								int payFee = 0;
								//�ʽ����˺�
								String fundAcct02 = "";
								//����Ӷ
								String empFee = "";
								//�ϼ��ʽ��˺�
								String empAcctLevlOne = "";
								//�ϼ�Ӷ��
								String empFeeLevlOne = "";
								for(Object tmp : acctList){
									String[] temp = (String[])tmp;
									if(null != temp[3] && !"".equals(temp[3])){
										if("1".equals(temp[3])){//֧��
											payFee = Integer.parseInt(temp[2]);
											fundAcct02 = temp[0];
										}else if("2".equals(temp[3])){//����
											empFee = temp[2];
										}else if("3".equals(temp[3])){//һ������
											empAcctLevlOne = temp[0];
											empFeeLevlOne = temp[2];
										}
									}
								}
								//������,�˻�����Ӷ�𲢸����˻���ϸ
								service.updAccountForReverse(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,orderForm.getTradeserial());
								//�ϼ�����Ӷ�𲢸����˻���ϸ
								if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
									service.updEmpAccountForReverse(empAcctLevlOne, empFeeLevlOne, tableName,orderForm.getTradeserial());
								}
							}
							SysUserInterface.revertCountAdd(user.getUser_id());
							return 0;
						}
					}else{
						return -1;
					}
				}else if("0003".equals(orderForm.getBuyid())||"0005".equals(orderForm.getBuyid())){
					return -2;
				}
			}catch (Exception e) {
				Log.info("�ֻ���������"+e.toString());
				return -3;
			}
			return 0;

		}
	   

}
