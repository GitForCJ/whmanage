package com.ejet.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.ejet.common.struts.bean.CountUtil;

/**
 * Excel报表封装类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class Excel
{
    /**
     * 工作薄
     */
    private HSSFWorkbook workbook;

    /**
     * 工作表
     */
    private HSSFSheet sheet;

    /**
     * 当前行数
     */
    private int currentRow = 0;

    /**
     * 总列数
     */
    private int totalCols;

    /**
     * 默认构造方法
     */
    public Excel()
    {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("sheet");
    }

    /**
     * 设置列数
     * @param cols 列数
     */
    public void setCols(int cols)
    {
        this.totalCols = cols;
    }

    /**
     * 创建标题
     * @param caption 标题
     */
    public void createCaption(String caption)
    {
        HSSFFont font = getFont(14, HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_CENTER);

        HSSFRow row = sheet.createRow(currentRow);
        createCell(row, 0, style, caption);
        for (int i = 1; i < totalCols; i++)
        {
            createCell(row, i, style, "");
        }

        mergeCol(0, totalCols - 1, currentRow);
        currentRow++;
    }

    /**
     * 创建一行列标题
     * @param colCaption 一行列标题
     */
    public void createColCaption(String[] colCaption)
    {
        HSSFFont font = getFont(10, HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_CENTER);

        HSSFRow row = sheet.createRow(currentRow);
        for (int i = 0; i < colCaption.length; i++)
        {
            createCell(row, i, style, colCaption[i]);
        }
        currentRow++;
    }

    /**
     * 创建多行列标题
     * @param colCaption 多行列标题
     */
    public void createColCaption(String[][] colCaption)
    {
        HSSFFont font = getFont(10, HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_CENTER);
        int[][][] spans = CountUtil.getSpans(colCaption);

        for (int i = 0; i < colCaption.length; i++)
        {
            HSSFRow row = sheet.createRow(currentRow);
            for (int j = 0; j < colCaption[i].length; j++)
            {
                createCell(row, j, style, colCaption[i][j]);

                if (spans[i][j] != null)
                {
                    mergeRegion(currentRow, j, currentRow + spans[i][j][0] - 1, j + spans[i][j][1] - 1);
                }
            }
            currentRow++;
        }
    }

    /**
     * 创建EXCEL的主体数据
     * @param data 主体数据，可支持的数据格式有：
     *              1、简单列表 List(String[])；
     *              2、合并一列 Map(List(String[]))；
     *              3、合并二列 Map(Map(List(String[]))。
     */
    public void createBody(Object data)
    {
        int nestedCount = DataUtil.getNestedCount(data);

        if (nestedCount == 0)
        {
            createBody((List) data);
        }
        else if (nestedCount == 1)
        {
            createOneMapBody((Map) data);
        }
        else if (nestedCount == 2)
        {
            createTwoMapBody((Map) data);
        }
        else
        {
            throw new IllegalArgumentException("不支持的数据格式");
        }
    }

    /**
     * 创建列表的主体数据
     * @param list List(String[])列表数据
     */
    private void createBody(List list)
    {
        HSSFFont font = getFont(10, HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_CENTER);

        for (int i = 0, ii = list.size(); i < ii; i++)
        {
            HSSFRow row = sheet.createRow(currentRow);
            String[] stringArray = (String[]) list.get(i);
            for (int j = 0; j < stringArray.length; j++)
            {
                createCell(row, j, style, stringArray[j]);
            }
            currentRow++;
        }
    }

    /**
     * 创建合并一列的主体数据
     * @param map Map(List(String[])) 嵌套一层的Map数据
     */
    private void createOneMapBody(Map map)
    {
        HSSFFont font = getFont(10, HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_CENTER);

        for (Iterator it = map.keySet().iterator(); it.hasNext();)
        {
            String key = (String) it.next();
            List list = (List) map.get(key);
            int size = list.size();
            mergeRow(currentRow, currentRow + size - 1, 0);

            for (int i = 0; i < size; i++)
            {
                HSSFRow row = sheet.createRow(currentRow);
                createCell(row, 0, style, key);

                String[] stringArray = (String[]) list.get(i);
                for (int j = 0; j < stringArray.length; j++)
                {
                    createCell(row, j + 1, style, stringArray[j]);
                }
                currentRow++;
            }
        }
    }

    /**
     * 创建合并二列的主体数据
     * @param map Map(Map(List(String[]))) 嵌套二层的Map数据
     */
    private void createTwoMapBody(Map map)
    {
        HSSFFont font = getFont(10, HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_CENTER);

        int[] rowSpans = CountUtil.getRowSpanOfTwoMap(map);//第一列的合并行数
        int rowCount = 0;
        for (Iterator it1 = map.keySet().iterator(); it1.hasNext(); rowCount++)
        {
            String key1 = (String) it1.next();
            Map map1 = (Map) map.get(key1);

            mergeRow(currentRow, currentRow + rowSpans[rowCount] - 1, 0);

            for (Iterator it2 = map1.keySet().iterator(); it2.hasNext();)
            {
                String key2 = (String) it2.next();
                List list = (List) map1.get(key2);
                int size = list.size();
                mergeRow(currentRow, currentRow + size - 1, 1);

                for (int i = 0; i < size; i++)
                {
                    HSSFRow row = sheet.createRow(currentRow);
                    createCell(row, 0, style, key1);
                    createCell(row, 1, style, key2);

                    String[] stringArray = (String[]) list.get(i);
                    for (int j = 0; j < stringArray.length; j++)
                    {
                        createCell(row, j + 2, style, stringArray[j]);
                    }
                    currentRow++;
                }
            }
        }
    }

    /**
     * 创建备注信息
     * @param remarks 备注信息
     */
    public void createRemarks(String[] remarks)
    {
        HSSFFont font = getFont(10, HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.RED.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_LEFT);

        for (int i = 0; i < remarks.length; i++)
        {
            HSSFRow row = sheet.createRow(currentRow);
            createCell(row, 0, style, remarks[i]);

            for (int j = 1; j < totalCols; j++)
            {
                createCell(row, j, style, "");
            }

            mergeCol(0, totalCols - 1, currentRow);
            currentRow++;
        }
    }

    /**
     * 创建单元格
     * @param row 行对象
     * @param index 在行中创建单元格的位置，从0开始。
     * @param style 单元格样式
     * @param value 单元格的值
     */
    private void createCell(HSSFRow row, int index, HSSFCellStyle style, String value)
    {
        HSSFCell cell = row.createCell((short) index);
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    /**
     * 合并行
     * @param startRow 起始行（从0开始）
     * @param endRow 结束行（从0开始）
     * @param col 列位置（从0开始）
     */
    private void mergeRow(int startRow, int endRow, int col)
    {
        sheet.addMergedRegion(new Region(startRow, (short) col, endRow, (short) col));
    }

    /**
     * 合并列
     * @param startCol 起始列（从0开始）
     * @param endCol 结束列（从0开始）
     * @param row 行位置（从0开始）
     */
    private void mergeCol(int startCol, int endCol, int row)
    {
        sheet.addMergedRegion(new Region(row, (short) startCol, row, (short) endCol));
    }

    /**
     * 合并区域
     * @param startRow 起始行（从0开始）
     * @param startCol 起始列（从0开始）
     * @param endRow 结束行（从0开始）
     * @param endCol 结束列（从0开始）
     */
    private void mergeRegion(int startRow, int startCol, int endRow, int endCol)
    {
        sheet.addMergedRegion(new Region(startRow, (short) startCol, endRow, (short) endCol));
    }

    /**
     * 创建本地路径的EXCEL文件
     * @param filePath EXCEL文件路径
     */
    public void createFile(String filePath)
    {
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(filePath);
            workbook.write(out);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("无法创建文件：" + filePath);
        }
        catch (IOException e)
        {
            throw new RuntimeException("无法写入文件：" + filePath);
        }
        finally
        {
            closeOutputStream(out);
        }
    }

    /**
     * 把EXCEL文件写入到输出流中
     * @param out 输出流
     * @throws IOException
     */
    public void createFile(OutputStream out) throws IOException
    {
        workbook.write(out);
    }

    /**
     * 关闭输出流
     * @param out 输出流
     */
    private void closeOutputStream(OutputStream out)
    {
        if (out != null)
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    /**
     * 根据字体高度和字体类型获得字体对象
     * @param fontHeight 字体高度
     * @param boldweight 字体类型
     * @param color 字体颜色
     * @return 字体对象
     */
    private HSSFFont getFont(int fontHeight, short boldweight, short color)
    {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) fontHeight);
        font.setFontName("新宋体");
        font.setColor(color);
        font.setBoldweight(boldweight);
        return font;
    }

    /**
     * 根据字体和对其方式获得单元格样式
     * @param font 字体
     * @param align 对其方式
     * @return 单元格样式
     */
    private HSSFCellStyle getStyle(HSSFFont font, short align)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //对齐方式
        style.setAlignment(align);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
}
