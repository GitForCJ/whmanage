package com.wlt.webm.mobile;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.ten.service.QBPayRequestHandler;
import com.wlt.webm.ten.service.TenpayConfigParser;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.util.TenpayUtil;
/**
 * q币接口 
 * @author caiSJ
 *
 */
public class QbMobile {
	
	
	/**
	 * Qb服务类
	 * @param userID  用户ID
	 * @param in_acct QQ账号
	 * @param num     购买的q币个数
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return    0:成功
	 * 			  -1:  系统繁忙稍后再试
	 * 			  -2：交易失败
                  11:余额不足 
                  12:网络中断
                  13:超时 请联系客服
	 *            15:Q币充值网络异常 
	 *            1 数字签名错误（检查密钥是否正确、md5加密是否正确）
	 *            2 订单重复提交
	 *            3 用户帐号不存在 
	 *            4 系统错误(指的是非在线卡支付逻辑的所有错误)。如果出现该错误，最多重复尝试充值3次，如果错误依旧，建议放弃充值或人工干预处理。
	 *            5 IP错误 
	 *            6 用户key错误 
	 *            7 参数错误 数据不合法
	 *            8 库存不足 
	 *            9 用户状态异常 
	 *            10 订单超时 
	 *            101 此功能暂时不可用 
	 *            102 该商业号权限不足 
	 *            103系统维护中
	 * @throws Exception  
	 */
	public static int  MobileQb(String userID,String in_acct,int num,HttpServletRequest request,HttpServletResponse response) {
		try {
			return HttpRequest.readContentFromGet(num+"", in_acct, userID, request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			return 13;
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
