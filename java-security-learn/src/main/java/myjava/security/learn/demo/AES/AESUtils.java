package myjava.security.learn.demo.AES;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.KeySpec;

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

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static byte[] AES_ECB256_Encrypt(byte[] content, byte[] keyBytes){
        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(256, new SecureRandom(keyBytes));
            SecretKey key=keyGenerator.generateKey();
//            SecretKey key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
            byte[]  keys =  key.getEncoded();
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }catch (IllegalBlockSizeException ex){
            System.out.println(ex.getMessage());
        }catch(BadPaddingException ex){
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] AES_ECB256_Decrypt(byte[] content, byte[] keyBytes){
        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(256, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128
            SecretKey key=keyGenerator.generateKey();
//            SecretKey key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }catch (IllegalBlockSizeException ex){
            System.out.println(ex.getMessage());
        }catch(BadPaddingException ex){
            System.out.println(ex.getMessage());
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    //TODO 测试没有成功
    public static byte[] AES_ECB256_Encrypt(byte[] content, char[] keyBytes){
        try{
            String salt = "ldcs2022";
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(keyBytes, salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey key = new SecretKeySpec(tmp.getEncoded(), "AES");
//            SecretKey key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
            byte[]  keys =  key.getEncoded();
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }catch (IllegalBlockSizeException ex){
            System.out.println(ex.getMessage());
        }catch(BadPaddingException ex){
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }


    public static byte[] AES_ECB_Encrypt(byte[] content, byte[] keyBytes){
        try{
//            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
//            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));
//            SecretKey key=keyGenerator.generateKey();
            SecretKey key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
            byte[]  keys =  key.getEncoded();
            System.out.println(new String(keys));
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }catch (IllegalBlockSizeException ex){
            System.out.println(ex.getMessage());
        }catch(BadPaddingException ex){
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] AES_ECB_Decrypt(byte[] content, byte[] keyBytes){
        try{
//            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
//            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128
//            SecretKey key=keyGenerator.generateKey();
            SecretKey key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (InvalidKeyException ex){
            System.out.println(ex.getMessage());
        }catch (IllegalBlockSizeException ex){
            System.out.println(ex.getMessage());
        }catch(BadPaddingException ex){
            System.out.println(ex.getMessage());
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try{
//            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
//            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));
//            SecretKey key=keyGenerator.generateKey();
            SecretKey key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
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
//            KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
//            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128
//            SecretKey key=keyGenerator.generateKey();
            SecretKey key = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
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
