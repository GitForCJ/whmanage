package com.ejet.common.struts.bean;

import com.ejet.common.struts.form.CountForm;
import com.ejet.util.Pagination;
import com.wlt.webm.rights.form.SysUserForm;

/**
 * ͳ�Ʊ���ĳ����࣬�����ṩ�˶Ա���ͳ�ƽӿڵ�Ĭ��ʵ�֣�<br>
 * 1��ʵ����<code>setForm(CountForm)</code>��<code>setUser(UserForm)</code>������<br>
 * 2��ʵ���˷�ҳ���ܡ�<br>
 * ���������������ѡ��ʵ�֡�<br>
 * Company��������###EJET###���޹�˾
 * Copyright: Copyright (c) 2009
 * @author ###ejet###
 * �޸����ڣ�2009-9-28
 * @version V2.1.2.0
 */
public abstract class Counter implements Count
{
    /**
     * ��ѯ��
     */
    protected CountForm form;

    /**
     * ��ѯ�û�
     */
    protected SysUserForm user;

    /**
     * ��ҳ����
     */
    protected Pagination pagination;

    /**
     * �������־
     */
    protected String excelState;
    /**
     * ���ò�ѯ������
     * @param form ��ѯ������
     * @see Count#setCountForm(CountForm)
     */
    public void setCountForm(CountForm form)
    {
        this.form = form;
        this.pagination = new Pagination(form.getPageIndex());
    }

    /**
     * ���ò�ѯ�û�
     * @param user �û���Ϣ
     * @see Count#setUserForm(UserForm)
     */
    public void setUserForm(SysUserForm user)
    {
        this.user = user;
    }

    /**
     * ��ʼ���������÷�������<code>setCountForm()</code>��<code>setUserForm()</code>֮�󱻵��ã�
     * ��������������֮ǰ���á���ˣ������ڴ˷����ڳ�ʼ���û���Ҫ����Դ��
     * @see Count#init()
     */
    public void init() throws Exception
    {
        
    }

    /**
     * �����ʾ��Ϣ��
     * @return ��ʾ��Ϣ��û����ʾ��Ϣ�򷵻� null��
     * @throws Exception 
     */
    public String getAlertMessage() throws Exception
    {
        return null;
    }

    /**
     * ��ñ������
     * @return �������
     * @see Count#getTitle()
     */
    public String getTitle() throws Exception
    {
        return null;
    }

    /**
     * ��ñ�����ͷ���⡣
     * @return ��ͷ����
     * @see Count#getColTitles()
     */
    public String[][] getColTitles() throws Exception
    {
        return null;
    }

    /**
     * ��ñ���չʾ�����ݡ�
     * @return 1���б�List(String[])��<br>
     *          2���кϲ�һ�е����ݣ�Map(List(String[]))��<br>
     *          3���кϲ����е����ݣ�Map(Map(List(String[])))��
     * @see Count#getBodyData()
     */
    public Object getBodyData() throws Exception
    {
        return null;
    }

    /**
     * ������ݿ��ҳ��Ϣ
     * @return ���ݿ��ҳ��Ϣ
     * @see Count#getPagination()
     */
    public Pagination getPagination() throws Exception
    {
        return pagination.finished() ? pagination : null;
    }

    /**
     * ��õ���EXCEL����������
     * @return EXCELÿһ�е�Ԫ�����������
     * @see Count#getExcelDataType()
     */
    public int[] getExcelDataType() throws Exception
    {
        return null;
    }

    /**
     * ��ñ���ע��Ϣ
     * @return ����ע��Ϣ
     * @see Count#getRemarks()
     */
    public String[] getRemarks()
    {
        return null;
    }
    /**
     * ���õ������־
     * @param excelState
     */
	public void setExcelState(String excelState) {
		this.excelState = excelState;
	}

}
