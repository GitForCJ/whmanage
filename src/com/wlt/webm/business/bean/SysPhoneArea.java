package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.util.List;

import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.util.PageAttribute;


/**
 * 号码区域管理
 */
public class SysPhoneArea
{
	/**
	 * 获取号码区域列表 
	 * @return 号码区域列表 
	 * @throws Exception
	 */
	public List listPhoneArea(PageAttribute page) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.id,a.phone_num,b.sa_name,a.cart_type,c.name")
			.append(" from sys_phone_area a,sys_area b,sys_operators c where a.province_code = b.sa_id")
			.append(" and a.phone_type=c.code")
			.append(" order by a.id desc")
			.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	public int countPhoneArea() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)")
			.append(" from sys_phone_area a,sys_area b where a.province_code = b.sa_id")
			.append(" order by a.id");
			return dbService.getInt(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 获取号码区域列表 (根据号码)
	 * @return 号码区域列表 
	 * @throws Exception
	 */
	public List listPhoneAreaByPhone(String pnum) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select id")
			.append(" from sys_phone_area where phone_num = '"+pnum+"'");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 获取区域列表 
	 * @return 区域列表 
	 * @throws Exception
	 */
	public List listArea() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select sa_id,sa_name ")
			.append(" from sys_area")
			.append(" where sa_pid = 1");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 添加号码区域
	 * @param paForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int add(SysPhoneAreaForm paForm) throws Exception {
		DBService dbService = new DBService();
		String cart_type="";
		int phonetype=paForm.getPhone_type();
		try {
//			if(phonetype==0){
//				cart_type="电信";
//			}else if(phonetype==1){
//				cart_type="移动";
//			}else {
//				cart_type="联通";
//			}
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_phone_area(phone_num,province_code,cart_type,phone_type) values(" +
					"'"+paForm.getPhone_num()+"','"+paForm.getProvince_code()+"','"
					+paForm.getCity_name()+"','"+phonetype+"')");
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
          return 1;
        } finally {
        	dbService.close();
        }
	}
	
	
	/**
	 * 删除号码区域
	 * @param paForm 
	 * @return bool
	 * @throws Exception
	 */
	public boolean del(SysPhoneAreaForm paForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("delete from sys_phone_area where id='"+paForm.getIds()[0]+"';");
			dbService.update(sql1.toString());
			return true;
        } catch (Exception ex) {
        	ex.printStackTrace();
           return false;
        } finally {
        	dbService.close();
        }
	}
	 /**
	 * 获取号码区域(单个)
	 * @return 面额信息
	 * @throws Exception
	 */
	public SysPhoneAreaForm getPhoneAreaInfo(String paId) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysPhoneAreaForm paForm = new SysPhoneAreaForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select  a.id,a.phone_num,a.province_code,a.city_name,a.cart_type,a.area_code,a.phone_type ")
                    .append(" from sys_phone_area a ")
                    .append(" where a.id=? ");

            String[] params = { paId };
            String[] fields = { "id","phone_num", "province_code","city_name","cart_type","area_code","phone_type"};
            
            db.populate(paForm, fields, sql.toString(), params);

            return paForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 获取号码区域(单个)
	 * @return 面额信息
	 * @throws Exception
	 */
	public SysPhoneAreaForm getPhoneAreaByPhone(String phone) throws Exception {
        DBService db = new DBService();
        SysPhoneAreaForm paForm = new SysPhoneAreaForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select  a.id,a.phone_num,a.province_code,a.city_name,a.cart_type,a.area_code,a.phone_type,b.sa_name ")
                    .append(" from sys_phone_area a ,sys_area b")
                    .append(" where a.province_code = b.sa_id and a.phone_num=? ");

            String[] params = { phone};
            String[] fields = { "id","phone_num", "province_code","city_name","cart_type","area_code","phone_type","province_name"};
            
            db.populate(paForm, fields, sql.toString(), params);

            return paForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
     * 更新号码区域
     * @param paForm 号码区域
     * @return int
     * @throws SQLException
     */
    public int update(SysPhoneAreaForm paForm) throws SQLException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("update sys_phone_area set");
        sql.append(" phone_num='"+paForm.getPhone_num()+"'");
        sql.append(" ,province_code='"+paForm.getProvince_code()+"'");
        sql.append(" ,city_name='"+paForm.getCity_name()+"'");
        sql.append(" ,cart_type='"+paForm.getCart_type()+"'");
        sql.append(" ,area_code='"+paForm.getArea_code()+"'");
        sql.append(" ,phone_type='"+paForm.getPhone_type()+"'");
        sql.append(" where");
        sql.append(" id='"+paForm.getId()+"'");
        return DBToolSCP.update(sql.toString());
    }
    /**
     * 获取区域id
     * @param pname 
     * @return int
     * @throws Exception 
     */
    public int getProvinceByName(String pname) throws Exception
    {
    	DBService db = new DBService();
    	try {
    		StringBuffer sql = new StringBuffer();
    		sql.append("select sa_id from sys_area where sa_pid = 1 and sa_flag = 1 and sa_name like '%"+pname+"%'");
    		return db.getInt(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	db.close();
        }
    }
}

