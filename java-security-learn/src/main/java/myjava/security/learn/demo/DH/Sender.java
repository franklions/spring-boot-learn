package myjava.security.learn.demo.DH;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/11/7
 * @since Jdk 1.8
 */
public class Sender {

    KeyPair senderKeyPair;
    SecretKey senderDesKey;

    public Sender() throws NoSuchAlgorithmException {
        KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
        senderKeyPairGenerator.initialize(1024);
        senderKeyPair = senderKeyPairGenerator.generateKeyPair();

    }

    public byte[] getPublicKey(){
        byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();//发送方公钥，发送给接收方（网络、文件。。。）
        return senderPublicKeyEnc;
    }

    public SecretKey getDesKey(byte[] receiverPublicKeyEnc) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
        PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
        KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
        senderKeyAgreement.init(senderKeyPair.getPrivate());
        senderKeyAgreement.doPhase(senderPublicKey, true);
         senderDesKey = senderKeyAgreement.generateSecret("DES");
         return senderDesKey;
    }

    public byte[]  encrypt(byte[] source) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
        byte[] result = cipher.doFinal(source);
        return result;
    }

    public byte[] decrypt(byte[] ciphertext ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, senderDesKey);
        byte[]  result = cipher.doFinal(ciphertext);
        return result;
    }
}
