package com.wlt.webm.pccommon;

import com.wlt.webm.util.Tools;


/**
 * 循环获得字符串（该类是线程安全的）<br>
 */
public class UniqueNo {
    /**
     * 类实例
     */
    private static UniqueNo uNo=new UniqueNo();
 
    
    /**
     * 序号
     */
	private int no=0;
    /**
     * 序号
     */
	private int no1=0;
    /**
     * 序号(最大两位)
     */
    private int no2=0;
    
    /**
     * 序号(最大6位)
     */
    private int no6 = 0;
    
    /**
     * 序号（最大7位）
     */
    private int no7=0;
    
    /**
     * 两位数锁
     */
    private Object no2Lock=new Object();
    /**
     * 一位数锁
     */
    private Object no1Lock=new Object();
    
    /**
     * 六位数锁
     */
    private Object no6Lock=new Object();
    
    /**
     * 七位数锁
     */
    private Object no7Lock=new Object();
    /**
     * 私有构造方法，不允许外部实例化。
     */
    private UniqueNo(){
        
    }

    /**
     * 获得类唯一实例
     * @return 类实例
     */
    public static UniqueNo getInstance(){
        return uNo;
    }

    /**
     * @return 循环获得01到99字符串
     */
    public String getNoTwo(){
    	synchronized(no2Lock){
	        no2++;
	        if(no2>99){  
	            no2=1;
	        }
	        return Tools.formatSn(no2, 2);
    	}
        
    }
    /**
     * @return 循环获得0001到9999字符串
     */
    public String getNoFour(){
    	synchronized(no1Lock){
	        no1++;
	        if(no1>9999){  
	            no1=1;
	        }
	        return Tools.formatSn(no1, 4);
    	}
        
    }
    
    /**
     * @return 循环获得000001到999999字符串
     */
    public  String getNoSix(){
    	synchronized(no6Lock){
	    	no6++;
	    	if(no6>999999){
	    		no6=1;
	    	}
	    	return Tools.formatSn(no6, 6);
    	}
    	
    }
	/**
	 * @return 循环获得01到99字符串
	 */
	public synchronized String getNo(){
		no++;
		if(no>99){
			no=1;
		}
		if(no<10){
			return "0"+no;
		}
		return no+"";
	}
    /**
     * @return 循环获得01到9999999字符串
     */
    public  String getNoSeven(){
    	synchronized(no7Lock){
	    	no7++;
	    	if(no7>9999999){
	    		no7=1;
	    	}
	    	return Tools.formatSn(no7, 7);
    	}
    	
    }
}
