package com.wlt.webm.tool;

import java.security.*;

import javax.crypto.Cipher;

import javax.crypto.SecretKey;

import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.DESKeySpec;

/**
 * 
 * @author 1989
 *
 */
public class DES {

	public DES(String str) {
		setKey(str);// �����ܳ�
	}

	public DES() {
	}

	public void setKey(String key) {
		PASSWORD_CRYPT_KEY = key;
	}

	private String PASSWORD_CRYPT_KEY = null;

	private final static String DES = "DES/ECB/PKCS5Padding";
	/**
	 * 
	 * ����
	 * 
	 * @param src
	 *            ����Դ
	 * 
	 * @param key
	 *            ��Կ�����ȱ�����8�ı���
	 * 
	 * @return ���ؼ��ܺ������
	 * 
	 * @throws Exception
	 */

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {

		// DES�㷨Ҫ����һ�������ε������Դ

		SecureRandom sr = new SecureRandom();

		// ��ԭʼ�ܳ����ݴ���DESKeySpec����

		DESKeySpec dks = new DESKeySpec(key);

		// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����

		// һ��SecretKey����

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher����ʵ����ɼ��ܲ���

		Cipher cipher = Cipher.getInstance(DES);

		// ���ܳ׳�ʼ��Cipher����

		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		// ���ڣ���ȡ���ݲ�����

		// ��ʽִ�м��ܲ���

		return cipher.doFinal(src);

	}

	/**
	 * 
	 * ����
	 * 
	 * @param src
	 *            ����Դ
	 * 
	 * @param key
	 *            ��Կ�����ȱ�����8�ı���
	 * 
	 * @return ���ؽ��ܺ��ԭʼ����
	 * 
	 * @throws Exception
	 */

	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {

		// DES�㷨Ҫ����һ�������ε������Դ

		SecureRandom sr = new SecureRandom();

		// ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����

		DESKeySpec dks = new DESKeySpec(key);

		// ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����

		// һ��SecretKey����

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher����ʵ����ɽ��ܲ���

		Cipher cipher = Cipher.getInstance(DES);

		// ���ܳ׳�ʼ��Cipher����

		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		// ���ڣ���ȡ���ݲ�����

		// ��ʽִ�н��ܲ���

		return cipher.doFinal(src);

	}

	/**
	 * 
	 * �������
	 * 
	 * @param data
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public String decrypt(String data) {

		try {

			return new String(decrypt(hex2byte(data.getBytes()),

			PASSWORD_CRYPT_KEY.getBytes()));

		} catch (Exception e) {

		}

		return null;

	}

	/**
	 * 
	 * �������
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public String getEncString(String src) {

		try {

			return byte2hex(encrypt(src.getBytes("UTF-8"), PASSWORD_CRYPT_KEY
					.getBytes("UTF-8")));
		} catch (Exception e) {

		}

		return null;

	}

	/**
	 * 
	 * ������ת�ַ���
	 * 
	 * @param b
	 * 
	 * @return
	 */

	public static String byte2hex(byte[] b) {

		String hs = "";

		String stmp = "";

		for (int n = 0; n < b.length; n++) {

			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)

				hs = hs + "0" + stmp;

			else

				hs = hs + stmp;

		}

		return hs.toUpperCase();

	}

	public static byte[] hex2byte(byte[] b) {

		if ((b.length % 2) != 0)

			throw new IllegalArgumentException("���Ȳ���ż��");

		byte[] b2 = new byte[b.length / 2];

		for (int n = 0; n < b.length; n += 2) {

			String item = new String(b, n, 2);

			b2[n / 2] = (byte) Integer.parseInt(item, 16);

		}

		return b2;
	}
	
	public static void main(String[] args) {
		DES des = new DES();
		des.setKey("12345678");
		String src = "11111111111111120151221142143";
		String enkey = des.getEncString(src);
		System.out.println("Դ��:"+src);
		System.out.println("��Կ:12345678");
		System.out.println("���ܺ�:"+enkey);
		System.out.println(des.decrypt(enkey));

	}
}