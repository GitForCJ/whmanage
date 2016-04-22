package com.wlt.webm.business.bean.webserviceclient;


/** 
* Tea�㷨
* KEYΪ16�ֽ�,ӦΪ����4��int������int[]��һ��intΪ4���ֽ�
* ���ܽ�������ӦΪ8�ı������Ƽ���������Ϊ64��
**/
public class Tea {
	private int[] KEY = new int[] { //���ܽ������õ�KEY
		1, 2, 3, 4 };
	private String separatoradd = "0"; //�����ַ����ָ���"+"

	private String separatordel = "1"; //�����ַ����ָ���"-"
	//����
	private byte[] encrypt(
		byte[] content,
		int offset,
		int[] key,
		int times) { //timesΪ��������
		int[] tempInt = byteToInt(content, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0, i;
		int delta = 0x9e3779b9; //�����㷨��׼����ֵ
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			sum += delta;
			y += ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
			z += ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
		}
		tempInt[0] = y;
		tempInt[1] = z;
		return intToByte(tempInt, 0);
	}
	//����
	private byte[] decrypt(
		byte[] encryptContent,
		int offset,
		int[] key,
		int times) {
		int[] tempInt = byteToInt(encryptContent, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0xC6EF3720, i;
		int delta = 0x9e3779b9; //�����㷨��׼����ֵ
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			z -= ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
			y -= ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
			sum -= delta;
		}
		tempInt[0] = y;
		tempInt[1] = z;

		return intToByte(tempInt, 0);
	}
	//byte[]������ת��int[]������
	private int[] byteToInt(byte[] content, int offset) {

		int[] result = new int[content.length >> 2];
		for (int i = 0, j = offset; j < content.length; i++, j += 4) {
			result[i] =
				transform(content[j + 3]) | transform(content[j + 2])
					<< 8 | transform(content[j + 1])
					<< 16 | (int) content[j]
					<< 24;
		}
		return result;

	}
	//int[]������ת��byte[]������
	private byte[] intToByte(int[] content, int offset) {
		byte[] result = new byte[content.length << 2];
		for (int i = 0, j = offset; j < result.length; i++, j += 4) {
			result[j + 3] = (byte) (content[i] & 0xff);
			result[j + 2] = (byte) ((content[i] >> 8) & 0xff);
			result[j + 1] = (byte) ((content[i] >> 16) & 0xff);
			result[j] = (byte) ((content[i] >> 24) & 0xff);
		}
		return result;
	}
	//��ĳ�ֽڱ����ͳɸ������轫��ת���޷�������
	private int transform(byte temp) {
		int tempInt = (int) temp;
		if (tempInt < 0) {
			tempInt += 256;
		}
		return tempInt;
	}
	//	ͨ��TEA�㷨������Ϣ
	public String encryptByTea(String info) {
		byte[] temp = info.getBytes();
		int n = 8 - temp.length % 8; //��temp��λ������8�ı���,��Ҫ����λ��
		byte[] encryptStr = new byte[temp.length + n];
		encryptStr[0] = (byte) n;
		System.arraycopy(temp, 0, encryptStr, n, temp.length);
		byte[] result = new byte[encryptStr.length];
		for (int offset = 0; offset < result.length; offset += 8) {
			byte[] tempEncrpt = encrypt(encryptStr, offset, KEY, 32);
			System.arraycopy(tempEncrpt, 0, result, offset, 8);
		}
		String tmp = encryptTwo(result);
		//		for (int i = 0; i < result.length; i++) {
		//			tmp = tmp + result[i] + this.separator;
		//		}
		return tmp;
	}

	private String encryptTwo(byte[] source) {
		String all = "";
		for (int i = 0; i < source.length; i++) {
			String tmp = this.separatoradd;
			int s = source[i];
			if (s < 0) {
				s = ~s + 1;
				tmp = this.separatordel;
			}
			String res = Integer.toHexString(s);
			if (res.length() == 1)
				res = "0" + res;
			all = all + res + tmp;
		}
		return all;
	}

	public byte[] decryptTwo(String res) {
		int k = 0;
		byte[] result = new byte[res.length() / 3];
		for (int i = 0, j = 3; i < res.length(); i = i + 3, j = i + 3) {
			String tmp = res.substring(i, j);
			if (this.separatoradd.charAt(0) == tmp.charAt(2))
				result[k++] = Byte.parseByte(tmp.substring(0, 2),16);
			else if (this.separatordel.charAt(0) == tmp.charAt(2))
				result[k++] = Byte.parseByte("-" + tmp.substring(0, 2),16);
		}
		return result;
	}
	//	  ͨ��TEA�㷨������Ϣ
	public String decryptByTea(String info) {
		byte[] secretInfo = this.decryptTwo(info);
		byte[] decryptStr = null;
		byte[] tempDecrypt = new byte[secretInfo.length];
		for (int offset = 0; offset < secretInfo.length; offset += 8) {
			decryptStr = decrypt(secretInfo, offset, KEY, 32);
			System.arraycopy(decryptStr, 0, tempDecrypt, offset, 8);
		}
		int n = tempDecrypt[0];
		return new String(tempDecrypt, n, decryptStr.length - n);

	}

	public static void main(String[] args) {
		Tea tea = new Tea();
		String src ="abcd1234";
		String tmp = tea.encryptByTea(src);
		String result = tea.decryptByTea("2700304515a02c16411f06e14f17b14e16401c01c0591390");
		System.out.print("ԭ���ݣ�");
		System.out.println(src);
		System.out.print("���ܺ�����ݣ�");
		System.out.println(tmp);
		System.out.print("���ܺ�����ݣ�");
		System.out.println(result);
	}
}
