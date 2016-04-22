package chinapay.model.bean;

/**
 * @author caiSJ
 * Flowlistdes加密
 */

public class TransactionBean {
	private String MerId; // 商户号，15位
	private String OrdNo; // 订单号，16位
	private String TransAmt; // 订单金额（单位分），12位
	private String CuryId; // 币种，3位
	private String TransDate; // 订单日期yyyyMMdd,8位
	private String TransType; // 交易类型，4位
	private String Version; // 版本号，8位
	private String GateId; // 网关编号，4位
	private String Priv1; // 私有域，不超过60字节
	private String ChkValue; // 交易请求签名，256字节
	private String TransStatus; // 代扣状态，4位
	private String OpenBankId; // 开户行号，4位
	private String CardType; // 卡折标志，1位
	private String CardNo; // 卡号/折号，25位
	private String UserName; // 持卡人姓名，20位
	private String CertType; // 证件类型，2位
	private String CertId; // 证件号，25位
	private String purpose; // 用途，20位
	private String ResponseCode; // 交易应答码，2位
	private String Message; // 返回信息，unicode码

	// 白名单增加字段
	private String Mobile; // 手机号码，变长40位
	private String Address; // 住址，变长512位
	private String Email; // 邮箱，变长40位

	public String toString() {
		return new StringBuffer("{merId=").append(MerId).append(", transType=")
				.append(TransType).append(", orderNo=").append(OrdNo).append(
						", transDate=").append(TransDate).append(", version=")
				.append(Version).append(", priv1=").append(Priv1).append(
						", chkValue=").append(ChkValue).append("}").toString();
	}

	public String getOrdNo() {
		return OrdNo;
	}

	public void setOrdNo(String ordNo) {
		OrdNo = ordNo;
	}

	public String getChkValue() {
		return ChkValue;
	}

	public void setChkValue(String chkValue) {
		ChkValue = chkValue;
	}

	public String getTransStatus() {
		return TransStatus;
	}

	public void setTransStatus(String transStatus) {
		TransStatus = transStatus;
	}

	public String getOpenBankId() {
		return OpenBankId;
	}

	public void setOpenBankId(String openBankId) {
		OpenBankId = openBankId;
	}

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getCertType() {
		return CertType;
	}

	public void setCertType(String certType) {
		CertType = certType;
	}

	public String getCertId() {
		return CertId;
	}

	public void setCertId(String certId) {
		CertId = certId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setMerId(String merId) {
		MerId = merId;
	}

	public String getMerId() {
		return MerId;
	}

	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}

	public String getTransAmt() {
		return TransAmt;
	}

	public void setCuryId(String curyId) {
		CuryId = curyId;
	}

	public String getCuryId() {
		return CuryId;
	}

	public void setTransDate(String transDate) {
		TransDate = transDate;
	}

	public String getTransDate() {
		return TransDate;
	}

	public void setTransType(String transType) {
		TransType = transType;
	}

	public String getTransType() {
		return TransType;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getVersion() {
		return Version;
	}

	public void setGateId(String gateId) {
		GateId = gateId;
	}

	public String getGateId() {
		return GateId;
	}

	public void setPriv1(String priv1) {
		Priv1 = priv1;
	}

	public String getPriv1() {
		return Priv1;
	}

	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}

	public String getResponseCode() {
		return ResponseCode;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getMessage() {
		return Message;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}
