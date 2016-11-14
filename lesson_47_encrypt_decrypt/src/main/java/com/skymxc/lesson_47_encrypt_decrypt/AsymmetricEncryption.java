package com.skymxc.lesson_47_encrypt_decrypt;

import android.util.Base64;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by sky-mxc
 * 不对称 加密和解密
 */
public class AsymmetricEncryption {
    private static final String TAG = "AsymmetricEncryption";

    /**
     * 生成 公钥私钥
     * @return
     */
    public static   String[] generateKey(){
        try {
            //密钥对生成器实例 获取
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            //生成密钥对
            KeyPair kp = kpg.generateKeyPair();
            //获取 公钥和私钥
            PrivateKey privateKey = kp.getPrivate();
            PublicKey publicKey = kp.getPublic();
            String encodePrivateKey = Base64.encodeToString(privateKey.getEncoded(),Base64.DEFAULT);
            String encodePublicKey = Base64.encodeToString(publicKey.getEncoded(),Base64.DEFAULT);
            return new String[]{encodePrivateKey,encodePublicKey};
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加密
     * @param key 公钥
     * @param content 内容
     */
    public static String encrypt(String key,String content){
        try {
            //用Base64  解码
            byte[] rawKey = Base64.decode(key.getBytes(),Base64.DEFAULT);
            //x509协议
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rawKey);
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.PUBLIC_KEY,publicKey);
            byte[] c= cipher.doFinal(content.getBytes());
            return new BigInteger(1,c).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     * @param key 私钥
     * @param encontent 加密内容
     * @return 解密后的内容
     */
    public static  String decrypt(String key,String encontent){
        try {
            //解码
            byte[] rawKey = Base64.decode(key.getBytes(),Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(rawKey);
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.PRIVATE_KEY,privateKey);
            byte[] c =cipher.doFinal(toByteArray(encontent));
            return  new String(c);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将加密内容转换为 二进制字节数组
     * @param encontent
     * @return
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
}
