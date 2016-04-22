/**
 * 
 */
package com.wlt.webm.scputil;

import java.util.*;

/**
 * ������
 */
public class Queue{
	
	/**
	 * �洢���������
	 */
	private LinkedList list = new LinkedList();
	
	/**
	 * ���췽��
	 */
	public Queue() {
    }
	
    /**
     * ����һ��Ԫ�ص�����ĩβ
     * @param x
     */
    public synchronized void put(Object x) {
        list.addLast(x);
    }
    
    /**
     * �Ӷ�����ȡ��(ɾ��)һ��Ԫ��,�������Ϊ��,�������쳣
     * @return
     */
    public synchronized Object get() {
        if (this.empty())
            throw new java.lang.RuntimeException("EmptyQueueException");
        return list.removeFirst();
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
    public int search(Object x) {
        return list.indexOf(x);
    }
}
