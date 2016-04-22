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
	 * 手机返销
	 * @author 
	 *
	 */
public class ReverseFill {
	
	   
	   /**
	    * app 返销服务 com.wlt.webm.mobile.ReverseFill.reverse(,)
	    * @param tradeserial String类型 需要冲正的那比交易的充值流水号
	    *                                 
	    * @param user  session中获取的SysUserForm
	    * 
	    * @return     0:返销成功  1;返销次数已用完 -1;//返销失败 -2;//该种交易类型不能冲正   -3;//返销出错
	    *          
	    * @throws Exception
	    */
	   public static int reverse(String tradeserial, SysUserForm user) throws Exception{
		   
		    Log.info("收到手机冲正请求；"+tradeserial+"----"+user.getUser_id());
			//判断冲正数量是否用完
			int n=SysUserInterface.revertCount(user.getUser_id());
	        if(0!=n){
	    		return 1;//冲正次数已用完	
	        }		
			try{
				//获取当前时间
				String nowTime=Tools.getNow();
				OrderBean orderBean = new OrderBean();
				//获取订单信息
				OrderForm orderForm = orderBean.getOrderInfotwo(tradeserial, "wlt_orderForm_"+nowTime.substring(2, 6));
				//返销所需数据
				String rollBack = orderForm.getWriteoff();
				//电话号码
				String payNo = orderForm.getTradeobject();
				//返销流水号26位
				String seqNo = Tools.getSeqNo(payNo)+"9";
				//
				String fee = orderForm.getFee();
				String tableName = nowTime.substring(2, 6);
				if("0001".equals(orderForm.getBuyid())){//广东电信
					Log.info("------------手机电信返销-----------:"+payNo);
					//调用返销接口
					RevertBusiness rb = new RevertBusiness(rollBack,payNo,seqNo);
					List list = rb.deal();
					if(null != list && list.size() > 0 && "000".equals(list.get(0))){//返销成功
						//查询账户明细记录
						List acctList = orderBean.listAcct(tableName, orderForm.getTradeserial());
						if(null != acctList && acctList.size() > 0){
							//交易金额
							int payFee = 0;
							//资金子账号
							String fundAcct02 = "";
							//自身返佣
							String empFee = "";
							//上级资金账号
							String empAcctLevlOne = "";
							//上级佣金
							String empFeeLevlOne = "";
							for(Object tmp : acctList){
								String[] temp = (String[])tmp;
								if(null != temp[3] && !"".equals(temp[3])){
									if("1".equals(temp[3])){//支出
										payFee = Integer.parseInt(temp[2]);
										fundAcct02 = temp[0];
									}else if("2".equals(temp[3])){//存入
										empFee = temp[2];
									}else if("3".equals(temp[3])){//一级存入
										empAcctLevlOne = temp[0];
										empFeeLevlOne = temp[2];
									}
								}
							}
							MobileChargeService service = new MobileChargeService();
							//自身返款,退回自身佣金并更新账户明细
							service.updAccountForReverse(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,orderForm.getTradeserial());
							//上级返回佣金并更新账户明细
							if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
								service.updEmpAccountForReverse(empAcctLevlOne, empFeeLevlOne, tableName,orderForm.getTradeserial());
							}
						}
						SysUserInterface.revertCountAdd(user.getUser_id());
						return 0;
					}else{//返销失败
						return -1;
					}
				}else if("0007".equals(orderForm.getBuyid())){//联通冲正
					Log.info("-----------手机联通返销----------:"+payNo);
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
							//查询账户明细记录
							List acctList = orderBean.listAcct(tableName, orderForm.getTradeserial());
							if(null != acctList && acctList.size() > 0){
								//交易金额
								int payFee = 0;
								//资金子账号
								String fundAcct02 = "";
								//自身返佣
								String empFee = "";
								//上级资金账号
								String empAcctLevlOne = "";
								//上级佣金
								String empFeeLevlOne = "";
								for(Object tmp : acctList){
									String[] temp = (String[])tmp;
									if(null != temp[3] && !"".equals(temp[3])){
										if("1".equals(temp[3])){//支出
											payFee = Integer.parseInt(temp[2]);
											fundAcct02 = temp[0];
										}else if("2".equals(temp[3])){//存入
											empFee = temp[2];
										}else if("3".equals(temp[3])){//一级存入
											empAcctLevlOne = temp[0];
											empFeeLevlOne = temp[2];
										}
									}
								}
								MobileChargeService service = new MobileChargeService();
								//自身返款,退回自身佣金并更新账户明细
								service.updAccountForReverse(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,orderForm.getTradeserial());
								//上级返回佣金并更新账户明细
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
				}else if("0008".equals(orderForm.getBuyid())){//广东移动
					Log.info("-----------手机移动返销-----------:"+payNo);
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
							//查询账户明细记录
							List acctList = orderBean.listAcct(tableName, orderForm.getTradeserial());
							if(null != acctList && acctList.size() > 0){
								//交易金额
								int payFee = 0;
								//资金子账号
								String fundAcct02 = "";
								//自身返佣
								String empFee = "";
								//上级资金账号
								String empAcctLevlOne = "";
								//上级佣金
								String empFeeLevlOne = "";
								for(Object tmp : acctList){
									String[] temp = (String[])tmp;
									if(null != temp[3] && !"".equals(temp[3])){
										if("1".equals(temp[3])){//支出
											payFee = Integer.parseInt(temp[2]);
											fundAcct02 = temp[0];
										}else if("2".equals(temp[3])){//存入
											empFee = temp[2];
										}else if("3".equals(temp[3])){//一级存入
											empAcctLevlOne = temp[0];
											empFeeLevlOne = temp[2];
										}
									}
								}
								//自身返款,退回自身佣金并更新账户明细
								service.updAccountForReverse(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,orderForm.getTradeserial());
								//上级返回佣金并更新账户明细
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
				Log.info("手机冲正出错"+e.toString());
				return -3;
			}
			return 0;

		}
	   

}
