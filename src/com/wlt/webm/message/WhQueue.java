
package com.wlt.webm.message;

import java.util.HashMap;

import com.commsoft.epay.util.logging.Log;

import whmessgae.TradeMsg;

/**
 * ������
 * �������ڣ�2013-11-26
 * Company�����������Ƽ����޹�˾
 * Copyright: Copyright (c) 2013
 * @author caiSJ
 */
public class WhQueue{
	
	/**
	 * �洢���������
	 */
//	private LinkedList list = new LinkedList();
	public  HashMap<String, TradeMsg> list =new HashMap<String, TradeMsg>();
	
	/**
	 * ���췽��
	 */
	public WhQueue() {
    }
	
    /**
     * ����һ��Ԫ�ص�����ĩβ
     * @param x
     */
    public synchronized void put(String key, TradeMsg x) {
    	Log.info("start put key");
        list.put(key, x);
        Log.info("put key:"+key);
    }
    
    /**
     * �Ӷ�����ȡ��(ɾ��)һ��Ԫ��,�������Ϊ��,�������쳣
     * @return
     */
    public synchronized TradeMsg get(String key) {
    	Log.info("start get key:"+key);
        if (this.empty())
            return null;
        Log.info("get key "+key);
        return list.remove(key);
    }
    
    /**
     * �ж϶����Ƿ�Ϊ��,Ϊ�շ�����,���򷵻ؼ�
     * @return
     */
    public boolean empty() {
        return list.isEmpty();
    }
    
    /**
     * ��ն���
     */
    public synchronized void clear() {
        list.clear();
    }
    /**
     *  ���Ҿ���������Ԫ�ص�λ�ã��������ڣ�����-1
     * @param x
     * @return
     */
//    public int search(Object x) {
//        return list.indexOf(x);
//    }
}
