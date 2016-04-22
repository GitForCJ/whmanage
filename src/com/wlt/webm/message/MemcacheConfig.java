package com.wlt.webm.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.util.Tools;

public class MemcacheConfig {  // ����ȫ�ֵ�Ψһʵ��
    private static MemCachedClient mcc = new MemCachedClient();
    
    private static MemcacheConfig MemcacheConfig = null;
    
    // �����뻺������������ӳ�
    static {

        // ѹ�����ã�����ָ����С����λΪK�������ݶ��ᱻѹ��
        //mcc.setCompressEnable( true );
        //mcc.setCompressThreshold( 64 * 1024 );
    }
    
    /**
     * �����͹��췽����������ʵ������
     *
     */
    private MemcacheConfig()
    {
       Log.info("��Ϣ����ؿ�ʼ����...");
        // �������б����Ȩ��
        String[] servers = {"localhost:11211"};
        Integer[] weights = {3};

        // ��ȡsocke���ӳص�ʵ������
        SockIOPool pool = SockIOPool.getInstance();

        // ���÷�������Ϣ
        pool.setServers( servers );
        pool.setWeights( weights );

        // ���ó�ʼ����������С������������Լ������ʱ��
        pool.setInitConn( 5 );
        pool.setMinConn( 100 );
        pool.setMaxConn( 3000 );
        pool.setMaxIdle( 1000 * 60 * 3 );

        // �������̵߳�˯��ʱ��
        pool.setMaintSleep( 30 );

        // ����TCP�Ĳ��������ӳ�ʱ��
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

        // ��ʼ�����ӳ�
        pool.initialize();
        Log.info("��Ϣ������������...");
    }
    
    /**
     * ��ȡΨһʵ��.
     * @return
     */
    public static MemcacheConfig getInstance()
    {
		synchronized (MemcacheConfig.class) {
			if(null == MemcacheConfig){
				MemcacheConfig = new MemcacheConfig();
			}
			
			return MemcacheConfig;
		}
    }
    
    /**
     * ���һ��ָ����ֵ��������.
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key, Object value)
    {
        return mcc.add(key, value,Tools.getDate(null, 1));
    }
    public boolean AddOuFei(String key, Object value)
    {
        return mcc.add(key, value,Tools.getDate(null, 3));
    }
    public boolean addAccountName(String key, Object value)
    {
        return mcc.add(key, value,Tools.getDate(null, 5));
    }
    
    public boolean setAccountName(String key, Object value)
    {
        return mcc.set(key, value,Tools.getDate(null, 5));
    }
    
    public boolean addTP(String key, Object value)
    {
        return mcc.add(key, value);
    }
    
    public boolean add(String key, Object value, Date expiry)
    {
        return mcc.add(key, value, Tools.getDate(expiry, 3));
    }
    
    public boolean replace(String key, Object value)
    {
        return mcc.replace(key, value);
    }
    
    public boolean replace(String key, Object value, Date expiry)
    {
        return mcc.replace(key, value, expiry);
    }
    
    public boolean delete(String key){
    	return mcc.delete(key);
    }
    
    public boolean set(String key, Object value){
    	return mcc.set(key, value);
    }
    
    public boolean set(String key, Object value, Date expiry){
    	return mcc.set(key, value, expiry);
    }
    
    /**
     * ����ָ���Ĺؼ��ֻ�ȡ����.
     * @param key
     * @return
     */
    public TradeMsg get(String key)
    {
    	Object obj=mcc.get(key);
    	if(null!=obj&&obj instanceof TradeMsg){
            return (TradeMsg)obj;
    	}
        return null;
    }
    
    public boolean get1(String key)
    {
    	if(null!=mcc.get(key)){
    	      return true;	
    	}else{
    		return false;
    	}
    }
    public String getAccountName(String key)
    {
    	if(null!=mcc.get(key)){
    	      return mcc.get(key).toString();
    	}else{
    		return "***";
    	}
    }
    public List<String[]> getJunBaoAcc(String key){
    	Object obj=mcc.get(key);
    	if(null!=obj){
            return (List<String[]>)obj;
    	}
        return null;
    }
    
    public TPForm getTP(String key)
    {
    	Object obj=mcc.get(key);
    	if(null!=obj&&obj instanceof TPForm){
            return (TPForm)obj;
    	}
        return null;
    }
    
    public boolean addMposUUID(String key, HashMap<String,String> value)
    {
        return mcc.add(key, value);
    }
    
    public HashMap<String,String> getMposUUID(String key)
    {
    	Object obj=mcc.get(key);
    	if(null!=obj&&obj instanceof HashMap){
            return (HashMap<String,String>)obj;
    	}
        return null;
    }
    
    public boolean add(String key, Object value,int minute)
    {
        return mcc.add(key, value,Tools.getDate(null, minute));
    }
    
    
    public static void main(String[] args)
    {
//        MemcacheConfig cache = MemcacheConfig.getInstance();
//    	MemcacheConfig.getInstance().add("hello", 234);
//        System.out.println("get value : " + MemcacheConfig.getInstance().get("hello"));
        
//        MemcacheConfig.getInstance().delete("hello");
//        
//        System.out.println("get value : " + MemcacheConfig.getInstance().get("hello"));
//        
//        MemcacheConfig.getInstance().set("hi", "wwws");
//        
//        System.out.println("get value : " + MemcacheConfig.getInstance().get("hi"));
    	System.out.println(MemcacheConfig.getInstance().addAccountName("123", "zs"));
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	System.out.println(MemcacheConfig.getInstance().addAccountName("123", "ls"));
    	System.out.println(MemcacheConfig.getInstance().getAccountName("123"));
        
    }
    }
