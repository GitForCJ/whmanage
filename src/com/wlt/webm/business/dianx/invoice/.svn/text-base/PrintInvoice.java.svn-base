package com.wlt.webm.business.dianx.invoice;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JButton;

/**
 * 打印票据类<br>
 * company 深圳市万恒科技有限公司<br>
 * copyright Copyright (c) 2009<br>
 * version 3.0.0.0
@author 鹿振
 */
public class PrintInvoice extends Applet
{

    /**
     * 打印类
     */
    public Printer printer = null;

    /**
     * 按钮显示容器
     */
    BorderLayout layoutButton = new BorderLayout();

    /**
     * 打印按钮
     */
    JButton button = new JButton();

    /**
     * 默认打印页面长度
     */
    int width = 800;

    /**
     * 默认打印页面宽，即高度
     */
    int height = 465;

    /**
     * 默认打印项个数
     */
    int itemLength = 0;

    /**
     * 构造函数
     */
    public PrintInvoice()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {

        }
    }

    /**
     * 打印命令执行函数
     */
    public void print()
    {
        // 获得打印任务
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        // 获得默认页面格式
        PageFormat pageFormat = printerJob.defaultPage();
        Paper paper = pageFormat.getPaper();
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        printer = new Printer(pras);

        String itemName = getParameter("itemName");
        String itemCoor = getParameter("itemCoor");
        if (itemName != null)
        {
            String[] itemArr = itemName.split("#");
            String[] scoorArr = itemCoor.split("#");
            itemLength = itemArr.length;
            for (int j = 0; j < itemLength; j++)
            {
                // 如果是设置发票大小则解析出来
                if (itemArr[j].equals("pageSize"))
                {
                    String[] xy = scoorArr[j].split("@");
                    if (xy[0] != null && xy[1] != null)
                    {
                        this.width = Integer.parseInt(xy[0]);
                        this.height = Integer.parseInt(xy[1]);
                    }
                }
                // 如果是设置发票大小则解析出来
                if (itemArr[j].equals("detailCoor"))
                {
                    String[] xy = scoorArr[j].split("@");
                    if (xy[0] != null && xy[1] != null)
                    {
                        printer.setDetailCoor(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                    }
                }

                printer.configItem.put(itemArr[j], scoorArr[j]);
            }
            // 配置文件不为空则获得页面传值
            for (int k = 0; k < itemArr.length; k++)
            {
                String jspValue = getParameter(itemArr[k]);
//                if(jspValue==null||jspValue.trim().length()==0){
//                    	jspValue="";
//                }
                if (jspValue != null)
                {
                    printer.setPrintItem(itemArr[k], jspValue);
                    // jspValue);
                }
            }
        }

        // 获得打印明细个数
        int detailNumber = 0;
        String detailNum = getParameter("detailNumber");
        detailNumber = Integer.parseInt(detailNum);

        if (detailNumber > 0)
        {
            printer.setDetailNumber(detailNumber);
        }

        String[] detail = new String[detailNumber];
        // 获得明细
        if (detailNumber > 0)
        {
            for (int i = 0; i < detailNumber; i++)
            {
                String dItem = String.valueOf(i);
                detail[i] = getParameter(dItem);
            }
            printer.setDetail(detail);
        }

        paper.setSize(width, height);
        paper.setImageableArea(0, 0, width, height);
        pageFormat.setPaper(paper);
        printerJob.setPrintable(printer, pageFormat);
        try
        {
            printerJob.print(pras);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 初始按钮信息
     * @throws Exception
     */
    private void jbInit() throws Exception
    {
        button.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        button.setSelectedIcon(null);
        button.setText("打  印");
        button.addActionListener(new PrintApp_jButton1_actionAdapter(this));
        this.setLayout(layoutButton);
        this.add(button, java.awt.BorderLayout.CENTER);
    }

    /**
     * 执行按钮操作事件
     * @param e
     */
    public void jButton1_actionPerformed(ActionEvent e)
    {
        try
        {
            print();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

/**
 * 内部类，监听按钮事件类
 */
class PrintApp_jButton1_actionAdapter implements ActionListener
{
    /**
     * 打印类
     */
    private PrintInvoice invoice;

    /**
     * 构造方法
     * @param invoice 打印类
     */
    PrintApp_jButton1_actionAdapter(PrintInvoice invoice)
    {
        this.invoice = invoice;
    }

    /**
     * 事件函数
     * @param e 事件对象
     */
    public void actionPerformed(ActionEvent e)
    {
        invoice.jButton1_actionPerformed(e);
    }
}
