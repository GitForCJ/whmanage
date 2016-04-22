package com.wlt.webm.pccommon.struts.bean;

import com.wlt.webm.pccommon.struts.form.CountForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.Pagination;
public abstract class Counter implements Count
{
    /**
     * 查询表单
     */
    protected CountForm form;

    /**
     * 查询用户
     */
    protected SysUserForm user;

    /**
     * 分页对象
     */
    protected Pagination pagination;

    /**
     * 设置查询条件表单
     * @param form 查询条件表单
     * @see Count#setCountForm(CountForm)
     */
    public void setCountForm(CountForm form)
    {
        this.form = form;
        this.pagination = new Pagination(form.getPageIndex());
    }

    /**
     * 设置查询用户
     * @param user 用户信息
     * @see Count#setUserForm(UserForm)
     */
    public void setUserForm(SysUserForm user)
    {
        this.user = user;
    }

    /**
     * 初始化方法，该方法将在<code>setCountForm()</code>和<code>setUserForm()</code>之后被调用，
     * 在所有其他方法之前调用。因此，可以在此方法内初始化用户需要的资源。
     * @see Count#init()
     */
    public void init() throws Exception
    {
        
    }

    /**
     * 获得提示信息。
     * @return 提示信息，没有提示信息则返回 null。
     * @throws Exception 
     */
    public String getAlertMessage() throws Exception
    {
        return null;
    }

    /**
     * 获得报表标题
     * @return 报表标题
     * @see Count#getTitle()
     */
    public String getTitle() throws Exception
    {
        return null;
    }

    /**
     * 获得报表列头标题。
     * @return 列头标题
     * @see Count#getColTitles()
     */
    public String[][] getColTitles() throws Exception
    {
        return null;
    }

    /**
     * 获得报表展示的数据。
     * @return 1、列表，List(String[])；<br>
     *          2、有合并一列的数据，Map(List(String[]))；<br>
     *          3、有合并两列的数据，Map(Map(List(String[])))。
     * @see Count#getBodyData()
     */
    public Object getBodyData() throws Exception
    {
        return null;
    }

    /**
     * 获得数据库分页信息
     * @return 数据库分页信息
     * @see Count#getPagination()
     */
    public Pagination getPagination() throws Exception
    {
        return pagination.finished() ? pagination : null;
    }

    /**
     * 获得导出EXCEL的数据类型
     * @return EXCEL每一列单元格的数据类型
     * @see Count#getExcelDataType()
     */
    public int[] getExcelDataType() throws Exception
    {
        return null;
    }

    /**
     * 获得报表备注信息
     * @return 报表备注信息
     * @see Count#getRemarks()
     */
    public String[] getRemarks()
    {
        return null;
    }

}
