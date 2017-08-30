package org.qianshengqian.common.utils;

/**
 * Created by chenguoqiang on 2017/5/16.
 */

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class QEncodeUtil {
    private static final String DES_ALGORITHM = "DES";

    /**
     * DES加密
     * @param plainData
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String encryption(String plainData, String secretKey) throws Exception{

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }catch(InvalidKeyException e){

        }

        try {
            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，
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
     * @param secretData
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String decryption(String secretData, String secretKey) throws Exception{

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
        }catch(InvalidKeyException e){
            e.printStackTrace();
            throw new Exception("InvalidKeyException", e);

        }

        try {

            byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));

            return new String(buf);

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
     */

    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException,InvalidKeyException,InvalidKeySpecException{

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
        keyFactory.generateSecret(keySpec);
        return keyFactory.generateSecret(keySpec);
    }


    static class Base64Utils {

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
        static public String encode(byte[] data) {
            char[] out = new char[((data.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
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
        static public byte[] decode(char[] data) {
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
    public static void main(String[] a) throws Exception{
        String input = "{'requestNum':'20170531','assetBatchNum':'','token':'dd09b2565b8b4ca7ab04ed6e9a00ad96'}";
        String key = "37d5aed075525d4fa0fe635231cba447";

        /*KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);// 设置密钥的长度为56位
        // 生成一个Key
        SecretKey generateKey = keyGenerator.generateKey();
        // 转变为字节数组
        byte[] encoded = generateKey.getEncoded();
        // 生成密钥字符串
        String key = Hex.encodeHexString(encoded);
        System.out.println("Key ： " + key);*/
        String result = encryption(input, key);
        System.out.println(result);

        String result1="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";
        System.out.println(decryption(result1, key));

    }
}

