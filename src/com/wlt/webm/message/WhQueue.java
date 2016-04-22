
package com.wlt.webm.message;

import java.util.HashMap;

import com.commsoft.epay.util.logging.Log;

import whmessgae.TradeMsg;

/**
 * 队列类
 * 创建日期：2013-11-26
 * Company：深圳市万恒科技有限公司
 * Copyright: Copyright (c) 2013
 * @author caiSJ
 */
public class WhQueue{
	
	/**
	 * 存储对象的链表
	 */
//	private LinkedList list = new LinkedList();
	public  HashMap<String, TradeMsg> list =new HashMap<String, TradeMsg>();
	
	/**
	 * 构造方法
	 */
	public WhQueue() {
    }
	
    /**
     * 插入一个元素到队列末尾
     * @param x
     */
    public synchronized void put(String key, TradeMsg x) {
    	Log.info("start put key");
        list.put(key, x);
        Log.info("put key:"+key);
    }
    
    /**
     * 从队列中取出(删除)一个元素,如果队列为空,则引发异常
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
//    public int search(Object x) {
//        return list.indexOf(x);
//    }
}
