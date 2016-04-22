package com.wlt.webm.uictool;

import java.util.HashMap;

/**
 * <p>Description: 常量类，返回码定义类</p>113.106.160.201
 */
public class Constant {
	
	/**
	 * 包开始标志
	 */
	public static byte[] BAG_HEAD = "FFFF".getBytes();
	
	/**
	 * 本机IP
	 */
	//public static String IP = "192.168.1.118";
	
	/**
	 * 本机PORT
	 */
	//public static int PORT = 7710;
	/**
	 * 正式库IP
	 */
	public static String IP = "132.121.130.164";
//	public static String IP = "132.121.249.40";//50服务器IP地址
//	public static String IP = "192.168.5.199";
	
	/**
	 * 正式库PORT
	 */
	public static int PORT = 5555;
//	public static int PORT = 7710;
	
	/**
	 * 正式库密码
	 */
	public static String AGENT_PASSWORD = "d03bec0d7d8a699b8c66d067c23fb90d";
	
	/**
	 * 测试库IP
	 */
//	public static String IP = "132.121.130.8";
	
	/**
	 * 测试库PORT
	 */
//	public static int PORT = 5888;
	
	/**
	 * 测试库密码
	 */
//	public static String AGENT_PASSWORD = "d03bec0d7d8a699b8c66d067c23fb908";
	
	/**
	 * 电信缴费代理商编码
	 */
	public static String AGENT_CODE = "0007";
	/**
	 * 联通缴费分配给代理商企业ID
	 */
	public static final String FACTORYID_UNICOM="1034";  
	
	/**
	 * 代理商IP
	 */
	public static String AGENT_IP = "132.121.249.34";
	
	/**
	 * 查询请求包类型
	 */
	public static byte[] QUERY_TYPE = "INF00160".getBytes();
	/**
	 * 查询请求包类型
	 */
	public static byte[] QUERYDETAIL_TYPE = "INF00330".getBytes();
	
	/**
	 * 缴费请求包类型
	 */
	public static byte[] FILL_TYPE = "INF00170".getBytes();
	
	/**
	 * 冲正请求包类型
	 */
	public static byte[] ROLL_TYPE = "INF00180".getBytes();
	
	/**
	 * 空中售号--号码查询
	 */
	public static byte[] QUERY_NUM = "INF00300".getBytes();
	
	/**
	 * 空中售号--绑定UIM卡
	 */
	public static byte[] BOND_NUM = "INF00310".getBytes();
	
	/**
	 * 空中售号--受理情况查询
	 */
	public static byte[] DETAIL_NUM = "INF00320".getBytes();
	
	/**
	 * 不带信息鉴真码的包头长度
	 */
	public static int BAG_LENGTH = 64;
	
	/**
	 * 带信息鉴真码的包头长度
	 */
	public static int BAG_LENGTH_MAC = 72;
	
	/**
	 * 信息鉴真码
	 */
	public static byte[] CHECK_INFO = "00000000".getBytes();
	
	/**
	 * 发送系统ID
	 */
	public static byte[] SEND_ID = "80".getBytes();
	
	/**
	 * 接收系统ID
	 */
	public static byte[] RECEIVE_ID = "00".getBytes();
	
	/**
	 * 预留空间 26位空格
	 */
	public static byte[] BLANK_SPACE = "                          ".getBytes();
	
	/**
	 * 号码类型 固话0
	 */
	public static String NUMBER_TYPE_GH = "0";
	/**
	 * 号码类型 小灵通1
	 */
	public static String NUMBER_TYPE_XLT = "1";
	/**
	 * 号码类型 手机2
	 */
	public static String NUMBER_TYPE_SJ = "2";
	/**
	 * 号码类型 宽带3
	 */
	public static String NUMBER_TYPE_KD = "3";
	
	/**
	 * 操作码 固话充值
	 */
	public static String OPERATE_CODE_PHONE = "000001";
	/**
	 * 操作码 宽带充值
	 */
	public static String OPERATE_CODE_BROADBAND = "000003";
	/**
	 * 操作码 合同充值 000004
	 */
	public static String OPERATE_CODE_CONTACT = "000004";
	
	/**
	 * 查询业务类型编码
	 */
	public static String QUERY_SERNO="";
	
	/**
	 * 联通查询业务类型编码
	 */
	public static String UCQUERY_SERNO="";
	
	/**
	 * 银联查询业务类型编码
	 */
	public static String UNPAYQUERY_SERNO="";
	
	/**
	 * 缴费业务类型编码
	 */
	public static String FILL_SERNO="";
	
	/**
	 * 联通缴费业务类型编码
	 */
	public static String UCFILL_SERNO="";
	/**
	 * 天作缴费业务类型编码
	 */
	public static String TZFILL_SERNO="";
	
	/**
	 * 银联扣费业务类型编码
	 */
	public static String UNPAYPAY_SERNO="";
	
	/**
	 * 返销业务类型编码
	 */
	public static String REVERT_SERNO="";
	
	/**
	 * 联通返销业务类型编码
	 */
	public static String UCREVERT_SERNO="";
	/**
	 * 天作返销业务类型编码
	 */
	public static String TZREVERT_SERNO="";
	
	/**
	 * 银联撤销业务类型编码
	 */
	public static String UNPAYUNDOPAY_SERNO="";
	
	/**
	 * 对账业务类型编码
	 */
	public static String CHECK_SERNO="";
	
	/**
	 * 联通对账业务类型编码
	 */
	public static String UCCHECK_SERNO="";
	/**
	 * 天作对账业务类型编码
	 */
	public static String TZCHECK_SERNO="";
	
	/**
	 * 银联冲正业务类型编码
	 */
	public static String UNPAYREVERT_SERNO="";
	
	/**
	 * 最大接收缓冲区大小
	 */
	public static int MaxBuff = 1024*8;
	
	/**
	 * 响应结果编码
	 */
	public static String RESPONSE_SUCCESS = "0000";
	
	/**
	 * uic标示 电信业务
	 */
	public static final String UIC_FLAG = "07";
	/**
	 * uic2标示 联通业务
	 */
	public static final String UIC2_FLAG = "08";
	/**
	 * uic3标示 银联业务
	 */
	public static final String UIC3_FLAG = "09";
	/**
	 * uic4标示 移动业务
	 */
	public static final String UIC4_FLAG = "13";
	/**
	 * uic5标示 天作业务
	 */
	public static final String UIC5_FLAG = "15";
	
	/**
	 * uic发送至scp 电信业务
	 */
	public static final String UIC_TO_SCP = "0701";
	/**
	 * uic发送至scp 联通业务
	 */
	public static final String UIC2_TO_SCP = "0801";
	/**
	 * uic发送至scp 银联业务
	 */
	public static final String UIC3_TO_SCP = "0901";
	/**
	 * uic发送至scp 移动业务
	 */
	public static final String UIC4_TO_SCP = "1301";
	/**
	 * uic5发送至scp TZ业务
	 */
	public static final String UIC5_TO_SCP = "1501";
	
	/**
	 * uic发送至task
	 */
	public static final String UIC_TO_TASK = "0706";
	/**
	 * uic发送至task
	 */
	public static final String UIC2_TO_TASK = "0806";
	
	
	//uni 模块
	
	/**
	 * uni本机IP
	 */
	//public static String UNIIP = "192.168.1.118";
	
	/**
	 * uni本机PORT
	 */
	//public static int UNIPORT = 7720;
	/**
	 *Uni测试库IP
	 */
	//public static String UNIIP = "218.17.225.81";
	
	/**
	 * Uni测试库PORT
	 */
	//public static int UNIPORT = 24903;
	/**
	 *Uni生产库IP
	 */
	
	public static String UNIIP = "218.17.225.81";//正式
	
//    public static String UNIIP = "113.106.160.201";//测试113.106.160.201
	
	/**
	 * Uni生产库PORT
	 */
//    
	public static int UNIPORT = 12113;//正式
	
//	public static int UNIPORT = 24903;//测试
	
	/**
	 * Uni签到账号ID
	 */
//	public static byte[] SIGNINID = "001034".getBytes();//测试库参数
	public static byte[] SIGNINID = "002124".getBytes();//生产库参数
	
	/**
	 *Uni 企业ID
	 */
//	public static byte[] FACTORYID ="1034".getBytes();//测试库参数
	public static byte[] FACTORYID ="2124".getBytes();//生产库参数
	
	/**
	 * Uni终端ID
	 */
//	public static byte[] TERMINALID = "20001034".getBytes();//测试库参数
	public static byte[] TERMINALID = "20124007".getBytes();//生产库参数
	/**
	 * Uni密码
	 */
//	public static byte[] UNIPASSWORD = "as234q".getBytes();//测试库参数
	public static byte[] UNIPASSWORD = "wsf54r".getBytes();//生产库参数
	

	
	/**
	 * Uni包开始标志
	 */
	public static byte[] TAG = "WT".getBytes();
	
	/**
	 *Uni 版本
	 */
	public static byte[] VERSION = "10".getBytes();
	/**
	 * Uni心跳类型
	 */
	public static byte[] CMD_PING_TYPE = "0202".getBytes();
	/**
	 * Uni签到类型
	 */
	public static byte[] CMD_SIGNIN_TYPE = "0201".getBytes();
	
	/**
	 * Uni签到返回类型
	 */
	public static byte[] RSP_SIGNIN_TYPE = "1201".getBytes();
	
	/**
	 * Uni缴费请求类型
	 */
	public static String CMD_FILL_TYPE = "0203";
	
	/**
	 * Uni缴费返回类型
	 */
	public static byte[] RSP_FILL_TYPE = "1203".getBytes();
	
	/**
	 * Uni冲正请求类型
	 */
	public static String CMD_ROLL_TYPE = "0204";
	
	/**
	 * Uni冲正返回类型
	 */
	public static byte[] RSP_ROLL_TYPE = "1204".getBytes();
	
	/**
	 * Uni查询请求类型
	 */
	public static String CMD_QUERY_TYPE = "0205";
	
	/**
	 * Uni查询返回类型
	 */
	public static byte[] RSP_QUERY_TYPE = "1205".getBytes();
	
	/**
	 * 报文回应状态
	 */
	public static byte[] ERRSTATUS = "0000".getBytes();
	
	/**
	 * 号码类型
	 */
	public static byte[] UNI_NUMBER_TYPE = "20".getBytes();
	
	/**
	 * Uni心跳请求包长度
	 */
	public static byte[]  CMD_PING_BAGLEN = "0076".getBytes();
	/**
	 * Uni签到请求包长度
	 */
	public static byte[]  CMD_SIGNIN_BAGLEN = "0114".getBytes();
	
	/**
	 * Uni签到返回包长
	 */
	public static byte[] RSP_SIGNIN_BAGLEN = "0102".getBytes();
	
	/**
	 * Uni充值请求包长
	 */
	public static byte[] CMD_FILL_BAGLEN = "0138".getBytes();
	
	/**
	 * Uni充值返回包长
	 */
	public static byte[] RSP_FILL_BAGLEN = "0138".getBytes();
	
	/**
	 * Uni冲正请求包长
	 */
	public static byte[] CMD_ROLL_BAGLEN = "0154".getBytes();
	
	/**
	 * Uni冲正返回包长度
	 */
	public static byte[] RSP_ROLL_BAGLEN = "0174".getBytes();
	
	/**
	 * Uni查询请求包长
	 */
	public static byte[] CMD_QUERY_BAGLEN = "0156".getBytes(); 
	
	/**
	 * Uni查询返回包长
	 */
	public static byte[] RSP_QUERY_BAGLEN = "0204".getBytes();
	
	
	/**
	 * Uni查询交易类型
	 */
	public static byte[] UNIQUERYTYPE = "01".getBytes();
	
	/**
	 * scp响应码信息
	 */
	public static HashMap scpRecode = new HashMap();
	
	/**
	 *初始化SCP返回码
	 */
	public static void initScpRecode()
	{
		scpRecode.put("000", "交易成功");
		scpRecode.put("001", "交易包格式错误");
		scpRecode.put("002", "其他错误");
		scpRecode.put("003", "处理中,交易被取消");
		scpRecode.put("004", "该账户正在交费");
		scpRecode.put("005", "系统故障");
		scpRecode.put("010", "系统锁定,无法交易");
		scpRecode.put("011", "未定义的交易码");
		scpRecode.put("020", "资金帐户验证失败");
		scpRecode.put("021", "资金帐户密码错误");
		scpRecode.put("022", "资金帐户已经销户");
		scpRecode.put("023", "资金帐户余额不足");
		scpRecode.put("030", "终端信息验证失败");
		scpRecode.put("031", "终端已经销户");
		scpRecode.put("032", "呼入号码不是绑定电话");		
		scpRecode.put("033", "终端接入号码错误");
		scpRecode.put("034", "PSAM卡号不存在");
		scpRecode.put("035", "向计费发送消息失败");
		scpRecode.put("072", "异常金额");
		scpRecode.put("102", "没有欠费");
		scpRecode.put("110", "输入号码不存在");
		scpRecode.put("111", "用户密码错误");
		scpRecode.put("112", "不能返销");
		scpRecode.put("113", "不是指定的客户端");
		scpRecode.put("114", "有多局编账号");
		scpRecode.put("115", "返销流水不存在");
		scpRecode.put("116", "没有要打印的单据");
		scpRecode.put("117", "该笔流水已返销");
		scpRecode.put("118", "该笔流水已销账");
		scpRecode.put("200", "对账时总额核对不匹配");
		scpRecode.put("201", "对账文件获取或发送失败");
		scpRecode.put("202", "对账信息错误");
		scpRecode.put("222", "金额错误");
		scpRecode.put("666", "计费系统错误");
		scpRecode.put("777", "要求对账的明细太多");
	}
	
	/**
	 * 保存的查询详细信息
	 */
	public static final HashMap toscpmessage = new HashMap();
	
	/**
	 * 与scp通信同步锁
	 */
	public static final Object toscpmessagesyn = new Object();
	
	/**
	 * 设置toscp的信息
	 * @param seqno 流水号
	 * @param type 消息类型
	 */
	public static void setToscpmessage(String seqno,String msg){
		synchronized(toscpmessagesyn){
			toscpmessage.put(seqno, msg);
		}
	}
	
	/**
	 * 获取toscp的信息
	 * @param seqno 流水号
	 * @param type 类型
	 * @return ArrayList  
	 */
	public static String getToscpmessage(String seqno){
		if(toscpmessage.get(seqno) == null){
			return null;
		}else{
			synchronized(toscpmessagesyn){
				return (String)toscpmessage.remove(seqno);
			}
		}
	}

	
	
	
	
	
//	 以下为银联相关配置信息
//	 ——————————————————————————————————————————————————————————

		/**
		 * 银联IP地址 
		 */
//		public static final String unionPayIP = "9.234.3.82";
		public static final String unionPayIP = "9.234.4.11";//上海正式库服务器
//		public static final String unionPayIP = "132.121.249.36";//48测试服务器
//		public static final String unionPayIP = "145.24.29.9";//广州银联测试端口（接口人：练恒光）
//		public static final String unionPayIP = "192.168.5.210";//测试fep模块使用
		/**
		 * 银联端口号 
		 */
		public static final int unionPayPort = 20257;
//		public static final int unionPayPort = 7730;
//		public static final int unionPayPort = 52102;//绑定测试时使用
//		public static final int unionPayPort = 7708;
		/**
		 * 公司对外IP 10.23.102.2
		 */
		public static final String companyIP = "10.23.102.2";
//		public static final String companyIP = "132.121.249.36";
//		public static final String companyIP = "145.24.29.102";//广州银联测试端口（接口人：练恒光）
//		public static final String companyIP = "192.168.5.210";
		/**
		 * 公司对外端口号 52236
		 */
		public static final int companyPort = 20257;
//		public static final int companyPort = 7840;
//		public static final int companyPort = 52102;//正式端口
		/**
		 * 重置密钥报文类型
		 */
		public static final String MSGTYPE_BIND = "0110";
		/**
		 * 重置密钥报文类型
		 */
		public static final String MSGTYPE_RESET = "0810";
		/**
		 * 签到消息报文类型
		 */
		public static final String MSGTYPE_SIGN = "0820";
		
		/**
		 * 签到返回消息报文类型
		 */
		public static final String MSGTYPE_R_SIGN = "0830";
		
		/**
		 * 查询消息
		 */
		public static final String MSGTYPE_QRY = "0200";
		
		/**
		 * 缴费消息
		 */
		public static final String MSGTYPE_PAY = "0200";
		
		/**
		 * 查询返回消息
		 */
		public static final String MSGTYPE_R_QRY = "0210";
		
		/**
		 * 冲正消息
		 */
		public static final String MSGTYPE_RVT = "0400";
		
		/**
		 * 冲正返回消息
		 */
		public static final String MSGTYPE_R_RVT= "0410";

		/**
		 * 查询交易处理码 300000
		 */
		public static final String trancode_qry = "300000";
		
		/**
		 * 扣费交易交易处理码
		 */
		public static final String trancode_pay = "190000";
		
		/**
		 * 撤销交易交易处理码 280000
		 */
		public static final String trancode_unp = "280000";
		/**
		 * 撤销交易交易处理码 930000
		 */
		public static final String trancode_bind = "930000";
		/**
		 * 22# 服务点输入方式码n3 012
		 */
		public static final String serverType = "012";

		/**
		 * 25# 服务点条件码	n2	"81"
		 */
		public static final String serverIfCode = "81";
		
		/**
		 * 26# 服务点PIN 采集代码	n2 	00
		 */
		public static final String pinPickCode = "00";
		
		/**
		 * 32# 接受机构标识码（渠道号） 49914603
		 */
		public static final String acceptOrgCode = "49914603";
		
		/**
		 * 33# 由银联方提供的发送机构标识码 49914603
		 */
		public static final String tranOrgCode = "49914603";
		
		/**
		 * 41# 由银联方提供的受卡机终端标识码（终端号） 02057079
		 */
//		public static final String termId = "02057079";
		public static final String termId = "05116483";
		
		/**
		 * 42# 受卡方标识码（商户号）	ans15 103440149580023
		 */
//		public static final String cardAccptrId = "103440149580023";
		public static final String cardAccptrId = "103440149580030";
		/**
		 * 账单类型	T5
		 */
		public static final String BillType = "T6";
//		public static final String BillType = "T5";
		
		/**
		 * 43# 受卡方名称地址	ans40 CHN440300zhongguoguangdongshenshenzhensh
		 */
		public static final String cardAccptrAddr = "CHN440300zhongguoguangdongshenshenzhensh";
		
		/**
		 * 49# 交易货币代码	an3 156
		 */
		public static final String currcyCode = "156";
		
		/**
		 * 60.1# 交易类型码	n2 0000
		 */
		public static final String tradeCode = "0000";
		
		/**
		 * 70# 默认值   固定为161与商户签到保持一致（001为天津本地使用）
		 */
		public static final String netManageCode = "161";
		/**
		 * mackey计算主密钥  3016745AB289EFCDBADCFE0325476981
		 */
		public static final String secretKey = "CD509871AFC03894CD509871AFC03894";//正式主密钥
//		public static final String secretKey = "3016745AB289EFCDBADCFE0325476981";
//		public static final String secretKey = "1234567890ABCDEF1234567890ABCDEF";
//	——————————————————————————————————————————————————————————————
//	以上为银联相关配置信息
//————————————————————————————————————————————以下为移动相关信息hrdt
		/**
		 * 移动代理商号码
		 */
		public static String CMPaySUPhone = "  13417169406";
		/**
		 * 移动缴费标准代码
		 */
		public static String CMPayPay = "";
		/**
		 * 移动冲正标准代码
		 */
		public static String CMPayUndoPay = "";
		/**
		 * 移动对账标准代码
		 */
		public static String CMPayCheck = "";
		/**
		 * 移动业务IP
		 */
		public static String CMPayIP = "218.204.254.138";//正式
//		public static String CMPayIP = "218.204.254.138";//测试
		/**
		 * 移动业务端口
		 */
		public static int CMPayPORT = 12009;//正式
//		public static int CMPayPORT = 12010;//测试
		/**
		 * 移动登陆密码
		 */
//		public static String CMPaySignPWD = "hr0577";//测试
		public static String CMPaySignPWD = "hr0755";//正式
//		public static String CMPaySignPWD = "";//正式
		/**
		 * 移动业务密码
		 */
		public static String CMPayPayPWD = "";
		/**
		 * 移动业务密码
		 */
		public static int CMPayBeginSeq = 0;
		/**
		 * 移动业务类型
		 */
		public static String CMPayBusinessType = "YDCZ";
//		————————————————————————————————————————————以上为移动相关信息	
		
//		————————————————————————————————————————————以下为农信社相关信息		
		/**
		 * 农信社业务IP
		 */
		public static String BCCIP = "218.204.254.138";
		/**
		 * 农信社端口
		 */
		public static int BCCPORT = 12009;
		
		/**
		 * 天作接口IP
		 */
//		public static String TZIP = "58.248.254.69";//测试环境
		public static String TZIP = "58.248.254.68";//生产环境	
		/**
		 * 天作端口
		 */ 
//		public static int TZPORT=9123;//测试环境 
		public static int TZPORT=9234;//生产环境
		/**
		 * 天作ftp密码
		 */
		public static String TZFTP_PWD="nK&3a0c(";
		
		/**
		 * TZ响应编码
		 */
		public static String TZRESPONSE_SUCCESS = "00000";
		/**
		 * 操作码 合同充值 000004
		 */
		public static String TZ_CODE_CONTACT = "000011";
		/**
		 * 易票联查询
		 */
		public static String YPL_QUERY_SERNO="05003";
		/**
		 * 易票联返销
		 */
		public static String YPL_REVERT_SERNO="06013";
		/**
		 *易票联充值
		 */
		public static String YPL_FILL_SERNO="01013";
		
     	public static String YKH_USERNO="2013112700000067";//正式;
		public static String YKH_SIGNKEY="4520B55D5A9D09E43DA50ED8F0768BFB";
//		public static String RETURN_BAK_URL="http://localhost:80/wh/business/bank.do?method=xzReturn";
		public static String RETURN_BAK_URL="http://www.wanhuipay.com/business/bank.do?method=xzReturn";
}
