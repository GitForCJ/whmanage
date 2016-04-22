package com.wlt.webm.scputil;

import java.util.Date;

import com.wlt.webm.scpcommon.Constant;

/**
 * <p>@Title: 深圳康索特电子代办系统业务控制中心模块</p>
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
	 * 序号(最大6位)(银行专款使用)
	 */
	private int no6_bank = 0;

	/**
	 * 序号（最大7位）
	 */
	private int no7 = 0;

	/**
	 * 重复交费同步锁
	 */
	private Object repay = new Object();

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
	 * 六位数锁
	 */
	private Object no6LockBank = new Object();

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
			return Tools.formatSn(no4, 4);
		}
	}
	/**
	 * @return 循环获得01到999999字符串
	 */
	public String getNoSix() {
		synchronized (no6Lock) {
			no6++;
			if (no6 > 999999) {
				no6 = 1;
			}
			return Tools.formatSn(no6, 6);
		}
	}
	/**
	 * @return 循环获得01到999999字符串
	 */
	public String getNoSix_bank2() {
		synchronized (no6LockBank) {
			no6_bank++;
			if (no6_bank > 999999) {
				no6_bank = 1;
			}
			return Tools.formatSn(no6_bank, 6);
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
			return Tools.formatSn(no2, 2);
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
			return Tools.formatSn(no7, 7);
		}

	}
}

