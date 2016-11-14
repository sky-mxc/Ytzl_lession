package com.skymxc.lesson_47_encrypt_decrypt;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sky-mxc
 * 对称加密
 */
public class SymmetricEncryption {
    private static final String TAG = "SymmetricEncryption";

    public static  final  String TYPE_AES = "AES";
    public static  final  String TYPE_DES = "DES";
    public static  final  String TYPE_3DES = "DESede";

    public static  String decrypt(String type ,String password,String encontent){
        try {
            //将密码 转换为 byte[]
            byte[] keyRaw = getRawKey(type,password);
            //将加密内容转换为 二进制 字节数组
            byte[] content = toByteArray(encontent);
            SecretKeySpec sks = new SecretKeySpec(keyRaw,type);
            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.DECRYPT_MODE,sks,new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] decontent= cipher.doFinal(content);
            return new String(decontent);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将加密内容 转换为 二进制 byte[]
     * @param encontent 加密的内容
     * @return  二进制 byte[]形式的加密内容
     */
    private static byte[] toByteArray(String encontent) {
        int size = encontent.length()/2;
        byte[] content = new byte[size];
        for (int i =0;i<size;i++){
            String str = encontent.substring(i*2,i*2+2);
            content[i] = (byte) Integer.parseInt(str,16);

        }
        return content;
    }


    /**
     * 加密
     * @param type  加密类型
     * @param password 密码
     * @param content 需要加密内容
     * @return
     */
    public static String encrypt(String type,String password,String content){


        try {
            byte[] keyRaw = getRawKey(type,password);
            //加密key空间
            SecretKeySpec sks = new SecretKeySpec(keyRaw,type);
            //获取加密操作对象
            Cipher cipher = Cipher.getInstance(type);
            /**
             * 初始化
             * params1 操作模式 ：加密还是解密
             * params2 : 加密key的空间
             */
            cipher.init(Cipher.ENCRYPT_MODE,sks,new IvParameterSpec(new byte[cipher.getBlockSize()]));

            //加密
            byte[] en = cipher.doFinal(content.getBytes());

            //转为字符串 为了不乱吗 使用 16进制转换

            //生成一个大整数；参数1：产生一个正数；参数2 字节数组，将会变成数字
            BigInteger bi  = new BigInteger(1,en);
            return bi.toString(16);//  十六进制字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串密码转换为 byte[] 密码
     * @param type 加密类型
     * @param password 密码
     * @return 字节密码
     */
    public  static byte[] getRawKey(String type,String password) throws NoSuchAlgorithmException, NoSuchProviderException {
        //密钥生成器 按照类型创建
        KeyGenerator kg = KeyGenerator.getInstance(type);
        //随机蜜蜜
        SecureRandom sr =SecureRandom.getInstance("SHA1PRNG","Crypto");
        //设置要作为密钥的字符串
        sr.setSeed(password.getBytes());
        kg.init(sr);
        //利用密钥产生器 产生密钥 并获取 编码后的字节数组
        return kg.generateKey().getEncoded();
    }
}
