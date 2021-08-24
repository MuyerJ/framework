package com.muyer.wechat.util;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

/**
 * Description: 
 * date: 2021/8/23 18:20
 * @author YeJiang
 * @version
 */
public class WXUtils {
    private volatile static BouncyCastleProvider bouncyCastleProvider = null;


    /**
     * 解密小程序的用户信息，手机号加密数据
     * @param sessionKey
     * @param encryptedData
     * @param iv
     * @return
     * @throws Exception
     */
    public static JSONObject decode(String sessionKey, String encryptedData, String iv) throws Exception {
        // 被加密的数据
        byte[] dataByte = Base64.getDecoder().decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.getDecoder().decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.getDecoder().decode(iv);

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Security.addProvider(getInstance());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, "UTF-8");
            return JSONObject.parseObject(result);
        }
        return null;
    }

    public static BouncyCastleProvider getInstance() {
        if (bouncyCastleProvider == null) {
            synchronized (BouncyCastleProvider.class) {
                if (bouncyCastleProvider == null) {
                    bouncyCastleProvider = new BouncyCastleProvider();
                }
            }
        }
        return bouncyCastleProvider;
    }
}
