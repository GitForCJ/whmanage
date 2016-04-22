package com.wlt.webm.pccommon.struts.bean;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 处理统计报表的工具类<br>
 * company 深圳市万恒科技有限公司<br>
 * copyright Copyright (c) 2008<br>
 * version 3.0.0.0
 * @author 鹿振
 */
public class CountUtil
{
    /**
     * 获得两层嵌套Map的针对报表的第一列合并行情况
     * @param map Map(Map(List))两层嵌套Map
     * @return 对应报表的第一列合并行情况
     */
    public static int[] getRowSpanOfTwoMap(Map map)
    {
        int[] spans = new int[map.size()];
        Iterator it = map.keySet().iterator();
        for (int i = 0; it.hasNext(); i++)
        {
            Object key = it.next();
            Map map2 = (Map) map.get(key);
            Iterator it2 = map2.keySet().iterator();
            for (int i2 = 0; it2.hasNext(); i2++)
            {
                Object key2 = it2.next();
                List list = (List) map2.get(key2);
                spans[i] += list.size();
            }
        }
        return spans;
    }

    /**
     * 根据多行报表标题获得一个合并的行列信息
     * @param colTitles 列标题
     * @return 报表标题合并的行列信息：第一维对应标题的行，第二维对应标题的列，
     *         第三维对应标题的合并行和列，已经被合并的行或列则对应的第三维为null。
     */
    public static int[][][] getSpans(String[][] colTitles)
    {
        int[][][] spans = new int[colTitles.length][][];

        for (int i = 0; i < colTitles.length; i++)
        {
            spans[i] = new int[colTitles[i].length][];
            for (int j = 0; j < colTitles[i].length; j++)
            {
                if (colTitles[i][j] != null)
                {
                    spans[i][j] = new int[2];
                    spans[i][j][0] = getRowSpan(colTitles, i, j);
                    spans[i][j][1] = getColSpan(colTitles, i, j);
                }
            }
        }

        return spans;
    }

    /**
     * 获得报表标题在某行某列的行跨度
     * @param colTitles 报表列标题
     * @param row 行
     * @param col 列
     * @return 行跨度
     */
    private static int getRowSpan(String[][] colTitles, int row, int col)
    {
        int rowSpan = 1;
        if (colTitles[row][col] != null)
        {
            for (int i = row + 1; i < colTitles.length; i++)
            {
                if (colTitles[i][col] == null)
                {
                    rowSpan++;
                }
                else
                {
                    break;
                }
            }
        }
        return rowSpan;
    }

    /**
     * 获得报表标题在某行某列的列跨度
     * @param colTitles 报表列标题
     * @param row 行
     * @param col 列
     * @return 列跨度
     */
    private static int getColSpan(String[][] colTitles, int row, int col)
    {
        int colSpan = 1;
        if (colTitles[row][col] != null)
        {
            for (int i = col + 1; i < colTitles[row].length; i++)
            {
                if (colTitles[row][i] == null)
                {
                    if(row == 0)
                    {
                        colSpan++;
                    }
                    else if (colTitles[row - 1][i] == null)
                    {
                        colSpan++;
                    }
                    else
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        return colSpan;
    }
}
