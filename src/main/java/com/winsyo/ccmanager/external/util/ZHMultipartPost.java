package com.winsyo.ccmanager.external.util;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;

//import com.eyue.qlqw.service.DataOutputStream;
//import com.eyue.qlqw.service.KeyValue;
//import org.apache.commons.collections.KeyValue;

/**
 * java 模拟 ENCTYPE="multipart/form-data"方式 提交FORM
 */
public class ZHMultipartPost {

  /**
   * @param args
   * @throws Exception
   */
  //	public static void main(String[] args) {
  //
  //		URL url = null;
  //		HttpURLConnection httpurlconnection = null;
  //		try {
  //			// 渠道商号
  //			String channelId = "10000325210";// 10000325210
  //			// 渠道商密钥
  //			String APIKEY = "7de07cd9b97e48aca88afafff6b27808";
  //
  //			// 将渠道商号和渠道商密钥组成<id>:<key>的字符串进行Base64位加密
  //			String sign = channelId + ":" + APIKEY;
  //			byte[] content = sign.getBytes();
  //			String base64 = (new BASE64Encoder()).encode(content);
  //
  //			/**
  //			 * multipart/form-data 分隔符(boundary)是必须的.
  //			 * ----multipartPostRequestMCUServer 是分隔符，分隔多个文件、表单项
  //			 * */
  //			String mch_id = "100001793057";
  //			String sd = "http://ulineapi.cms.cmbxm.mbcloud.com/v1/mchinlet/refreshwxconfig?mch_id="
  //					+ mch_id;
  //			url = new URL(sd);
  //			String boundary = "----multipartPostRequestMCUServer";
  //			httpurlconnection = (HttpURLConnection) url.openConnection();
  //			httpurlconnection.setDoOutput(true);
  //			httpurlconnection.setRequestMethod("POST");
  //			httpurlconnection.setRequestProperty("Content-Type",
  //					"multipart/form-data; boundary=" + boundary);
  //			httpurlconnection.setRequestProperty("Authorization", "Basic "
  //					+ base64);
  //			/* put the data from the FORM into a Hashtable */
  //			Hashtable<String, String> hashtable = new Hashtable<String, String>();
  //			hashtable.put("mch_type", "mch");
  //			// hashtable.put("config_channel", "reg_offline");
  //			// hashtable.put("config_key", "subscribe_appid");
  //			// hashtable.put("operation", "login");
  //			String boundaryMessage = getBoundaryMessage(boundary, hashtable,
  //					"", "", "");
  //			String endBoundary = "\r\n--" + boundary + "--\r\n";
  //			System.out.println(boundaryMessage);
  //
  //			OutputStream dout = httpurlconnection.getOutputStream();
  //			dout.write(boundaryMessage.getBytes());
  //			dout.write(endBoundary.getBytes());
  //			dout.flush();
  //			dout.close();
  //
  //			int code = httpurlconnection.getResponseCode();
  //			System.out.println("-------code   " + code);
  //
  //			InputStream is = httpurlconnection.getInputStream();
  //
  //			try {
  //				byte[] buffer = new byte[1024];
  //				@SuppressWarnings("unused")
  //				int len = -1;
  //				while ((len = is.read(buffer)) != -1) {
  //					System.out.println(new String(buffer));
  //
  //					String sdd = new String(buffer);
  //					JSONObject js = JSONObject.fromObject(sdd);
  //					System.out.println(js.getJSONObject("content").getString(
  //							"mch_id"));
  //					System.out.println(js.getJSONObject("error").getString(
  //							"type"));
  //				}
  //			} finally {
  //				is.close();
  //			}
  //		} catch (Exception e) {
  //			e.printStackTrace();
  //		} finally {
  //			if (httpurlconnection != null)
  //				httpurlconnection.disconnect();
  //
  //		}
  //
  //	}

  //	public static void main(String[] args) throws Exception {
  //		// 渠道商号
  //		String channelId = "10000325210";// 10000325210
  //		// 渠道商密钥
  //		String APIKEY = "7de07cd9b97e48aca88afafff6b27808";
  //
  //		// 将渠道商号和渠道商密钥组成<id>:<key>的字符串进行Base64位加密
  //		String sign = channelId + ":" + APIKEY;
  //		byte[] content = sign.getBytes();
  //		String base64 = (new BASE64Encoder()).encode(content);
  //		String mch_id = "100001793057";
  //		//
  ////		String url = "http://ulineapi.cms.cmbxm.mbcloud.com/v1/mchinlet/refreshwxconfig?mch_id="+ mch_id;
  //		//商户进件修改
  //		///v1/mchinlet/update?mch_id=<商户的mch_id>
  //		String url = "http://ulineapi.cms.cmbxm.mbcloud.com/v1/mchinlet/update?mch_id="+ mch_id;
  //
  //		
  //		Hashtable<String, String> hashtable = new Hashtable<String, String>();
  ////		hashtable.put("mch_type", "mch");
  //		hashtable.put("payment_type1", "23");
  //		hashtable.put("license_period", "1");
  //		JSONObject js = MultiPost(url,hashtable,base64);
  //		System.out.println(js);
  //		
  //	}
  //	

  static HostnameVerifier hv = new HostnameVerifier() {
    @Override
    public boolean verify(String urlHostName, SSLSession session) {
      System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
      return true;
    }

    public boolean verify1(String arg0, SSLSession arg1) {
      // TODO Auto-generated method stub
      return false;
    }
  };

  private static void trustAllHttpsCertificates() throws Exception {
    TrustManager[] trustAllCerts = new TrustManager[1];
    TrustManager tm = new miTM();
    trustAllCerts[0] = tm;
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, null);
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }

  static class miTM implements TrustManager, X509TrustManager {

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
      // TODO Auto-generated method stub

    }

    @Override
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
      // TODO Auto-generated method stub

    }
  }

  /*POST请求  通过multipart/form-data形式*/
  public static JSONObject MultiPost(Hashtable<String, File> fileHashtable, String url, Hashtable<String, String> hashtable, String base64) throws Exception {
    URL realUrl = null;
    HttpURLConnection httpurlconnection = null;
    JSONObject resultJson = null;
    try {
      /**
       * multipart/form-data 分隔符(boundary)是必须的.
       * ----multipartPostRequestMCUServer 是分隔符，分隔多个文件、表单项
       * */
      realUrl = new URL(url);
      String boundary = "----multipartPostRequestMCUServer";
      trustAllHttpsCertificates();
      HttpsURLConnection.setDefaultHostnameVerifier(hv);
      httpurlconnection = (HttpURLConnection) realUrl.openConnection();
      httpurlconnection.setDoOutput(true);
      httpurlconnection.setRequestMethod("POST");
      httpurlconnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      httpurlconnection.setRequestProperty("Authorization", "Basic " + base64);

      File fileImgF = fileHashtable.get("id_card_img_f");
      File fileImgB = fileHashtable.get("id_card_img_b");
      File fileImg = fileHashtable.get("license_img");
      Map<String, String> map = new HashMap<String, String>();
      map.put("id_card_img_f", fileImgF.getName());
      map.put("id_card_img_b", fileImgB.getName());
      map.put("license_img", fileImg.getName());
      HashMap<String, File> fileMap = new HashMap<String, File>();
      fileMap.put("id_card_img_f", fileImgF);
      fileMap.put("id_card_img_b", fileImgB);
      fileMap.put("license_img", fileImg);

      String boundaryMessage = getBoundaryMessage(boundary, hashtable, map);

      String endBoundary = "--" + boundary + "\r\n";
      System.out.println(boundaryMessage);

      OutputStream dout = httpurlconnection.getOutputStream();
      File file = null;
      dout.write(boundaryMessage.getBytes());
      for (String key : map.keySet()) {
        StringBuffer res = new StringBuffer();
        String fileField = key;
        String fileName = map.get(key);
        //				String fileType = "png";//text/plain
        String fileType = "text/plain";//
        if (fileField != null && !"".equals(fileField) && fileName != null && !"".equals(fileName) && fileType != null && !"".equals(fileType)) {
          res.append(endBoundary).append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"").append(fileName).append("\"\r\n")
              .append("Content-Type: ")
              .append(fileType).append("\r\n\r\n");
          dout.write(res.toString().getBytes());
          file = fileMap.get(key);
          InputStream in = new FileInputStream(file);
          byte[] bufferOut = new byte[1024];
          int bytes = 0;
          // 每次1024KB数据,并且将文件数据写入到输出流中
          while ((bytes = in.read(bufferOut)) != -1) {
            dout.write(bufferOut, 0, bytes);
          }
          dout.write("\r\n".getBytes());
        }
      }
      dout.write(("--" + boundary + "--" + "\r\n").getBytes());

      //            DataInputStream in = new DataInputStream(
      //                    new FileInputStream(fileImgF));
      //            FileInputStream in=new FileInputStream(fileImgF);

      //            InputStream in=new FileInputStream(fileImgF);
      //            byte[] bufferOut = new byte[1024];
      //            int bytes = 0;
      //            // 每次1024KB数据,并且将文件数据写入到输出流中
      //            while ((bytes = in.read(bufferOut)) != -1) {
      //            	dout.write(bufferOut, 0, bytes);
      //            }
      //
      //
      //			@SuppressWarnings("resource")
      ////			FileInputStream fis2=new FileInputStream(fileImgB);
      //			InputStream fis2=new FileInputStream(fileImgB);
      //			byte[] buffer2=new byte[1024];
      //			int len2 = 0;
      //	        while((len2=fis2.read(buffer2))>=0){
      //	        	dout.write(buffer2, 0, len2);
      //	        }
      //
      //			@SuppressWarnings("resource")
      ////			FileInputStream fis3=new FileInputStream(fileImg);
      //			InputStream fis3=new FileInputStream(fileImg);
      //			byte[] buffer3=new byte[1024];
      //			int len3 = 0;
      //	        while((len3=fis3.read(buffer3))>=0){
      //	        	dout.write(buffer3, 0, len3);
      //	        }

      dout.flush();
      dout.close();

      int code = httpurlconnection.getResponseCode();
      System.out.println("-------code   " + code);

      InputStream is = httpurlconnection.getInputStream();

      try {
        byte[] buffer = new byte[1024];
        @SuppressWarnings("unused")
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
          //					System.out.println(new String(buffer));

          String sdd = new String(buffer);
          resultJson = JSONObject.parseObject(sdd);
          //					System.out.println(resultJson.getJSONObject("content").getString("mch_id"));
          //					System.out.println(resultJson.getJSONObject("error").getString("type"));
        }
      } finally {
        is.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e);
    } finally {
      if (httpurlconnection != null) {
        httpurlconnection.disconnect();
      }

    }
    return resultJson;
  }

  @SuppressWarnings("unchecked")
  public static String getBoundaryMessage(String boundary, Hashtable params, Map<String, String> map) {
    StringBuffer res = new StringBuffer();
    Enumeration keys = params.keys();
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      String value = (String) params.get(key);
      res.append("--").append(boundary).append("\r\n").append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n").append("\r\n").append(value).append("\r\n");
    }

    //		for(String key:map.keySet()){
    //			String fileField = key;
    //			String fileName = map.get(key);
    ////			String fileType = "png";//text/plain
    //			String fileType = "text/plain";//
    //			if (fileField != null && !"".equals(fileField) && fileName != null
    //					&& !"".equals(fileName) && fileType != null
    //					&& !"".equals(fileType)) {
    //				res.append("Content-Disposition: form-data; name=\"")
    //						.append(fileField).append("\"; filename=\"")
    //						.append(fileName).append("\"\r\n").append("Content-Type: ")
    //						.append(fileType).append("\r\n\r\n").append("--").append(boundary)
    //						.append("\r\n");;
    //
    //
    //			}
    //		}

    return res.toString();
  }

  /*POST请求  通过multipart/form-data形式*/
  public static JSONObject MultiPost2(String url, Hashtable<String, String> hashtable, String base64) throws Exception {
    URL realUrl = null;
    HttpURLConnection httpurlconnection = null;
    JSONObject resultJson = null;
    try {
      /**
       * multipart/form-data 分隔符(boundary)是必须的.
       * ----multipartPostRequestMCUServer 是分隔符，分隔多个文件、表单项
       * */
      realUrl = new URL(url);
      String boundary = "----multipartPostRequestMCUServer";
      trustAllHttpsCertificates();
      HttpsURLConnection.setDefaultHostnameVerifier(hv);
      httpurlconnection = (HttpURLConnection) realUrl.openConnection();
      httpurlconnection.setDoOutput(true);
      httpurlconnection.setRequestMethod("POST");
      httpurlconnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      httpurlconnection.setRequestProperty("Authorization", "Basic " + base64);

      String boundaryMessage = getBoundaryMessage2(boundary, hashtable, "", "", "");
      String endBoundary = "\r\n--" + boundary + "--\r\n";
      System.out.println(boundaryMessage);

      OutputStream dout = httpurlconnection.getOutputStream();
      dout.write(boundaryMessage.getBytes());
      dout.write(endBoundary.getBytes());
      dout.flush();
      dout.close();

      int code = httpurlconnection.getResponseCode();
      System.out.println("-------code   " + code);

      InputStream is = httpurlconnection.getInputStream();

      try {
        byte[] buffer = new byte[1024];
        @SuppressWarnings("unused")
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
          //					System.out.println(new String(buffer));

          String sdd = new String(buffer);
          resultJson = JSONObject.parseObject(sdd);
          //					System.out.println(resultJson.getJSONObject("content").getString("mch_id"));
          //					System.out.println(resultJson.getJSONObject("error").getString("type"));
        }
      } finally {
        is.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e);
    } finally {
      if (httpurlconnection != null) {
        httpurlconnection.disconnect();
      }

    }
    return resultJson;
  }

  @SuppressWarnings("unchecked")
  public static String getBoundaryMessage2(String boundary, Hashtable params, String fileField, String fileName, String fileType) {
    StringBuffer res = new StringBuffer("--").append(boundary).append("\r\n");
    Enumeration keys = params.keys();
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      String value = (String) params.get(key);
      res.append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n").append("\r\n").append(value).append("\r\n").append("--").append(boundary).append("\r\n");
    }
    if (fileField != null && !"".equals(fileField) && fileName != null && !"".equals(fileName) && fileType != null && !"".equals(fileType)) {
      res.append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"").append(fileName).append("\"\r\n").append("Content-Type: ").append(fileType)
          .append("\r\n\r\n");
    }
    return res.toString();
  }

  /*POST请求  通过multipart/form-data形式*/
  public static JSONObject MultiPost1(Hashtable<String, File> fileHashtable, String url, Hashtable<String, String> hashtable, String base64) throws Exception {
    URL realUrl = null;
    HttpURLConnection httpurlconnection = null;
    JSONObject resultJson = null;
    try {
      /**
       * multipart/form-data 分隔符(boundary)是必须的.
       * ----multipartPostRequestMCUServer 是分隔符，分隔多个文件、表单项
       * */
      realUrl = new URL(url);
      String boundary = "----multipartPostRequestMCUServer";
      httpurlconnection = (HttpURLConnection) realUrl.openConnection();
      httpurlconnection.setDoOutput(true);
      httpurlconnection.setRequestMethod("POST");
      httpurlconnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      httpurlconnection.setRequestProperty("Authorization", "Basic " + base64);

      File fileImgF = fileHashtable.get("id_card_img_f");
      File fileImgB = fileHashtable.get("id_card_img_b");
      File fileImg = fileHashtable.get("license_img");
      Map<String, String> map = new HashMap<String, String>();
      map.put("id_card_img_f", fileImgF.getName());
      map.put("id_card_img_b", fileImgB.getName());
      map.put("license_img", fileImg.getName());

      String boundaryMessage = getBoundaryMessage1(boundary, hashtable, map);

      String endBoundary = "\r\n--" + boundary + "--\r\n";
      System.out.println(boundaryMessage);

      OutputStream dout = httpurlconnection.getOutputStream();

      dout.write(boundaryMessage.getBytes());
      dout.write(endBoundary.getBytes());

      DataInputStream in = new DataInputStream(new FileInputStream(fileImgF));
      byte[] bufferOut = new byte[1024];
      int bytes = 0;
      // 每次�?KB数据,并且将文件数据写入到输出流中
      while ((bytes = in.read(bufferOut)) != -1) {
        dout.write(bufferOut, 0, bytes);
      }

      @SuppressWarnings("resource")
      FileInputStream fis2 = new FileInputStream(fileImgB);
      byte[] buffer2 = new byte[1024];
      int len2 = 0;
      while ((len2 = fis2.read(buffer2)) >= 0) {
        dout.write(buffer2, 0, len2);
      }

      @SuppressWarnings("resource")
      FileInputStream fis3 = new FileInputStream(fileImg);
      byte[] buffer3 = new byte[1024];
      int len3 = 0;
      while ((len3 = fis3.read(buffer3)) >= 0) {
        dout.write(buffer3, 0, len3);
      }

      dout.flush();
      dout.close();

      int code = httpurlconnection.getResponseCode();
      System.out.println("-------code   " + code);

      InputStream is = httpurlconnection.getInputStream();

      try {
        byte[] buffer = new byte[1024];
        @SuppressWarnings("unused")
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
          //					System.out.println(new String(buffer));

          String sdd = new String(buffer);
          resultJson = JSONObject.parseObject(sdd);
          //					System.out.println(resultJson.getJSONObject("content").getString("mch_id"));
          //					System.out.println(resultJson.getJSONObject("error").getString("type"));
        }
      } finally {
        is.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e);
    } finally {
      if (httpurlconnection != null) {
        httpurlconnection.disconnect();
      }

    }
    return resultJson;
  }

  @SuppressWarnings("unchecked")
  public static String getBoundaryMessage1(String boundary, Hashtable params, Map<String, String> map) {
    StringBuffer res = new StringBuffer("--").append(boundary).append("\r\n");
    Enumeration keys = params.keys();
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      String value = (String) params.get(key);
      res.append("Content-Disposition: form-data; name=\"").append(key).append("\"\r\n").append("\r\n").append(value).append("\r\n").append("--").append(boundary).append("\r\n");
    }

    for (String key : map.keySet()) {
      String fileField = key;
      String fileName = map.get(key);
      //			String fileType = "png";//text/plain
      String fileType = "text/plain";//
      if (fileField != null && !"".equals(fileField) && fileName != null && !"".equals(fileName) && fileType != null && !"".equals(fileType)) {
        res.append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"").append(fileName).append("\"\r\n").append("Content-Type: ")
            .append(fileType).append("\r\n\r\n")
            .append("--").append(boundary).append("\r\n");
        ;
      }
    }

    return res.toString();
  }

  /*POST请求*/
  public static String sendPost(String url, Map<String, String> addMap, String base64) throws Exception {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";

    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      URLConnection conn = realUrl.openConnection();

      // 设置通用的请求属性
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("Content-Type", "multipart/form-data");
      // conn.setRequestProperty("user-agent",
      // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      conn.setRequestProperty("user-agent", "python-requests/2.13.0");
      conn.setRequestProperty("Authorization", "Basic " + base64);

      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(addMap);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println("line:" + line);
        result += line;
      }
    } catch (Exception e) {
      System.out.println("发送 POST 请求出现异常！" + e);
      e.printStackTrace();
      throw new Exception(e);
    }
    // 使用finally块来关闭输出流、输入流
    finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
        throw new Exception(ex);
      }
    }
    return result;
  }

  /*GET请求*/
  public static String sendGet(String url, String param, String base64) throws Exception {
    String result = "";
    BufferedReader in = null;
    try {
      String urlName = url + "?" + param;
      URL realUrl = new URL(urlName);
      //打开和URL之间的连接
      trustAllHttpsCertificates();
      HttpsURLConnection.setDefaultHostnameVerifier(hv);
      URLConnection conn = realUrl.openConnection();
      //设置通用的请求属性
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
      conn.setRequestProperty("Authorization", "Basic " + base64);
      //建立实际的连接

      conn.connect();
      //获取所有响应头字段
      Map<String, List<String>> map = conn.getHeaderFields();
      //遍历所有的响应头字段
      for (String key : map.keySet()) {
        System.out.println(key + "--->" + map.get(key));
      }
      //定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      System.out.println("发送GET请求出现异常！" + e);
      e.printStackTrace();
      throw new Exception(e);
    }
    //使用finally块来关闭输入流
    finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
        throw new Exception(ex);
      }
    }
    return result;
  }

  public static String postFile(String actionUrl, Map<String, String> params, Map<String, File> files, String key) throws IOException {
    String BOUNDARY = "---------------------------" + System.currentTimeMillis();//分割符
    String PREFIX = "--"; //前缀
    String LINEND = "\r\n"; //换行符
    String MULTIPART_FROM_DATA = "multipart/form-data";//数据类型
    String CHARSET = "UTF-8";//字符编码

    URL uri = new URL(actionUrl);
    HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
    conn.setReadTimeout(5 * 1000); // 缓存的最长时间
    conn.setDoInput(true);// 允许输入
    conn.setDoOutput(true);// 允许输出
    conn.setUseCaches(false); // 不允许使用缓存

    //        conn.setRequestProperty("Authorization", "Basic " + Base64.encodeBytes((username + ":" + passwd).getBytes()));//认证
    conn.setRequestProperty("Authorization", key);//认证

    conn.setRequestMethod("POST");
    conn.setRequestProperty("connection", "keep-alive");
    //      conn.setRequestProperty("Charsert", "UTF-8");
    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

    // 首先组拼文本类型的参数
    StringBuilder sb = new StringBuilder();
    for (Entry<String, String> entry : params.entrySet()) {
      sb.append(PREFIX);
      sb.append(BOUNDARY);
      sb.append(LINEND);
      sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
      //          sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
      sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
      sb.append(LINEND);
      sb.append(entry.getValue());
      sb.append(LINEND);

    }
    //        System.out.println(sb);

    DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
    outStream.write(sb.toString().getBytes());
    //        fos.write(sb.toString().getBytes());
    InputStream in = null;
    // 发送文件数据
    if (files != null) {
      for (Entry<String, File> file : files.entrySet()) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append(PREFIX);
        sb1.append(BOUNDARY);
        sb1.append(LINEND);
        // name是post中传参的键 filename是文件的名称  
        sb1.append("Content-Disposition: form-data; name=\"" + file.getKey() + "\"; filename=\"" + file.getValue() + "\"" + LINEND);
        sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
        sb1.append(LINEND);
        //                System.out.println(sb1);
        outStream.write(sb1.toString().getBytes());
        //                fos.write(sb1.toString().getBytes());  
        InputStream is = new FileInputStream(file.getValue());

        int bytesAvailable;
        while ((bytesAvailable = is.available()) > 0) {
          int bufferSize = Math.min(bytesAvailable, 4096);
          byte[] buffer = new byte[bufferSize];
          int bytesRead = is.read(buffer, 0, bufferSize);
          outStream.write(buffer, 0, bytesRead);
        }

        is.close();
        outStream.write(LINEND.getBytes());
        //                fos.write(LINEND.getBytes());  
      }

      // 请求结束标志  

    }
    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
    outStream.write(end_data);
    //        fos.write(end_data); 
    outStream.flush();
    //        fos.flush();  
    //        fos.close();  
    outStream.close();
    // 得到响应码  
    StringBuilder sb2 = null;
    int res = conn.getResponseCode();
    if (res == 200) {
      in = conn.getInputStream();
      int ch;
      sb2 = new StringBuilder();
      while ((ch = in.read()) != -1) {
        sb2.append((char) ch);
      }
      //            Util.print("sb222-->"+sb2);  
    } else {
      return "error";
    }
    in.close();
    outStream.close();
    conn.disconnect();
    //        return sb2.toString();
    String message = new String(sb2.toString().getBytes("iso-8859-1"), "UTF-8");
    return message;
  }

  /**
   * @param @param url 请求地址
   * @param @param map 集合
   * @param @param charset 编码 utf-8
   * @param @return 设定文件
   * @return String    返回类型
   * @Title: doPost post请求
   * @Description: TODO(这里用一句话描述这个方法的作用)
   */
  public static String doPost(String url, Map<String, String> map, String charset) {
    HttpClient httpClient = null;
    HttpPost httpPost = null;
    String result = null;
    try {
      httpClient = new SSLClient();
      httpPost = new HttpPost(url);
      httpPost.setHeader("appkey", "keda");
      //设置参数  
      List<NameValuePair> list = new ArrayList<NameValuePair>();
      Iterator iterator = map.entrySet().iterator();
      while (iterator.hasNext()) {
        Entry<String, String> elem = (Entry<String, String>) iterator.next();
        list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
      }
      if (list.size() > 0) {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
        httpPost.setEntity(entity);
      }
      HttpResponse response = httpClient.execute(httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, charset);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public static String dopost1(String urlPath, String jsonObject) throws Exception {
    try {
      // Configure and open a connection to the site you will send the request
      URL url = new URL(urlPath);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      //设置为POST提交
      urlConnection.setRequestMethod("POST");
      // 设置doOutput属性为true表示将使用此urlConnection写入数据
      urlConnection.setRequestProperty("Charset", "UTF-8");
      urlConnection.setDoOutput(true);
      // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
      urlConnection.setRequestProperty("content-type", "application/json");
      urlConnection.setRequestProperty("accept", "application/json");
      // 得到请求的输出流对象
      OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
      // 把数据写入请求的Body
      out.write(jsonObject);
      out.flush();
      out.close();

      // 从服务器读取响应
      InputStream inputStream = urlConnection.getInputStream();
      String encoding = urlConnection.getContentEncoding();
      String body = getString(inputStream);
      System.out.println(urlConnection.getResponseCode());
      System.out.println(body);
      if (urlConnection.getResponseCode() == 200) {
        return body;
      } else {
        throw new Exception(body);
      }
    } catch (IOException e) {

      throw e;
    }
  }

  public static String doJsonPost(String urlPath, String Json) {
    // HttpClient 6.0被抛弃了
    String result = "";
    BufferedReader reader = null;
    try {
      URL url = new URL(urlPath);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setRequestProperty("Connection", "Keep-Alive");
      conn.setRequestProperty("Charset", "UTF-8");
      // 设置文件类型:
      conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      // 设置接收类型否则返回415错误
      //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
      // 往服务器里面发送数据
      if (Json != null && !TextUtils.isEmpty(Json)) {
        byte[] writebytes = Json.getBytes();
        // 设置文件长度
        conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
        OutputStream outwritestream = conn.getOutputStream();
        outwritestream.write(Json.getBytes());
        outwritestream.flush();
        outwritestream.close();

      }
      if (conn.getResponseCode() == 200) {
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        result = reader.readLine();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  public static String getString(InputStream is) throws Exception {
    InputStreamReader inputStreamReader = new InputStreamReader(is, "utf-8");
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String str = null;
    StringBuffer buffer = new StringBuffer();
    while ((str = bufferedReader.readLine()) != null) {
      buffer.append(str);
    }
    bufferedReader.close();
    return buffer.toString().trim().substring(1);
  }

  public static class SSLClient extends DefaultHttpClient {

    public SSLClient() throws Exception {
      super();
      SSLContext ctx = SSLContext.getInstance("TLS");
      X509TrustManager tm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = this.getConnectionManager();
      SchemeRegistry sr = ccm.getSchemeRegistry();
      sr.register(new Scheme("https", 443, ssf));
    }
  }

  public static String readJSONString(HttpServletRequest request) {
    StringBuffer json = new StringBuffer();
    String line = null;
    try {
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null) {
        json.append(line);
      }
      if (json.length() < 1) {
        Map<String, String[]> hm = request.getParameterMap();
        if (hm != null && hm.size() > 0) {
          json.append(readjson(hm).toString());
        }
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return json.toString();
  }

  public static JSONObject readjson(Map<String, String[]> hm) {
    JSONObject jobj = new JSONObject();
    //通过循环遍历的方式获得key和value并set到JSONObject中
    Iterator it = hm.keySet().iterator();
    while (it.hasNext()) {
      String key = it.next().toString();
      String[] values = hm.get(key);
      jobj.put(key, values[0]);
    }
    return jobj;
  }

  public static JSONObject readjson(HttpServletRequest request) {
    JSONObject JSONObject = new JSONObject();
    Map pmap = request.getParameterMap();
    //通过循环遍历的方式获得key和value并set到jsonobject中
    Iterator it = pmap.keySet().iterator();
    while (it.hasNext()) {
      String key = it.next().toString();
      String[] values = (String[]) pmap.get(key);
      JSONObject.put(key, values[0]);
    }
    return JSONObject;
  }

  public static JSONObject doPost(String url, JSONObject json) {
    DefaultHttpClient client = new DefaultHttpClient();
    client.getParams().setIntParameter("http.socket.timeout", 300000);
    HttpPost post = new HttpPost(url);
    //              post.setCharacterEncoding("utf-8");
    JSONObject response = null;
    try {
      StringEntity s = new StringEntity(json.toString(), "utf-8");
      s.setContentEncoding("UTF-8");
      s.setContentType("application/json");//发送json数据需要设置contentType
      post.setEntity(s);
      HttpResponse res = client.execute(post);
      if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
        response = JSONObject.parseObject(result);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return response;
  }

}