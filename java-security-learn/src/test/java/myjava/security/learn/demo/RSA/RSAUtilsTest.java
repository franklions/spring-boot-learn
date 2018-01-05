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
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ9oWHUogZ8ue6/Z\n" +
                        "1UuSgXAjtWI6PkXWpvmXSxuHtqrZ4Bk++yPJzHeEKs0QHYO9ZpXouanKT5ecxumT\n" +
                        "wQ2Ok/su0ijAJXLq+T7qzaGN4YjbPAAoCR04iy9q2G0NEmovEtwD9zzBx82rbQz1\n" +
                        "615h5K/MPo/ZZJ67WPyKvJBOxQxZAgMBAAECgYEAh+JxFV30MPd05FByrDs1L5yg\n" +
                        "8BzqHKqAJ986XTtxEW8swxiXCMjrDS6TNb75CeYYXs3GBuC4vGsefNpBBQd3SJyv\n" +
                        "4+JTvt5Tg8jY5mHEzoTnUjCPSbA0aaiLmo2xDtw9fpJ0UihSj6QG1kRXEBPk1Acz\n" +
                        "XEDFozgxvOOtp0fBK8kCQQDUAaEYAP1fNKFpaA4bv6NxQzKB7jfqqMeBqfb66mMW\n" +
                        "0cEZEjq5kVFfXo8hUkY+yHv0WTJjvAS6df1bAPhAxwfvAkEAwHyF2bBfW5GTd+Yf\n" +
                        "8rfUq9SuDJjmt8rOpMwXLDthl1A0endNeUHXQ5q/glQsVPsU0gYtcdZe3SYYr4F5\n" +
                        "xIsoNwJAJbCgdgb/BGTz6SxRjcdVBDbBDO5PzIzy4u9XdCYlNZJWtOFTxVe7UqwZ\n" +
                        "3/+jc1frCXauCaMMaCW/w+T5PXLkywJARTWAuwDbiIUTTO0FV7QZV58k5Yqkijj1\n" +
                        "i/OF8XV4ZXuFIvN6e7rct4YQjb/OINAWcF2gQWoeuc1U2uXm7nBKiQJAR44RxrrC\n" +
                        "7Uw4WYNHvjCacaW/BeZjoO2ZMBW38nZBKTqNs3YLzuxVLdL3Dph+Z5x0qlbUMw6C\n" +
                        "2SKlPisT60BVhA==";
        String mi= RSAUtils.signToHex(content.getBytes(),privateKey.replace("\n",""),"MD5");
        System.out.println(mi);
        String mi2Hex="9dfd7e90965470bca77dad2ac4f7b86835f2e710497c8af15d7edba96b9351ba63364192ed650b40a017e43f3c4636f1318de92f4a2b3bd7b0f1d4e561fa2f67709f51e50ad00c3942a76d44fdb05ac47407bf747d6df2dfdba4a0b713d6509ce6c4d47480812e9e7338c2dade9b98ea405d1ae14bf7fad233d2fd460f412f32";
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