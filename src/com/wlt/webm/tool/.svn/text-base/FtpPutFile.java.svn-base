package com.wlt.webm.tool;

import com.commsoft.epay.util.logging.Log;
import com.enterprisedt.net.ftp.*;
/**
 * <p>Description: FTP上传文件类</p>
 */
public class FtpPutFile {
	
    /**
     *ftp主机地址
     */
    private String ftpRemote;
    
    /**
     *ftp用户名
     */
    private String ftpUser;
    
    /**
     *ftp密码
     */
    private String ftpPassword;
    
    /**
     * 文件路径
     */
    private String ftpPath;

    /**
     * 构造函数
     * @param ftpRemote String ftp主机地址
     * @param ftpUser String ftp用户名
     * @param ftpPassword String  ftp密码
     * @param ftpPath ftp路径
     */
    public FtpPutFile(String ftpRemote,String ftpUser,String ftpPassword,String ftpPath) {
        this.ftpRemote = ftpRemote;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
        this.ftpPath = ftpPath;
    }

    /**
     * 函数功能：ftp文件
     * @param ftpFileName String 待ftp的文件路径和文件名
     * @param tofile String ftp计费中心的文件名
     * @param fileResult String ftp成功后的文件名
     * @return boolean 传送成功与否
     */
    public boolean putFile(String ftpFileName,String tofile,String fileResult) {
        boolean success = false;

        try {   
        	Log.info(ftpRemote + "|" + ftpUser + "|" + ftpPassword + "|" + ftpPath);
        	Log.info("FTP上传对账文件:"+ftpFileName+"|"+fileResult);
            FTPClient ftpClient = new FTPClient();
            //设置远端IP
            ftpClient.setRemoteHost(ftpRemote);
            //连接
            ftpClient.connect();
            //用户名，密码登入
            ftpClient.login(ftpUser, ftpPassword);
            //设置连接方式
            ftpClient.setConnectMode(FTPConnectMode.PASV);
            //设置类型
            ftpClient.setType(FTPTransferType.BINARY);
            //设置ftp路径,如果ftp路径不为空或者不为Null
            if((!ftpPath.equals("")) && ftpPath != null){
            	ftpClient.chdir(ftpPath);
            }
            boolean fileExist = false;
 		    try{
 		    	ftpClient.get(fileResult);
 		    	
 		    	Log.info(" ----  log -----");
 		    	
 		    	fileExist = true;
 		    }catch(Exception e)
 		    {
 		    	 Log.error(e);
 		    }
 		   if(fileExist)
 		    {
// 			   System.out.println("远程文件已存在:" + fileResult);
 			    Log.info("远程文件已存在:" + fileResult);
 		    	return true;
 		    }
            //传送文件          
            ftpClient.put(ftpFileName, tofile);
            Log.info("本地文件：" + ftpFileName + "传送过去的文件名:" + tofile);
            
            //如果传送的名字和待重命名的名字不同，则重命名，否则不重命名
            if(!tofile.equals(fileResult)){
                //修改名字
                ftpClient.rename(tofile,fileResult);
                Log.debug(tofile + "改名：" + fileResult + "成功");
            }else{
            	Log.debug("不用重命名传过去的文件");
            }

            ftpClient.quit();           
            success=true;
           
        } catch (Exception ex) {
        	ex.printStackTrace();
            success=false;
            Log.error(ex);
        }      
        
        return success;
    }

    /**
     * 函数功能：设置ftp主机地址
     * @param ftpRemote ftp主机地址
     */
    public void setFtpRemote(String ftpRemote) {
        this.ftpRemote = ftpRemote;
    }
    /**
     * 函数功能：设置ftp用户名
     * @param ftpUser ftp用户名
     */
    public void setFtpUser(String ftpUser){
        this.ftpUser=ftpUser;
    }
    /**
     * 函数功能：设置ftp密码
     * @param ftpPassword ftp密码
     */
    public void setFtpPassword(String ftpPassword){
        this.ftpPassword=ftpPassword;
    }

}
