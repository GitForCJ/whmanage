package com.wlt.webm.rights.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.rights.form.SysNoticeForm;
import com.wlt.webm.tool.Tools;



/**
 * 公告管理
 */
public class SysNotice
{
	/**
	 * @throws Exception
	 */
	public List getSysNoticeList() throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		List list=null;
		try {
			sql.append("select a.an_id,a.an_title,a.an_faceid,a.an_activedate,a.an_deaddate,a.if_active,b.sa_name,a.contentType  ");
			sql.append(" from sys_annotice a left join sys_area b");
			sql.append(" on b.sa_id = a.an_faceid ORDER BY an_activedate DESC");
			list = dbService.getList(sql.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[5] && !"".equals(temp[5])){
					if("0".equals(temp[5])){
						temp[5] = "无效";
					}else if("1".equals(temp[5])){
						temp[5] = "有效";
					}
				}
				if(null != temp[2] && !"".equals(temp[2])){
					if("0".equals(temp[2])){
						temp[6] = "所有";
					}
				}
				if(null!=temp[7] && !"".equals(temp[7]))
				{
					if("1".equals(temp[7]))
						temp[7]="最新公告";
					else
						temp[7]="最新活动";
				}
				if(temp[1].length()>12){
					temp[1] = temp[1].substring(0,12)+"...";
				}else{
					temp[1] = temp[1];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		
		return list;
	}
	/**
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public List getSysNoticeListLatest() throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		String date=Tools.getNowDate();

		List list=null;
		try {
			sql.append("select a.an_id,a.an_title,a.an_faceid,a.an_activedate,a.an_deaddate,a.if_active,b.sa_name,a.an_content,a.contentType,9 ");
			sql.append(" from sys_annotice a left join sys_area b");
			sql.append(" on b.sa_id = a.an_faceid WHERE an_deaddate>='"+date+"' order by a.an_activedate desc ");
			list = dbService.getList(sql.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[5] && !"".equals(temp[5])){
					if("0".equals(temp[5])){
						temp[5] = "无效";
					}else if("1".equals(temp[5])){
						temp[5] = "有效";
					}
				}
				if(null != temp[2] && !"".equals(temp[2])){
					if("0".equals(temp[2])){
						temp[6] = "所有";
					}
				}
				if(temp[1].length()>19){
					temp[1] = temp[1].substring(0,18)+"...";
				}else{
					temp[1] = temp[1].substring(0,temp[1].length())+"...";
				}
				
				
				
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
				String a=temp[3].replace("-","");
				Calendar cal = Calendar.getInstance();
		    	cal.add(Calendar.DATE,-7);
		    	Date end=cal.getTime();
		    	Date ss=sf.parse(a);
		    	 
		    	if(ss.before(end))
					temp[9]=1+"".trim();
				else
					temp[9]=0+"".trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return list;
	}
	/**
	 * 添加公告
	 * @throws Exception
	 */
	public void add(SysNoticeForm snf) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();

		try {
			sql.append("insert into sys_annotice(an_title,an_content,an_type,an_faceid,an_createtime,an_activedate,an_deaddate,if_active,contentType) " +
					"values('"+snf.getAn_title()+"','"+snf.getAn_content()+"','"+snf.getAn_type()+"','"+snf.getAn_faceid()+"'," +
							"'"+snf.getAn_createtime()+"','"+snf.getAn_activedate()+"','"+snf.getAn_deaddate()+"','"+snf.getIf_active()+"',"+snf.getContentType()+")");
			dbService.update(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
	}
	 /**
	 * 获取公告信息(单个)
	 * @return 公告信息
	 * @throws Exception
	 */
	public SysNoticeForm getNoticeInfo(String id) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysNoticeForm noticeForm = new SysNoticeForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.an_id, a.an_title,a.an_content,a.an_type,a.an_faceid,a.an_createtime,a.an_activedate,a.an_deaddate,a.if_active,a.contentType ")
                    .append(" from sys_annotice a ")
                    .append(" where a.an_id=? ");

            String[] params = { id };
            String[] fields = { "an_id","an_title", "an_content" ,"an_type","an_faceid","an_createtime","an_activedate","an_deaddate","if_active","contentType"};
            
            db.populate(noticeForm, fields, sql.toString(), params);

            return noticeForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	/**
	 * 修改公告  
	 * @throws Exception
	 */
	public void update(SysNoticeForm snf) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		
		try {
			sql.append("update sys_annotice set an_title='"+snf.getAn_title()+"',an_content='"+snf.getAn_content()+"',an_type='"+snf.getAn_type()+"',an_faceid='"+snf.getAn_faceid()+
					"',an_createtime='"+snf.getAn_createtime()+"',an_activedate='"+snf.getAn_activedate()+"',an_deaddate='"+snf.getAn_deaddate()+"',if_active='"+snf.getIf_active()+"' " +
					" ,contentType="+snf.getContentType()+" where an_id='"+snf.getAn_id()+"';");
			dbService.update(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
	}
	
}

