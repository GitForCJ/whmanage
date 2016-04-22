package com.wlt.webm.tool;

import java.math.BigDecimal;

import org.apache.commons.lang.ArrayUtils;

import com.wlt.webm.db.DBConfig;

/**
 * 浮点型数据运算
 * @author Administrator
 *
 */
public class FloatArith {
	
	private static final int DEF_DIV_SCALE = 4;
	
	/**
	 * 
	 */
	public FloatArith(){
		
	}
	/** 
	* 提供精确的加法运算。 
	* @param v1 被加数 
	* @param v2 加数 
	* @return 两个参数的和 

	*/ 
	public static double add(double v1, double v2) { 
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.add(b2).doubleValue(); 
	} 
	
	/** 
	* 提供精确的减法运算。 
	* @param v1 被减数 
	* @param v2 减数 
	* @return 两个参数的差 
	*/ 
	public static double sub(double v1, double v2) { 
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.subtract(b2).doubleValue(); 
	} 

	/** 
	* 提供精确的乘法运算。 
	* @param v1 被乘数 
	* @param v2 乘数 
	* @return 两个参数的积 
	*/ 
	public static double mul(double v1, double v2) { 
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.multiply(b2).doubleValue(); 
	}
	
	/** 
	* 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
	* 小数点以后2位，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @return 两个参数的商 
	*/ 
	public static double div(double v1, double v2) { 
	return div(v1, v2, DEF_DIV_SCALE); 
	} 
	
	/** 

	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	* 定精度，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @param scale 表示表示需要精确到小数点以后几位。 
	* @return 两个参数的商 
	*/ 
	public static double div(double v1, double v2, int scale) { 
	if (scale < 0) { 
	throw new IllegalArgumentException( "lost scale"); 
	} 
	BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
	BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	} 

	/**
	 * 将元转换成厘
	 * @param val
	 * @return
	 */
    public static double yun2li(String val){
    	return mul(Double.valueOf(val),1000);
    }
	/**
	 * 将厘转换成元
	 * @param val
	 * @return
	 */
    public static double li2yuan(String val){
    	return div(Double.valueOf(val),1000);
    }
    /**
	 * 将元转换成分
	 * @param val
	 * @return
	 */
    public static int yuan2fen(String val){
    	return (int)mul(Integer.valueOf(val),100);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		double a=Double.valueOf("0.98");
//		double b=100;
//		double c=FloatArith.sub(1,FloatArith.div(a,100));
//		System.out.println((int)FloatArith.mul(FloatArith.yun2li("100"), c));
//		System.out.println(FloatArith.sub(1,b)<0);
//		System.out.println(FloatArith.div(a,b)*50000);
//		System.out.println(FloatArith.yun2li("-20.565"));
//		System.out.println(FloatArith.li2yuan("20567"));
//		System.out.println(FloatArith.div(Double.valueOf("0.98"),100));
//		System.out.println((int)(FloatArith.mul(100000.0, 1-FloatArith.div(Double.valueOf("0.98"),100))));
//		double d=Double.valueOf("125689");
//		System.out.println((int)FloatArith.mul(d, 0.0038));
//		System.out.println((int)FloatArith.sub(d, FloatArith.mul(d, 0.0038)));
//		    Object[] obj=new Object[]{1,2,3};
//			Object[] obj2=new Object[]{5,90};
//		 Object[] obj3=new Object[obj.length+obj2.length];
//		 Object[] obj4=ArrayUtils.addAll(obj, obj2);
//		 System.arraycopy(obj, 0, obj3, 0, obj.length);
//		 System.arraycopy(obj2, 0, obj3, obj.length, obj2.length);
//		 System.out.println(obj3.length);
//         for (int i = 0; i < obj3.length; i++) {
//			System.out.print(obj3[i]+"   ");
//		}
//         System.out.println("=="+yun2li("120000000"));
         
			double a = FloatArith.div(Double.valueOf(1.6), 100);// 总佣金比
			double b = FloatArith.mul(30000, a);// 总佣金
			double c = FloatArith.mul(b, FloatArith.div(Double
					.valueOf(81), 100));// 代办点应得佣金
			int facctfee = (int) FloatArith.sub(30000, c);// 应扣金额
			int parentfee = (int) FloatArith.sub(b, c);// 上级节点应得佣金
			System.out.println(facctfee+"   "+parentfee);
			//99376      99300
			
	}

}
