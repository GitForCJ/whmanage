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
 * Excel�����װ��
 * Company��������###EJET###���޹�˾
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class Excel
{
    /**
     * ������
     */
    private HSSFWorkbook workbook;

    /**
     * ������
     */
    private HSSFSheet sheet;

    /**
     * ��ǰ����
     */
    private int currentRow = 0;

    /**
     * ������
     */
    private int totalCols;

    /**
     * Ĭ�Ϲ��췽��
     */
    public Excel()
    {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("sheet");
    }

    /**
     * ��������
     * @param cols ����
     */
    public void setCols(int cols)
    {
        this.totalCols = cols;
    }

    /**
     * ��������
     * @param caption ����
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
     * ����һ���б���
     * @param colCaption һ���б���
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
     * ���������б���
     * @param colCaption �����б���
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
     * ����EXCEL����������
     * @param data �������ݣ���֧�ֵ����ݸ�ʽ�У�
     *              1�����б� List(String[])��
     *              2���ϲ�һ�� Map(List(String[]))��
     *              3���ϲ����� Map(Map(List(String[]))��
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
            throw new IllegalArgumentException("��֧�ֵ����ݸ�ʽ");
        }
    }

    /**
     * �����б����������
     * @param list List(String[])�б�����
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
     * �����ϲ�һ�е���������
     * @param map Map(List(String[])) Ƕ��һ���Map����
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
     * �����ϲ����е���������
     * @param map Map(Map(List(String[]))) Ƕ�׶����Map����
     */
    private void createTwoMapBody(Map map)
    {
        HSSFFont font = getFont(10, HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);
        HSSFCellStyle style = getStyle(font, HSSFCellStyle.ALIGN_CENTER);

        int[] rowSpans = CountUtil.getRowSpanOfTwoMap(map);//��һ�еĺϲ�����
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
     * ������ע��Ϣ
     * @param remarks ��ע��Ϣ
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
     * ������Ԫ��
     * @param row �ж���
     * @param index �����д�����Ԫ���λ�ã���0��ʼ��
     * @param style ��Ԫ����ʽ
     * @param value ��Ԫ���ֵ
     */
    private void createCell(HSSFRow row, int index, HSSFCellStyle style, String value)
    {
        HSSFCell cell = row.createCell((short) index);
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    /**
     * �ϲ���
     * @param startRow ��ʼ�У���0��ʼ��
     * @param endRow �����У���0��ʼ��
     * @param col ��λ�ã���0��ʼ��
     */
    private void mergeRow(int startRow, int endRow, int col)
    {
        sheet.addMergedRegion(new Region(startRow, (short) col, endRow, (short) col));
    }

    /**
     * �ϲ���
     * @param startCol ��ʼ�У���0��ʼ��
     * @param endCol �����У���0��ʼ��
     * @param row ��λ�ã���0��ʼ��
     */
    private void mergeCol(int startCol, int endCol, int row)
    {
        sheet.addMergedRegion(new Region(row, (short) startCol, row, (short) endCol));
    }

    /**
     * �ϲ�����
     * @param startRow ��ʼ�У���0��ʼ��
     * @param startCol ��ʼ�У���0��ʼ��
     * @param endRow �����У���0��ʼ��
     * @param endCol �����У���0��ʼ��
     */
    private void mergeRegion(int startRow, int startCol, int endRow, int endCol)
    {
        sheet.addMergedRegion(new Region(startRow, (short) startCol, endRow, (short) endCol));
    }

    /**
     * ��������·����EXCEL�ļ�
     * @param filePath EXCEL�ļ�·��
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
            throw new RuntimeException("�޷������ļ���" + filePath);
        }
        catch (IOException e)
        {
            throw new RuntimeException("�޷�д���ļ���" + filePath);
        }
        finally
        {
            closeOutputStream(out);
        }
    }

    /**
     * ��EXCEL�ļ�д�뵽�������
     * @param out �����
     * @throws IOException
     */
    public void createFile(OutputStream out) throws IOException
    {
        workbook.write(out);
    }

    /**
     * �ر������
     * @param out �����
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
     * ��������߶Ⱥ��������ͻ���������
     * @param fontHeight ����߶�
     * @param boldweight ��������
     * @param color ������ɫ
     * @return �������
     */
    private HSSFFont getFont(int fontHeight, short boldweight, short color)
    {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) fontHeight);
        font.setFontName("������");
        font.setColor(color);
        font.setBoldweight(boldweight);
        return font;
    }

    /**
     * ��������Ͷ��䷽ʽ��õ�Ԫ����ʽ
     * @param font ����
     * @param align ���䷽ʽ
     * @return ��Ԫ����ʽ
     */
    private HSSFCellStyle getStyle(HSSFFont font, short align)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //�߿�
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //���뷽ʽ
        style.setAlignment(align);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
}
