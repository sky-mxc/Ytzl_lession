package com.skymxc.lesson_47_encrypt_decrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sky-mxc
 * 摘要
 */
public class Digest {
    private static final String TAG = "Digest";

    public static String SHA1(String content){
    return digest("SHA1",content.getBytes());
    }

    public static String MD5(String content){
        return digest("MD5",content.getBytes());
    }

    /**
     * 读取文件的 MD5 摘要
     * @param file 文件
     * @return
     */
    public static String MD5(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteBuffer buffer=  fis.getChannel()
                     .map(FileChannel.MapMode.READ_ONLY,0,fis.available());
            return digest("MD5",buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 读取 字节数组 摘要
     * @param algorithm 摘要模式
     * @param content 内容
     * @return
     */
    public static String digest(String algorithm,byte[] content){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(content);
            byte[] b = md.digest();
            return new BigInteger(1,b).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 读取 文件字节 摘要
     * @param algorithm 摘要模式
     * @param buffer 内容
     * @return
     */
    public static String digest(String algorithm,ByteBuffer buffer){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(buffer);
            byte[] b = md.digest();
            return new BigInteger(1,b).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
