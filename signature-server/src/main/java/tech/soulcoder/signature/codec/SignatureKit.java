package tech.soulcoder.signature.codec;

import java.util.TreeMap;


import cn.hutool.core.map.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yunfeng.lu
 * @create 2019-07-09.
 */

public class SignatureKit {

    private static final Logger log = LoggerFactory.getLogger(SignatureKit.class);

    /**
     *
     * @param requestParamMap  入参
     * @param appSecret  验签的  appSecret
     * @param serverAuthStaticKey  服务端静态秘钥
     * @return 加密结果
     * @throws Exception
     */
    public static String calculateSig(TreeMap<String, Object> requestParamMap,
                                      String appSecret,
                                      String serverAuthStaticKey) throws Exception {
        log.debug("======> calculateSig {} , {} , {}", requestParamMap, appSecret, serverAuthStaticKey);
        StringBuilder requestParam = new StringBuilder();
        if (MapUtil.isNotEmpty(requestParamMap)) {
            requestParamMap.forEach((k, v) -> {
                if (v != null) {
                    requestParam.append(k).append("=").append(v).append("&");
                }
            });
            requestParam.deleteCharAt(requestParam.length() - 1);
        }
        return calculateSig(requestParam.toString(), appSecret, serverAuthStaticKey);

    }

    private static String calculateSig(String requestParam,
                                      String appSecret,
                                      String serverAuthStaticKey) throws Exception {
        log.debug("======> calculateSig {} , {} , {}", requestParam, appSecret, serverAuthStaticKey);
        String paramsBase64EncodedStr = Base64Kit.encodeToString(requestParam);
        return CodecKit.hmacSHA1ToStr(paramsBase64EncodedStr, appSecret + serverAuthStaticKey);
    }

}
