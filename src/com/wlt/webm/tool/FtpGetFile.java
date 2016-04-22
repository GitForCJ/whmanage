package com.wlt.webm.tool;

import java.io.File;
import java.io.FileOutputStream;
import com.commsoft.epay.util.logging.Log;
import com.enterprisedt.net.ftp.*;
/**
 * <p>Description: FTP取文件类</p>
 */
public class FtpGetFile{
	
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
     * 构造函数
     * @param ftpRemote String ftp主机地址
     * @param ftpUser String ftp用户名
     * @param ftpPassword String  ftp密码
     */
    public FtpGetFile(String ftpRemote,String ftpUser,String ftpPassword) {
        this.ftpRemote = ftpRemote;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
    }

    /**
     * 函数功能：ftp文件
     * @param ftpGetFile String 需下载的文件名
     * @param saveFile String 保存的文件名
     * @param fileHead String 文件第一行
     * @param fileEnd String 文件最后一行
     * @return boolean 传送是否成功
     */
    public boolean getFile(String ftpGetFile,String saveFile,String fileHead,String fileEnd) {
        boolean success = false;

        try {                  	
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
	            //设置超时时间
	            ftpClient.setTimeout(3*60);
	            
	            byte[] fileMes = ftpClient.get(ftpGetFile);
	            //ftp退出
	            ftpClient.quit();
	            String temp = saveFile+"doing";
			    File tfile = new File(temp);
			    
			    //输入流进行文件书写
	            FileOutputStream os = new FileOutputStream(tfile); 
	            //写入文件第一行
	            os.write(fileHead.getBytes());
	            os.write("\n".getBytes());
	            os.write(fileMes);//写入下载文件内容
	            os.flush();
	            os.close();
	            
			    File sfile = new File(saveFile);
			    tfile.renameTo(sfile);
	            success=true;
           
        } catch (Exception ex) {
        	ex.printStackTrace();
            success=false;
            Log.error(ex);
        }      
        
        return success;
    }

    /**
     * 函数功能：下载文件
     * @param ftpGetFile String 需下载的文件名
     * @param saveFile String 保存的文件名
     * @param fileHead String 文件第一行
     * @return boolean 传送成功与否
     */
    public boolean getFile(String ftpGetFile,String saveFile) {
        boolean success = false;

        try {                  	
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
            //设置超时时间
            ftpClient.setTimeout(3*60);
            
            String temp = saveFile+"doing";
            ftpClient.get(temp,ftpGetFile);
		    File tfile = new File(temp);
		    File sfile = new File(saveFile);
		    tfile.renameTo(sfile);
            //ftp退出
            ftpClient.quit();
            success=true;
            Log.info("FTP文件成功，远端文件:"+ftpGetFile+",本地文件:"+saveFile);
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

