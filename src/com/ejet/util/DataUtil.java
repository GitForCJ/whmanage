package com.ejet.util;

import java.util.*;

/**
 * ����ת��������
 * Company��������###EJET###���޹�˾
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public final class DataUtil
{
    /**
     * ����С������
     */
    private static final String SUBTOTAL = "�� С�� ��";

    /**
     * ���ݺϼ�����
     */
    private static final String TOTAL = "�� �ϼ� ��";

    /**
     * ����Map����СǶ����
     */
    private static final int MIN_NESTED_COUNT = 1;

    /**
     * ���಻����ʵ����
     */
    private DataUtil()
    {
    }

    /**
     * ��List(String[])ת����Map(String, String)��
     * List�е�һ����ΪMap��key��
     * List�еڶ�����ΪMap��value��
     * @param srcList List(String[])
     * @return Map(String, String)
     */
    public static Map toStringMap(List srcList)
    {
        return toStringMap(srcList, 0, 1);
    }

    /**
     * ��List(String[])ת����Map(String, String)��
     * List��ָ������ΪMap��key��
     * List��ָ������ΪMap��value��
     * @param srcList List(String[])
     * @param keyIndex List�н���ת����Map��key���У�0Ϊ��һ�У�
     * @param valueIndex List�н���ת����Map��value���У�0Ϊ��һ�У�
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
     * ��List(String[])ת����Map(String, List(String))��
     * List�е�һ����ΪMap��key��
     * List�еڶ�����ΪMap��value��
     * @param srcList List(String[])
     * @return Map(String, List(String))
     */
    public static Map toStringListMap(List srcList)
    {
        return toStringListMap(srcList, 0, 1);
    }

    /**
     * ��List(String[])ת����Map(String, List(String))��
     * List��ָ������ΪMap��key��
     * List��ָ������ΪMap��value��
     * @param srcList List(String[])
     * @param keyIndex List�н���ת����Map��key���У�0Ϊ��һ�У�
     * @param valueIndex List�н���ת����Map��value���У�0Ϊ��һ�У�
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
     * ��List(String[])ת����Map(String, String[])��
     * List�е�һ����ΪMap��key��
     * List�еڶ��е����һ�е�������ΪMap��value��
     * @param srcList List(String[])
     * @return Map(String, String[])
     */
    public static Map toStringsMap(List srcList)
    {
        return toStringsMap(srcList, 0, 1);
    }

    /**
     * ��List(String[])ת����Map(String, String[])��
     * List��ָ������ΪMap��key��
     * List��ָ����ʼ�е����һ��֮���������ΪMap��value��
     * @param srcList List(String[])
     * @param keyIndex List�н���ת����Map��Key���У�0Ϊ��һ�У�
     * @param valueStart List�н���ת����Map��value����ʼ�У���0��ʼ��������ʼ�У�
     * @return Map(String, String[])
     */
    public static Map toStringsMap(List srcList, int keyIndex, int valueStart)
    {
        int valueEnd = getColumns(srcList);
        return toStringsMap(srcList, keyIndex, valueStart, valueEnd);
    }

    /**
     * ��List(String[])ת����Map(String, String[])��
     * List��ָ������ΪMap��key��
     * List��ָ����ʼ�кͽ�����֮���������ΪMap��value��
     * @param srcList List(String[])
     * @param keyIndex List�н���ת����Map��Key���У�0Ϊ��һ�У�
     * @param valueStart List�н���ת����Map��value����ʼ�У���0��ʼ��������ʼ�У�
     * @param valueEnd List�н���ת����Map��value�Ľ����У���0��ʼ�������������У�
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
     * ��List(String[])ת����Map(String, List(String[]))��
     * List�е�һ����ΪMap��key��
     * List�еڶ��е����һ�е�������ΪMap��value��
     * @param srcList List(String[])
     * @return Map(String, List(String[]))
     */
    public static Map toStringsListMap(List srcList)
    {
        return toStringsListMap(srcList, 0, 1);
    }

    /**
     * ��List(String[])ת����Map(String, List(String[]))��
     * List��ָ������ΪMap��key��
     * List��ָ����ʼ�е����һ��֮���������ΪMap��value��
     * @param srcList List(String[])
     * @param keyIndex List�н���ת����Map��Key���У�0Ϊ��һ�У�
     * @param valueStart List�н���ת����Map��value����ʼ�У���0��ʼ��������ʼ�У�
     * @return Map(String, List(String[]))
     */
    public static Map toStringsListMap(List srcList, int keyIndex, int valueStart)
    {
        int valueEnd = getColumns(srcList);
        return toStringsListMap(srcList, keyIndex, valueStart, valueEnd);
    }

    /**
     * ��List(String[])ת����Map(String, List(String[]))��
     * List��ָ������ΪMap��key��
     * List��ָ����ʼ�кͽ�����֮���������ΪMap��value��
     * @param srcList List(String[])
     * @param keyIndex List�н���ת����Map��Key���У�0Ϊ��һ�У�
     * @param valueStart List�н���ת����Map��value����ʼ�У���0��ʼ��������ʼ�У�
     * @param valueEnd List�н���ת����Map��value�Ľ����У���0��ʼ�������������У�
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
     * ��List(String[])ת����ָ��Ƕ�ײ������Map��
     * ����Ƕ�׵Ĳ������Ĭ�Ͻ�List��һ����Ϊ��һ��Map��key����List�ڶ�����Ϊ�ڶ���Map��key���Դ����ƣ�ʣ�µĵ�һ����Ϊ���һ��Map��value��
     * @param nestedCount ���MapǶ�׵Ĳ����
     * @param srcList List(String[])
     * @return Ƕ�ײ����Ϊ1ʱ������Map(String, String)��
     *         Ƕ�ײ����Ϊ2ʱ������Map(String, Map(String, String))��
     *         Ƕ�ײ����Ϊ3ʱ������Map(String, Map(String, Map(String, String)))��
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
     * ��List(String[])ת����ָ��Ƕ�ײ������Map��
     * ����Ƕ�׵Ĳ������Ĭ�Ͻ�List��һ����Ϊ��һ��Map��key����List�ڶ�����Ϊ�ڶ���Map��key���Դ����ƣ�ʣ�µ���������Ϊ���һ��Map��value��
     * @param nestedCount ���MapǶ�׵Ĳ����
     * @param srcList List(String[])
     * @return Ƕ�ײ����Ϊ1ʱ������Map(String, List(String[]))��
     *         Ƕ�ײ����Ϊ2ʱ������Map(String, Map(String, List(String[])))��
     *         Ƕ�ײ����Ϊ3ʱ������Map(String, Map(String, Map(String, List(String[]))))��
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
     * ����ԴMap��ԴList����һ���µ�Map������Map������Map��keyΪԴMap��key��
     * ��Map��valueΪԴMap��List��ÿ��Ԫ�ض�Ӧ��ԴList��һ����������ɵ�List��
     * @param strListMap Map(String, List(String))
     * @param srcList List(String[])
     * @return Map(String, List(String[]))ԴMap�е�key��ԴList�е�String[]��ɡ�
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
     * ���ݵ�һ��Map�е�value�͵ڶ���Map�е�key�Ķ�Ӧ��ϵ�ϲ�����MapΪһ��Map
     * @param firstMap Map(String, List(String))��һ��Map
     * @param secondMap Map(String, Object)�ڶ���Map
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
     * ��Map�е�ֵList(String[])�����кϼƣ���ÿһ��List�������һ���ϼƵ��в���Map������һ���ܵĺϼ��
     * @param stringsListMap Map(String, List(String[]))Ҫ�ϼƵ�Map
     * @param startCol Ҫ�ϲ�����ʼ�У�0��ʾ��һ��
     * @return Map(String, List(String[])) �Ѿ��ϼ��˵�����
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
     * ��Map�е�ֵList(String[])�����кϼƣ���ÿһ��List�������һ���ϼƵ��в���Map������һ���ܵĺϼ��
     * @param stringsListMap Map(String, List(String[]))Ҫ�ϼƵ�Map
     * @param startCol Ҫ�ϲ�����ʼ�У�0��ʾ��һ��
     * @return Map(String, List(String[])) �Ѿ��ϼ��˵�����
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
     * ����������Map����ײ�Map��ÿ��Listֵ��һ������£��С�����һ������
     * ��Map�е����е�ֵ�����ۼӵ���һ�����Ƶ�List�в���ӵ�Map�У������MapҲ��һ���ۼ��
     * �ı���Դ����
     * @param dataMap Ƕ����������Map����Ͳ�ΪList(String[])
     * @param startCol �������Map��ֵList�ϼƵ���ʼ��
     * @return �ϼƺ��Map
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

        Map map = (Map) dataMap.get(dataMap.keySet().iterator().next());//�������Map
        List list = (List) map.get(map.keySet().iterator().next());//���������List
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
                sum(sums, (List) map.get("�ϼ�"));
        }

        if (dataMap.size() > 1)
        {
            map = new LinkedHashMap();
            putSumData(map, "", sums, list);
            dataMap.put("�ϼ�", map);
        }

        return dataMap;
    }

    /**
     * ���������Map�����ݽ��кϼƣ��ȶ��б����С�ϼƣ��ٶ�Map�е�ÿ��ֵ�ϼ��ۼӵ����һ���ϼ�Ԫ���С�
     * �ı���Դ���ݡ�
     * @param dataMap ����Map
     * @param startCol �ϼ��б����ʼ��
     * @return �ϼƺ��Map
     */
    public static Map sumColToMap(Map dataMap, int startCol)
    {
        if (dataMap.size() == 0)
        {
            return dataMap;
        }

        sumColForList(dataMap, startCol);//�кϼ�

        Object obj = dataMap.get(dataMap.keySet().iterator().next());
        if (obj instanceof List)
        {
            return sumList(dataMap, startCol);//�б�ϼ�
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
        	dataMap.put("�ϼ�", sumMap);	
        }

        return dataMap;
    }

    /**
     * ���Map��ֵList��һ������£��С�����һ������
     * ��List�����е�ֵ�����ۼӵ���һ�����Ƶ�List�в���ӵ�Map�С�
     * ��Map�е�Ԫ�ظ���Ϊ1���򲻽��кϼơ��ı���Դ���ݡ�
     * @param dataListMap Map(String, List(String))) ����
     * @param startCol �ϼƵ���ʼ��
     * @return Map ���кϼ�ֵ��Map
     */
    private static Map sumList(Map dataListMap, int startCol)
    {
        if (dataListMap.size() <= 1)
        {
            return dataListMap;
        }

        List list = (List) dataListMap.get(dataListMap.keySet().iterator().next());//ȡ����һ��List
        int listCols = ((String[]) list.get(0)).length;
        double[][] sums = new double[list.size()][listCols - startCol];

        Iterator it = dataListMap.keySet().iterator();
        while (it.hasNext())
        {
            sum(sums, (List) dataListMap.get(it.next()));
        }

        putSumData(dataListMap, "�ϼ�", sums, list);

        return dataListMap;
    }

    /**
     * �ϼ�����Ƕ�׵�Map����ײ�List(String[])�����ݣ���List���һ��Ԫ�ؼ���ϼ��
     * @param map ����Ƕ�׵�Map����Ͳ�ΪList(String[])
     * @param startCol �ϼƵ���ʼ��
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
     * ��Map�е�ֵList(String[])�����кϼƣ�����List�������һ���ϼƵ��С�
     * @param stringsListMap Map(String, List(String[]))Ҫ�ϼƵ�Map
     * @param startCol Ҫ�ϲ�����ʼ�У�0��ʾ��һ��
     * @return Map(String, List(String[])) �Ѿ��ϼ��˵�����
     */
    public static Map sumRow(Map stringsListMap, int startCol)
    {
        return sumRow(stringsListMap, startCol, 1);
    }

    /**
     * ��Map�е�ֵList(String[])��spaceΪ��������кϼƣ�����List�������space�кϼ��
     * @param stringsListMap Map(String, List(String[]))Ҫ�ϼƵ�Map
     * @param startCol Ҫ�ϲ�����ʼ�У�0��ʾ��һ��
     * @param space ���
     * @return Map(String, List(String[])) �Ѿ��ϼ��˵�����
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
     * ��Դ���������ݵ�����������������һ�кϼ�����ֻ��һ�������򲻽��кϼơ�<br>
     * ����ϼƵ���ʼ��>0����ϼ����е�һ�� String[0] Ϊ"�ϼ�"���ϼ����� double ���ͺϼƣ�������Ϊ ""��
     * @param srcList List(String[])Դ����
     * @param startCol �ϼƵ���ʼ�У�����������һ��Ϊ0��
     * @return �������кϼƵ�Դ����
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
     * ��Դ���ݵ�ÿһ�н��кϼƲ���ӵ����е����һ��
     * @param srcList List(String[]) Դ����
     * @param startCol �ϲ�����ʼ��
     * @return List(String[]) �ϼƺ������
     */
    public static List sumRow(List srcList, int startCol)
    {
        return sumRow(srcList, startCol, 1);
    }

    /**
     * ��Դ���ݵ�ÿһ����space�ļ�����кϼƲ���ӵ����е����space��
     * @param srcList List(String[])Դ����
     * @param startCol �ϼƵ���ʼ��
     * @param space ���
     * @return List(String[])�ϼƺ������
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
     * ��List(String[])ĳһ�е�����ת����String[]
     * @param srcList List(String[])Դ����
     * @param colIndex Ҫת�����У�0��ʾ��һ�С�
     * @return String[]�ַ�������
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
     * ��List(String[])ĳһ�е�����ȥ�ظ�ת����String[]
     * @param srcList List(String[])Դ����
     * @param colIndex Ҫת�����У�0��ʾ��һ�С�
     * @return String[]�ַ�������
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
     * ��չ�б�
     * @param srcList ����չ��Դ�б�List(String[])
     * @param holdStart Դ�б���������ʼ�����ݣ���0��ʼ��������ʼ�У�
     * @param holdEnd Դ�б������Ľ��������ݣ���0��ʼ�������������У�
     * @param extendData ��ӦԴ�б���չ�е���������
     * @param extendIndex Դ�б��б���չ���У�0Ϊ��һ�У�
     * @param extendValueStart Դ�б��б���չ��������ʼ�У���0��ʼ��������ʼ�У�
     * @param extendValueEnd Դ�б��б���չ��������ʼ�У���0��ʼ�������������У�
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
     * ����ԴList���ݺ����еĵڶ����������Ӧ���һ����չ�����ݡ�<br>
     * 
     * Դ���ݾ�����<br>
     * "����","�칫","10"<br>
     * "����","סլ","20"<br>
     * "����","����","30"<br>
     * "����","�칫","40"<br><br>
     * 
     * ��չ�����ݾ�����<br>
     * "�칫","סլ","����"<br><br>
     * 
     * ���ɵĽ��List���ݣ�<br>
     * "����","10","20","30"<br>
     * "����","40",null,null<br>
     * 
     * @param srcList List(String[3])Դ����
     * @param extendData String[]��չ�е�����
     * @return List(String[]) ��չ�������
     */
    public static List getExtendedList(List srcList, String[] extendData)
    {
        if (srcList.size() == 0)
            return new ArrayList();

        int extendLen = ((String[]) srcList.get(0)).length - 2;//ÿһ����չ���Ӧ�����ݳ���
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
     * ���������б���һ��Map�������б��һ����ΪMap��key��
     * ����������һ����չ���б��μ� getExtendedList(List, String[]) ��������
     * @param srcList �����б�
     * @param extendData ��չ�е�����
     * @return ��չ���ݺ��Map
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
     * ���������б���һ��Ƕ�׵�Map�������б��startCol��֮ǰ��ΪǶ�׵�Map��
     * ����������һ����չ���б��μ� getExtendedList(List, String[]) ��������
     * @param srcList �����б�
     * @param startCol ��ʼ�У���һ��Ϊ0��������ʼ�п�ʼ���б���Ϊ��չ�б�ǰ���������ΪMap
     * @param extendData ��չ�е�����
     * @return ��չ���ݺ��Map
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
     * �Զ��Ƕ�׵�ԴMap����ײ��List���ݺ����еĵڶ����������Ӧ���һ����չ�����ݡ������getExtendedList()������
     * @param srcMap Ƕ�׶���Map����ײ�Map��valueΪList(String[])
     * @param extendData ��List(String[])�ڶ������ݵĺ�����չ�Ķ�Ӧ����
     * @return ��չ�������
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
     * ����allFirstCols���ݺ�srcList��һ�ж�Ӧ�Ĺ�ϵ����һ�������ݡ�
     * @param allFirstCols ��Ӧ��Դ���ݵ�һ�е���������
     * @param srcList List(String[]) Դ����
     * @param rowLen ÿһ�еĳ���
     * @return List(String[])���ɵ�������
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
     * ���ݸ��������ݻ��һ������˵����������ݵ�Map
     * @param allKeys Ҫ����Map�����е�keyֵ
     * @param allFirstCols Ҫ����Map��value(List)�ж�Ӧ�����е�һ�е�ֵ
     * @param dataMap Map(String, List(String[]))������������
     * @param rowLen Map��value������
     * @return Map(String, List(String[]))����������
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
     * ��ԴMap�ж�Ӧ��key�Լ�Ƕ��Map��key��List�ĵ�һ�н������䡣
     * @param filledDatas �������������
     * @param srcMap �������Map����Ƕ�׶��Map����Ͳ����ΪList��
     * @param rowLen ��Ͳ�List���г�
     * @return ������Map
     */
    public static Map getFilledMap(String[][] filledDatas, Map srcMap, int rowLen)
    {
        return (Map) getFilledObject(filledDatas, srcMap, rowLen, filledDatas.length);
    }

    /**
     * ����Object�����е����ݣ�Object��ΪList��Map��
     * @param filledDatas �������������
     * @param obj ��������List(String[])��Ƕ�׵�Map����Ͳ����ΪList(String[]��
     * @param rowLen List(String[])������
     * @param level �����������ڵ�Map�еĲ����
     * @return �����Object
     */
    private static Object getFilledObject(String[][] filledDatas, Object obj, int rowLen, int level)
    {
        if (level <= 1)//��ײ��List
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
     * �������ݡ�һ�����ڷ־��������ϻ��ܵ���Ӧ�ĵ��С�
     * @param totUpMap Map(String, String) ���ܵĹ������ݡ����磺keyΪ���У�valueΪ�־֡�
     * @param dataList List(String[]) Ҫ���ܵ����ݣ����е�һ�ж�Ӧ���ܹ������ݵ�value��
     * @return List(String[]) ���ܺ�����ݣ���һ�г�Ϊ���ܹ��������е�key��
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
     * �������ݡ�һ�����ڷ־��������ϻ��ܵ���Ӧ�ĵ��С�
     * @param totUpMap Map(String, String) ���ܵĹ������ݡ����磺keyΪ���У�valueΪ��Ӧ�ķ־��б�
     * @param dataListMap �����ܵ����ݣ�Ƕ��������Map����Ͳ����ΪList(String[])��
     *          ����key��Ӧ���ܹ������ݵ�value���û��������е�ValueӦ����һ�¡�
     * @return Map(String, List(String[])) ���ܺ�����ݣ�keyΪ���ܹ��������е�key��
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
     * ����Դ����List��ĳһ�е��ַ��������������򷵻��ַ�������List�е����������򷵻�-1��
     * @param srcList Ҫ������Դ����List(String[])
     * @param searchString Ҫ�������ַ���
     * @param searchColIndex Ҫ������Դ���ݵ��У�0��ʾ��һ�У�
     * @return �����ַ�������Դ�����е�������0��ʾ��һ�У���δ�������򷵻�-1��
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
     * �����ַ���������������һ���б��б�ĵ�һ��Ϊ�����ֵ��������Ϊ null��
     * @param stringArray ��Ӧ�б�ĵ�һ��
     * @param cols �б������
     * @return List(String[])�б�
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
     * ��Դ�б�����ʼ�п�ʼ���һ�����б�
     * @param list List(String[]) Դ�б�
     * @param startCol Դ�б����ʼ�У�������
     * @return ���б�
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
     * ��Դ�б�����ʼ�е������л��һ�����б�
     * @param list List(String[]) Դ�б�
     * @param startCol Դ�б����ʼ�У�������
     * @param endCol Դ�б�Ľ����У���������
     * @return List(String[]) ���б�
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
     * ���ݲ�������д�Դ�б��л��һ�����б�
     * @param list List(String[]) Դ�б�
     * @param cols Դ�б��еĲ��������
     * @return ���б�
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
     * �����б�����Ϊһ���б����Ӻ���б��Ԫ�ظ���Ϊ���ٵ�Դ�б�ĸ�����
     * @param list1 List(String[]) Ԫ��Ϊ�ַ���������б�1
     * @param startCol1 ��һ���б����ӵ���ʼ��
     * @param list2 List(String[]) Ԫ��Ϊ�ַ���������б�2
     * @param startCol2 �ڶ����б����ӵ���ʼ��
     * @return List(String[])���Ӻ���б�
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
     * ��������һ�µ�����Ƕ�׵�Map������Ͳ�������б�һһ��Ӧƴ�ӳ�һ���б���������Map�С����� <code>join(List, int, List, int)</code>
     * @param map1 ��һ������Ƕ�׵�Map����Ͳ�ΪList(String[])
     * @param startCol1 ��һ��Map����Ͳ�List��ʼ���ӵ���ʼ��
     * @param map2 �ڶ�������Ƕ�׵�Map����Ͳ�ΪList(String[])
     * @param startCol2 �ڶ���Map����Ͳ�List��ʼ���ӵ���ʼ��
     * @return ����Mapƴ�Ӻ�Ľ��Map�����Map�Ͳ���Map����һ�£�����Ͳ�ListΪ���Ӻ�Ľ����
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
     * ��Դ�б��й涨����ʼ��������������ݿ�����Ŀ���б��д���ʼ�п�ʼ��λ�á�
     * ������Դ�б��Ԫ�������ᳬ��Ŀ���б��Ԫ������
     * @param srcList List(String[])Դ�б�
     * @param srcStartCol Դ�б���ʼ��
     * @param destList List(String[])Ŀ���б�
     * @param destStartCol Ŀ���б���ʼ��
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
     * ��Դ�б��й涨����ʼ������ֹ�е����ݿ�����Ŀ���б��д���ʼ�п�ʼ��λ�á�
     * ������Դ�б��Ԫ�������ᳬ��Ŀ���б��Ԫ������
     * @param srcList List(String[])Դ�б�
     * @param srcStartCol Դ�б���ʼ��
     * @param srcEndCol Դ�б���ֹ��
     * @param destList List(String[])Ŀ���б�
     * @param destStartCol Ŀ���б���ʼ��
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
     * �������б�ĵ�һ������������ţ���1��ʼ��
     * @param list List(String[])�����б�
     * @return ������������ŵ������б�
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
     * ����б������
     * @param list List(String[]) Ԫ��ΪString������б�
     * @return �б���������б���û��Ԫ���򷵻�0��
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
     * ���Map��List���ݵ�Ƕ�ײ������
     * @param obj Map �� List
     * @return �������Ϊnull�򷵻�-1��
     * 	       �������ΪList�򷵻�0��
     *         ���Ϊһ��Map��Map��valueΪList���򷵻�1��
     *         ���Ϊ����Map��Map��valueΪMap�������ΪList���򷵻�2��
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
        throw new IllegalArgumentException("������ΪList��Map");
    }

    /**
     * ��dataList�Ӻ��������sums���ж�Ӧ�����ݺϼƵ�sums�
     * @param sums �ϼƵ�ֵ
     * @param dataList �����б�
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
     * �ѺϼƵ�ֵ����Դ�����б�����һ���µ������б���ӵ�Map��
     * @param map Ҫ��Ӻϼ�ֵ��Map
     * @param key Ҫ��ӵ�Map�е�key
     * @param sums �ϼƵ�ֵ
     * @param list ��벿����ϼƵ�ֵ��Ӧ�������б�
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
     * ����һ��Ƕ�׵�Map�����Ƶ������ų���Ͳ�List(String[])��startCol��ʼ֮������ݣ����� null ��䡣
     * @param map �����Ƶ�Ƕ��������Map
     * @param startCol Map����Ͳ��б����ʼ�У��Ӹ��п�ʼ�����ݲ�������
     * @return ���Ƶ�Map
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
     * ��List(String)ת��ΪString[]
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
        list.add(new String[] { "����1", "�־�1", "1", "1" });
        list.add(new String[] { "����1", "�־�2", "2", "1" });
        list.add(new String[] { "����1", "�־�3", "3", "1" });
        list.add(new String[] { "����2", "�־�1", "4", "1" });
        list.add(new String[] { "����2", "�־�2", "5", "1" });
        list.add(new String[] { "����2", "�־�3", "6", "1" });
        Map map = toStringsListMap(list, 1, 2, 4);
    }
}
