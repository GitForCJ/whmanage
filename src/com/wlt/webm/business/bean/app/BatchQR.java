package com.wlt.webm.business.bean.app;

import java.sql.PreparedStatement;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.DES;

public class BatchQR {
	
	public static String WINDOWS_DIR="C:\\picture\\";
	public static String LINUX_DIR="/opt/data/QR/";
	public static String LOGONAME="logo.png";
	public static String URLPATH="http://192.168.1.117:9009/wh/business/QR.jsp?&id=";
	
	public boolean batchProductQR(int sum){
		if(sum<=0||sum>1000){
			return false;
		}
		String imgedir=null;
		String os=System.getProperty("os.name");
		if(os.indexOf("Window")!=-1){
			imgedir=WINDOWS_DIR;
		}else{
			imgedir=LINUX_DIR;
		}
		boolean flag=false;
		DBService db=null;
		PreparedStatement st=null;
		int error=0;
		try{
			DES des=new DES();
			des.setKey("wh#@!a59");
			db=new DBService();
			int n=db.getInt("SELECT MAX(url_id) FROM wht_app_url");
		    st=db.getConn().prepareStatement("insert into wht_app_url(url_id,url,imgedir,imgename,imgedirname) values(?,?,?,?,?)");
			db.getConn().setAutoCommit(false);
		    for(int i=1;i<=sum;i++){
				n=n+i;
				if(QRCodeUtil.encode(URLPATH+n, imgedir+"logo.png", imgedir+"QRcard"+n+".png", true)){
				st.setInt(1,n);
				st.setString(2, URLPATH+des.decrypt(n+""));
				st.setString(3, imgedir);
				st.setString(4, "QRcard"+n+".png");
				st.setString(5, imgedir+"QRcard"+n+".png");
				st.addBatch();
				if((n+1)%50==0){
					st.executeBatch();
					st.clearBatch();
				}
				}else{
					error++;
				}
			}
			st.executeBatch();
			db.getConn().commit();
			flag=true;
			Log.info("生成智能名片二维码失败张数:"+error);
		}catch (Exception e) {
			db.rollback();
			Log.error("生成智能名片二维码出错:"+e.toString());
		}finally{
			if(null!=st){
//				st.close();
				st=null;
			}
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		BatchQR qr=new BatchQR();
		qr.batchProductQR(1);
	}

}
