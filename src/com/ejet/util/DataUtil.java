package com.ejet.util;

import java.util.*;

/**
 * 数据转换处理类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public final class DataUtil
{
    /**
     * 数据小计名称
     */
    private static final String SUBTOTAL = "【 小计 】";

    /**
     * 数据合计名称
     */
    private static final String TOTAL = "【 合计 】";

    /**
     * 生成Map的最小嵌套数
     */
    private static final int MIN_NESTED_COUNT = 1;

    /**
     * 此类不允许实例化
     */
    private DataUtil()
    {
    }

    /**
     * 将List(String[])转换成Map(String, String)，
     * List中第一列作为Map的key，
     * List中第二列作为Map的value。
     * @param srcList List(String[])
     * @return Map(String, String)
     */
    public static Map toStringMap(List srcList)
    {
        return toStringMap(srcList, 0, 1);
    }

    /**
     * 将List(String[])转换成Map(String, String)，
     * List中指定列作为Map的key，
     * List中指定列作为Map的value。
     * @param srcList List(String[])
     * @param keyIndex List中将被转换成Map的key的列（0为第一列）
     * @param valueIndex List中将被转换成Map的value的列（0为第一列）
     * @return Map(String, String)
     */
    public static Map toStringMap(List srcList, int keyIndex, int valueIndex)
    {
        Map map = new LinkedHashMap();
        for (int i = 0, ii = srcList.size(); i < ii; i++)
        {
            String[] srcRow = (String[]) srcList.get(i);
            map.put(srcRow[keyIndex], srcRow[valueIndex]);
        }
        return map;
    }

    /**
     * 将List(String[])转换成Map(String, List(String))，
     * List中第一列作为Map的key，
     * List中第二列作为Map的value。
     * @param srcList List(String[])
     * @return Map(String, List(String))
     */
    public static Map toStringListMap(List srcList)
    {
        return toStringListMap(srcList, 0, 1);
    }

    /**
     * 将List(String[])转换成Map(String, List(String))，
     * List中指定列作为Map的key，
     * List中指定列作为Map的value。
     * @param srcList List(String[])
     * @param keyIndex List中将被转换成Map的key的列（0为第一列）
     * @param valueIndex List中将被转换成Map的value的列（0为第一列）
     * @return Map(String, List(String))
     */
    public static Map toStringListMap(List srcList, int keyIndex, int valueIndex)
    {
        Map map = new LinkedHashMap();
        for (int i = 0, ii = srcList.size(); i < ii; i++)
        {
            String[] srcRow = (String[]) srcList.get(i);
            String key = srcRow[keyIndex];
            String value = srcRow[valueIndex];
            if (map.containsKey(key))
            {
                ((List) map.get(key)).add(value);
            }
            else
            {
                List list = new ArrayList();
                list.add(value);
                map.put(key, list);
            }
        }
        return map;
    }

    /**
     * 将List(String[])转换成Map(String, String[])，
     * List中第一列作为Map的key，
     * List中第二列到最后一列的数据作为Map的value。
     * @param srcList List(String[])
     * @return Map(String, String[])
     */
    public static Map toStringsMap(List srcList)
    {
        return toStringsMap(srcList, 0, 1);
    }

    /**
     * 将List(String[])转换成Map(String, String[])，
     * List中指定列作为Map的key，
     * List中指定起始列到最后一列之间的数据作为Map的value。
     * @param srcList List(String[])
     * @param keyIndex List中将被转换成Map的Key的列（0为第一列）
     * @param valueStart List中将被转换成Map的value的起始列（从0开始，包含起始列）
     * @return Map(String, String[])
     */
    public static Map toStringsMap(List srcList, int keyIndex, int valueStart)
    {
        int valueEnd = getColumns(srcList);
        return toStringsMap(srcList, keyIndex, valueStart, valueEnd);
    }

    /**
     * 将List(String[])转换成Map(String, String[])，
     * List中指定列作为Map的key，
     * List中指定起始列和结束列之间的数据作为Map的value。
     * @param srcList List(String[])
     * @param keyIndex List中将被转换成Map的Key的列（0为第一列）
     * @param valueStart List中将被转换成Map的value的起始列（从0开始，包含起始列）
     * @param valueEnd List中将被转换成Map的value的结束列（从0开始，不包含结束列）
     * @return Map(String, String[])
     */
    public static Map toStringsMap(List srcList, int keyIndex, int valueStart, int valueEnd)
    {
        Map map = new LinkedHashMap();
        int valueLen = valueEnd - valueStart;
        for (int i = 0, ii = srcList.size(); i < ii; i++)
        {
            String[] srcRow = (String[]) srcList.get(i);
            String key = srcRow[keyIndex];
            String[] row = new String[valueLen];
            System.arraycopy(srcRow, valueStart, row, 0, valueLen);
            map.put(key, row);
        }

        return map;
    }

    /**
     * 将List(String[])转换成Map(String, List(String[]))，
     * List中第一列作为Map的key，
     * List中第二列到最后一列的数据作为Map的value。
     * @param srcList List(String[])
     * @return Map(String, List(String[]))
     */
    public static Map toStringsListMap(List srcList)
    {
        return toStringsListMap(srcList, 0, 1);
    }

    /**
     * 将List(String[])转换成Map(String, List(String[]))，
     * List中指定列作为Map的key，
     * List中指定起始列到最后一列之间的数据作为Map的value。
     * @param srcList List(String[])
     * @param keyIndex List中将被转换成Map的Key的列（0为第一列）
     * @param valueStart List中将被转换成Map的value的起始列（从0开始，包含起始列）
     * @return Map(String, List(String[]))
     */
    public static Map toStringsListMap(List srcList, int keyIndex, int valueStart)
    {
        int valueEnd = getColumns(srcList);
        return toStringsListMap(srcList, keyIndex, valueStart, valueEnd);
    }

    /**
     * 将List(String[])转换成Map(String, List(String[]))，
     * List中指定列作为Map的key，
     * List中指定起始列和结束列之间的数据作为Map的value。
     * @param srcList List(String[])
     * @param keyIndex List中将被转换成Map的Key的列（0为第一列）
     * @param valueStart List中将被转换成Map的value的起始列（从0开始，包含起始列）
     * @param valueEnd List中将被转换成Map的value的结束列（从0开始，不包含结束列）
     * @return Map(String, List(String[]))
     */
    public static Map toStringsListMap(List srcList, int keyIndex, int valueStart, int valueEnd)
    {
        Map map = new LinkedHashMap();
        int valueLen = valueEnd - valueStart;
        for (int i = 0, ii = srcList.size(); i < ii; i++)
        {
            String[] srcRow = (String[]) srcList.get(i);
            String key = srcRow[keyIndex];
            String[] row = new String[valueLen];
            System.arraycopy(srcRow, valueStart, row, 0, valueLen);
            if (map.containsKey(key))
            {
                ((List) map.get(key)).add(row);
            }
            else
            {
                List list = new ArrayList();
                list.add(row);
                map.put(key, list);
            }
        }

        return map;
    }

    /**
     * 将List(String[])转换成指定嵌套层次数的Map。
     * 根据嵌套的层次数，默认将List第一列作为第一层Map的key，将List第二列作为第二层Map的key，以此类推，剩下的第一列作为最后一层Map的value。
     * @param nestedCount 结果Map嵌套的层次数
     * @param srcList List(String[])
     * @return 嵌套层次数为1时，返回Map(String, String)；
     *         嵌套层次数为2时，返回Map(String, Map(String, String))；
     *         嵌套层次数为3时，返回Map(String, Map(String, Map(String, String)))；
     *         ......
     */
    public static Map toNestedStringMap(int nestedCount, List srcList)
    {
        if (nestedCount <= MIN_NESTED_COUNT)
        {
            return toStringMap(srcList);
        }

        Map map = toStringsListMap(srcList);
        for (Iterator i = map.keySet().iterator(); i.hasNext();)
        {
            Object key = i.next();
            List list = (List) map.get(key);
            map.put(key, toNestedStringMap(nestedCount - 1, list));
        }

        return map;
    }

    /**
     * 将List(String[])转换成指定嵌套层次数的Map。
     * 根据嵌套的层次数，默认将List第一列作为第一层Map的key，将List第二列作为第二层Map的key，以此类推，剩下的所有列作为最后一层Map的value。
     * @param nestedCount 结果Map嵌套的层次数
     * @param srcList List(String[])
     * @return 嵌套层次数为1时，返回Map(String, List(String[]))；
     *         嵌套层次数为2时，返回Map(String, Map(String, List(String[])))；
     *         嵌套层次数为3时，返回Map(String, Map(String, Map(String, List(String[]))))；
     *         ......
     */
    public static Map toNestedStringsListMap(int nestedCount, List srcList)
    {
        Map map = toStringsListMap(srcList);
        if (nestedCount > MIN_NESTED_COUNT)
        {
            for (Iterator i = map.keySet().iterator(); i.hasNext();)
            {
                Object key = i.next();
                List list = (List) map.get(key);
                map.put(key, toNestedStringsListMap(nestedCount - 1, list));
            }
        }

        return map;
    }

    /**
     * 根据源Map和源List生成一个新的Map（有序Map），新Map的key为源Map的key，
     * 新Map的value为源Map中List的每个元素对应的源List第一列所在行组成的List。
     * @param strListMap Map(String, List(String))
     * @param srcList List(String[])
     * @return Map(String, List(String[]))源Map中的key和源List中的String[]组成。
     */
    public static Map getStringsListMap(Map strListMap, List srcList)
    {
        Map map = new LinkedHashMap();

        if (srcList.size() > 0)
        {
            int srcListRowLen = ((String[]) srcList.get(0)).length;

            Set keys = strListMap.keySet();
            Iterator it = keys.iterator();
            while(it.hasNext())
            {
                String key = (String) it.next();
                List strList = (List) strListMap.get(key);
                
                List list = new ArrayList();
                
                for (int i = 0; i < strList.size(); i++)
                {
                    String str = (String) strList.get(i);
                    int rowIndexOfSrcList = indexOf(srcList, str, 0);
                    if (rowIndexOfSrcList >= 0)
                    {
                        list.add(srcList.get(rowIndexOfSrcList));
                        map.put(key, srcList.get(rowIndexOfSrcList));
                    }
                    else
                    {
                        String[] row = new String[srcListRowLen];
                        row[0] = str;
                        list.add(row);
                    }
                }

                if (list.size() > 0)
                {
                    map.put(key, list);
                }
            }
        }

        return map;
    }

    /**
     * 根据第一个Map中的value和第二个Map中的key的对应关系合并两个Map为一个Map
     * @param firstMap Map(String, List(String))第一个Map
     * @param secondMap Map(String, Object)第二个Map
     * @return Map(String, Map(
     */
    public static Map getStringsListMap(Map firstMap, Map secondMap)
    {
        Map map = new LinkedHashMap();

        if (secondMap.size() == 0)
            return map;

        Iterator it = firstMap.keySet().iterator();
        while (it.hasNext())
        {
            String key = (String) it.next();
            List valueList = (List) firstMap.get(key);
            Map valuesMap = new LinkedHashMap();
            for (int i = 0, il = valueList.size(); i < il; i++)
            {
                String value = (String) valueList.get(i);
                if (secondMap.containsKey(value))
                {
                    valuesMap.put(value, secondMap.get(value));
                }
            }
            map.put(key, valuesMap);
        }

        return map;
    }

    /**
     * 对Map中的值List(String[])进行列合计，在每一个List最后增加一条合计的行并在Map中增加一条总的合计项。
     * @param stringsListMap Map(String, List(String[]))要合计的Map
     * @param startCol 要合并的起始列，0表示第一列
     * @return Map(String, List(String[])) 已经合计了的数据
     */
    public static Map sumColToRow(Map stringsListMap, int startCol)
    {
    	if (getNestedCount(stringsListMap) == 2)
    	{
    		return sumColToRowTwo(stringsListMap, startCol);
    	}

        Map map = new LinkedHashMap();

        Iterator it = stringsListMap.keySet().iterator();
        if (it.hasNext())
        {
            String[] row = (String[]) ((List) stringsListMap.get(it.next())).get(0);
            int len = row.length;
            double[] sums = new double[len - startCol];

            it = stringsListMap.keySet().iterator();
            while (it.hasNext())
            {
                String key = (String) it.next();
                List list = sumCol((List) stringsListMap.get(key), startCol);
                row = (String[]) list.get(list.size() - 1);
                for (int j = startCol, k = 0; j < row.length; j++, k++)
                {
                    if (row[j] != null && row[j].length() > 0)
                        sums[k] += Double.parseDouble(row[j]);
                }
                map.put(key, list);
            }

            row = new String[len];
            for (int i = 0; i < startCol; i++)
            {
                row[i] = "";
            }
            for (int i = startCol, j = 0; i < len; i++, j++)
            {
                row[i] = "" + sums[j];
            }
            List list = new ArrayList();
            list.add(row);
            map.put(TOTAL, list);
        }

        return map;
    }
    /**
     * 对Map中的值List(String[])进行列合计，在每一个List最后增加一条合计的行并在Map中增加一条总的合计项。
     * @param stringsListMap Map(String, List(String[]))要合计的Map
     * @param startCol 要合并的起始列，0表示第一列
     * @return Map(String, List(String[])) 已经合计了的数据
     */
    private static Map sumColToRowTwo(Map stringsListMap, int startCol)
    {
    	Map rsmap = new LinkedHashMap();
    	Map midmap = new LinkedHashMap();
    	double[] sums = null;
    	int len=0;
    	List list=new ArrayList();
    	Iterator it = stringsListMap.keySet().iterator();
    	while(it.hasNext()){
    		Object key=it.next();
    		Map secondMap=sumColToRow((Map)stringsListMap.get(key),startCol);
    		rsmap.put(key,secondMap);
    		String[] sumRow=(String [])((List) secondMap.get(TOTAL)).get(0);
    		list.add(sumRow);
    	    len =sumRow.length;
    	}
    	sums=new double[len-startCol];
    	for(int i=0;i<list.size();i++){
    		String[] sumRow=(String[])list.get(i);
    		for (int j = startCol, k = 0; j < sumRow.length; j++, k++)
            {
                if (sumRow[j] != null && sumRow[j].length() > 0)
                    sums[k] += Double.parseDouble(sumRow[j]);
            }
    	}
        list=new ArrayList();
        String []row=new String[len];
        for (int i = 0; i < startCol; i++)
        {
            row[i] = "";
        }
        for (int i = startCol, j = 0; i < len; i++, j++)
        {
            row[i] = "" + sums[j];
        }
        list.add(row);
        midmap.put("", list);
        rsmap.put(TOTAL, midmap);

        
        return rsmap;
    }
    /**
     * 针对最多两层Map中最底层Map的每个List值都一致情况下（行、列数一样），
     * 对Map中的所有的值进行累加到另一个相似的List中并添加到Map中，最外层Map也有一条累加项。
     * 改变了源数据
     * @param dataMap 嵌套最多两层的Map，最低层为List(String[])
     * @param startCol 对最里层Map的值List合计的起始列
     * @return 合计后的Map
     */
    public static Map sumColToList(Map dataMap, int startCol)
    {
        if (dataMap.size() == 0)
        {
            return dataMap;
        }

        Object obj = dataMap.get(dataMap.keySet().iterator().next());
        if (obj instanceof List)
        {
            return sumList(dataMap, startCol);
        }

        Map map = (Map) dataMap.get(dataMap.keySet().iterator().next());//区域里层Map
        List list = (List) map.get(map.keySet().iterator().next());//区域最里层List
        int listCols = ((String[]) list.get(0)).length;

        double[][] sums = new double[list.size()][listCols - startCol];

        Iterator it = dataMap.keySet().iterator();
        while (it.hasNext())
        {
            map = (Map) dataMap.get(it.next());
            map = sumList(map, startCol);
            if (map.size() == 1)
                sum(sums, (List) map.get(map.keySet().iterator().next()));
            else
                sum(sums, (List) map.get("合计"));
        }

        if (dataMap.size() > 1)
        {
            map = new LinkedHashMap();
            putSumData(map, "", sums, list);
            dataMap.put("合计", map);
        }

        return dataMap;
    }

    /**
     * 对最多两层Map的数据进行合计，先对列表进行小合计，再对Map中的每项值合计累加到最后一个合计元素中。
     * 改变了源数据。
     * @param dataMap 数据Map
     * @param startCol 合计列表的起始列
     * @return 合计后的Map
     */
    public static Map sumColToMap(Map dataMap, int startCol)
    {
        if (dataMap.size() == 0)
        {
            return dataMap;
        }

        sumColForList(dataMap, startCol);//行合计

        Object obj = dataMap.get(dataMap.keySet().iterator().next());
        if (obj instanceof List)
        {
            return sumList(dataMap, startCol);//列表合计
        }

        Map sumMap = null;

        for (Iterator i = dataMap.keySet().iterator(); i.hasNext();)
        {
            Object key = i.next();
            Map value = (Map) dataMap.get(key);
            value = sumList(value, startCol);
            if(dataMap.size()>1)
            {
            	if (sumMap == null)
                {
                    sumMap = copyNoDataMap(value, startCol);
                }
                sumMap = Operation.plus(sumMap, value, startCol);	
            }
        }
        if(dataMap.size()>1)
        {
        	dataMap.put("合计", sumMap);	
        }

        return dataMap;
    }

    /**
     * 针对Map的值List都一致情况下（行、列数一样），
     * 对List的所有的值进行累加到另一个相似的List中并添加到Map中。
     * 若Map中的元素个数为1，则不进行合计。改变了源数据。
     * @param dataListMap Map(String, List(String))) 数据
     * @param startCol 合计的起始列
     * @return Map 带有合计值的Map
     */
    private static Map sumList(Map dataListMap, int startCol)
    {
        if (dataListMap.size() <= 1)
        {
            return dataListMap;
        }

        List list = (List) dataListMap.get(dataListMap.keySet().iterator().next());//取出第一个List
        int listCols = ((String[]) list.get(0)).length;
        double[][] sums = new double[list.size()][listCols - startCol];

        Iterator it = dataListMap.keySet().iterator();
        while (it.hasNext())
        {
            sum(sums, (List) dataListMap.get(it.next()));
        }

        putSumData(dataListMap, "合计", sums, list);

        return dataListMap;
    }

    /**
     * 合计任意嵌套的Map中最底层List(String[])的数据，在List最后一个元素加入合计项。
     * @param map 任意嵌套的Map，最低层为List(String[])
     * @param startCol 合计的起始列
     */
    private static void sumColForList(Map map, int startCol)
    {
        for (Iterator i = map.keySet().iterator(); i.hasNext();)
        {
            Object value = map.get(i.next());
            if (value instanceof Map)
            {
                sumColForList((Map) value, startCol);
            }
            else
            {
                sumCol((List) value, startCol);
            }
        }
    }

    /**
     * 对Map中的值List(String[])进行行合计，并在List最后增加一条合计的列。
     * @param stringsListMap Map(String, List(String[]))要合计的Map
     * @param startCol 要合并的起始列，0表示第一列
     * @return Map(String, List(String[])) 已经合计了的数据
     */
    public static Map sumRow(Map stringsListMap, int startCol)
    {
        return sumRow(stringsListMap, startCol, 1);
    }

    /**
     * 对Map中的值List(String[])以space为间隔进行行合计，并在List最后增加space列合计项。
     * @param stringsListMap Map(String, List(String[]))要合计的Map
     * @param startCol 要合并的起始列，0表示第一列
     * @param space 间隔
     * @return Map(String, List(String[])) 已经合计了的数据
     */
    public static Map sumRow(Map stringsListMap, int startCol, int space)
    {
        Iterator it = stringsListMap.keySet().iterator();
        while (it.hasNext())
        {
            String key = (String) it.next();
            Object obj = stringsListMap.get(key);
            if (obj instanceof Map)
            {
                sumRow((Map) obj, startCol, space);
            }
            else
            {
                stringsListMap.put(key, sumRow((List) obj, startCol, space));
            }
        }

        return stringsListMap;
    }

    /**
     * 在源数据有数据的情况下在其最后增加一行合计项，如果只有一行数据则不进行合计。<br>
     * 如果合计的起始列>0，则合计项中第一列 String[0] 为"合计"，合计列以 double 类型合计，其余列为 ""。
     * @param srcList List(String[])源数据
     * @param startCol 合计的起始列（包含），第一列为0。
     * @return 增加了列合计的源数据
     */
    public static List sumCol(List srcList, int startCol)
    {
        if (srcList.size() > 0)
        {
            int cols = ((String[]) srcList.get(0)).length;

            double[] sum = new double[cols - startCol + 1];
            String[] row;

            for (int i = 0; i < srcList.size(); i++)
            {
                row = (String[]) srcList.get(i);
                for(int j = startCol, k = 0; j < cols; j++, k++)
                {
                    if (row[j] != null && row[j].length() > 0)
                        sum[k] += Double.parseDouble(row[j]);
                }
            }

            row = new String[cols];

            if (startCol > 0)
            {
                row[0] = SUBTOTAL;
            }
            for (int i = 1; i < startCol; i++)
            {
                row[i] = "";
            }
            for (int i = startCol, j = 0; i < cols; i++, j++)
            {
                row[i] = "" + sum[j];
            }

            srcList.add(row);
        }
        
        return srcList;
    }

    /**
     * 对源数据的每一行进行合计并添加到该行的最后一列
     * @param srcList List(String[]) 源数据
     * @param startCol 合并的起始列
     * @return List(String[]) 合计后的数据
     */
    public static List sumRow(List srcList, int startCol)
    {
        return sumRow(srcList, startCol, 1);
    }

    /**
     * 对源数据的每一行以space的间隔进行合计并添加到该行的最后space列
     * @param srcList List(String[])源数据
     * @param startCol 合计的起始列
     * @param space 间隔
     * @return List(String[])合计后的数据
     */
    public static List sumRow(List srcList, int startCol, int space)
    {
        List rsList = new ArrayList();

        if (srcList.size() > 0)
        {
            int srcCols = ((String[]) srcList.get(0)).length;

            for (int i = 0; i < srcList.size(); i++)
            {
                String[] srcRow = (String[]) srcList.get(i);
                String[] rsRow = new String[srcCols + space];
                double[] sums = new double[space];

                for (int j = 0; j < startCol; j++)
                {
                    rsRow[j] = srcRow[j];
                }

                for (int j = startCol; j < srcCols; j++)
                {
                    rsRow[j] = srcRow[j];
                    if (rsRow[j] != null && rsRow[j].length() > 0)
                        sums[(j - startCol) % space] += Double.parseDouble(rsRow[j]);
                }

                for (int j = 0; j < space; j++)
                {
                    rsRow[j + srcCols] = "" + sums[j];
                }

                rsList.add(rsRow);
            }
        }

        return rsList;
    }

    /**
     * 对List(String[])某一列的数据转换成String[]
     * @param srcList List(String[])源数据
     * @param colIndex 要转换的列，0表示第一列。
     * @return String[]字符串数组
     */
    public static String[] getStringArray(List srcList, int colIndex)
    {
        int len = srcList.size();
        String[] array = new String[len];
        for (int i = 0; i < len; i++)
        {
            array[i] = ((String[]) srcList.get(i))[colIndex];
        }

        return array;
    }

    /**
     * 对List(String[])某一列的数据去重复转换成String[]
     * @param srcList List(String[])源数据
     * @param colIndex 要转换的列，0表示第一列。
     * @return String[]字符串数组
     */
    public static String[] getNoRepeatStringArray(List srcList, int colIndex)
    {
        List list = new ArrayList();
        for (int i = 0; i < srcList.size(); i++)
        {
            String str = ((String[]) srcList.get(i))[colIndex];
            if (!list.contains(str))
            {
                list.add(str);
            }
        }

        String[] array = new String[list.size()];
        list.toArray(array);

        return array;
    }

    /**
     * 扩展列表
     * @param srcList 被扩展的源列表List(String[])
     * @param holdStart 源列表被保留的起始列数据（从0开始，包含起始列）
     * @param holdEnd 源列表被保留的结束列数据（从0开始，不包含结束列）
     * @param extendData 对应源列表扩展列的完整数据
     * @param extendIndex 源列表中被扩展的列（0为第一列）
     * @param extendValueStart 源列表中被扩展的数据起始列（从0开始，包含起始列）
     * @param extendValueEnd 源列表中被扩展的数据起始列（从0开始，不包含结束列）
     * @return List(String[])
     */
    public static List extendList(List srcList, int holdStart, int holdEnd, String[] extendData, int extendIndex, int extendValueStart, int extendValueEnd)
    {
        List list = new ArrayList();
        if (srcList.size() == 0)
        {
            return list;
        }

        Map holdMap = toStringsMap(srcList, 0, holdStart, holdEnd);
        Map valueListMap = toStringsListMap(srcList, 0, extendIndex, extendValueEnd);
        
        for (Iterator i = valueListMap.keySet().iterator(); i.hasNext();)
        {
            Object key = i.next();
            
            String[] row = new String[(holdEnd - holdStart) + extendData.length * (extendValueEnd - extendValueStart)];
            
            String[] holdValue = (String[]) holdMap.get(key);
            System.arraycopy(holdValue, 0, row, 0, holdValue.length);

            List valuesList = (List) valueListMap.get(key);
            Map valuesMap = (Map) toStringsMap(valuesList, 0, extendValueStart - extendIndex, extendValueEnd - extendIndex);
            
            for (int j = 0; j < extendData.length; j++)
            {
                String[] values = (String[]) valuesMap.get(extendData[j]);
                if (values != null)
                {
                    System.arraycopy(values, 0, row, holdValue.length + j * values.length, values.length);
                }
            }

            list.add(row);
        }

        return list;
    }

    /**
     * 根据源List数据和其中的第二列与数组对应获得一个扩展的数据。<br>
     * 
     * 源数据举例：<br>
     * "朝阳","办公","10"<br>
     * "朝阳","住宅","20"<br>
     * "朝阳","公话","30"<br>
     * "高新","办公","40"<br><br>
     * 
     * 扩展列数据举例：<br>
     * "办公","住宅","公话"<br><br>
     * 
     * 生成的结果List数据：<br>
     * "朝阳","10","20","30"<br>
     * "高新","40",null,null<br>
     * 
     * @param srcList List(String[3])源数据
     * @param extendData String[]扩展列的数据
     * @return List(String[]) 扩展后的数据
     */
    public static List getExtendedList(List srcList, String[] extendData)
    {
        if (srcList.size() == 0)
            return new ArrayList();

        int extendLen = ((String[]) srcList.get(0)).length - 2;//每一个扩展项对应的数据长度
        List list = new ArrayList();
        String[] keys = getNoRepeatStringArray(srcList, 0);
        Map strsListMap = toStringsListMap(srcList);
        for (int i = 0; i < keys.length; i++)
        {
            Map strsMap = toStringsMap((List) strsListMap.get(keys[i]));

            String[] row = new String[1 + extendData.length * extendLen];
            row[0] = keys[i];
            for (int j = 0; j < extendData.length; j++)
            {
                String[] strs = (String[]) strsMap.get(extendData[j]);
                if (strs == null)
                {
                    continue;
                }
                for (int k = 0; k < strs.length; k++)
                {
                    row[1 + j * extendLen + k] = strs[k];
                }
            }

            list.add(row);
        }

        return list;
    }

    /**
     * 根据数据列表获得一个Map，数据列表第一列做为Map的key，
     * 其余列生成一个扩展的列表（参见 getExtendedList(List, String[]) 方法）。
     * @param srcList 数据列表
     * @param extendData 扩展列的数据
     * @return 扩展数据后的Map
     */
    public static Map getExtendedMap(List srcList, String[] extendData)
    {
        if (srcList.size() == 0)
            return new LinkedHashMap();

        Map map = toStringsListMap(srcList);
        Iterator it = map.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            List list = (List) map.get(key);
            map.put(key, getExtendedList(list, extendData));
        }

        return map;
    }

    /**
     * 根据数据列表获得一个嵌套的Map，数据列表从startCol列之前做为嵌套的Map，
     * 其余列生成一个扩展的列表（参见 getExtendedList(List, String[]) 方法）。
     * @param srcList 数据列表
     * @param startCol 起始列（第一列为0），从起始列开始的列表做为扩展列表，前面的数据做为Map
     * @param extendData 扩展列的数据
     * @return 扩展数据后的Map
     */
    public static Map getExtendedMap(List srcList, int startCol, String[] extendData)
    {
        if (srcList.size() == 0)
        {
        	return new LinkedHashMap();
        }
        Map map = toNestedStringsListMap(startCol, srcList);
        return getExtendedMap(map, extendData);
    }

    /**
     * 对多层嵌套的源Map中最底层的List数据和其中的第二列与数组对应获得一个扩展的数据。请参阅getExtendedList()方法。
     * @param srcMap 嵌套多层的Map，最底层Map的value为List(String[])
     * @param extendData 对List(String[])第二列数据的横向扩展的对应数据
     * @return 扩展后的数据
     */
    public static Map getExtendedMap(Map srcMap, String[] extendData)
    {
        Map map = new LinkedHashMap();

        for (Iterator i = srcMap.keySet().iterator(); i.hasNext();)
        {
            Object key = i.next();
            Object value = srcMap.get(key);
            if (value instanceof Map)
            {
                map.put(key, getExtendedMap((Map) value, extendData));
            }
            else
            {
                map.put(key, getExtendedList((List) value, extendData));
            }
        }

        return map;
    }
    
    /**
     * 按照allFirstCols数据和srcList第一列对应的关系生成一个新数据。
     * @param allFirstCols 对应于源数据第一列的所有数据
     * @param srcList List(String[]) 源数据
     * @param rowLen 每一行的长度
     * @return List(String[])生成的新数据
     */
    public static List getFilledList(String[] allFirstCols, List srcList, int rowLen)
    {
        List list = new ArrayList();

        String[] row = null;

        for (int i = 0; i < allFirstCols.length; i++)
        {
            int index = indexOf(srcList, allFirstCols[i], 0);
            if (index == -1)
            {
                row = new String[rowLen];
                row[0] = allFirstCols[i];
                list.add(row);
            }
            else
            {
                list.add(srcList.get(index));
            }
        }

        return list;
    }

    /**
     * 根据给定的数据获得一个填充了的有完整数据的Map
     * @param allKeys 要填充的Map中所有的key值
     * @param allFirstCols 要填充的Map的value(List)中对应的所有第一列的值
     * @param dataMap Map(String, List(String[]))不完整的数据
     * @param rowLen Map中value的列数
     * @return Map(String, List(String[]))完整的数据
     */
    public static Map getFilledMap(String[] allKeys, String[] allFirstCols, Map dataMap, int rowLen)
    {
        Map map = new LinkedHashMap();

        for (int i = 0; i < allKeys.length; i++)
        {
            List list = dataMap.containsKey(allKeys[i]) ? (List) dataMap.get(allKeys[i]) : new ArrayList();
            map.put(allKeys[i], getFilledList(allFirstCols, list, rowLen));
        }

        return map;
    }

    /**
     * 对源Map中对应的key以及嵌套Map的key或List的第一列进行扩充。
     * @param filledDatas 扩充的完整数据
     * @param srcMap 被扩充的Map，可嵌套多层Map，最低层必须为List。
     * @param rowLen 最低层List的列长
     * @return 扩充后的Map
     */
    public static Map getFilledMap(String[][] filledDatas, Map srcMap, int rowLen)
    {
        return (Map) getFilledObject(filledDatas, srcMap, rowLen, filledDatas.length);
    }

    /**
     * 扩充Object对象中的数据，Object可为List或Map。
     * @param filledDatas 完整的填充数据
     * @param obj 被填充对象List(String[])或嵌套的Map（最低层必须为List(String[]）
     * @param rowLen List(String[])的列数
     * @param level 被填充对象所在的Map中的层次数
     * @return 填充后的Object
     */
    private static Object getFilledObject(String[][] filledDatas, Object obj, int rowLen, int level)
    {
        if (level <= 1)//最底层的List
        {
            if (obj == null)
            {
                obj = new ArrayList();
            }
            return getFilledList(filledDatas[filledDatas.length - 1], (List) obj, rowLen);
        }
        else
        {
            Map objMap = (Map) obj;
            if (objMap == null)
            {
                objMap = new LinkedHashMap();
            }
            Map map = new LinkedHashMap();
            for (int i = 0, ii = filledDatas[filledDatas.length - level].length; i < ii; i++)
            {
                String data = filledDatas[filledDatas.length - level][i];
                Object objValue = objMap.get(data);
                map.put(data, getFilledObject(filledDatas, objValue, rowLen, level - 1));
            }
            return map;
        }
    }

    /**
     * 汇总数据。一般用于分局数据向上汇总到对应的地市。
     * @param totUpMap Map(String, String) 汇总的规则数据。例如：key为地市，value为分局。
     * @param dataList List(String[]) 要汇总的数据，其中第一列对应汇总规则数据的value。
     * @return List(String[]) 汇总后的数据，第一列成为汇总规则数据中的key。
     */
    public static List getTotUpList(Map totUpMap, List dataList)
    {
        List list = new ArrayList();

        if (dataList.size() > 0)
        {
            int rowLen = ((String[]) dataList.get(0)).length;

            Iterator keyIt = totUpMap.keySet().iterator();
            while (keyIt.hasNext())
            {
                String key = (String) keyIt.next();

                String[] row = new String[rowLen];
                row[0] = key;

                double[] sum = new double[rowLen - 1];

                List secondColsList = (List) totUpMap.get(key);
                for (int j = 0; j < secondColsList.size(); j++)
                {
                    int index = indexOf(dataList, (String) secondColsList.get(j), 0);
                    if (index != -1)
                    {
                        String[] data = (String[]) dataList.get(index);
                        for (int k = 1; k < data.length; k++)
                        {
                            if (data[k] != null && data[k].length() > 0)
                                sum[k - 1] += Double.parseDouble(data[k]);
                        }
                    }
                }

                for (int j = 0; j < sum.length; j++)
                {
                    row[j + 1] = "" + sum[j];
                }

                list.add(row);
            }
        }

        return list;
    }

    /**
     * 汇总数据。一般用于分局数据向上汇总到对应的地市。
     * @param totUpMap Map(String, String) 汇总的规则数据。例如：key为地市，value为对应的分局列表。
     * @param dataListMap 被汇总的数据，嵌套任意层的Map，最低层必须为List(String[])，
     *          其中key对应汇总规则数据的value，该汇总数据中的Value应保持一致。
     * @return Map(String, List(String[])) 汇总后的数据，key为汇总规则数据中的key。
     */
    public static Map getTotUpMap(Map totUpMap, Map dataListMap)
    {
        Map map = new LinkedHashMap();
        for (Iterator i = totUpMap.keySet().iterator(); i.hasNext();)
        {
            Object totUpKey = i.next();
            List totUpValueList = (List) totUpMap.get(totUpKey);
            for (int j = 0, jj = totUpValueList.size(); j < jj; j++)
            {
                Object totUpValue = totUpValueList.get(j);
                Object dataValue = dataListMap.get(totUpValue);
                if (map.containsKey(totUpKey))
                {
                    Object value = map.get(totUpKey);
                    if (value instanceof List)
                    {
                        map.put(totUpKey, Operation.plus((List) value, (List) dataValue, 1));
                    }
                    else
                    {
                        map.put(totUpKey, Operation.plus((Map) value, (Map) dataValue, 1));
                    }
                }
                else
                {
                    map.put(totUpKey, dataValue);
                }
            }
        }
        return map;
    }

    /**
     * 搜索源数据List中某一列的字符串，若搜索到则返回字符串所在List中的行数，否则返回-1。
     * @param srcList 要搜索的源数据List(String[])
     * @param searchString 要搜索的字符串
     * @param searchColIndex 要搜索的源数据的列（0表示第一列）
     * @return 搜索字符串所在源数据中的行数（0表示第一行），未搜索到则返回-1。
     */
    public static int indexOf(List srcList, String searchString, int searchColIndex)
    {
        for (int i = 0; i < srcList.size(); i++)
        {
            if (searchString.equals(((String[]) srcList.get(i))[searchColIndex]))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据字符串数组和列数获得一个列表，列表的第一列为数组的值，其余列为 null。
     * @param stringArray 对应列表的第一列
     * @param cols 列表的列数
     * @return List(String[])列表
     */
    public static List getList(String[] stringArray, int cols)
    {
        List list = new ArrayList();
        for (int i = 0; i < stringArray.length; i++)
        {
            String[] row = new String[cols];
            row[0] = stringArray[i];
            list.add(row);
        }
        return list;
    }

    /**
     * 从源列表中起始列开始获得一个子列表
     * @param list List(String[]) 源列表
     * @param startCol 源列表的起始列（包含）
     * @return 子列表
     */
    public static List getSubList(List list, int startCol)
    {
        if (list.size() == 0)
        {
            return list;
        }
        return getSubList(list, startCol, ((String[]) list.get(0)).length);
    }

    /**
     * 从源列表中起始列到结束列获得一个子列表
     * @param list List(String[]) 源列表
     * @param startCol 源列表的起始列（包含）
     * @param endCol 源列表的结束列（不包含）
     * @return List(String[]) 子列表
     */
    public static List getSubList(List list, int startCol, int endCol)
    {
        if (list.size() == 0)
        {
            return list;
        }

        List subList = new ArrayList();
        for (int i = 0, il = list.size(); i < il; i++)
        {
            String[] row = (String[]) list.get(i);
            String[] subRow = new String[endCol - startCol];
            for (int j = startCol, k = 0; j < endCol; j++, k++)
            {
                subRow[k] = row[j];
            }
            subList.add(subRow);
        }

        return subList;
    }

    /**
     * 根据不规则的列从源列表中获得一个子列表
     * @param list List(String[]) 源列表
     * @param cols 源列表中的不规则的列
     * @return 子列表
     */
    public static List getSubList(List list, int[] cols)
    {
        if (list.size() == 0)
        {
            return list;
        }

        List subList = new ArrayList();
        for (int i = 0, il = list.size(); i < il; i++)
        {
            String[] row = (String[]) list.get(i);
            String[] subRow = new String[cols.length];
            for (int j = 0; j < cols.length; j++)
            {
                subRow[j] = row[cols[j]];
            }
            subList.add(subRow);
        }

        return subList;
    }

    /**
     * 两个列表连接为一个列表，连接后的列表的元素个数为最少的源列表的个数。
     * @param list1 List(String[]) 元素为字符串数组的列表1
     * @param startCol1 第一个列表连接的起始行
     * @param list2 List(String[]) 元素为字符串数组的列表2
     * @param startCol2 第二个列表连接的起始行
     * @return List(String[])连接后的列表
     */
    public static List join(List list1, int startCol1, List list2, int startCol2)
    {
        List list = new ArrayList();

        int size1 = list1.size();
        int size2 = list2.size();
        if (size1 == 0 || size2 == 0)
        {
            return list;
        }

        int minSize = size1 > size2 ? size2 : size1;
        for (int i = 0; i < minSize; i++)
        {
            String[] row1 = (String[]) list1.get(i);
            String[] row2 = (String[]) list2.get(i);
            String[] row = new String[row1.length + row2.length - startCol1 - startCol2];
            System.arraycopy(row1, startCol1, row, 0, row1.length - startCol1);
            System.arraycopy(row2, startCol2, row, row1.length - startCol1, row2.length - startCol2);
            list.add(row);
        }

        return list;
    }

    /**
     * 根据两个一致的任意嵌套的Map，把最低层的两个列表一一对应拼接成一个列表，并放入结果Map中。参阅 <code>join(List, int, List, int)</code>
     * @param map1 第一个任意嵌套的Map，最低层为List(String[])
     * @param startCol1 第一个Map中最低层List开始连接的起始列
     * @param map2 第二个任意嵌套的Map，最低层为List(String[])
     * @param startCol2 第二个Map中最低层List开始连接的起始列
     * @return 两个Map拼接后的结果Map，结果Map和参数Map保持一致，仅最低层List为连接后的结果。
     */
    public static Map join(Map map1, int startCol1, Map map2, int startCol2)
    {
        Map map = new LinkedHashMap();
        for (Iterator i = map1.keySet().iterator(); i.hasNext();)
        {
            Object key = i.next();
            Object value1 = map1.get(key);
            Object value2 = map2.get(key);
            if (value1 instanceof List)
            {
                map.put(key, join((List) value1, startCol1, (List) value2, startCol2));
            }
            else
            {
                map.put(key, join((Map) value1, startCol1, (Map) value2, startCol2));
            }
        }
        return map;
    }

    /**
     * 从源列表中规定的起始列往后的所有数据拷贝到目标列表中从起始列开始的位置。
     * 拷贝的源列表的元素数不会超出目标列表的元素数。
     * @param srcList List(String[])源列表
     * @param srcStartCol 源列表起始列
     * @param destList List(String[])目标列表
     * @param destStartCol 目标列表起始列
     */
    public static void copy(List srcList, int srcStartCol, List destList, int destStartCol)
    {
        int srcSize = srcList.size();
        int destSize = destList.size();
        if (srcSize == 0 || destSize == 0)
        {
            return;
        }
        int srcEndCol = ((String[]) srcList.get(0)).length;
        copy(srcList, srcStartCol, srcEndCol, destList, destStartCol);
    }

    /**
     * 从源列表中规定的起始列至终止列的数据拷贝到目标列表中从起始列开始的位置。
     * 拷贝的源列表的元素数不会超出目标列表的元素数。
     * @param srcList List(String[])源列表
     * @param srcStartCol 源列表起始列
     * @param srcEndCol 源列表终止列
     * @param destList List(String[])目标列表
     * @param destStartCol 目标列表起始列
     */
    public static void copy(List srcList, int srcStartCol, int srcEndCol, List destList, int destStartCol)
    {
        int srcSize = srcList.size();
        int destSize = destList.size();
        if (srcSize == 0 || destSize == 0)
        {
            return;
        }
        int size = srcSize > destSize ? destSize : srcSize;

        for (int i = 0; i < size; i++)
        {
            String[] destRow = (String[]) destList.get(i);
            String[] srcRow = (String[]) srcList.get(i);
            System.arraycopy(srcRow, srcStartCol, destRow, destStartCol, srcEndCol - srcStartCol);
        }
    }

    /**
     * 在数据列表的第一列增加自增序号（从1开始）
     * @param list List(String[])数据列表
     * @return 增加了自增序号的数据列表
     */
    public static List addSerialNumber(List list)
    {
        List serialList = new ArrayList();

        for (int i = 0, ii = list.size(); i < ii; i++)
        {
            String[] row = (String[]) list.get(i);
            String[] serialRow = new String[row.length + 1];
            serialRow[0] = "" + (i + 1);
            System.arraycopy(row, 0, serialRow, 1, row.length);
            serialList.add(serialRow);
        }

        return serialList;
    }

    /**
     * 获得列表的列数
     * @param list List(String[]) 元素为String数组的列表
     * @return 列表的列数，列表中没有元素则返回0。
     */
    public static int getColumns(List list)
    {
        int columns = 0;
        if (list.size() > 0)
        {
            columns = ((String[]) list.get(0)).length;
        }
        return columns;
    }

    /**
     * 获得Map或List数据的嵌套层次数。
     * @param obj Map 或 List
     * @return 如果参数为null则返回-1；
     * 	       如果参数为List则返回0；
     *         如果为一层Map（Map中value为List）则返回1；
     *         如果为二层Map（Map中value为Map，再深层为List，则返回2；
     *         ......
     */
    public static int getNestedCount(Object obj)
    {
    	if (obj == null)
    	{
    		return -1;
    	}
    	else if (obj instanceof List)
        {
            return 0;
        }
        else if (obj instanceof Map)
        {
            Map map = (Map) obj;
            if (map.size() > 0)
            {
                Object o = map.get(map.keySet().iterator().next());
                return getNestedCount(o) + 1;
            }
            else
            {
                return 1;
            }
        }
        throw new IllegalArgumentException("参数不为List或Map");
    }

    /**
     * 对dataList从后面数起和sums的列对应的数据合计到sums里。
     * @param sums 合计的值
     * @param dataList 数据列表
     */
    private static void sum(double[][] sums, List dataList)
    {
        for (int i = 0, il = dataList.size(); i < il; i++)
        {
            String[] data = (String[]) dataList.get(i);
            for (int j = data.length - sums[i].length, k = 0; j < data.length; j++, k++)
            {
                if (data[j] != null && data[j].length() > 0)
                    sums[i][k] += Double.parseDouble(data[j]);
            }
        }
    }

    /**
     * 把合计的值根据源数据列表生成一个新的数据列表并添加到Map中
     * @param map 要添加合计值的Map
     * @param key 要添加到Map中的key
     * @param sums 合计的值
     * @param list 后半部分与合计的值对应的数据列表
     */
    private static void putSumData(Map map, Object key, double[][] sums, List list)
    {
        List sumsList = new ArrayList();
        for (int i = 0, il = list.size(); i < il; i++)
        {
            String[] row = (String[]) list.get(i);
            String[] sumsRow = new String[row.length];
            int startCol = row.length - sums[i].length;

            for (int j = 0; j < startCol; j++)
            {
                sumsRow[j] = row[j];
            }
            for (int k = startCol, m = 0; k < row.length; k++, m++)
            {
                sumsRow[k] = String.valueOf(sums[i][m]);
            }
            sumsList.add(sumsRow);
        }
        map.put(key, sumsList);
    }

    /**
     * 复制一份嵌套的Map，复制的数据排除最低层List(String[])从startCol开始之后的数据，其用 null 填充。
     * @param map 被复制的嵌套任意层的Map
     * @param startCol Map中最低层列表的起始列，从该列开始的数据不被复制
     * @return 复制的Map
     */
    private static Map copyNoDataMap(Map map, int startCol)
    {
        Map copyMap = new LinkedHashMap();

        for (Iterator i = map.keySet().iterator(); i.hasNext();)
        {
            Object key = i.next();
            Object value = map.get(key);
            if (value instanceof List)
            {
                List valueList = (List) value;
                List copyList = new ArrayList();
                for (int j = 0, jj = valueList.size(); j < jj; j++)
                {
                    String[] valueRow = (String[]) valueList.get(j);
                    String[] copyRow = new String[valueRow.length];
                    System.arraycopy(valueRow, 0, copyRow, 0, startCol);
                    copyList.add(copyRow);
                }
                copyMap.put(key, copyList);
            }
            else
            {
                copyMap.put(key, copyNoDataMap((Map) value, startCol));
            }
        }

        return copyMap;
    }

    /**
     * 将List(String)转换为String[]
     * @param srcList List(String)
     * @return String[]
     */
    public static String[] listToStrings(List srcList)
    {
        String[] stringArray = new String[srcList.size()];
        srcList.toArray(stringArray);
        return stringArray;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        List list = new ArrayList();
        list.add(new String[] { "地市1", "分局1", "1", "1" });
        list.add(new String[] { "地市1", "分局2", "2", "1" });
        list.add(new String[] { "地市1", "分局3", "3", "1" });
        list.add(new String[] { "地市2", "分局1", "4", "1" });
        list.add(new String[] { "地市2", "分局2", "5", "1" });
        list.add(new String[] { "地市2", "分局3", "6", "1" });
        Map map = toStringsListMap(list, 1, 2, 4);
    }
}
