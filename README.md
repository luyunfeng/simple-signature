# simple-signature
简单验签

# 基本参数简介
appSecret 秘钥

serverAuthStaticKey  静态身份key

# 参数签名生成方式
1. 将除了sig以外的所有请求参数的原始值（即不做任何处理的参数值，比如不能进行URL编码）按照参数名的字典序排序
2. 将排序后的参数键值对用&拼接，即拼接成key1=val1&key2=val2&...
3. 将步骤2得到的字符串进行Base64编码（注意Base64编码时要设置字符集为utf8），假设Base64编码后的字符串为base64EncodedStr
4. 准备下一步需要的HMAC-SHA1哈希key：appSecret直接拼上serverAuthenticateStaticKey， 假设得到的HMAC-SHA1哈希key是sha1Key：sha1Key是appSecret末尾再直接拼上serverAuthenticateStaticKey
5. 使用sha1Key对base64EncodedStr进行HMAC-SHA1哈希得到字节数组（注意是字节数组，不要转成十六进制字符串，否则签名计算会出错；一般的HMAC-SHA1算法得到的结果是字节数组的十六进制表示，请务必留意这里和一般情况不太一样），用伪代码表示即
sha1ResultBytes = hmac-sha1(base64EncodedStr, sha1Key)