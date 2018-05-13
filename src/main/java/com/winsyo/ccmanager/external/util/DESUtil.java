package com.winsyo.ccmanager.external.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES加解密工具类
 * @author 程高伟
 * @date 2016年6月15日 上午10:02:50
 */
public class DESUtil {
  private static final String DES_ALGORITHM = "DES";
  private final static String DES = "DES";

  /**
   * DES加密
   * 
   * @param plainData 原始字符串
   * @param secretKey 加密密钥
   * @return 加密后的字符串
   * @throws Exception
   */
  public static String encryption(String plainData, String secretKey) throws Exception {

    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(DES_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {

    }

    try {
      // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must
      // be multiple of 8 when decrypting with padded cipher异常，
      // 不能把加密后的字节数组直接转换成字符串
      byte[] buf = cipher.doFinal(plainData.getBytes());

      return Base64Utils.encode(buf);

    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
      throw new Exception("IllegalBlockSizeException", e);
    } catch (BadPaddingException e) {
      e.printStackTrace();
      throw new Exception("BadPaddingException", e);
    }

  }

  /**
   * DES解密
   * @param secretData 密码字符串
   * @param secretKey 解密密钥
   * @return 原始字符串
   * @throws Exception
   */
  public static String decryption(String secretData, String secretKey) throws Exception {

    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(DES_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new Exception("NoSuchAlgorithmException", e);
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
      throw new Exception("NoSuchPaddingException", e);
    } catch (InvalidKeyException e) {
      e.printStackTrace();
      throw new Exception("InvalidKeyException", e);

    }

    try {

      byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));

      return new String(buf, "utf-8");

    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
      throw new Exception("IllegalBlockSizeException", e);
    } catch (BadPaddingException e) {
      e.printStackTrace();
      throw new Exception("BadPaddingException", e);
    }
  }

  /**
   * 获得秘密密钥
   * 
   * @param secretKey
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   * @throws InvalidKeyException
   */
  private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
    DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
    keyFactory.generateSecret(keySpec);
    return keyFactory.generateSecret(keySpec);
  }

  static private class Base64Utils {

    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    static private byte[] codes = new byte[256];

    static {
      for (int i = 0; i < 256; i++)
        codes[i] = -1;
      for (int i = 'A'; i <= 'Z'; i++)
        codes[i] = (byte) (i - 'A');
      for (int i = 'a'; i <= 'z'; i++)
        codes[i] = (byte) (26 + i - 'a');
      for (int i = '0'; i <= '9'; i++)
        codes[i] = (byte) (52 + i - '0');
      codes['+'] = 62;
      codes['/'] = 63;
    }

    /**
     * 将原始数据编码为base64编码
     */
    static private String encode(byte[] data) {
      char[] out = new char[((data.length + 2) / 3) * 4];
      for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
        boolean quad = false;
        boolean trip = false;
        int val = (0xFF & data[i]);
        val <<= 8;
        if ((i + 1) < data.length) {
          val |= (0xFF & data[i + 1]);
          trip = true;
        }
        val <<= 8;
        if ((i + 2) < data.length) {
          val |= (0xFF & data[i + 2]);
          quad = true;
        }
        out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
        val >>= 6;
        out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
        val >>= 6;
        out[index + 1] = alphabet[val & 0x3F];
        val >>= 6;
        out[index + 0] = alphabet[val & 0x3F];
      }

      return new String(out);
    }

    /**
     * 将base64编码的数据解码成原始数据
     */
    static private byte[] decode(char[] data) {
      int len = ((data.length + 3) / 4) * 3;
      if (data.length > 0 && data[data.length - 1] == '=')
        --len;
      if (data.length > 1 && data[data.length - 2] == '=')
        --len;
      byte[] out = new byte[len];
      int shift = 0;
      int accum = 0;
      int index = 0;
      for (int ix = 0; ix < data.length; ix++) {
        int value = codes[data[ix] & 0xFF];
        if (value >= 0) {
          accum <<= 6;
          shift += 6;
          accum |= value;
          if (shift >= 8) {
            shift -= 8;
            out[index++] = (byte) ((accum >> shift) & 0xff);
          }
        }
      }
      if (index != out.length)
        throw new Error("miscalculated data length!");
      return out;
    }
  }

  /**
   * Description 根据键值进行加密
   * @param data 
   * @param key  加密键byte数组
   * @return
   * @throws Exception
   */
  public static String encrypt(String data, String key) throws Exception {
    byte[] bt = encrypt(data.getBytes(), key.getBytes());
    String strs = new BASE64Encoder().encode(bt);
    return strs;
  }

  /**
   * Description 根据键值进行解密
   * @param data
   * @param key  加密键byte数组
   * @return
   * @throws IOException
   * @throws Exception
   */
  public static String decrypt(String data, String key) throws IOException, Exception {
    if (data == null)
      return null;
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] buf = decoder.decodeBuffer(data);
    byte[] bt = decrypt(buf, key.getBytes());
    return new String(bt, "utf-8");
  }

  /**
   * Description 根据键值进行加密
   * @param data
   * @param key  加密键byte数组
   * @return
   * @throws Exception
   */
  private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    // 生成一个可信任的随机数源
    SecureRandom sr = new SecureRandom();

    // 从原始密钥数据创建DESKeySpec对象
    DESKeySpec dks = new DESKeySpec(key);

    // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey securekey = keyFactory.generateSecret(dks);

    // Cipher对象实际完成加密操作
    Cipher cipher = Cipher.getInstance(DES);

    // 用密钥初始化Cipher对象
    cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

    return cipher.doFinal(data);
  }

  /**
   * Description 根据键值进行解密
   * @param data
   * @param key  加密键byte数组
   * @return
   * @throws Exception
   */
  private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
    // 生成一个可信任的随机数源
    SecureRandom sr = new SecureRandom();

    // 从原始密钥数据创建DESKeySpec对象
    DESKeySpec dks = new DESKeySpec(key);

    // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey securekey = keyFactory.generateSecret(dks);

    // Cipher对象实际完成解密操作
    Cipher cipher = Cipher.getInstance(DES);

    // 用密钥初始化Cipher对象
    cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

    return cipher.doFinal(data);
  }

}
