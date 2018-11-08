package myjava.security.learn.demo.DH;

import javax.crypto.*;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/11/7
 * @since Jdk 1.8
 */
public class DHUtils {
    private static String src = "cakin24 security dh";

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender.getPublicKey());
        SecretKey senderDesKey =  sender.getDesKey(receiver.getPublicKey());
        SecretKey receiverDesKey = receiver.getDesKey(sender.getPublicKey());

        System.out.println(senderDesKey.toString());

        if (receiverDesKey.equals(senderDesKey)) {
            System.out.println("双方密钥相同");
        }

        byte[] result = sender.encrypt(src.getBytes());
        System.out.println("jdk dh encrypt : " + Base64.encodeBase64String(result));
        System.out.println("jdk dh decrypt : " + new String(receiver.decrypt(result)));
    }

}
