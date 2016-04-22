package com.ejet.common.struts.bean;

import com.ejet.common.struts.form.CountForm;
import com.ejet.util.Pagination;
import com.wlt.webm.rights.form.SysUserForm;

/**
 * 统计报表的接口。<hr>
 * 系统自动调用顺序：<br>
 * 1、调用<code>setForm(CountForm)</code>和<code>setUser(UserForm)</code>方法；<br>
 * 2、调用<code>init()</code>方法；<br>
 * 3、除了上面两点外，其他方法调用顺序则不固定，实现这些方法时，之间不应该有关联调用关系。<br>
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * @author ###ejet###
 * 修改日期：2009-9-28
 * @version V2.1.2.0
 */
public interface Count
{
    /**
     * 设置查询条件表单
     * @param form 查询条件表单
     */
    public void setCountForm(CountForm form);

    /**
     * 设置查询用户
     * @param user 用户信息
     */
    public void setUserForm(SysUserForm user);

     /**
      *设置导报表标志
      * @param excelState
      */
    public void setExcelState(String excelState) ;
    /**
     * 初始化方法，该方法将在<code>setCountForm()</code>和<code>setUserForm()</code>之后被调用，
     * 在所有其他方法之前调用。因此，可以在此方法内初始化用户需要的资源。
     * @throws Exception 
     */
    public void init() throws Exception;

    /**
     * 获得提示信息。
     * @return 提示信息，没有提示信息则返回 null。
     * @throws Exception 
     */
    public String getAlertMessage() throws Exception;

    /**
     * 获得报表标题
     * @return 报表标题
     * @throws Exception 
     */
    public String getTitle() throws Exception;

    /**
     * 获得报表列头标题。<br>
     * <hr>
     * 1、如果是一行列头标题，则可以通过如下代码实现：
     * <code><pre>
     * String[][] colTitles = { { "列1", "列2", "列3" } };
     * return colTitles;
     * </pre></code>
     * <hr>
     * 2、如果是二行列头标题，则可以通过如下代码实现：
     * <code><pre>
     * String[][] colTitles = { { "行1列1", "行1列2", null, "行1列4" },
     *                          { null, "行2列2", "行2列3", "行2列4" } };
     * return colTitles;
     * </pre></code>
     * 将显示的列标题实际效果为
     * <table border="1">
     *   <tr align="center"><td rowspan="2">行1列1</td><td colspan="2">行1列2</td><td>行1列4</td></tr>
     *   <tr align="center"><td>行2列2</td><td>行2列3</td><td>行2列4</td></tr>
     * </table>
     * @return 列头标题
     * @throws Exception 
     */
    public String[][] getColTitles() throws Exception;

    /**
     * 获得报表展示的数据。
     * @return 1、列表，List(String[])；<br>
     *          2、有合并一列的数据，Map(List(String[]))；<br>
     *          3、有合并两列的数据，Map(Map(List(String[])))。
     * @throws Exception 
     */
    public Object getBodyData() throws Exception;

    /**
     * 获得数据库分页信息
     * @return 数据库分页信息
     * @throws Exception 
     */
    public Pagination getPagination() throws Exception;

    /**
     * 获得导出EXCEL的数据类型
     * @return EXCEL每一列单元格的数据类型
     * @throws Exception 
     */
    public int[] getExcelDataType() throws Exception;

    /**
     * 获得报表备注信息
     * @return 报表备注信息
     */
    public String[] getRemarks();
}
