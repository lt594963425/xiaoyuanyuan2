package com.android.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/6/26 0026
 * Time: 下午 14:41
 */
public class Hmacmd5Utils {
    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);

    }

    /*byte数组转换为HexString*/
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String inputStr = System.currentTimeMillis() + "";
        System.out.println("时间戳：" + inputStr);
        byte[] inputData = inputStr.getBytes();

        for (int i = 0; i < inputData.length; i++) {
            System.out.println("数据：" + inputStr.substring(i,i+1) + "------>" + inputData[i]);
        }

        System.out.println("需要加密的字节数组：" + inputData);
        String token = "A4L0IT9XHC35MG2G";
        byte[] hd5Data = Hmacmd5Utils.encryptHMAC(inputData, token);
        System.out.println("加密后的字节数组：" + hd5Data);
        System.out.println("加密的字节数组转16进制：" + Hmacmd5Utils.byteArrayToHexString(hd5Data));

    }

}
