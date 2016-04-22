package com.wlt.webm.business.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.wlt.webm.business.bean.SysPhoneArea;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.util.PageAttribute;


/**
 * �����������<br>
 */
public class SysPhoneAreaAction extends DispatchAction
{
	
	/**
	 * ���������б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPhoneAreaForm srForm = (SysPhoneAreaForm) form;
		SysPhoneArea spa = new SysPhoneArea();
		PageAttribute page = new PageAttribute(srForm.getCurPage(),Constant.PAGE_SIZE);
		page.setRsCount(spa.countPhoneArea());
		List list = spa.listPhoneArea(page);
		request.setAttribute("page",page); 
	    request.setAttribute("itypeList",list);
		return mapping.findForward("success");
	}
	/**
	 * ��Ӻ�������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPhoneAreaForm srForm = (SysPhoneAreaForm) form;
		SysPhoneArea sit= new SysPhoneArea();
		request.setAttribute("mess",sit.add(srForm)==0?"��ӳɹ�":"���ʧ��");
		return new ActionForward("/business/wltphoneareaadd.jsp");
	}
	
	
	/**
	 * ɾ����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPhoneAreaForm srForm = (SysPhoneAreaForm) form;
		SysPhoneArea sit = new SysPhoneArea();
		request.setAttribute("mess",sit.del(srForm)==true?"ɾ���ɹ�":"ɾ��ʧ��");
		return new ActionForward("/business/wltphoneareaadd.jsp");
	}
	 /**
     * ���º�������
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward update(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SysPhoneAreaForm sForm = (SysPhoneAreaForm) form;
        SysPhoneArea sit =new SysPhoneArea();
        sit.update(sForm);
        return mapping.findForward("addsuccess");
    }
    /**
     * �����������
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward importFile(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SysPhoneAreaForm sForm = (SysPhoneAreaForm) form;
    	FormFile file = sForm.getFile();
    	String path=request.getSession().getServletContext().getRealPath("/upload"); 
    	String newName=System.currentTimeMillis()+".txt";
    	if (file.getFileSize() > 0) {   
 		   try {
 			   this.copyFile(file.getInputStream(),path, newName);
 			} catch (IOException e) {
 				e.printStackTrace();
 			}       
 		}
        return mapping.findForward("addsuccess");
    }
    /**  
	   * д�ļ������� ����� 
	   * @param in  
	   * @param fileName  
     * @throws Exception 
	   */  
	  private String copyFile(InputStream in,String path,String fileName) throws Exception{
	      	FileOutputStream fs = new FileOutputStream(path+File.separator+fileName);   
	        byte[] buffer = new byte[1024 * 1024];   
	        int bytesum = 0;   
	        int byteread = 0;   
	        while ((byteread = in.read(buffer)) != -1) {   
	            bytesum += byteread;   
	            fs.write(buffer, 0, byteread);   
	            fs.flush();   
	        }   
	        fs.close();   
	        in.close();
	        BufferedReader br=new BufferedReader(new FileReader(path+File.separator+fileName));
	        BufferedWriter bw=new BufferedWriter(new FileWriter(path+File.separator+"check"+fileName));
	        String line="";
	        String[] numArray=null;
	        SysPhoneArea spa = new SysPhoneArea();
	        while((line=br.readLine())!=null){
	        	SysPhoneAreaForm spaf = new SysPhoneAreaForm();
	        	//1868203,�㶫,����,��ͨ
	        	numArray=line.split("\\,");
	        	if(numArray.length!=4){
	        		bw.write(line+"  ��ʽ����1");
	        		bw.newLine();
	        		continue;
	        	}
	        	String num = numArray[0];
	        	String prov = numArray[1];
	        	String city=numArray[2];
	        	String cardType = numArray[3];
	        	int phoneNum=0;
	        	try{
	        		phoneNum=Integer.parseInt(num);
	        	}catch(Exception e){
	        		bw.write(line+"  ��ʽ����2");
	        		bw.newLine();
	        		continue;
	        	}
	        	if(phoneNum<1000000||phoneNum>9999999){
	        		bw.write(line+"  �Ŷ�ӦΪ7λ");
	        		bw.newLine();
	        		continue;
	        	}
	    		List list = spa.listPhoneAreaByPhone(num);
	    		if(list!=null&&list.size()>0){
	    			bw.write(line+"  �Ŷ��Ѵ���");
	    			bw.newLine();
	    			continue;
	    		}
	    		int provinceCode = spa.getProvinceByName(prov);
	    		if(0 == provinceCode){
	    			bw.write(line+"  δ֪ʡ��");
	    			bw.newLine();
	    			continue;
	    		}
	    		if(cardType.contains("�ƶ�")){
	    			spaf.setPhone_type(1);
	    		}else if(cardType.contains("��ͨ")){
	    			spaf.setPhone_type(2);
	    		}else if(cardType.contains("����")){
	    			spaf.setPhone_type(0);
	    		}else {
	    			spaf.setPhone_type(0);
				}
	    		spaf.setPhone_num(num);
	    		spaf.setProvince_code(provinceCode);
	    		spaf.setCity_name(city);
	    		spa.add(spaf);
	    		bw.write(line+"  �ɹ�");
	    		bw.newLine();
	        }
	        br.close();
	        bw.flush();
	        bw.close();
	        return null;
	  }  
}