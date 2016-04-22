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
 * 号码区域管理<br>
 */
public class SysPhoneAreaAction extends DispatchAction
{
	
	/**
	 * 号码区域列表
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
	 * 添加号码区域
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
		request.setAttribute("mess",sit.add(srForm)==0?"添加成功":"添加失败");
		return new ActionForward("/business/wltphoneareaadd.jsp");
	}
	
	
	/**
	 * 删除号码区域
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
		request.setAttribute("mess",sit.del(srForm)==true?"删除成功":"删除失败");
		return new ActionForward("/business/wltphoneareaadd.jsp");
	}
	 /**
     * 更新号码区域
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
     * 导入号码区域
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
	   * 写文件到本地 并入库 
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
	        	//1868203,广东,深圳,联通
	        	numArray=line.split("\\,");
	        	if(numArray.length!=4){
	        		bw.write(line+"  格式错误1");
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
	        		bw.write(line+"  格式错误2");
	        		bw.newLine();
	        		continue;
	        	}
	        	if(phoneNum<1000000||phoneNum>9999999){
	        		bw.write(line+"  号段应为7位");
	        		bw.newLine();
	        		continue;
	        	}
	    		List list = spa.listPhoneAreaByPhone(num);
	    		if(list!=null&&list.size()>0){
	    			bw.write(line+"  号段已存在");
	    			bw.newLine();
	    			continue;
	    		}
	    		int provinceCode = spa.getProvinceByName(prov);
	    		if(0 == provinceCode){
	    			bw.write(line+"  未知省份");
	    			bw.newLine();
	    			continue;
	    		}
	    		if(cardType.contains("移动")){
	    			spaf.setPhone_type(1);
	    		}else if(cardType.contains("联通")){
	    			spaf.setPhone_type(2);
	    		}else if(cardType.contains("电信")){
	    			spaf.setPhone_type(0);
	    		}else {
	    			spaf.setPhone_type(0);
				}
	    		spaf.setPhone_num(num);
	    		spaf.setProvince_code(provinceCode);
	    		spaf.setCity_name(city);
	    		spa.add(spaf);
	    		bw.write(line+"  成功");
	    		bw.newLine();
	        }
	        br.close();
	        bw.flush();
	        bw.close();
	        return null;
	  }  
}