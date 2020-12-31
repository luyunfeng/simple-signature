package tech.soulcoder.signature.codec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 编码工具类
 *
 * @author yunfeng.lu
 * @create 2019-06-10.
 */
public class CodecKit {

    /**
     * HMAC SHA1签名或摘要算法
     */
    public static String hmacSHA1ToStr(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
        if (data == null || key == null) {
            return null;
        }
        return hmacSHA1ToStr(data.getBytes(), key.getBytes());
    }

    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * HMAC SHA1签名或摘要算法
     *
     * @param data 待摘要的字节数据
     * @param key  使用的key
     */
    public static String hmacSHA1ToStr(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        return DigestUtils.md5Hex(hmacSHA1(data, key));
    }

    public static byte[] hmacSHA1(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        if (data == null || key == null) {
            return null;
        }
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        return mac.doFinal(data);
    }
}
