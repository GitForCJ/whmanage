/*
 * Copyright 2011 the original author or authors.
 * 
 * Licensed under the fit License, Version 1.0 (the "License");
 */
package com.wlt.webm.util;

/**
 * @description:分页�?
 * @author:Tony 2011/01/20
 * @version:1.0
 * @modify:
 */
public class PageAttribute {
	//每页显示�?
	private int pageSize;
	//总记录数
	private int rsCount;
	//页数
	private int pageCount;
	//当前�?
	private int curPage;
	//下一�?
	private int nextPage;
	//上一�?
	private int proPage;
	//当前�?
	private int curRecored;
	//查询条件
	private String  params;
	
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getCurRecored() {
		return curRecored;
	}

	public void setCurRecored(int curRecored) {
		this.curRecored = curRecored;
	}

	public int getNextPage() {
		if(curPage==pageCount){
			//如果当前页在�?后一�?
			nextPage = pageCount;
		}else{
			//如果当前页不在第�?�?
			nextPage = curPage + 1;
		}
		return nextPage;
	}
	
	public int getProPage() {
		if(curPage==1){
			//如果当前页在第一�?
			proPage = 1;
		}else{
			proPage = curPage - 1;
		}
		return proPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRsCount() {
		return rsCount;
	}
	public void setRsCount(int rsCount) {
		//总记录数
		this.rsCount = rsCount;
		//页数
		if(rsCount%pageSize==0){
			pageCount = rsCount/pageSize;
			if(pageCount==0){
				pageCount = 1;
			}
		}else{
			pageCount = rsCount/pageSize + 1;
		}
		//�?测当前页
		if(curPage<=0){
			curPage = 1;
		}
		if(curPage>pageCount){
			curPage = pageCount;
		}
	}
	public int getPageCount() {
		return pageCount;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	public PageAttribute(int curPage,int pageSize){
		this.curPage = curPage;
		this.pageSize = pageSize;
	}

}
