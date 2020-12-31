package tech.soulcoder.signature;

import java.util.TreeMap;

import com.google.common.collect.Maps;
import tech.soulcoder.signature.codec.SignatureKit;

/**
 * @author yunfeng.lu
 * @create 2020/12/31.
 */
public class CodecTest {

    public static void main(String[] args) throws Exception {
        TreeMap<String, Object> requestParamMap = Maps.newTreeMap();
        requestParamMap.put("a","b");
        requestParamMap.put("c","ww");
        requestParamMap.put("d","aa");
        String s = SignatureKit.calculateSig(requestParamMap, "miyao", "jingtaimima");
        System.out.println(s);
    }
}
