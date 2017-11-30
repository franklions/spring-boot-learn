package java.security.learn.demo.AES;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

/**
 * @author flsh
 * @version 1.0
 * @description
 * Cipher.getInstance("AES")    和    Cipher.getInstance("AES/CBC/PKCS5Padding")   一样，即默认。
 *
 *  AES/CBC/NoPadding (128)
 *  AES/CBC/PKCS5Padding (128)
 *  AES/ECB/NoPadding (128)
 *   AES/ECB/PKCS5Padding (128)
 *   DES/CBC/NoPadding (56)
 *   DES/CBC/PKCS5Padding (56)
 *   DES/ECB/NoPadding (56)
 *   DES/ECB/PKCS5Padding (56)
 *   DESede/CBC/NoPadding (168)
 *   DESede/CBC/PKCS5Padding (168)
 *   DESede/ECB/NoPadding (168)
 *   DESede/ECB/PKCS5Padding (168)
 *   RSA/ECB/PKCS1Padding (1024, 2048)
 *   RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
 *   RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
 *
 * KeyGenerator.getInstance(?)
 * AES (128)
 * DES (56)
 * DESede (168)
 * HmacSHA1
 * HmacSHA256

 * @date 2017/11/29
 * @since Jdk 1.8
 */
public class AESUtils {

    /**
     * 密钥生成器算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 密钥长度
     */
    private static final Integer DEFAULT_KEY_SIZE = 128;


    public static byte[] AES_ECB_Encrypt(byte[] content, byte[] keyBytes){
        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] AES_ECB_Decrypt(byte[] content, byte[] keyBytes){
        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }
}
