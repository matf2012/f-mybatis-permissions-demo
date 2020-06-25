package com.f.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Util {
    // 全局数组
    private final static String[] STRDIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    /**
     * 返回形式为数字跟字符串
     *
     * @param bByte byte
     * @return String
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return STRDIGITS[iD1] + STRDIGITS[iD2];
    }

    // 返回形式只为数字//unused method
    /*    private static String byteToNum(byte bByte) {
            int iRet = bByte;
            if (iRet < 0) {
                iRet += 256;
            }
            return String.valueOf(iRet);
        }
    */

    /**
     * {转换字节数组为16进制字串}
     *
     * @param bByte byte[]
     * @return String
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 将字符串通过MD5转换
     *
     * @param strObj
     * @return
     */
    public static String getMD5Code(String strObj) {
        String resultString = null;
        try {
            //            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            throw new SecurityException("获取MD5校验码失败", ex);
        }
        return resultString;
    }
}
