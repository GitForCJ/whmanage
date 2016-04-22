package oufei;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;


/**
 * 
 *
 */
public class OuFeiBusiness implements Runnable{

	private OuFeiOrderInfo orderList;
	private String[] str=null;
	
	/**
	 * @param orderList
	 * @param str
	 */
	public OuFeiBusiness(OuFeiOrderInfo orderList,String[] str) {
		this.orderList =orderList;
		this.str=str;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Log.info("欧飞取单成功,号码："+orderList.getRecharge_account()+"充值金额："+orderList.getProduct_par_value()
				+" 订单编号："+orderList.getOrder_id()+" 编号："+orderList.getId());
		String orderstate = "5";  //设置默认充值失败
//		if(com.wlt.webm.util.Tools.isJC(orderList.getRecharge_account())){//特殊地市直接返回失败
//			orderstate = "5";
//		}else{
		Log.info("检测订单是否为可充值订单。");
		int sum = OuFeiBean.checkOrder(orderList.getOrder_id(), orderList.getId());  //确定订单是否为可充值订单
		if(sum==1){ 
			Log.info(orderList.getOrder_id()+"订单可充值！");
			try {//userno,userpid,areacode,login,parentid
				Log.info("开始充值订单："+orderList.getOrder_id());
				BiProd bp=new BiProd();
				int result =bp.ghFill(orderList.getRecharge_account(),
						 orderList.getOrder_id(),
						str[0], str[1], orderList.getProduct_par_value(), 
						str[2], str[3],Integer.parseInt(str[4]));
				
				Log.info("订单号 :"+orderList.getOrder_id()+" 充值结果:"+result);
				if(result == 0||result>=20){
					orderstate = "4" ;
				}
				else if(result==1||result==-1||result==6){
					orderstate = "5" ;//失败
				}else{
					orderstate = "6";//可疑
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			orderstate = "5" ;
		}
		//}//特殊地市直接返回失败  结束
		Log.info("向殴飞返回结果");
		OuFeiBean.returnResult(orderList.getOrder_id(), orderList.getId(), orderstate,str);
	}

}

