package com.wlt.webm.business.bean.unicom3gquery.xml;

public class RequestMsgHeader {
	String Version;
	String TimeStamp;
	String TransId;
	String RandomValue;
	String AppId;
	String ApiName;
	String Sign;
	String Format;

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

	public String getTransId() {
		return TransId;
	}

	public void setTransId(String transId) {
		TransId = transId;
	}

	public String getRandomValue() {
		return RandomValue;
	}

	public void setRandomValue(String randomValue) {
		RandomValue = randomValue;
	}

	public String getAppId() {
		return AppId;
	}

	public void setAppId(String appId) {
		AppId = appId;
	}

	public String getApiName() {
		return ApiName;
	}

	public void setApiName(String apiName) {
		ApiName = apiName;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}
