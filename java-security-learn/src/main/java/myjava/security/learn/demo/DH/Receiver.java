package myjava.security.learn.demo.DH;

import javax.crypto.*;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
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
public class Receiver {

    KeyPair receiverKeypair;
    SecretKey receiverDesKey;


    /**
     * 接收方以发送方的公钥构造密钥
     * @throws NoSuchAlgorithmException
     */
    public Receiver(byte[] sendPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        //解析甲方的公钥
        //转换公钥的材料
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(sendPublicKey);
        KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
        PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509KeySpec);

        KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
        //由甲方的公钥构造乙方密钥
        DHParameterSpec dhParamSpec=((DHPublicKey)senderPublicKey).getParams();
        receiverKeyPairGenerator.initialize(dhParamSpec);
        //产生密钥对f
         receiverKeypair = receiverKeyPairGenerator.generateKeyPair();

    }

    public byte[] getPublicKey(){
        byte[] receiverPublicKeyEnc = receiverKeypair.getPublic().getEncoded();
        return receiverPublicKeyEnc;
    }

    public SecretKey getDesKey(byte[] senderPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKey);
        PublicKey receiverPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
        KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
        receiverKeyAgreement.init(receiverKeypair.getPrivate());
        receiverKeyAgreement.doPhase(receiverPublicKey, true);
        receiverDesKey = receiverKeyAgreement.generateSecret("DES");
        return receiverDesKey;
    }

    public byte[]  encrypt(byte[] source) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, receiverDesKey);
        byte[] result = cipher.doFinal(source);
        return result;
    }

    public byte[] decrypt(byte[] ciphertext ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
        byte[]  result = cipher.doFinal(ciphertext);
        return result;
    }

}
