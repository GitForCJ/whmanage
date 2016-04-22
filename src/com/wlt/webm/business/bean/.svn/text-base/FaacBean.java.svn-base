package com.wlt.webm.business.bean;

import com.wlt.webm.business.form.ChildFaactForm;
import com.wlt.webm.db.DBService;


/**
 * 资金账户管理
 */
public class FaacBean
{
	/**
	 * 获取资金账户(单个)
	 * @return cfForm
	 * @throws Exception
	 */
	public ChildFaactForm getUserFundAcct(String userid,String acctType) throws Exception {
        DBService db = new DBService();
        ChildFaactForm cfForm = new ChildFaactForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.accttypeid,a.usableleft,a.state,a.childfacct")
                    .append(" from wlt_childfacct a ")
                    .append(" where a.accttypeid = '"+acctType+"' and a.fundacct=(select fundacct from wlt_facct where termid = ?) ");

            String[] params = { userid };
            String[] fields = { "accttypeid","usableleft","state","childfacct"};
            
            db.populate(cfForm, fields, sql.toString(), params);

            return cfForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
}

