package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 后台任务日志信息
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author Ejet.Shen 
 */
public class TaskForm extends ActionForm
{
    /**
     * 页数
     */
    private int pageIndex;

    /**
     * 编号组
     */
    private String[] ids;
    /**
     * 编号
     */
    private String id;
    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务类别
     */
    private String type;

    /**
     * 任务执行级别
     */
    private String grade;

    /**
     * 任务应执行起始日期
     */
    private String startdate;

    /**
     * 任务应执行终止日期
     */
    private String enddate;

    /**
     * 日志状态
     */
    private String state;

    /**
     * 是否可重执行
     */
    private String reexec;
    
	/**
     * @return 是否可重执行
     */
    public String getReexec()
    {
        return reexec;
    }

    /**
     * @param reexec 是否可重执行
     */
    public void setReexec(String reexec)
    {
        this.reexec = reexec;
    }

    /**
	 * @return 任务应执行终止日期 
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate 任务应执行终止日期 
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @return 任务执行级别 
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade 任务执行级别 
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return 编号 
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 编号 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 编号组 
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids 编号组 
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * @return 任务名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 任务名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 页数
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex 页数
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return 任务应执行起始日期
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate 任务应执行起始日期
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return 日志状态
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state 日志状态
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return 任务类别 
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type 任务类别 
	 */
	public void setType(String type) {
		this.type = type;
	}

  
}
