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

public class MemcacheConfig {  // 创建全局的唯一实例
    private static MemCachedClient mcc = new MemCachedClient();
    
    private static MemcacheConfig MemcacheConfig = null;
    
    // 设置与缓存服务器的连接池
    static {

        // 压缩设置，超过指定大小（单位为K）的数据都会被压缩
        //mcc.setCompressEnable( true );
        //mcc.setCompressThreshold( 64 * 1024 );
    }
    
    /**
     * 保护型构造方法，不允许实例化！
     *
     */
    private MemcacheConfig()
    {
       Log.info("消息缓存池开始启动...");
        // 服务器列表和其权重
        String[] servers = {"localhost:11211"};
        Integer[] weights = {3};

        // 获取socke连接池的实例对象
        SockIOPool pool = SockIOPool.getInstance();

        // 设置服务器信息
        pool.setServers( servers );
        pool.setWeights( weights );

        // 设置初始连接数、最小和最大连接数以及最大处理时间
        pool.setInitConn( 5 );
        pool.setMinConn( 100 );
        pool.setMaxConn( 3000 );
        pool.setMaxIdle( 1000 * 60 * 3 );

        // 设置主线程的睡眠时间
        pool.setMaintSleep( 30 );

        // 设置TCP的参数，连接超时等
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

        // 初始化连接池
        pool.initialize();
        Log.info("消息缓存池启动完成...");
    }
    
    /**
     * 获取唯一实例.
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
     * 添加一个指定的值到缓存中.
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
     * 根据指定的关键字获取对象.
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
