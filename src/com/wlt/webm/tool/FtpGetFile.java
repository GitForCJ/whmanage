package com.wlt.webm.tool;

import java.io.File;
import java.io.FileOutputStream;
import com.commsoft.epay.util.logging.Log;
import com.enterprisedt.net.ftp.*;
/**
 * <p>Description: FTPȡ�ļ���</p>
 */
public class FtpGetFile{
	
    /**
     *ftp������ַ
     */
    private String ftpRemote;
    
    /**
     *ftp�û���
     */
    private String ftpUser;
    
    /**
     *ftp����
     */
    private String ftpPassword;
    
    /**
     * ���캯��
     * @param ftpRemote String ftp������ַ
     * @param ftpUser String ftp�û���
     * @param ftpPassword String  ftp����
     */
    public FtpGetFile(String ftpRemote,String ftpUser,String ftpPassword) {
        this.ftpRemote = ftpRemote;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
    }

    /**
     * �������ܣ�ftp�ļ�
     * @param ftpGetFile String �����ص��ļ���
     * @param saveFile String ������ļ���
     * @param fileHead String �ļ���һ��
     * @param fileEnd String �ļ����һ��
     * @return boolean �����Ƿ�ɹ�
     */
    public boolean getFile(String ftpGetFile,String saveFile,String fileHead,String fileEnd) {
        boolean success = false;

        try {                  	
	            FTPClient ftpClient = new FTPClient();
	            //����Զ��IP
	            ftpClient.setRemoteHost(ftpRemote);
	            //����
	            ftpClient.connect();
	            //�û������������
	            ftpClient.login(ftpUser, ftpPassword);
	            //�������ӷ�ʽ
	            ftpClient.setConnectMode(FTPConnectMode.PASV);
	            //��������
	            ftpClient.setType(FTPTransferType.BINARY);
	            //���ó�ʱʱ��
	            ftpClient.setTimeout(3*60);
	            
	            byte[] fileMes = ftpClient.get(ftpGetFile);
	            //ftp�˳�
	            ftpClient.quit();
	            String temp = saveFile+"doing";
			    File tfile = new File(temp);
			    
			    //�����������ļ���д
	            FileOutputStream os = new FileOutputStream(tfile); 
	            //д���ļ���һ��
	            os.write(fileHead.getBytes());
	            os.write("\n".getBytes());
	            os.write(fileMes);//д�������ļ�����
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
     * �������ܣ������ļ�
     * @param ftpGetFile String �����ص��ļ���
     * @param saveFile String ������ļ���
     * @param fileHead String �ļ���һ��
     * @return boolean ���ͳɹ����
     */
    public boolean getFile(String ftpGetFile,String saveFile) {
        boolean success = false;

        try {                  	
        	FTPClient ftpClient = new FTPClient();
            //����Զ��IP
            ftpClient.setRemoteHost(ftpRemote);
            //����
            ftpClient.connect();
            //�û������������
            ftpClient.login(ftpUser, ftpPassword);
            //�������ӷ�ʽ
            ftpClient.setConnectMode(FTPConnectMode.PASV);
            //��������
            ftpClient.setType(FTPTransferType.BINARY);
            //���ó�ʱʱ��
            ftpClient.setTimeout(3*60);
            
            String temp = saveFile+"doing";
            ftpClient.get(temp,ftpGetFile);
		    File tfile = new File(temp);
		    File sfile = new File(saveFile);
		    tfile.renameTo(sfile);
            //ftp�˳�
            ftpClient.quit();
            success=true;
            Log.info("FTP�ļ��ɹ���Զ���ļ�:"+ftpGetFile+",�����ļ�:"+saveFile);
        } catch (Exception ex) {
        	ex.printStackTrace();
            success=false;
            Log.error(ex);
        }      
        
        return success;
    }
    
    /**
     * �������ܣ�����ftp������ַ
     * @param ftpRemote ftp������ַ
     */
    public void setFtpRemote(String ftpRemote) {
        this.ftpRemote = ftpRemote;
    }
    /**
     * �������ܣ�����ftp�û���
     * @param ftpUser ftp�û���
     */
    public void setFtpUser(String ftpUser){
        this.ftpUser=ftpUser;
    }
    /**
     * �������ܣ�����ftp����
     * @param ftpPassword ftp����
     */
    public void setFtpPassword(String ftpPassword){
        this.ftpPassword=ftpPassword;
    }
    
}

