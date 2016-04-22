package com.wlt.webm.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ThreeDes
{
    /**
     * 3DES�����㷨
     */
    private static final String algorithm = "DESede";

    /**
     * ��Կ�ַ���
     */
    private static final String keyCharsetName = "ISO-8859-1";

    /**
     * �����ַ���
     */
    private static final String srcCharsetName = "UTF-8";

    /**
     * �����ַ���
     */
    private static final String destCharsetName = "ISO-8859-1";

    /**
     * ����
     * @param key ��Կ������Ϊ24�ֽ�
     * @param src ����
     * @return 8�ֽڱ��������ģ�ʧ���򷵻�null
     */
    public static byte[] encrypt(byte[] key, byte[] src)
    {
        SecretKey secretkey = new SecretKeySpec(key, algorithm);
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretkey);
            return cipher.doFinal(src);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * ����
     * @param key ��Կ������Ϊ24�ֽ�
     * @param src ����
     * @return 8�ֽڱ��������ģ�ʧ���򷵻�null
     */
    public static String encrypt(String key, String src)
    {
        try
        {
            return new String(encrypt(key.getBytes(keyCharsetName), src.getBytes(srcCharsetName)), destCharsetName);
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }

    /**
     * ����
     * @param key ��Կ������Ϊ24�ֽ�
     * @param src ����
     * @return ���ģ�ʧ���򷵻�null
     */
    public static byte[] decrypt(byte[] key, byte[] src)
    {
        SecretKey secretkey = new SecretKeySpec(key, algorithm);
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretkey);
            return cipher.doFinal(src);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * ����
     * @param key ��Կ������Ϊ24�ֽ�
     * @param dest ����
     * @return ���ģ�ʧ���򷵻�null
     */
    public static String decrypt(String key, String dest)
    {
        try
        {
            return new String(decrypt(key.getBytes(keyCharsetName), dest.getBytes(destCharsetName)), srcCharsetName);
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }

    /**
     * ��ָ������Կ�����ļ��ܣ����Լ��ܽ��ת����ASCII�룬������ƶ��ķָ��ַ�������ASCII���γ����ս����
     * @param key ��Կ������Ϊ24�ֽ�
     * @param src ����
     * @param splitString �ָ��ַ���
     * @return ���ܽ����ʧ���򷵻�null
     */
    public static String encryptAscii(String key, String src, String splitString)
    {
        try
        {
            byte[] bytes = encrypt(key.getBytes(keyCharsetName), src.getBytes(srcCharsetName));
            StringBuffer result = new StringBuffer(100);
            if (bytes.length > 0)
            {
                result.append(bytes[0]);

                for (int i = 1; i < bytes.length; i++)
                {
                    result.append(splitString).append(bytes[i]);
                }
            }
            return result.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * ��ָ������Կ��ASCII���Ľ��ܡ�
     * @param key ��Կ������Ϊ24�ֽ�
     * @param dest ����
     * @param splitString �ָ��ַ���
     * @return ���ܽ����ʧ���򷵻�null
     */
    public static String decryptAscii(String key, String dest, String splitString)
    {
        try
        {
            String[] asciis = dest.split(splitString);
            byte[] bytes = new byte[asciis.length];
            for (int i = 0; i < asciis.length; i++)
            {
                bytes[i] = Byte.parseByte(asciis[i]);
            }

            return new String(decrypt(key.getBytes(keyCharsetName), bytes), srcCharsetName);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        String key = "www.xunfang.com.88866500";

        String src = "a����cc08";

        String dest = encryptAscii(key, src, ",");

        src = decryptAscii(key, dest, ",");
    }

}
