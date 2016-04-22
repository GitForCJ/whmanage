package com.wlt.webm.business.TZ.tool;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密解密类 创建日期：2011-11-25 
 * Company：深圳市万恒科技有限公司 
 * copyright: Copyright (c) 2008
 * @author caiSJ
 * @version 2.0.0.0
 */
public class DESUtil {

	private final static String DES = "DES";

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// 此类提供强加密随机数生成器
		SecureRandom sr = new SecureRandom();
		// 指定一个DES密钥
		DESKeySpec dks = new DESKeySpec(key);
		// 密钥工厂用来将密钥（类型 Key 的不透明加密密钥）转换为密钥规范
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		// SecretKey 分组秘密（对称）密钥
		SecretKey securekey = keyFactory.generateSecret(dks);
		// 此类为加密和解密提供密码功能
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding", "SunJCE");
		// 加密模式
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	private static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding", "SunJCE");
		// 解密模式
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	private static byte[] hex2byte(byte[] b) {

		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");

		byte[] b2 = new byte[b.length / 2];

		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decrypt(String data, String key) {
		try {
			return new String(
					decrypt(hex2byte(data.getBytes()), key.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final static String encrypt(String data, String key) {
		try {

			byte[] hash = encrypt(data.getBytes(), key.getBytes());

			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0"
							+ Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}

			return hexString.toString().toUpperCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String source = "11111111";
		String key = "96D0028878D58C89";
		System.out.println("明文:" + source);
		System.out.println("密钥:" + key);
		String encryptResult = DESUtil.encrypt(source, key);
		System.out.println("密文:" + encryptResult);
		String decryptResult = DESUtil.decrypt("2C8F00D7A6A08D04", "12345678");
		System.out.println("解密:" + decryptResult);
	}

}
