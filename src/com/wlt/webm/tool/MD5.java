package com.wlt.webm.tool;

import java.security.MessageDigest;

/**
 * MD5加密类<br>
 */
public class MD5 {

    /**
     * 16进制数字
     */
    private final static String[] hexDigits = {
            "0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b)
    {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
        {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 转换字节为16进制字符串
     * @param b 字节
     * @return 16进制字符串
     */
    private static String byteToHexString(byte b)
    {
        int n = b;
        if (n < 0)
        {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 加密字符串
     * @param src 被加密的字符串
     * @return 加密后的32位字符串
     */
    public static String encode(String src)
    {
        String resultString = null;
        try
        {
            resultString = new String(src);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        }
        catch (Exception ex)
        {

        }
        return resultString;
    }
}