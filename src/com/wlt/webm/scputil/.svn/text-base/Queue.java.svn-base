/**
 * 
 */
package com.wlt.webm.scputil;

import java.util.*;

/**
 * 队列类
 */
public class Queue{
	
	/**
	 * 存储对象的链表
	 */
	private LinkedList list = new LinkedList();
	
	/**
	 * 构造方法
	 */
	public Queue() {
    }
	
    /**
     * 插入一个元素到队列末尾
     * @param x
     */
    public synchronized void put(Object x) {
        list.addLast(x);
    }
    
    /**
     * 从队列中取出(删除)一个元素,如果队列为空,则引发异常
     * @return
     */
    public synchronized Object get() {
        if (this.empty())
            throw new java.lang.RuntimeException("EmptyQueueException");
        return list.removeFirst();
    }
    
    /**
     * 判断队列是否为空,为空返回真,否则返回假
     * @return
     */
    public boolean empty() {
        return list.isEmpty();
    }
    
    /**
     * 清空队列
     */
    public synchronized void clear() {
        list.clear();
    }
    /**
     *  查找距队首最近的元素的位置，若不存在，返回-1
     * @param x
     * @return
     */
    public int search(Object x) {
        return list.indexOf(x);
    }
}
