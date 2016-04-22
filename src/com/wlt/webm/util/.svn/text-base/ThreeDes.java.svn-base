package com.wlt.webm.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ThreeDes
{
    /**
     * 3DES加密算法
     */
    private static final String algorithm = "DESede";

    /**
     * 密钥字符集
     */
    private static final String keyCharsetName = "ISO-8859-1";

    /**
     * 明文字符集
     */
    private static final String srcCharsetName = "UTF-8";

    /**
     * 密文字符集
     */
    private static final String destCharsetName = "ISO-8859-1";

    /**
     * 加密
     * @param key 密钥，必须为24字节
     * @param src 明文
     * @return 8字节倍数的密文，失败则返回null
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
     * 加密
     * @param key 密钥，必须为24字节
     * @param src 明文
     * @return 8字节倍数的密文，失败则返回null
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
     * 解密
     * @param key 密钥，必须为24字节
     * @param src 密文
     * @return 明文，失败则返回null
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
     * 解密
     * @param key 密钥，必须为24字节
     * @param dest 密文
     * @return 明文，失败则返回null
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
     * 用指定的密钥对明文加密，并对加密结果转换成ASCII码，最后用制定的分隔字符串连接ASCII码形成最终结果。
     * @param key 密钥，必须为24字节
     * @param src 明文
     * @param splitString 分割字符串
     * @return 加密结果，失败则返回null
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
     * 用指定的密钥对ASCII密文解密。
     * @param key 密钥，必须为24字节
     * @param dest 密文
     * @param splitString 分割字符串
     * @return 解密结果，失败则返回null
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

        String src = "a中文cc08";

        String dest = encryptAscii(key, src, ",");

        src = decryptAscii(key, dest, ",");
    }

}
