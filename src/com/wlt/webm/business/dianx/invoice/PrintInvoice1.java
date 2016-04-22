package com.wlt.webm.business.dianx.invoice;

import java.awt.print.*;
import java.applet.*;
import javax.print.attribute.*;

import javax.swing.JButton;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

/**
 * <p>Title: 获得打印信息，并执行打印</p>
 *
 * <p>Description:打印类 </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 深圳市万恒科技有限公司/p>
 *
 * @author 胡俊
 * 修改人：宁博
 * 修改日期：2010-03-31
 * version 3.0.0.0
 */
public class PrintInvoice1 extends Applet {
	/**
	 * 构造函数
	 */
  public PrintInvoice1() {
    try {
      jbInit();
    }
    catch (Exception ex) {
    	
    }
  }

  /**
   * 打印接口
   */
  public Printer printer = null;

  /**
   * 页面宽度
   */
  double pageWidth = 0;

  /**
   * 页面高度
   */
  double pageHeight = 0;
  /**
 * 按钮
 */
JButton jButton1 = new JButton();
  /**
 * 布局器
 */
BorderLayout borderLayout1 = new BorderLayout();

  /**
   * 初始化APPLET
   */
  public void init() {
  }
  /**
   * 打印开始
   */
  public void start() {
  }
  /**
   * 打印结束
   */
  public void stop() {
  }

  /**
   * 打印结束
   */
  public void destroy() {
  }
/**
 * 获得打印信息
 */
  public void print() {
    //获得打印任务
    PrinterJob printerJob = PrinterJob.getPrinterJob();

    //获得默认页面格式
    PageFormat pageFormat = printerJob.defaultPage();
    Paper paper = pageFormat.getPaper();

    HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

    printer = new Printer(pras);
    
    //交易类型
    String tradetype = getParameter("tradetype");
    if (tradetype != null) {
    	tradetype="交易类型:"+tradetype;
        printer.settradetypePosition(15, 10);
        printer.settradetype(tradetype);
      }
    //交易终端
    String terminal = getParameter("terminal");
    if (terminal != null) {
    	terminal="交易终端:"+terminal;
        printer.setterminalPosition(15, 20);
        printer.setterminal(terminal);
      }
    //客户名称
    String userName = getParameter("userName");
    if (userName != null) {
    	userName="客户姓名:"+userName;
        printer.setUserNamePosition(15, 30);
        printer.setUserName(userName);
      } 
    //用户号码
    String phone = getParameter("phone");
    if (phone != null) {
    	phone="客户号码:"+phone;
    	printer.setPhonePosition(15,40);
    	printer.setPhone(phone);
    }
    //合同号码
    String tradetime = getParameter("tradetime");
    if (tradetime != null) {
      tradetime="交易时间:"+tradetime;
      printer.settradetimePosition(15, 50);
      printer.settradetime(tradetime);
    }    
    //流水号码
    String tradeNo = getParameter("tradeNo");
    if (tradeNo != null) {
    	tradeNo="交易流水号:"+tradeNo;
        printer.settradeNoPosition(15,60);
        printer.settradeNo(tradeNo);
    }
    
    //计费周期位置
    String payDate = getParameter("payDate");
    if (payDate != null) {
    	payDate="计费周期:"+payDate;
        printer.setpayDatePosition(15,70);
        printer.setpayDate(payDate);
    }

    //设置明细
    int payNumber = 0;   
    payNumber = Integer.parseInt( getParameter("detailNumber") ) ;
    if(payNumber > 0){
    	printer.setDetailNumber(0);
    }
    String [] detail = new String[payNumber];
    if(payNumber >0 )
    {
    	
    	for(int i=0;i<payNumber;i++){
    		String j = String.valueOf(i);
    		detail[i] = getParameter(j);
    	}
    	  printer.setDetail(detail);
    	  if(tradetype!=null&&tradetype.length()!=0){
    		  printer.setFlagpoint(80);
    	  }else{
    		  printer.setFlagpoint(10);
    	  }
    	  }
    
    //本月应收
    String total = getParameter("total");
    if (total != null) {
    	total="本月应收:"+total;
        printer.settotalPosition(15,80);
        printer.settotal(total);
    }
    
    // 本次实收金额
    String fee = getParameter("fee");
    if (fee != null) {
    	fee="本次实收金额:"+fee;
        printer.setfeePosition(15,90);
        printer.setfee(fee);
    }
    
    
    int width = 800;
    int height = 500;
    paper.setSize(width, 500);
    paper.setImageableArea(0,0,width,height);
    pageFormat.setPaper(paper);
    printerJob.setPrintable(printer, pageFormat);

    try {
      printerJob.print(pras);
    }
    catch (Exception e) {
    	e.printStackTrace();
    }
    
  }
  /**
   * 打印开始
 * @param args 参数
   */
  public static void main(String[] args) {

    new PrintInvoice();
  }
/**
 * 初始按钮信息
 * @throws Exception
 */
  private void jbInit() throws Exception {
    jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
    jButton1.setSelectedIcon(null);
    jButton1.setText("打  印");
    jButton1.addActionListener(new PrintApp_jButton1_actionAdapter1(this));
    this.setLayout(borderLayout1);
    this.add(jButton1, java.awt.BorderLayout.CENTER);
  }
  /**
   * 执行按钮操作事件
   * @param e
   */
  public void jButton1_actionPerformed(ActionEvent e) {
    try {
      print();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
/**
 * 内部类，监听按钮事件类
 *
 */
class PrintApp_jButton1_actionAdapter1 implements ActionListener {
  /**
 * 监听参数
 */
private PrintInvoice1 adaptee;
  
  /**
 * @param adaptee 监听事件参数
 */
PrintApp_jButton1_actionAdapter1(PrintInvoice1 adaptee) {
    this.adaptee = adaptee;
  }

/**
 * @param e  事件
 */
public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}
