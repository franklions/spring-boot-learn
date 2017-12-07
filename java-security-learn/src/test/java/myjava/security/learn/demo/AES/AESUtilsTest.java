package myjava.security.learn.demo.AES;

import myjava.security.learn.demo.RSA.Base64Utils;
import org.junit.Test;

import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

import static org.junit.Assert.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 *
 * @date 2017/12/5
 * @since Jdk 1.8
 */
public class AESUtilsTest {
    @Test
    public void AES_ECB_Encrypt() throws Exception {
        String content = "hello word 中国!";
        String key="1234567890123456";
        try {
            byte[] cipherByte = AESUtils.AES_ECB_Encrypt(content.getBytes("utf-8"),
                    key.getBytes("utf-8"));
            System.out.println(Base64Utils.encode(cipherByte));
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void AES_ECB_Decrypt() throws Exception {
        String content = "wNSIt+6Oru7D6oakPhlxIUg8r32Ll0652dD9KheedQY=";
        String key="1234567890123456";
        try {
            byte[] sourceByte = AESUtils.AES_ECB_Decrypt(Base64Utils.decode(content),
                    key.getBytes("utf-8"));
            System.out.println(new String(sourceByte));
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }catch (IllegalBlockSizeException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void AES_CBC_Encrypt() throws Exception {
        String content = "hello word 中国!";
        String key="1234567890123456";
        String vi = "1234567890123456";
        try {
            byte[] cipherByte = AESUtils.AES_CBC_Encrypt(content.getBytes("utf-8"),
                    key.getBytes("utf-8"),vi.getBytes("utf-8"));
            System.out.println(Base64Utils.encode(cipherByte));
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void AES_CBC_Decrypt() throws Exception {
        String content = "DkCSI4n/TYCHdLv5pLbat+0KcANOc3Pcl+Y4eBnZLj8=";
        String key="1234567890123456";
        String vi = "1234567890123456";
        try {
            byte[] sourceByte = AESUtils.AES_CBC_Decrypt(Base64Utils.decode(content),
                    key.getBytes("utf-8"),vi.getBytes("utf-8"));
            System.out.println(new String(sourceByte));
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }catch (IllegalBlockSizeException ex){
            System.out.println(ex.getMessage());
        }
    }

}