package com.winsyo.ccmanager.external.znyoo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.winsyo.ccmanager.external.util.MD5Utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ZnyooService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final String key = "c6b1f9716b4b39174819ffa4edf06649";

  private static final String encryptId = "000600000000027";

  private static final Integer apiVersion = 1;

  private static final String serverUrl = "https://www.znyoo.com/oss-transaction/gateway/";

  public static final String agencyType = "zhtxn";

  public static final String officeCode = "000600000000027";

  private static DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

  public static void main(String[] args) {
    ZnyooService znyooService = new ZnyooService();
//    String pay = znyooService.pay("000600000000027", 16.0, "5316930021582222", "13699999950", 0.5, "吴志文", "350524199011082558", "309", "2002"); // pay_20180521142801782
//    String code = znyooService.queryPayOrder("pay_20180521142801782");
//    znyooService.settle("pay_20180521142801782", 0.5); // settle_20180521145427670
//    znyooService.querySettleOrder("settle_20180521145427670");
  }

  /**
   * 下单
   *
   * @param srcAmt 金额（元）
   * @param accountNumber 卡号
   * @param tel 预留手机号
   * @param fastpayFee 交易手续费
   * @param holderName 姓名
   * @param idcard 身份证
   * @param cvv2 CVV
   * @param expired 卡片有效期
   * @return 交易订单号
   */
  public String pay(Double srcAmt, String accountNumber, String tel, Double fastpayFee, String holderName, String idcard, String cvv2, String expired) {
    // 方法名
    String method = "fastpayPrecreate2";

    // 参数
    JSONObject obj = new JSONObject();
    obj.put("encryptId", encryptId);
    obj.put("apiVersion", apiVersion);
    obj.put("txnDate", Instant.now().toEpochMilli());
    obj.put("method", method);
    obj.put("mid", officeCode); // 商户号
    obj.put("srcAmt", srcAmt); // 金额（元）
    obj.put("bizOrderNumber", "pay_" + sdf.format(LocalDateTime.now())); // 商户订单号
    obj.put("accountNumber", accountNumber); // 卡号
    obj.put("tel", tel); // 预留手机号
    obj.put("fastpayFee", fastpayFee); // 交易手续费 0.5%传0.5
    obj.put("agencyType", agencyType); // 通道标示 由接口方提供
    obj.put("holderName", holderName); // 姓名
    obj.put("idcard", idcard); // 身份证
    obj.put("cvv2", cvv2);
    obj.put("expired", expired);
    obj.put("settAccountNumber", "");
    obj.put("settAccountTel", "");
    String param = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);

    // 签名
    JSONObject content = new JSONObject();
    content.put("content", param);
    content.put("key", key);
    String signJson = JSON.toJSONString(content, SerializerFeature.WriteMapNullValue);
    String sign = MD5Utils.md5(signJson, "utf-8");

    // 发送请求
    JSONObject postData = new JSONObject();
    postData.put("content", param);
    postData.put("sign", sign);
    String response = sendHttpsPost(serverUrl + method, JSON.toJSONString(postData));
    JSONObject resultObj = JSONObject.parseObject(response);

    // 验证签名
    JSONObject resultSignObj = new JSONObject();
    resultSignObj.put("result", resultObj.getString("result"));
    resultSignObj.put("key", key);
    signJson = JSON.toJSONString(resultSignObj, SerializerFeature.WriteMapNullValue);
    sign = MD5Utils.md5(signJson, "utf-8");
    logger.info("我方生成签名 " + sign);
    logger.info("接受到的签名 " + resultObj.getString("sign"));
    logger.info("验证签名结果 " + String.valueOf(sign.equals(resultObj.getString("sign"))));

    JSONObject result = resultObj.getJSONObject("result");
    String code = result.getString("code");
    logger.info(code);
    if ("000000".equals(code)) {
      JSONObject data = result.getJSONObject("data");
      String bizOrderNumber = data.getString("bizOrderNumber");
      logger.info(bizOrderNumber);
      return bizOrderNumber;
    }
    return null;
  }

  /**
   * 查询交易订单
   *
   * @param bizOrderNumber 订单号
   */
  public String queryPayOrder(String bizOrderNumber) {
    // 方法名
    String method = "fastpayQuery";

    // 参数
    JSONObject obj = new JSONObject();
    obj.put("encryptId", encryptId);
    obj.put("apiVersion", apiVersion);
    obj.put("txnDate", Instant.now().toEpochMilli());
    obj.put("method", method);
    obj.put("mid", officeCode); // 商户号
    obj.put("bizOrderNumber", bizOrderNumber); // 订单号
    String param = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);

    // 签名
    JSONObject signData = new JSONObject();
    signData.put("content", param);
    signData.put("key", key);
    String signJson = JSON.toJSONString(signData, SerializerFeature.WriteMapNullValue);
    String sign = MD5Utils.md5(signJson, "utf-8");

    // 发送请求
    JSONObject postData = new JSONObject();
    postData.put("content", param);
    postData.put("sign", sign);
    String response = sendHttpsPost(serverUrl + method, JSON.toJSONString(postData));
    JSONObject resultObj = JSONObject.parseObject(response);

    // 验证签名
    JSONObject resultSignObj = new JSONObject();
    resultSignObj.put("result", resultObj.getString("result"));
    resultSignObj.put("key", key);
    signJson = JSON.toJSONString(resultSignObj, SerializerFeature.WriteMapNullValue);
    sign = MD5Utils.md5(signJson, "utf-8");
    logger.info("我方生成签名 " + sign);
    logger.info("接受到的签名 " + resultObj.getString("sign"));
    logger.info("验证签名结果 " + String.valueOf(sign.equals(resultObj.getString("sign"))));

    // 返回消息解析
    JSONObject result = resultObj.getJSONObject("result");
    String code = result.getString("code");
    String message = result.getString("message");
    logger.info("结果编码 " + code);
    logger.info("响应信息 " + message);

    JSONObject data = result.getJSONObject("data");
    String dataMessage = data.getString("dataMessage");
    String txnStatus = data.getString("txnStatus");
    logger.info("dataMessage " + dataMessage);
    logger.info("txnStatus " + txnStatus);

    return getResult(txnStatus);
  }

  /**
   * 结算
   *
   * @param extraFee 代付手续费
   */
  public String settle(String txn, Double extraFee) {
    // 方法
    String method = "fastpayTransferCreateByTxn";

    // 参数
    JSONObject obj = new JSONObject();
    obj.put("encryptId", encryptId);
    obj.put("apiVersion", apiVersion);
    obj.put("txnDate", Instant.now().toEpochMilli());
    obj.put("method", method);
    obj.put("mid", officeCode); // 商户号
    obj.put("bizOrderNumber", "settle_" + sdf.format(LocalDateTime.now())); // 订单号
    obj.put("extraFee", extraFee); // 代付手续费
//    obj.put("agencyType", agencyType); // 通道标示
    obj.put("txn", txn); // 通道标示

    String param = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);

    // 签名
    JSONObject signData = new JSONObject();
    signData.put("content", param);
    signData.put("key", key);
    String signJson = JSON.toJSONString(signData, SerializerFeature.WriteMapNullValue);
    String sign = MD5Utils.md5(signJson, "utf-8");

    // 发送请求
    JSONObject postData = new JSONObject();
    postData.put("content", param);
    postData.put("sign", sign);
    String response = sendHttpsPost(serverUrl + method, JSON.toJSONString(postData));
    JSONObject resultObj = JSONObject.parseObject(response);

    // 验证签名
    JSONObject resultSignObj = new JSONObject();
    resultSignObj.put("result", resultObj.getString("result"));
    resultSignObj.put("key", key);
    signJson = JSON.toJSONString(resultSignObj, SerializerFeature.WriteMapNullValue);
    sign = MD5Utils.md5(signJson, "utf-8");
    logger.info("我方生成签名 " + sign);
    logger.info("接受到的签名 " + resultObj.getString("sign"));
    logger.info("验证签名结果 " + String.valueOf(sign.equals(resultObj.getString("sign"))));

    // 返回消息解析
    JSONObject result = resultObj.getJSONObject("result");
    String code = result.getString("code");
    logger.info("结果编码 " + code);
    String message = result.getString("message");
    logger.info("响应信息 " + message);

    JSONObject data = result.getJSONObject("data");
    String dataMessage = data.getString("dataMessage");
    String txnStatus = data.getString("txnStatus");
    String bizOrderNumber = data.getString("bizOrderNumber");
    logger.info("查询结果 " + dataMessage);
    logger.info("txnStatus " + txnStatus);

    return getResult(txnStatus);
  }

  private String getResult(String txnStatus) {
    switch (txnStatus) {
      case "p": // 支付中
        return "1";

      case "s": // 交易成功
        return "0";

      case "c": // 交易失败
        return "2";

      default:
        return "2";
    }
  }

  /**
   * 查询结算订单
   */
  public void querySettleOrder(String bizOrderNumber) {
    // 方法
    String method = "fastpayTransferQuery";

    // 参数
    JSONObject obj = new JSONObject();
    obj.put("encryptId", encryptId);
    obj.put("apiVersion", apiVersion);
    obj.put("txnDate", Instant.now().toEpochMilli());
    obj.put("method", method);
    obj.put("mid", officeCode); // 商户号
    obj.put("bizOrderNumber", bizOrderNumber); // 订单号
    String param = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);

    // 签名
    JSONObject signData = new JSONObject();
    signData.put("content", param);
    signData.put("key", key);
    String signJson = JSON.toJSONString(signData, SerializerFeature.WriteMapNullValue);
    String sign = MD5Utils.md5(signJson, "utf-8");

    // 发送请求
    JSONObject postData = new JSONObject();
    postData.put("content", param);
    postData.put("sign", sign);
    String response = sendHttpsPost(serverUrl + method, JSON.toJSONString(postData));
    JSONObject resultObj = JSONObject.parseObject(response);

    // 验证签名
    JSONObject resultSignObj = new JSONObject();
    resultSignObj.put("result", resultObj.getString("result"));
    resultSignObj.put("key", key);
    signJson = JSON.toJSONString(resultSignObj, SerializerFeature.WriteMapNullValue);
    sign = MD5Utils.md5(signJson, "utf-8");
    logger.info("我方生成签名 " + sign);
    logger.info("接受到的签名 " + resultObj.getString("sign"));
    logger.info("验证签名结果 " + String.valueOf(sign.equals(resultObj.getString("sign"))));

    // 返回消息解析
    JSONObject result = resultObj.getJSONObject("result");
    String code = result.getString("code");
    logger.info("结果编码 " + code);
    String message = result.getString("message");
    logger.info("返回消息 " + message);

    JSONObject data = result.getJSONObject("data");
    logger.info(data.toString());

    String dataMessage = data.getString("dataMessage");
    logger.info("查询结果 " + dataMessage);
  }

  public String sendHttpsPost(String url, String params) {
    DataOutputStream out = null;
    BufferedReader in = null;
    StringBuffer result = new StringBuffer();
    URL u = null;
    HttpsURLConnection con = null;
    //尝试发送请求
    try {
      logger.info("请求参数：" + params);
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
      u = new URL(url);
      //打开和URL之间的连接
      con = (HttpsURLConnection) u.openConnection();
      //设置通用的请求属性
      con.setSSLSocketFactory(sc.getSocketFactory());
      con.setHostnameVerifier(new TrustAnyHostnameVerifier());
      //con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setUseCaches(false);
      //发送POST请求必须设置如下两行
      con.setDoOutput(true);
      con.setDoInput(true);

      con.connect();
      out = new DataOutputStream(con.getOutputStream());
      out.write(params.getBytes("utf-8"));
      // 刷新、关闭
      out.flush();
      out.close();
      //读取返回内容
      //InputStream is = con.getInputStream();
      //定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line).append(System.lineSeparator());
      }
      logger.info("请求返回结果：" + result);
      return result.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    //使用finally块来关闭输出流、输入流
    finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
        if (con != null) {
          con.disconnect();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return result.toString();
  }

  private static class TrustAnyTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[]{};
    }

  }

  private static class TrustAnyHostnameVerifier implements HostnameVerifier {

    public boolean verify(String hostname, SSLSession session) {
      return true;
    }

  }

}
