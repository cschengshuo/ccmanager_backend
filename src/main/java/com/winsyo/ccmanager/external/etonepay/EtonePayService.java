package com.winsyo.ccmanager.external.etonepay;

import com.winsyo.ccmanager.external.util.MD5Utils;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * 易通提现接口
 */
@Service
public class EtonePayService {

  public static void main(String[] args) {
    EtonePayService etonePayService = new EtonePayService();
    etonePayService.pay("160", "徐枫", "130184198504180016", "6217001630025579589", "18610601315",
        "中国建设银行", "18610601315", "4581240211586178", "438", "2301");
  }

  // 请求地址
  private static final String baseUrl = "http://58.56.23.89:7002";
//  private static final String baseUrl = "https://cashier.etonepay.com";

  // 商户编号
  public static final String merchantId = "888888888888888";

  private static DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  private static DateTimeFormatter sdf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

  /**
   * 支付
   * @param tranAmt 交易金额(单位：分)
   * @param userName 用户名
   * @param quickPayCertNo 身份证号
   * @param arrviedAcctNo 入账卡卡号
   * @param arrviedPhone 入账卡绑定手机号
   * @param arrviedBankName 入账卡开户行
   * @param userPhoneHF 交易卡绑定手机号
   * @param userAcctNo 交易卡卡号
   * @param cardCvn2 交易卡cvv2
   * @param cardExpire 有效日期 YYMM
   */
  public void pay(String tranAmt, String userName, String quickPayCertNo, String arrviedAcctNo, String arrviedPhone, String arrviedBankName, String userPhoneHF, String userAcctNo,
      String cardCvn2, String cardExpire) {
    String version = "1.0.0"; // 版本号 固定
    String transCode = "8888"; // 交易代码 固定
    String merOrderNum = "O" + sdf2.format(LocalDateTime.now()); // 商户订单号
    String bussId = "ONL0017"; // 业务代码 固定
    String sysTraceNum = "P" + sdf2.format(LocalDateTime.now()); // 商户请求流水号
    String tranDateTime = sdf.format(LocalDateTime.now()); // 交易时间 YYYYMMDDHHMMSS
    String currencyType = "156"; // 货币代码 固定
    String merURL = "https://www.winsyo.com"; // 商户返回页面
    String backURL = ""; // 回调商户地址
    String orderInfo = ""; // 订单信息
    String userId = ""; // 用户 ID
    String userNameHF = Hex.encodeHexString(userName.getBytes(), false); // 开户名（姓名）为Hex编码(16进制转码)后数据
    String userIp = ""; // 订单用户 IP
    String bankId = "888880170122900"; // 支付方式代码 固定
    String stlmId = ""; // 结算规则代码
    String entryType = "1"; // 入口类型 固定
    String attach = ""; // 附加数据
    String reserver1 = "50"; // 商户上传的手续费
    String reserver2 = ""; // 保留域 2
    String reserver3 = ""; // 保留域 3
    String reserver4 = "7"; // 保留域 4 固定

    // 签名
    String txnString =
        version + "|" + transCode + "|" + merchantId + "|" + merOrderNum + "|" + bussId + "|" + tranAmt + "|" + sysTraceNum + "|" + tranDateTime + "|" + currencyType + "|" + merURL
            + "|" + backURL + "|" + orderInfo + "|" + userId;
    String signValue = MD5Utils.md5(txnString + "8EF53C251102A4E6", "utf-8"); // 数字签名

    // 封装参数
    MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
    parameter.add("version", version);
    parameter.add("transCode", transCode);
    parameter.add("merchantId", merchantId);
    parameter.add("merOrderNum", merOrderNum);
    parameter.add("bussId", bussId);
    parameter.add("tranAmt", tranAmt);
    parameter.add("sysTraceNum", sysTraceNum);
    parameter.add("tranDateTime", tranDateTime);
    parameter.add("currencyType", currencyType);
    parameter.add("merURL", merURL);
    parameter.add("backURL", backURL);
    parameter.add("orderInfo", orderInfo);
    parameter.add("userId", userId);
    parameter.add("userNameHF", userNameHF);
    parameter.add("quickPayCertNo", quickPayCertNo);
    parameter.add("arrviedAcctNo", arrviedAcctNo);
    parameter.add("arrviedPhone", arrviedPhone);
    parameter.add("arrviedBankName", arrviedBankName);
    parameter.add("userPhoneHF", userPhoneHF);
    parameter.add("userAcctNo", userAcctNo);
    parameter.add("cardCvn2", cardCvn2);
    parameter.add("cardExpire", cardExpire);
    parameter.add("userIp", userIp);
    parameter.add("bankId", bankId);
    parameter.add("stlmId", stlmId);
    parameter.add("entryType", entryType);
    parameter.add("attach", attach);
    parameter.add("reserver1", reserver1);
    parameter.add("reserver2", reserver2);
    parameter.add("reserver3", reserver3);
    parameter.add("reserver4", reserver4);
    parameter.add("signValue", signValue);

    // 发送Post请求
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // 请求内容为 application/x-www-form-urlencoded
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(parameter, headers);
    String url = baseUrl + "/NetPay/SynonymNamePay.action";
    String result = restTemplate.postForObject(url, formEntity, String.class);

    dealResponse(result);

  }

  /**
   * 解析返回内容
   * @param result
   */
  private void dealResponse(String result) {
    Map<String, String> response = parseResponse(result);
    String transCode = response.get("transCode");
    String merchantId = response.get("merchantId");
    String respCode = response.get("respCode");
    String sysTraceNum = response.get("sysTraceNum");
    String merOrderNum = response.get("merOrderNum");
    String orderId = response.get("orderId");
    String bussId = response.get("bussId");
    String tranAmt = response.get("tranAmt");
    String orderAmt = response.get("orderAmt");
    String bankFeeAmt = response.get("bankFeeAmt");
    String integralAmt = response.get("integralAmt");
    String vaAmt = response.get("vaAmt");
    String bankAmt = response.get("bankAmt");
    String bankId = response.get("bankId");
    String integralSeq = response.get("integralSeq");
    String vaSeq = response.get("vaSeq");
    String bankSeq = response.get("bankSeq");
    String tranDateTime = response.get("tranDateTime");
    String payMentTime = response.get("payMentTime");
    String settleDate = response.get("settleDate");
    String currencyType = response.get("currencyType");
    String orderInfo = response.get("orderInfo");
    String userId = response.get("userId");
    String userIp = response.get("userIp");
    String reserver1 = response.get("reserver1");
    String reserver2 = response.get("reserver2");
    String reserver3 = response.get("reserver3");
    String reserver4 = response.get("reserver4");
    String signValue = response.get("signValue");

    String txnString =
        transCode + "|" + merchantId + "|" + respCode + "|" + sysTraceNum + "|" + merOrderNum + "|" + orderId + "|" + bussId + "|" + tranAmt + "|" + orderAmt + "|" + bankFeeAmt
            + "|" + integralAmt + "|" + vaAmt + "|" + bankAmt + "|" + bankId + "|" + integralSeq + "|" + vaSeq + "|" + bankSeq + "|" + tranDateTime + "|" + payMentTime + "|"
            + settleDate + "|" + currencyType + "|" + orderInfo + "|" + userId;
    String genSignValue = MD5Utils.md5(txnString + "8EF53C251102A4E6", "utf-8"); // 数字签名
    boolean equals = genSignValue.equals(signValue);
    System.out.println(genSignValue);
    System.out.println(signValue);
    System.out.println(equals);

    System.out.println(sysTraceNum);
    System.out.println(merOrderNum);

    try {
      String s = parseHex(reserver3);
      System.out.println(s);
    } catch (DecoderException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

  }

  /**
   * 解析16进制字符串
   */
  public String parseHex(String response) throws DecoderException, UnsupportedEncodingException {
    byte[] bytes = Hex.decodeHex(response);
    return new String(bytes, "utf-8");
  }

  /**
   * 解析接口响应
   */
  public Map<String, String> parseResponse(String response) {
    String[] pairs = StringUtils.tokenizeToStringArray(response, "&");
    Map<String, String> result = new HashMap<>(pairs.length);
    for (String pair : pairs) {
      int idx = pair.indexOf('=');
      if (idx == -1) {
        result.put(pair, null);
      } else {
        String name = pair.substring(0, idx);
        String value = pair.substring(idx + 1);
        result.put(name, value);
      }
    }
    return result;

  }

}
