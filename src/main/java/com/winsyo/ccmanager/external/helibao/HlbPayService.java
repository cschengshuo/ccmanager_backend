package com.winsyo.ccmanager.external.helibao;

import com.alibaba.fastjson.JSONObject;
import com.winsyo.ccmanager.external.util.MD5Utils;
import com.winsyo.ccmanager.external.util.RequestUtils;
import com.winsyo.ccmanager.external.util.ZHMultipartPost;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HlbPayService {

  private final Logger logger = LoggerFactory.getLogger(HlbPayService.class);

  public static final String officeCode = "0000000039";
  public static final String key = "E8T3I6H1";
  public static final String merchantName = "南京闻知阅网络科技有限公司";

  public String createMerchant(String phone, String IdNumber) {
    TreeMap<String, String> reqMap = new TreeMap<>();
    // 商户名称
    reqMap.put("merchantName", merchantName);
    // 机构编号
    reqMap.put("officeCode", officeCode);
    // 手机号
    reqMap.put("phone", phone);
    //身份证号
    reqMap.put("IdCard", IdNumber);
    //加密
    String paramSrc = RequestUtils.getParamSrc(reqMap);
    //生成签名
    String sign = MD5Utils.sign(paramSrc, key, "GBK");
    reqMap.put("sign", sign);//签名
    String URL = "http://116.62.100.174/Bank/mobile/zhBank/createMerchant";//请求地址
    //表单post请求方式访问接口地址
    String message = ZHMultipartPost.doPost(URL, reqMap, "utf-8");
    logger.info(message);

    JSONObject obj1 = JSONObject.parseObject(message);
    if (obj1.getBoolean("success")) {
      JSONObject obj2 = JSONObject.parseObject(obj1.getString("data"));
      logger.info(obj2.getString("merchantCode"));
      return obj2.getString("merchantCode");
    }
    return null;
  }

}
