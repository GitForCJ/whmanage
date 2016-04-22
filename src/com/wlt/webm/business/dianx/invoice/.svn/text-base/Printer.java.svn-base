package com.wlt.webm.business.dianx.invoice;

import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

import javax.print.attribute.HashPrintRequestAttributeSet;

/**
 * 获取所有打印信息并执行打印操作<br>
 * company 深圳市万恒科技有限公司<br>
 * copyright Copyright (c) 2009<br>
 * @author 鹿振
 * 修改人：宁博
 * 修改日期：2010-03-31
 * version 3.0.0.0
 */
public class Printer implements Printable
{
	/**
	 * 判断是打收据明细位置，0表示发票
	 */
	  private int flagpoint = 0;
	  
	  /**
	 * 用户名称
	 */
	private String userName = "";
	  /**
	 * 用户名称X坐标
	 */
	  private int userNamePositionX = 0;
	  /**
	 * 用户名称Y坐标
	 */
	private int userNamePositionY = 0;
	
	 /**
	 * 用户电话
	 */
	private String phone = "";
	  /**
	 * 用户电话X坐标
	 */
	  private int phonePositionX = 0;
	  /**
	 * 用户电话Y坐标
	 */
	private int phonePositionY = 0;
	 /**
	 * 交易类型
	 */
	private String tradetype = "";
	  /**
	 * 交易类型X坐标
	 */	
	  private int tradetypePositionX = 0;
	  /**
	 * 交易类型Y坐标
	 */
	private int tradetypePositionY = 0;
	 /**
	 * 终端
	 */
	private String terminal = "";
	  /**
	 * 终端X坐标
	 */	
	  private int terminalPositionX = 0;
	  /**
	 * 终端Y坐标
	 */
	private int terminalPositionY = 0;
	
	 /**
	 * 交易时间
	 */
	private String tradetime = "";
	  /**
	 * 交易时间X坐标
	 */	
	  private int tradetimePositionX = 0;
	  /**
	 * 交易时间Y坐标
	 */
	private int tradetimePositionY = 0;
	
	 /**
	 * 交易流水号
	 */
	private String tradeNo = "";
	  /**
	 * 交易流水号X坐标
	 */	
	  private int tradeNoPositionX = 0;
	  /**
	 * 交易流水号Y坐标
	 */
	private int tradeNoPositionY = 0;
	
	 /**
	 * 计费周期
	 */
	private String payDate = "";
	  /**
	 * 计费周期X坐标
	 */	
	  private int payDatePositionX = 0;
	  /**
	 * 计费周期Y坐标
	 */
	private int payDatePositionY = 0;
	
	 /**
	 * 本月应收
	 */
	private String total = "";
	  /**
	 * 本月应收X坐标
	 */	
	  private int totalPositionX = 0;
	  /**
	 * 本月应收Y坐标
	 */
	private int totalPositionY = 0;
	
	 /**
	 * 本次实收金额
	 */
	private String fee = "";
	  /**
	 * 本次实收金额X坐标
	 */	
	  private int feePositionX = 0;
	  /**
	 * 本次实收金额Y坐标
	 */
	private int feePositionY = 0;
    /**
     * configItem用来保存配置文件打印项名称
     */
    public static HashMap configItem = new HashMap();

    /**
     * jsp页面传来需要打印的信息
     */
    private HashMap printItem = new HashMap();

    /**
     * 缴费明细个数
     */
    private int detailNumber = 0;

    /**
     * 明细起始x坐标
     */

    private int detailXcoor = 90;

    /**
     * 明细起始y坐标
     */
    private int detailYcoor = 160;

    /**
     * 打印时明细名称和值之间的间隔
     */
    private int xAsc = 90;

    /**
     * 打印明细时，上下两条明细之间的间隔
     */
    private int yAsc = 7;

    /**
     * 明细信息
     */
    private String[] detail = null;

    /**
     * 打印
     */
    private HashPrintRequestAttributeSet hashPrintRequestAttributeSet = null;

	/**
	 * @param str 用户电话
	 */
	public void setPhone(String str) {
		phone = str;
	}

    /**
     * 设置用户电话
     * @param x int
     * @param y int
     */
    public void setPhonePosition(int x, int y) {
    	phonePositionX = x;
    	phonePositionY= y;
    }
	/**
     * @param str 用户名
     */
    public void setUserName(String str) {
      userName = str;
    }
    /**
     * 设置用户姓名
     * @param x int
     * @param y int
     */
    public void setUserNamePosition(int x, int y) {
      userNamePositionX = x;
      userNamePositionY = y;
    }
	/**
     * @param str 用户名
     */
    public void settradetype(String str) {
    	tradetype = str;
    }
    /**
     * 设置用户姓名
     * @param x int
     * @param y int
     */
    public void settradetypePosition(int x, int y) {
    	tradetypePositionX = x;
    	tradetypePositionY = y;
    }
	/**
     * @param str 终端
     */
    public void setterminal(String str) {
    	terminal = str;
    }
    /**
     * 设置终端坐标
     * @param x int
     * @param y int
     */
    public void setterminalPosition(int x, int y) {
    	terminalPositionX = x;
    	terminalPositionY = y;
    }
	/**
     * @param str 交易时间
     */
    public void settradetime(String str) {
    	tradetime = str;
    }
    /**
     * 设置交易时间坐标
     * @param x int
     * @param y int
     */
    public void settradetimePosition(int x, int y) {
    	tradetimePositionX = x;
    	tradetimePositionY = y;
    }
    
	/**
     * @param str 交易流水号
     */
    public void settradeNo(String str) {
    	tradeNo = str;
    }
    /**
     * 设置交易流水号坐标
     * @param x int
     * @param y int
     */
    public void settradeNoPosition(int x, int y) {
    	tradeNoPositionX = x;
    	tradeNoPositionY = y;
    }
    
	/**
     * @param str 计费周期
     */
    public void setpayDate(String str) {
    	payDate = str;
    }
    /**
     * 设置计费周期坐标
     * @param x int
     * @param y int
     */
    public void setpayDatePosition(int x, int y) {
    	payDatePositionX = x;
    	payDatePositionY = y;
    }
	
	/**
     * @param str 本月应收
     */
    public void settotal(String str) {
    	total = str;
    }
    /**
     * 设置本月应收坐标
     * @param x int
     * @param y int
     */
    public void settotalPosition(int x, int y) {
    	totalPositionX = x;
    	totalPositionY = y;
    }
    
	/**
     * @param str 本次实收金额
     */
    public void setfee(String str) {
    	fee = str;
    }
    /**
     * 设置本次实收金额坐标
     * @param x int
     * @param y int
     */
    public void setfeePosition(int x, int y) {
    	feePositionX = x;
    	feePositionY = y;
    }
	/**
     * 构造函数
     * @param hashPrintRequestAttributeSet HashPrintRequestAttributeSet
     */
    public Printer(HashPrintRequestAttributeSet hashPrintRequestAttributeSet)
    {

        this.hashPrintRequestAttributeSet = hashPrintRequestAttributeSet;
    }

    /**
     * 设置打印项
     * @param printItem 打印项
     * @param jspValue 值
     */
    public void setPrintItem(String printItem, String jspValue)
    {
        this.printItem.put(printItem, jspValue);
    }

    /**
     * 设置明细个数
     * @param detailNum 明细个数
     */
    public void setDetailNumber(int detailNum)
    {
        detailNumber = detailNum;
    }

    /**
     * 设置打印坐标
     * @param detailX X坐标
     * @param detailY Y坐标
     */
    public void setDetailCoor(int detailX, int detailY)
    {
        this.detailXcoor = detailX;
        this.detailYcoor = detailY;

    }

    /**
     * 设置明细信息
     * @param detail 明细信息
     */
    public void setDetail(String[] detail)
    {
        this.detail = detail;
    }
    
  
    /**
     * 打印信息
     * @param g 图形类
     * @param pf 页面格式化类
     * @param pageIndex 页数
     * @return 打印结果
     */
    public int print(Graphics g, PageFormat pf, int pageIndex)
    {
        /* 位置2 */
        Graphics2D g2d = (Graphics2D) g;
        // 只打一页
        if (pageIndex > 0)
        {
            return Printable.NO_SUCH_PAGE;
        }
        // 设置字体
        Font font = new Font("宋体", Font.PLAIN, 10);
        Font font1 = new Font("黑体", Font.PLAIN, 14);
        g2d.setFont(font);
        g2d.translate(pf.getImageableX(), pf.getImageableY());
                
        if(flagpoint !=0 )
        { 
            //打印用户名称
        	if (tradetype.length() != 0) {
                g2d.drawString(tradetype, tradetypePositionX, tradetypePositionY);
              }
            if (terminal.length() != 0) {
                  g2d.drawString(terminal, terminalPositionX, terminalPositionY);
              }
            if (userName.length() != 0) {
                g2d.drawString(userName, userNamePositionX, userNamePositionY);
            }
            if (phone.length() != 0) {
                g2d.drawString(phone, phonePositionX, phonePositionY);
              }
            if (tradetime.length() != 0) {
                g2d.drawString(tradetime, tradetimePositionX, tradetimePositionY);
              }
            if (tradeNo.length() != 0) {
                g2d.drawString(tradeNo, tradeNoPositionX,tradeNoPositionY);
              }
            if (payDate.length() != 0) {
                g2d.drawString(payDate,payDatePositionX,payDatePositionY);
              }
            
            
        	int i=0;
        	int j=detail.length;
        	for(;i<j;i=i+2){
        		String mes = detail[i]+""+detail[i+1];
        		if (mes.length()!=0) {
//        			if(mes.length()>10){
//    	    	        g2d.drawString(mes.substring(0, 10), 15, flagpoint+i*6);
//    	    			i=i+2;
//    	    			j=j+2;
//    	    			g2d.drawString(mes.substring(10), 15, flagpoint+i*6);
//        			}else{
        				if(detail[i].length()!=0){
        				   g2d.drawString(mes, 15, flagpoint+i*10);
        				}
//        			}
        	      }
       	}
            if (total.length() != 0) {
            	i=i+1;
                g2d.drawString(total,totalPositionX,flagpoint+(i)*10);
              } 	
            if (fee.length() != 0) {
            	i=i+1;
                g2d.drawString(fee,feePositionX,flagpoint+(i)*10);
              } 
        }else{
//        	 迭代jsp页面传来的打印项，在configItem中去找
            Iterator iterator = printItem.keySet().iterator();
            while (iterator.hasNext())
            {
                String keyItem = (String) (iterator.next());
                if (configItem.get(keyItem) != null)
                {
                    String configCoor = (String) configItem.get(keyItem);
                    String[] xy = configCoor.split("@");
                    String printValue = (String) printItem.get(keyItem);
                    if (xy[0] != null && xy[1] != null && printValue != null)
                    {
                    	if(printValue.equals("重打票据")){
                    		g2d.setFont(font1);
                    		g2d.drawString(printValue, Integer.parseInt( xy[0]), Integer.parseInt( xy[1] ));
                    	}else{
                    		g2d.setFont(font);
                    		g2d.drawString(printValue, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                    	}
                    }
                }

            }
            // 打印明细
            if (detailNumber != 0)
            {
                for (int i = 0; i < detailNumber; i = i + 2)
                {
                    String detailName = detail[i];
                    String detailValue = detail[i + 1];
                    if (detailName.length() != 0 && detailValue.length() != 0)
                    {
                        g2d.drawString(detailName, detailXcoor + 150 * (i / 40), detailYcoor + (i % 40)
                                * yAsc);
                        g2d.drawString(detailValue, detailXcoor + 150 * (i / 40) + xAsc, detailYcoor
                                + (i % 40) * yAsc);
                    }
                }

            }
        }
        

        return Printable.PAGE_EXISTS;
    }
    
    /**
     * @param flagpoint
     */
    public void setFlagpoint(int flagpoint) {
    	this.flagpoint = flagpoint;
    }
}
