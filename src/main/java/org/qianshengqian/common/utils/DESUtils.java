package org.qianshengqian.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;


/**
 * Created by chenguoqiang on 2017/6/7.
 */
public class DESUtils {


        public final static String DES_KEY_STRING = "ABSujsuu";
        private static final String DEFAULT_URL_ENCODING = "UTF-8";

        public static String encrypt(String message, String key) throws Exception {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
        }

        public static String decrypt(String message, String key) throws Exception {

            byte[] bytesrc = decodeBase64(message);//convertHexString(message);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        }

        public static byte[] convertHexString(String ss) {
            byte digest[] = new byte[ss.length() / 2];
            for (int i = 0; i < digest.length; i++) {
                String byteString = ss.substring(2 * i, 2 * i + 2);
                int byteValue = Integer.parseInt(byteString, 16);
                digest[i] = (byte) byteValue;
            }

            return digest;
        }

        public static String toHexString(byte b[]) {
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                String plainText = Integer.toHexString(0xff & b[i]);
                if (plainText.length() < 2)
                    plainText = "0" + plainText;
                hexString.append(plainText);
            }

            return hexString.toString();
        }


       /* public static String encodeBase64(byte[] b) {
            return Base64.encodeToString(b, Base64.DEFAULT);
        }

        public static byte[] decodeBase64(String base64String) {
            return Base64.decode(base64String, Base64.DEFAULT);
        }*/
    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * Base64解码.
     */
    public static String decodeBase64String(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void main(String args[]) throws Exception{
        String key="37d5aed0";
        String message="{'requestNum':'201800001','assetBatchNum':'OUT20170615163300039681','token':'b44aca8005384f56b80345cdf3f3cb7a'}";
        System.out.println(encrypt(message, key));
        System.out.println(decrypt("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",key));

    }
}
