package com.wlt.webm.mobile;

import java.util.Map;

import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.OuFeiChargeUtil;
import com.wlt.webm.business.bean.SysInvoke;
import com.wlt.webm.business.form.SysInvokeForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.xunjie.bean.SlsBean;

/**
 * @author lenovo
 *
 */
public class NationWide {
	
	/**
	 * 第三方全国充值接口
	 * @param type      移动[0]|联通[1]|电信[2]
	 * @param paraMoney 交易金额 单位元 只能为 30、、50、100、200、300、500
	 * @param payNo   交易手机号码11位
	 * @param nowTime 交易时间14位
	 * @param sepNo   流水号 25位
	 * @param userid  用户id
	 * @return  0表示充值成功  1表示失败 2表示异常 3表示充值中 5表示未找到产品对应接口
	 */
	public static int nationWideFill(int type,String paraMoney,String payNo,String nowTime,String sepNo,String userid){
		int status=5;
        String sql ="select cm_face from  wlt_three where cm_type='"+type+"'"+" and cm_fee='"+paraMoney+"'";
        System.out.println(sql);
        DBService db=null;
		Map ofResultMap;
		try {
			db=new DBService();
			String code=db.getString(sql);
			if(null==code||"".equals("")){
				return status;//未找到产品对应接口
			}
			else if("0005".equals(code)){
			// 殴飞充值
			SysInvokeForm invokeForm = new SysInvokeForm();
			SysInvoke invoke = new SysInvoke();
			MobileChargeService service = new MobileChargeService();
			ofResultMap = OuFeiChargeUtil.charge(paraMoney, sepNo,
					nowTime, payNo);
		
		String retCode = (String) ofResultMap.get("retcode");
		// 插入调用日志
		invokeForm.setUser_id(userid);
		invokeForm.setOrd_id(sepNo);
		invokeForm.setIn_input((String) ofResultMap.get("input"));
		invokeForm.setIn_output(retCode);
		invokeForm.setIn_time(nowTime);
		invokeForm.setIn_desc("殴飞充值");
		invokeForm.setIn_code("0005");
		invokeForm.setIn_otherput("");
		invokeForm.setSe_code("P0001");
		invoke.add(invokeForm);
		if ("9".equals(retCode)) {//失败
			status = 1;
		} else {//循环查询订单状态
			int times = 0;
			while (times < 50) {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String ordState = service.getOrderStatus(sepNo,
						"wlt_orderForm_" + nowTime.substring(2, 6));
				if ("4".equals(ordState)) {
					times++;
				} else {
					if ("1".equals(ordState)) {// 失败
						status = 1;
					} else if ("6".equals(ordState)) {// 异常
						status = 2;
					}
					break;
				}
			}
			// 如果没有回调，发送查询请求
			if (times == 50) {
				// 发送查询请求
				int ouf = OuFeiChargeUtil.query(sepNo);
				if (1 == ouf) {// 成功
					status = 0;
				} else if (0 == ouf) {// 充值中
					status = 3;
				} else if (9 == ouf) {// 失败
					status = 1;
				} else if (-1 == ouf) {// 无此订单
					status = 1;
				} else {
					status = 3;
				}
			}
		}
			}else if("0009".equals(code)){
				SlsBean sls=new SlsBean();
				status =sls.slsFill(payNo, sepNo, paraMoney);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			status=2;
		}
		return 0;
	}
	
	

	public static void main(String[] args) {
       NationWide.nationWideFill(0, "30", "18682033916", "20131011235959", "1111111111111111111111111", "2");
	}

}
