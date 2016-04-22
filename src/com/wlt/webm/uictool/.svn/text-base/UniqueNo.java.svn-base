package com.wlt.webm.uictool;

/**
 * <p>@Description: 循环获得字符串（该类是线程安全的）</p>
 */
public class UniqueNo {
	/**
	 * 类实例
	 */
	private static UniqueNo uNo = new UniqueNo();

	/**
	 * 序号(最大两位)
	 */
	private int no2 = 0;

	/**
	 * 序号(最大4位)
	 */
	private int no4 = 0;
	/**
	 * 序号(最大6位)
	 */
	private int no6 = 0;

	/**
	 * 序号（最大7位）
	 */
	private int no7 = 0;

	/**
	 * 两位数锁
	 */
	private Object no2Lock = new Object();

	/**
	 * 四位数锁
	 */
	private Object no4Lock = new Object();
	/**
	 * 六位数锁
	 */
	private Object no6Lock = new Object();

	/**
	 * 七位数锁
	 */
	private Object no7Lock = new Object();

	/**
	 * 私有构造方法，不允许外部实例化。
	 */
	private UniqueNo() {

	}

	/**
	 * 获得类唯一实例
	 * 
	 * @return 类实例
	 */
	public static UniqueNo getInstance() {
		return uNo;
	}


	/**
	 * @return 循环获得01到99字符串
	 */
	public String getNoFour() {
		synchronized (no4Lock) {
			no4++;
			if (no4 > 9999) {
				no4 = 1;
			}
			return formatSn(no4, 4);
		}
	}
	/**
	 * @return 循环获得01到99字符串
	 */
	public String getNoSix() {
		synchronized (no6Lock) {
			no6++;
			if (no6 > 999999) {
				no6 = 1;
			}
			return formatSn(no6, 6);
		}
	}

	/**
	 * @return 循环获得01到99字符串
	 */
	public String getNoTwo() {
		synchronized (no2Lock) {
			no2++;
			if (no2 > 99) {
				no2 = 1;
			}
			return formatSn(no2, 2);
		}
	}

	/**
	 * @return 循环获得01到9999999字符串
	 */
	public String getNoSeven() {
		synchronized (no7Lock) {
			no7++;
			if (no7 > 9999999) {
				no7 = 1;
			}
			return formatSn(no7, 7);
		}

	}
	 /**
	   * 将序号格式化为字符串，不足位数在前面补0
	   * @param sn int  序号
	   * @param length int  字符串位数
	   * @return String
	   */
	  public static String formatSn(long sn, int length) {
		  String str = sn+"";
		  String strSn = str;
		  for (int i = 0; i < length - str.length(); i++) {
			  strSn = "0" + strSn;
		  }
		  return strSn;
	  }
}

