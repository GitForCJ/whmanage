package com.wlt.webm.pccommon;

import java.sql.SQLException;
import java.util.List;

import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.scpdb.DBService;


/**
 * XML������<br>
 */
public class XMLBean {
	
    /**
     * ��ȡ����ҵ������
     * @param dbName ���ݿ�
     * @param operation 1 ���� 2 �˿� 3 ����
     * @return ����ҵ�������б�
     * @throws Exception
     */
    public List getTradeBackServiceList(String dbName, String operation) throws Exception
    {
    	DBService db = null;
    	List list = null;
    	try{
    		
    		String sql = "";
        	if(dbName.equals(Constants.DBNAME_SCP)){
        		db = new DBService(Constants.DBNAME_SCP);
        		if(operation.equals("1")){
        			sql = "select a.code,a.name from ec_service a,ec_returnservice b where a.code=b.service  and b.type=1";	
        		}else if(operation.equals("2")){
        			sql = "select a.code,a.name from ec_service a,ec_returnservice b where a.code=b.service  and b.type=2";	
        		}else if(operation.equals("3")){
        			sql = "select a.code,a.name from ec_service a,ec_returnservice b where a.code=b.service  and b.type=3";	
        		}else{
        			sql = "select code,name from ec_service";	
        		}
        		
        	}else {
        		db = new DBService(Constants.DBNAME_SMP);
        		if(operation.equals("1")){
        			sql = "select a.code,a.name from em_service a,em_returnservice b where a.code=b.service  and b.type=1";	
        		}else if(operation.equals("2")){
        			sql = "select a.code,a.name from em_service a,em_returnservice b where a.code=b.service  and b.type=2";	
        		}else if(operation.equals("3")){
        			sql = "select a.code,a.name from em_service a,em_returnservice b where a.code=b.service  and b.type=3";	
        		}else{
        			sql = "select code,name from em_service";	
        		}
        	}	
        	list = db.getList(sql);
    	}catch(Exception e){
    		throw e;
    	}finally{
    		db.close();
    	}
    	return list;  
    }
    
    /**
     * ���ҵ��������Ϣ�б�
     * @param num 1 ��ȡ���е�ҵ������
     * 			   2 ��ȡ�̻���G���ɷ�ҵ������
     * 			   3 ��ȡ�̻���G���ɷѡ������ۿ�ҵ������
     *             4 ��ȡ�̻���G���ɷѡ������ۿ�����������֧��ҵ������
     *             5 ��ȡ�̻���G���ɷѡ�����ɷ�ҵ������
     * @return List(String[])ҵ�����ͱ�ź������б�
     * @throws SQLException
     * 01001 ���Žɷ�
		03002 �����ۿ�
		03004 ����Ϸ��
		05001 �������
		05002 �ƶ�ҵ�����

     */
    public List getServiceList(int num) throws SQLException
    {
    	if(num==1){
    		return DBToolSCP.getList("select code,name from ec_service order by code");
    	}else if(num==2){
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','03002') order by code");
    	}else if(num==3){
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','03002','03004') order by code");
    	}else if(num == 5){
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','05001','05002') order by code");
    	}else if(num == 10){
    		return DBToolSCP.getList("select code,name from ec_service where code like '010%' order by code");
    	}else if(num == 30){
    		return DBToolSCP.getList("select code,name from ec_service where code like '030%' order by code");
    	}else if(num == 50){
    		return DBToolSCP.getList("select code,name from ec_service where code like '050%' order by code");
    	}else {
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','03002','03004','05001','05002') order by code");
    	}
    }
    
    /**
     * ��ѯ���׷�ʽ�б�
     * @return List(String[])���׷�ʽ�б�
     * @throws SQLException
     */
    public List tradeTypeList() throws SQLException
    {
        String sql = "select id,name from ec_tradetype order by id";
        return DBToolSCP.getList(sql.toString());
    }
    /**
     * ������õ��˷ѷ�����ҵ�������б�
     * @return List(String[])���õ��˷ѷ�����ҵ�������б�
     * @throws SQLException
     */
    public List getReturnBackServiceList() throws SQLException
    {
        return DBToolSCP.getList("select code,name from ec_service where code in(select service from ec_returnservice)");
    }
}
