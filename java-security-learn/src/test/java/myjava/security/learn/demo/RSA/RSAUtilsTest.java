package myjava.security.learn.demo.RSA;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/11/29
 * @since Jdk 1.8
 */
public class RSAUtilsTest {
    @org.junit.Test
    public void genKeyPair() throws Exception {
        Map keyMap =  RSAUtils.genKeyPair();
        System.out.println("-----BEGIN RSA PRIVATE KEY-----");
        System.out.println(RSAUtils.getPrivateKey(keyMap));
        System.out.println("-----END RSA PRIVATE KEY-----");
    }

    @org.junit.Test
    public void sign() throws Exception {
        String content = "8f8c733bf4ab4a039d85d7d1d470759b";
        String privateKey =
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ44b4F3I+oB5tvecLo/Z6D6y+k5" +
                "B4YKFEqUoug76gQqp4HiKSjIP+zNV68dMYi9hGN46DsZ3FfwaPEUH8LtggkXIByiJHO6Z5i0hw4D" +
                "S8roihpxnrsOMyi2ZcrtLOjbB/sR9havAjap1gcrdwvGqjIww6HZmNyVhs4dgZtNU6xJAgMBAAEC" +
                "gYAu4RXYzE4t1aZrWvsXlBXGUsj8L1OVWYMtneCu4qGx3lmHWFVNqheWuDjUv9efZlfFiVt1t3Hb" +
                "9HWDiKzoeqreQyzKRqrly18qQ+9ZIApNF4e2at/m6qzqLZzbmcCbgmH3YWRYitmJiCQiVcdhftJp" +
                "wTP2nRaEcSIUZ/CXib1IaQJBANWN5bdgIxXIhnI0mAzg3sbWM58MH3fmu8dSwCLBMRYs827Tqx/+" +
                "QCybobpgPRpBzp3vP7e36uLqCkVwuYU02FMCQQC9qwiLTUK3stoAzfOBWr6LekoxcB9B+ml5Cm4v" +
                "9V1lLPTESCBRKCI8mzXt1iNPTltaHgeSvfzADYSCcC4jE6VzAkAed54caWTYCMHVrVc8DyD5cTnw" +
                "KQScUJjduRqcWr+sEEGywBQb/mQdeR67vls5UcYuliGbDmw+b3GDsq9lJ8LtAkEAhSWnFtuDBaBZ" +
                "qAzsb5d7gkC0CuayGsgm+ODYn3Q5P0iC/7O+dBqfhjpTboZQPWE9ghLosBwNbelPZ55G1Tr6fwJB" +
                "AK2MnHbgWadpDaDUZfV/kAyhwJB9eWeKseY6rlfBtklnWoQjINT/Jn8lzhTSZJ/JmaRCelZCzb3f" +
                "717Bsug+1WU=";
        String mi= RSAUtils.signToHex(content.getBytes(),privateKey,"MD5");
        System.out.println(mi);
        String mi2Hex="29db568349491ed4854ef45277c62f634016b4f2fc7be2be717048378e87e0e303f8e3dfa3" +
                "76c2b37295a6a8dbd33bf28c53c32d6284fd0a32b6d6131671c30806b905c691f2c60892cd9b2f7967" +
                "cb885481ff4e724e8cf19da9324836e08913c12d2537110f2b6e44245102a0d62f4e9a1345a380d7b6b397b7d07b111c7f2a";
        assertEquals(mi,mi2Hex);
    }

    @org.junit.Test
    public void verify() throws Exception {


    }

    @org.junit.Test
    public void decryptByPrivateKey() throws Exception {
    }

    @org.junit.Test
    public void decryptBypublicKey() throws Exception {
    }

    @org.junit.Test
    public void encryptBypublicKey() throws Exception {
    }

    @org.junit.Test
    public void encryptByPrivateKey() throws Exception {
    }

    @org.junit.Test
    public void getPrivateKey() throws Exception {
    }

    @org.junit.Test
    public void getPublicKey() throws Exception {
    }

}