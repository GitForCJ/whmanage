/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the fit License, Version 1.0 (the "License");
 */
package com.wlt.webm.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @description:分页标签类
 * @author:King 2009/07/28
 * @version:1.0
 * @modify:
 */
public class PageTag extends BodyTagSupport {
	private static final long serialVersionUID = -6250075601936086320L;
	//日志
	private Log log = LogFactory.getLog(this.getClass());
	//自定义标签的url属性
	private String url;
	//自定义标签的page属性，page属于是一个PageAttribute对象，请保存在request中
	private PageAttribute page;
	//自定义标签的样式,每种样式对应一个风格，同时也对应本类中的一个方法(增加新的样式时请增加相应的css以及脚本文件,如果有的情况下)
	private String style;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPage(PageAttribute page) {
		this.page = page;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspTagException {
		//根据不同的style定位到不同的方法,(可以继续扩展)
		StringBuffer buf = new StringBuffer();
		if("green".equals(style)){
			//绿色样式
			getGreenPageinfo(buf);
		}else if("blue".equals(style)){
			//绿色样式
			getBluePageinfo(buf);
		}else if("red".equals(style)){
			getRedPageinfo(buf);
		}else if("black".equals(style)){
			//getBlackPageinfo(buf);
			getBluePageinfo(buf);
		}
		JspWriter out = pageContext.getOut();
		try {
			out.print(buf.toString());
		} catch (IOException e) {
			log.error("输出标签信息失败：" + e.getMessage());
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	/**
	 * 绿色样式
	 * 此样式风格：页面显示十个图片按钮，分别从第一页到第十页，如果大于十页，刚有下十页按钮，如果小于十页,则没有，另外还有一个跳转按钮,当前页绿色标记
	 * @param buf
	 */
	public void getGreenPageinfo(StringBuffer buf){
		try{
			//取得request
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			//取得当前页
			int curPage = page.getCurPage();
			String path =request.getContextPath();
			//每页显示的页数
			int pageCountView = 10;
			
			//判断url中是否有?符号
			if(url.indexOf("?")!=-1){
				 if(null!=page.getParams()&&!"".equals(page.getParams())){
					 url = path+"/"+url +page.getParams()+ "&curPage=";
				 }else{
					 url = path+"/"+url + "&curPage=";
				 }
			}else{
				if(null!=page.getParams()&&!"".equals(page.getParams())){
					url = path+"/"+url +"?"+ page.getParams()+"curPage=";
				}else{
					url = path+"/"+url +"?"+"curPage=";
				}
			}
			
			buf.append("<div class=\"page_body\">\n");
			if(curPage==1){
				buf.append("<a href=\"javascript:void(0)\" class=\"page_home\" title=\"最前页\"></a>\n");
				buf.append("<a href=\"javascript:void(0)\" class=\"page_forward\" title=\"上一页\">上一页</a>\n");
			}else{
				buf.append("<a name=\"pageHref\" href=\"" + url + "1&"+"\" class=\"page_home\" title=\"最前页\"></a>\n");
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getProPage() + "\" class=\"page_forward\" title=\"上一页\">上一页</a>\n");
			}
			if(curPage>pageCountView && curPage!=1){
				//只有当curPage大于pageCountView,并且curPage!=1时才有前十页按钮
				buf.append("<a name=\"pageHref\" href=\"" + url + (curPage-pageCountView)+ "\" title=\"前十页\">...</a>\n");
			}
			//得到新的起始页
			int start = (curPage/pageCountView)*pageCountView;
			if(curPage%pageCountView==0){
				start = (curPage/pageCountView-1)*pageCountView;
			}else{
				start = (curPage/pageCountView)*pageCountView;
			}
			//得到新的终止页
			int end = 0;
			if(page.getPageCount()-start<10){
				end = page.getPageCount();
			}else{
				end = start + pageCountView;
			}
			for(int i=start+1;i<=end;i++){
				if(curPage==i){
					buf.append("<span class=\"on\" style=\"font-weight: bold\">" + i + "</span>\n");
				}else{
					buf.append("<a name=\"pageHref\" href=\"" + url + i + "\">" + i + "</a>\n");
				}
			}	
			if(page.getPageCount()-curPage>10){
				//只有当页数-当前页大于pageCountView，才有后十页按钮
				buf.append("<a name=\"pageHref\" href=\"" + url + (curPage + pageCountView)+  "\" title=\"后十页\">...</a>\n");
			}
			if(curPage==page.getPageCount()){
				buf.append("<a href=\"javascript:void(0);\" class=\"page_behind\" title=\"下一页\">下一页</a>\n");
				buf.append("<a href=\"javascript:void(0);\" class=\"page_end\" title=\"最后页\"></a>\n");
			}else{
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getNextPage() + "\" class=\"page_behind\" title=\"下一页\">下一页</a>\n");
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getPageCount()+ "\" class=\"page_end\" title=\"最后页\"></a>\n");
			}
			buf.append("<span>&nbsp;&nbsp;&nbsp;&nbsp;跳转:</span>\n");
			buf.append("<input type=\"text\" size=\"5\" name=\"skipCurPage\"  id=\"skipCurPage\" class=\"input\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\"/><label style=\"display:none\">" + url+ "</label>\n");
			buf.append("<span>页</span>\n");
			buf.append("<input type=\"button\" name=\"skipBtn\" id=\"skipBtn\" value=\"GO\" class=\"button_c\" />&nbsp;&nbsp;每页显示 " + page.getPageSize() + " 条信息，共 " + page.getRsCount() + " 条信息，当前 " + page.getCurPage() + "/" + page.getPageCount() + " 页\n");
			buf.append("</div>");
		}catch(Exception e){
			log.error("调用PageTag.getGreenPageinfo方法失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 绿色样式
	 * 此样式风格：页面显示十个图片按钮，分别从第一页到第十页，如果大于十页，刚有下十页按钮，如果小于十页,则没有，另外还有一个跳转按钮,当前页绿色标记
	 * @param buf
	 */
	public void getBlackPageinfo(StringBuffer buf){
		try{
			//取得request
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String path =request.getContextPath();
			//取得当前页
			int curPage = page.getCurPage();
			//每页显示的页数
			int pageCountView = 5;
			
			//判断url中是否有?符号
			if(url.indexOf("?")!=-1){
				 if(null!=page.getParams()&&!"".equals(page.getParams())){
					 url =path+ "/"+url +page.getParams()+ "&curPage=";
				 }else{
					 url =path+"/"+ url + "&curPage=";
				 }
			}else{
				if(null!=page.getParams()&&!"".equals(page.getParams())){
					url =path+"/"+ url +"?"+ page.getParams()+"curPage=";
				}else{
					url = path+"/"+url +"?curPage=";
				}
			}
			if(curPage==1){
				//buf.append("<a href=\"javascript:void(0)\"  title=\"最前页\"></a>\n");
				//buf.append("<a href=\"javascript:void(0)\"  title=\"上一页\">上一页</a>\n");
				//buf.append("<li><a class='hover' href=\"" + url + curPage +"\">1</a></li>");
			}else{
				//buf.append("<a  href=\"" + url + "1&"+"\"  title=\"最前页\"></a>\n");
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getProPage() + "\" >上一页</a>");
			}
			if(curPage>pageCountView && curPage!=1){
				//只有当curPage大于pageCountView,并且curPage!=1时才有前十页按钮
				buf.append("<a name=\"pageHref\" href=\"" + url + (curPage-5)+ "\" title=\"前五页\">...</a>");
			}
			//得到新的起始页
			int start = (curPage/pageCountView)*pageCountView;
			if(curPage%pageCountView==0){
				start = (curPage/pageCountView-1)*pageCountView;
			}else{
				start = (curPage/pageCountView)*pageCountView;
			}
			//得到新的终止页
			int end = 0;
			if(page.getPageCount()-start<5){
				end = page.getPageCount();
			}else{
				end = start + pageCountView;
			}
			for(int i=start+1;i<=end;i++){
				if(curPage==i){
					buf.append("<a  class='hover'   name=\"pageHref\" href=\"" + url + i+ "\">" + i + "</a>");
				}else{
					buf.append("<a   name=\"pageHref\" href=\"" + url + i + "\">" + i + "</a>");
				}
			}	
			if(curPage%5==0){
				//只有当页数-当前页大于pageCountView，才有后十页按钮
				 buf.append("<a name=\"pageHref\" href=\"" + url + (curPage + pageCountView)+ "\" title=\"后五页\">...</a>");
			}else if((curPage==(page.getPageCount()-5))||(curPage==(page.getPageCount()-4))||(curPage==(page.getPageCount()-6))||(curPage==(page.getPageCount()-7))){
				 buf.append("<a name=\"pageHref\" href=\"" + url + (curPage + pageCountView)+  "\" title=\"后五页\">...</a>");
			}else if(curPage<page.getPageCount()&&curPage<((page.getPageCount()%5)*5)+1){
				 buf.append("<a name=\"pageHref\" href=\"" + url + (curPage + pageCountView)+  "\" title=\"后五页\">...</a>");
			}
			
			if(curPage==page.getPageCount()){
				buf.append("<a  name=\"pageHref\" href=\"javascript:void(0);\"  title=\"下一页\">下一页</a>");
				//buf.append("<li><a href=\"javascript:void(0);\"  title=\"最后页\"></a></li>");
			}else{
				buf.append("<a name=\"pageHref\"  href=\"" + url + page.getNextPage()  +"\"  title=\"下一页\">下一页</a>");
				//buf.append("<li><a  href=\"" + url + page.getPageCount()+ "\"  title=\"最后页\"></a></li>");
			}
			
		}catch(Exception e){
			log.error("调用PageTag.getBlackPageinfo方法失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	//专门为汽车预定页面 显示不出完整的
	public void getRedPageinfo(StringBuffer buf){
		try{
			//取得request
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			//取得当前页
			int curPage = page.getCurPage();
			String path =request.getContextPath();
			//每页显示的页数
			int pageCountView = 6;
			
			//判断url中是否有?符号
			if(url.indexOf("?")!=-1){
				 if(null!=page.getParams()&&!"".equals(page.getParams())){
					 url = path+"/"+url +page.getParams()+ "&curPage=";
				 }else{
					 url = path+"/"+url + "&curPage=";
				 }
			}else{
				if(null!=page.getParams()&&!"".equals(page.getParams())){
					url = path+"/"+url +"?"+ page.getParams()+"curPage=";
				}else{
					url = path+"/"+url +"?"+"curPage=";
				}
			}
			
			buf.append("<div class=\"page_body\">\n");
			if(curPage==1){
				buf.append("<a href=\"javascript:void(0)\" class=\"page_home\" title=\"最前页\"></a>\n");
				buf.append("<a href=\"javascript:void(0)\" class=\"page_forward\" title=\"上一页\">上一页</a>\n");
			}else{
				buf.append("<a name=\"pageHref\" href=\"" + url + "1&"+"\" class=\"page_home\" title=\"最前页\"></a>\n");
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getProPage() + "\" class=\"page_forward\" title=\"上一页\">上一页</a>\n");
			}
			if(curPage>pageCountView && curPage!=1){
				//只有当curPage大于pageCountView,并且curPage!=1时才有前十页按钮
				buf.append("<a name=\"pageHref\" href=\"" + url + (curPage-pageCountView)+ "\" title=\"前六页\">...</a>\n");
			}
			//得到新的起始页
			int start = (curPage/pageCountView)*pageCountView;
			if(curPage%pageCountView==0){
				start = (curPage/pageCountView-1)*pageCountView;
			}else{
				start = (curPage/pageCountView)*pageCountView;
			}
			//得到新的终止页
			int end = 0;
			if(page.getPageCount()-start<6){
				end = page.getPageCount();
			}else{
				end = start + pageCountView;
			}
			for(int i=start+1;i<=end;i++){
				if(curPage==i){
					buf.append("<span class=\"on\">" + i + "</span>\n");
				}else{
					buf.append("<a name=\"pageHref\" href=\"" + url + i + "\">" + i + "</a>\n");
				}
			}	
			if(page.getPageCount()-curPage>6){
				//只有当页数-当前页大于pageCountView，才有后十页按钮
				buf.append("<a name=\"pageHref\" href=\"" + url + (curPage + pageCountView)+  "\" title=\"后六页\">...</a>\n");
			}
			if(curPage==page.getPageCount()){
				buf.append("<a href=\"javascript:void(0);\" class=\"page_behind\" title=\"下一页\">下一页</a>\n");
				buf.append("<a href=\"javascript:void(0);\" class=\"page_end\" title=\"最后页\"></a>\n");
			}else{
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getNextPage() + "\" class=\"page_behind\" title=\"下一页\">下一页</a>\n");
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getPageCount()+ "\" class=\"page_end\" title=\"最后页\"></a>\n");
			}
			buf.append("&nbsp;&nbsp;共 " + page.getRsCount() + " 条信息，当前 " + page.getCurPage() + "/" + page.getPageCount() + " 页\n");
			buf.append("</div>");
		}catch(Exception e){
			log.error("调用PageTag.getGreenPageinfo方法失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 蓝色样式
	 * 此样式风格：页面显示五个图片按钮，分别从第一页到第五页，如果大于五页，刚有下五页按钮，如果小于五页,则没有，另外还有一个跳转按钮,当前页绿色标记
	 * @param buf
	 */
	public void getBluePageinfo(StringBuffer buf){
		//取得request
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		try{
			//取得当前页
			int curPage = page.getCurPage();
			String path =request.getContextPath();
			//每页显示的页数
			int pageCountView = 5;
			//判断url中是否有?符号
			if(url.indexOf("?")!=-1){
				url = path+"/"+url + "&curPage=";
			}else{
				url = path+"/"+url + "?curPage=";
			}
			
			buf.append("<div class=\"page_body\">\n");
			if(curPage==1){
				//buf.append("<a href=\"javascript:void(0);\" class=\"page_home\" title=\"最前\">最前</a>\n");
				buf.append("<a href=\"javascript:void(0);\" class=\"page_forward\" title=\"前一页\">前一页</a>\n");
			}else{
				//buf.append("<a name=\"pageHref\" href=\"" + url + "1\" class=\"page_home\" title=\"最前\">最前</a>\n");
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getProPage() + "\" class=\"page_forward\" title=\"前一页\">前一页</a>\n");
			}
			if(curPage>pageCountView){
				//只有当curPage大于pageCountView时才有前五页按钮
				//buf.append("<a name=\"pageHref\" href=\"" + url + (curPage-pageCountView) + "\" title=\"前五页\">前五页</a>\n");
			}
			//得到新的起始页
			int start = (curPage/pageCountView)*pageCountView;
			if(curPage%pageCountView==0){
				start = (curPage/pageCountView-1)*pageCountView;
			}else{
				start = (curPage/pageCountView)*pageCountView;
			}
			//得到新的终止页
			int end = 0;
			if(page.getPageCount()-start<pageCountView){
				end = page.getPageCount();
			}else{
				end = start + pageCountView;
			}
			buf.append("<script>" +
					"function changePage(url){" +
					"var num=document.getElementById('curPage').value;" +
					"location.href=url+num}" +
					"</script>");
			for(int i=start+1;i<=end;i++){
				if(curPage==i){
					buf.append("<span class=\"on\">" + i + "</span>\n");
				}else{
					buf.append("<a name=\"pageHref\" href=\"" + url + i + "\">" + i + "</a>\n");
				}
			}	
			if(page.getPageCount()-curPage>0 && (page.getPageCount()/pageCountView!=curPage/pageCountView || curPage%pageCountView==0)){
				//只有当页数-当前页大于pageCountView，才有后五页按钮
				//buf.append("<a name=\"pageHref\" href=\"" + url + (curPage + pageCountView) + "\" title=\"后五页\">后五页</a>\n");
			}
			if(curPage==page.getPageCount()){
				buf.append("<a href=\"javascript:void(0);\" class=\"page_behind\" title=\"后一页\">后一页</a>\n");
				//buf.append("<a href=\"javascript:void(0);\" class=\"page_end\" title=\"最后页\">向后</a>\n");
			}else{
				buf.append("<a name=\"pageHref\" href=\"" + url + page.getNextPage() + "\" class=\"page_behind\" title=\"后一页\">后一页</a>\n");
				//buf.append("<a name=\"pageHref\" href=\"" + url + page.getPageCount() + "\" class=\"page_end\" title=\"最后页\">最后</a>\n");
			}
			buf.append("<span>&nbsp;&nbsp;跳转:</span>\n");
			buf.append("<input type=\"text\" name=\"curPage\" size=\"4\" id=\"curPage\" class=\"input\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\"/><label style=\"display:none\">" + url + "</label>\n");
			buf.append("<span>页</span>\n");
			buf.append("<input type=\"button\" name=\"skipBtn\" id=\"skipBtn\" value=\"GO\" class=\"page_button\" onclick=\"changePage('"+url+"')\" />&nbsp;&nbsp;每页 " + page.getPageSize() + " 条，共 " + page.getRsCount() + " 条," + page.getCurPage() + "/" + page.getPageCount() + " 页\n");
			buf.append("</div>");
		}catch(Exception e){
			log.error("调用PageTag.getBluePageinfo方法失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 蓝色样式(用于ajax)
	 * 此样式风格：页面显示五个图片按钮，分别从第一页到第五页，如果大于五页，刚有下五页按钮，如果小于五页,则没有，另外还有一个跳转按钮,当前页绿色标记
	 * @param page
	 */
	public String getBluePageinfoAjax(int curPage,int pageSize,int rsCount){
		StringBuilder buf = new StringBuilder();
		try{
			int pageCountView = 5;
			//定义分页变量
			PageAttribute page = new PageAttribute(curPage,pageSize);
			page.setRsCount(rsCount);
			
			curPage = page.getCurPage();
			
			buf.append("<div class=\"page_body\">\n");
			if(curPage==1){
				buf.append("<a curPage=\"1\" href=\"javascript:void(0);\" class=\"page_home\" title=\"最前页\">最前页</a>\n");
				buf.append("<a curPage=\"" + page.getProPage() + "\" href=\"javascript:void(0);\" class=\"page_forward\" title=\"前一页\">前一页</a>\n");
			}else{
				buf.append("<a name=\"pageHref\" curPage=\"1\" href=\"1\" class=\"page_home\" title=\"最前页\">最前页</a>\n");
				buf.append("<a name=\"pageHref\" curPage=\"" + page.getProPage() + "\" href=\"" + page.getProPage() + "\" class=\"page_forward\" title=\"前一页\">前一页</a>\n");
			}
			if(curPage>pageCountView){
				//只有当curPage大于pageCountView时才有前五页按钮
				buf.append("<a name=\"pageHref\" curPage=\"" + (curPage-pageCountView) + "\" href=\"" + (curPage-pageCountView) + "\" title=\"前五页\">前五页</a>\n");
			}
			//得到新的起始页
			int start = (curPage/pageCountView)*pageCountView;
			if(curPage%pageCountView==0){
				start = (curPage/pageCountView-1)*pageCountView;
			}else{
				start = (curPage/pageCountView)*pageCountView;
			}
			//得到新的终止页
			int end = 0;
			if(page.getPageCount()-start<pageCountView){
				end = page.getPageCount();
			}else{
				end = start + pageCountView;
			}
			for(int i=start+1;i<=end;i++){
				if(curPage==i){
					buf.append("<span class=\"on\">" + i + "</span>\n");
				}else{
					buf.append("<a name=\"pageHref\"  curPage=\"" + i + "\" href=\"" + i + "\">" + i + "</a>\n");
				}
			}	
			if(page.getPageCount()-curPage>0 && (page.getPageCount()/pageCountView!=curPage/pageCountView || curPage%pageCountView==0)){
				//只有当页数-当前页大于pageCountView，才有后五页按钮
				buf.append("<a name=\"pageHref\" curPage=\"" + (curPage + pageCountView) + "\" href=\"" + (curPage + pageCountView) + "\" title=\"后五页\">后五页</a>\n");
			}
			if(curPage==page.getPageCount()){
				buf.append("<a curPage=\"" + page.getNextPage() + "\" href=\"javascript:void(0);\" class=\"page_behind\" title=\"后一页\">后一页</a>\n");
				buf.append("<a curPage=\"" + page.getPageCount() + "\" href=\"javascript:void(0);\" class=\"page_end\" title=\"最后页\">最后页</a>\n");
			}else{
				buf.append("<a name=\"pageHref\" curPage=\"" + page.getNextPage() + "\" href=\"" + page.getNextPage() + "\" class=\"page_behind\" title=\"后一页\">后一页</a>\n");
				buf.append("<a name=\"pageHref\" curPage=\"" + page.getPageCount() + "\" href=\"" + page.getPageCount() + "\" class=\"page_end\" title=\"最后页\">最后页</a>\n");
			}
			buf.append("<span>&nbsp;&nbsp;&nbsp;&nbsp;跳转:</span>\n");
			buf.append("<input type=\"text\" name=\"curPage\" size=\"5\" id=\"curPage\" class=\"input\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\"/><label style=\"display:none\">url</label>\n");
			buf.append("<span>页</span>\n");
			buf.append("<input type=\"button\" name=\"skipBtn\" id=\"skipBtn\" value=\"GO\" class=\"page_button\" />&nbsp;&nbsp;每页显示 " + page.getPageSize() + " 条信息，共 " + page.getRsCount() + " 条信息，当前 " + page.getCurPage() + "/" + page.getPageCount() + " 页\n");
			buf.append("</div>");
		}catch(Exception e){
			log.error("调用PageTag.getBluePageinfoAjax方法失败:" + e.getMessage());
			e.printStackTrace();
		}
		return buf.toString();
	}
	
	/**
	 * 蓝色样式(用于ajax)
	 * 此样式风格：页面显示十个图片按钮，分别从第一页到第十页，如果大于十页，刚有下十页按钮，如果小于十页,则没有，另外还有一个跳转按钮,当前页绿色标记
	 * @param page
	 */
	public String getBluePageinfoAjax(int curPage,int pageSize,int rsCount,String pageType){
		StringBuilder buf = new StringBuilder();
		try{
			int pageCountView = 10;
			//定义分页变量
			PageAttribute page = new PageAttribute(curPage,pageSize);
			page.setRsCount(rsCount);
			
			curPage = page.getCurPage();
			
			buf.append("<div class=\"page_body\">\n");
			if(curPage==1){
				buf.append("<a curPage=\"1\" href=\"javascript:void(0);\" class=\"page_home\" title=\"最前页\"></a>\n");
				buf.append("<a curPage=\"" + page.getProPage() + "\" href=\"javascript:void(0);\" class=\"page_forward\" title=\"前一页\"></a>\n");
			}else{
				buf.append("<a name=\"page" + pageType + "Href\" curPage=\"1\" href=\"1\" class=\"page_home\" title=\"最前页\"></a>\n");
				buf.append("<a name=\"page" + pageType + "Href\" curPage=\"" + page.getProPage() + "\" href=\"" + page.getProPage() + "\" class=\"page_forward\" title=\"前一页\"></a>\n");
			}
			if(curPage>pageCountView){
				//只有当curPage大于pageCountView时才有前十页按钮
				buf.append("<a name=\"page" + pageType + "Href\" curPage=\"" + (curPage-pageCountView) + "\" href=\"" + (curPage-pageCountView) + "\" title=\"前十页\">...</a>\n");
			}
			//得到新的起始页
			int start = (curPage/pageCountView)*pageCountView;
			if(curPage%pageCountView==0){
				start = (curPage/pageCountView-1)*pageCountView;
			}else{
				start = (curPage/pageCountView)*pageCountView;
			}
			//得到新的终止页
			int end = 0;
			if(page.getPageCount()-start<pageCountView){
				end = page.getPageCount();
			}else{
				end = start + pageCountView;
			}
			for(int i=start+1;i<=end;i++){
				if(curPage==i){
					buf.append("<span class=\"on\">" + i + "</span>\n");
				}else{
					buf.append("<a name=\"page" + pageType + "Href\"  curPage=\"" + i + "\" href=\"" + i + "\">" + i + "</a>\n");
				}
			}	
			if(page.getPageCount()-curPage>0 && (page.getPageCount()/pageCountView!=curPage/pageCountView || curPage%pageCountView==0)){
				//只有当页数-当前页大于pageCountView，才有后十页按钮
				buf.append("<a name=\"page" + pageType + "Href\" curPage=\"" + (curPage + pageCountView) + "\" href=\"" + (curPage + pageCountView) + "\" title=\"后十页\">...</a>\n");
			}
			if(curPage==page.getPageCount()){
				buf.append("<a curPage=\"" + page.getNextPage() + "\" href=\"javascript:void(0);\" class=\"page_behind\" title=\"后一页\"></a>\n");
				buf.append("<a curPage=\"" + page.getPageCount() + "\" href=\"javascript:void(0);\" class=\"page_end\" title=\"最后页\"></a>\n");
			}else{
				buf.append("<a name=\"page" + pageType + "Href\" curPage=\"" + page.getNextPage() + "\" href=\"" + page.getNextPage() + "\" class=\"page_behind\" title=\"后一页\"></a>\n");
				buf.append("<a name=\"page" + pageType + "Href\" curPage=\"" + page.getPageCount() + "\" href=\"" + page.getPageCount() + "\" class=\"page_end\" title=\"最后页\"></a>\n");
			}
			buf.append("<span>&nbsp;&nbsp;&nbsp;&nbsp;跳转:</span>\n");
			buf.append("<input type=\"text\" size=\"5\" name=\"cur" + pageType + "Page\" id=\"cur" + pageType + "Page\" class=\"input\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\"/><label style=\"display:none\">url</label>\n");
			buf.append("<span>页</span>\n");
			buf.append("<input type=\"button\" name=\"skip" + pageType + "Btn\" id=\"skip" + pageType + "Btn\" value=\"GO\" class=\"page_button\" />&nbsp;&nbsp;每页显示 " + page.getPageSize() + " 条信息，共 " + page.getRsCount() + " 条信息，当前 " + page.getCurPage() + "/" + page.getPageCount() + " 页\n");
			buf.append("</div>");
		}catch(Exception e){
			log.error("调用PageTag.getBluePageinfoAjax方法失败:" + e.getMessage());
			e.printStackTrace();
		}
		return buf.toString();
	}
}
