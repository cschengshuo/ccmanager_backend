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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static Logger logger = LoggerFactory.getLogger(EtonePayService.class);

  public static void main(String[] args) {
    EtonePayService etonePayService = new EtonePayService();
  }

  // 请求地址
  private static final String baseUrl = "https://cashier.etonepay.com"; // 测试环境地址 http://58.56.23.89:7002
  // 商户编号
  public static final String merchantId = "888201711020132"; // 测试环境 888888888888888
  // 数据秘钥
  public static final String dataKey = "3M5tbN1q4U278cgj"; // 测试环境 8EF53C251102A4E6
  // 时间格式化 yyyyMMddHHmmss
  private static DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  // 时间格式化 yyyyMMddHHmmssSSS
  private static DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

  /**
   * 易通支付
   *
   * @param tranAmt 交易金额(单位：分)
   * @param fee 交易手续费(单位：分)
   * @param userName 用户姓名
   * @param quickPayCertNo 身份证号
   * @param arrviedAcctNo 入账卡卡号
   * @param arrviedPhone 入账卡绑定手机号
   * @param arrviedBankName 入账卡开户行
   * @param userPhoneHF 交易卡绑定手机号
   * @param userAcctNo 交易卡卡号
   * @param cardCvn2 交易卡cvv2
   * @param cardExpire 有效日期 YYMM
   */
  public void pay(String tranAmt, String fee, String userName, String quickPayCertNo, String arrviedAcctNo,
      String arrviedPhone, String arrviedBankName, String userPhoneHF, String userAcctNo, String cardCvn2,
      String cardExpire) {
    String version = "1.0.0"; // 版本号 固定
    String transCode = "8888"; // 交易代码 固定
    String merOrderNum = "O" + dtf2.format(LocalDateTime.now()); // 商户订单号
    String bussId = "ONL0017"; // 业务代码 固定
    String sysTraceNum = "P" + dtf2.format(LocalDateTime.now()); // 商户请求流水号
    String tranDateTime = dtf1.format(LocalDateTime.now()); // 交易时间 YYYYMMDDHHMMSS
    String currencyType = "156"; // 货币代码 固定
    String merURL = "https://www.winsyo.com"; // 商户返回页面
    String backURL = "http://www.winsyo.com:8080/ccmanagerOK/init/receiveResponse"; // 回调商户地址 TODO 实现回调
    String orderInfo = ""; // 订单信息
    String userId = ""; // 用户 ID
    String userNameHF = Hex.encodeHexString(userName.getBytes(), false); // 开户名（姓名）为Hex编码(16进制转码)后数据
    String userIp = ""; // 订单用户 IP
    String bankId = "888880170122900"; // 支付方式代码 固定
    String stlmId = ""; // 结算规则代码
    String entryType = "1"; // 入口类型 固定
    String attach = ""; // 附加数据
    String reserver2 = ""; // 保留域 2
    String reserver3 = ""; // 保留域 3
    String reserver4 = "7"; // 保留域 4 固定

    // 签名
    String txnString = version + "|" + transCode + "|" + merchantId + "|" + merOrderNum + "|" + bussId + "|"
        + tranAmt + "|" + sysTraceNum + "|" + tranDateTime + "|" + currencyType + "|" + merURL + "|" + backURL
        + "|" + orderInfo + "|" + userId;
    String signValue = MD5Utils.md5(txnString + dataKey, "utf-8"); // 数字签名

    // 封装参数
    MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
    parameter.add("version", version); // 版本号
    parameter.add("transCode", transCode); // 交易代码
    parameter.add("merchantId", merchantId); // 商户编号
    parameter.add("merOrderNum", merOrderNum); // 商户订单号
    parameter.add("bussId", bussId); // 业务代码
    parameter.add("tranAmt", tranAmt); // 交易金额(单位： 分)
    parameter.add("sysTraceNum", sysTraceNum); // 商户请求流水号
    parameter.add("tranDateTime", tranDateTime); // 交易时间
    parameter.add("currencyType", currencyType); // 货币代码
    parameter.add("merURL", merURL); // 商户返回页面
    parameter.add("backURL", backURL); // 回调商户地址
    parameter.add("orderInfo", orderInfo); // 订单信息
    parameter.add("userId", userId); // 用户 ID
    parameter.add("userNameHF", userNameHF); // 开户名（姓名）
    parameter.add("quickPayCertNo", quickPayCertNo); // 身份证号
    parameter.add("arrviedAcctNo", arrviedAcctNo); // 入账卡卡号
    parameter.add("arrviedPhone", arrviedPhone); // 入账卡绑定手机号
    parameter.add("arrviedBankName", arrviedBankName); // 入账卡开户行
    parameter.add("userPhoneHF", userPhoneHF); // 交易卡绑定手机号
    parameter.add("userAcctNo", userAcctNo); // 交易卡卡号
    parameter.add("cardCvn2", cardCvn2); // 交易卡cvv2
    parameter.add("cardExpire", cardExpire); // 有效日期
    parameter.add("userIp", userIp); // 订单用户 IP
    parameter.add("bankId", bankId); // 支付方式代码
    parameter.add("stlmId", stlmId); // 结算规则代码
    parameter.add("entryType", entryType); // 入口类型
    parameter.add("attach", attach); // 附加数据
    parameter.add("reserver1", fee); // 保留域 1 商户上传的手续费
    parameter.add("reserver2", reserver2); // 保留域 2
    parameter.add("reserver3", reserver3); // 保留域 3
    parameter.add("reserver4", reserver4); // 保留域 4
    parameter.add("signValue", signValue); // 数字签名

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
   */
  private void dealResponse(String result) {
    Map<String, String> response = parseResponse(result);
    String transCode = response.get("transCode"); // 交易代码
    String merchantId = response.get("merchantId"); // 商户编号
    String respCode = response.get("respCode"); // 返回码
    String sysTraceNum = response.get("sysTraceNum"); // 商户请求流水号
    String merOrderNum = response.get("merOrderNum"); // 商户订单号
    String orderId = response.get("orderId"); // 支付网关订单号
    String bussId = response.get("bussId"); // 业务代码
    String tranAmt = response.get("tranAmt"); // 实际交易金额
    String orderAmt = response.get("orderAmt"); // 订单金额
    String bankFeeAmt = response.get("bankFeeAmt"); // 支付渠道手续费
    String integralAmt = response.get("integralAmt"); // 积分抵扣金额
    String vaAmt = response.get("vaAmt"); // 虚拟账户支付金额
    String bankAmt = response.get("bankAmt"); // 支付渠道支付金额
    String bankId = response.get("bankId"); // 支付渠道 ID
    String integralSeq = response.get("integralSeq"); // 积分交易流水号
    String vaSeq = response.get("vaSeq"); // 虚拟账户交易流水号
    String bankSeq = response.get("bankSeq"); // 支付机构交易流水号
    String tranDateTime = response.get("tranDateTime"); // 交易时间
    String payMentTime = response.get("payMentTime"); // 支付时间
    String settleDate = response.get("settleDate"); // 清算日
    String currencyType = response.get("currencyType"); // 货币代码
    String orderInfo = response.get("orderInfo"); // 订单信息
    String userId = response.get("userId"); // 用户 ID
    String userIp = response.get("userIp"); // 支付用户 IP
    String reserver1 = response.get("reserver1"); // 保留域 1
    String reserver2 = response.get("reserver2"); // 保留域 2
    String reserver3 = response.get("reserver3"); // 保留域 3
    String reserver4 = response.get("reserver4"); // 保留域 4
    String signValue = response.get("signValue"); // 数字签名

    // 生成签名
    String txnString = transCode + "|" + merchantId + "|" + respCode + "|" + sysTraceNum + "|" + merOrderNum + "|"
        + orderId + "|" + bussId + "|" + tranAmt + "|" + orderAmt + "|" + bankFeeAmt + "|" + integralAmt + "|"
        + vaAmt + "|" + bankAmt + "|" + bankId + "|" + integralSeq + "|" + vaSeq + "|" + bankSeq + "|"
        + tranDateTime + "|" + payMentTime + "|" + settleDate + "|" + currencyType + "|" + orderInfo + "|"
        + userId;
    String genSignValue = MD5Utils.md5(txnString + dataKey, "utf-8");
    // 将生成的签名与收到的签名进行比较
    boolean verify = genSignValue.equals(signValue);
    if (!verify) {
      logger.info("验证签名失败");
    }

    logger.info("商户请求流水号 " + sysTraceNum);
    logger.info("商户订单号 " + merOrderNum);
    try {
      reserver3 = parseHex(reserver3);
      logger.info(reserver3);
    } catch (Exception e) {
      logger.info("解析返回消息失败", e);
    }

  }

  /**
   * 解析16进制字符串
   */
  private String parseHex(String response) throws DecoderException, UnsupportedEncodingException {
    byte[] bytes = Hex.decodeHex(response);
    return new String(bytes, "utf-8");
  }

  /**
   * 解析接口响应
   */
  private Map<String, String> parseResponse(String response) {
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
