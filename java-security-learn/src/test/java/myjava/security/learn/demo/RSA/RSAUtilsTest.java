package myjava.security.learn.demo.RSA;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.junit.Test;

import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
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
        System.out.println(RSAUtils.getPrivateKeyHex(keyMap));
        System.out.println("-----END RSA PRIVATE KEY-----");
    }

    @Test
    public void signAndVerify() throws Exception {
        Map keyMap =  RSAUtils.genKeyPair();
        System.out.println(RSAUtils.showPrivate(RSAUtils.getPrivateKey(keyMap)));

        System.out.println(RSAUtils.showPrivate(RSAUtils.getPublicKey(keyMap)));

        String content = "8f8c733bf4ab4a039d85d7d1d470759b";

        System.out.println("---------------------------content-----------------");
        System.out.println(content);

        String base64Sign = RSAUtils.sign(content.getBytes("utf-8"),
                RSAUtils.getPrivateKey(keyMap),"MD5");
        System.out.println("-----------------------------sign base64-----------------------");
        System.out.println(base64Sign);

        System.out.println("-------------------------------------------");
        System.out.println(RSAUtils.verify(content.getBytes(),
                RSAUtils.getPublicKey(keyMap),base64Sign,"MD5"));

    }

    @org.junit.Test
    public void sign() throws Exception {
        String content = "8f8c733bf4ab4a039d85d7d1d470759b";
//        String privateKey =
//                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ9oWHUogZ8ue6/Z\n" +
//                        "1UuSgXAjtWI6PkXWpvmXSxuHtqrZ4Bk++yPJzHeEKs0QHYO9ZpXouanKT5ecxumT\n" +
//                        "wQ2Ok/su0ijAJXLq+T7qzaGN4YjbPAAoCR04iy9q2G0NEmovEtwD9zzBx82rbQz1\n" +
//                        "615h5K/MPo/ZZJ67WPyKvJBOxQxZAgMBAAECgYEAh+JxFV30MPd05FByrDs1L5yg\n" +
//                        "8BzqHKqAJ986XTtxEW8swxiXCMjrDS6TNb75CeYYXs3GBuC4vGsefNpBBQd3SJyv\n" +
//                        "4+JTvt5Tg8jY5mHEzoTnUjCPSbA0aaiLmo2xDtw9fpJ0UihSj6QG1kRXEBPk1Acz\n" +
//                        "XEDFozgxvOOtp0fBK8kCQQDUAaEYAP1fNKFpaA4bv6NxQzKB7jfqqMeBqfb66mMW\n" +
//                        "0cEZEjq5kVFfXo8hUkY+yHv0WTJjvAS6df1bAPhAxwfvAkEAwHyF2bBfW5GTd+Yf\n" +
//                        "8rfUq9SuDJjmt8rOpMwXLDthl1A0endNeUHXQ5q/glQsVPsU0gYtcdZe3SYYr4F5\n" +
//                        "xIsoNwJAJbCgdgb/BGTz6SxRjcdVBDbBDO5PzIzy4u9XdCYlNZJWtOFTxVe7UqwZ\n" +
//                        "3/+jc1frCXauCaMMaCW/w+T5PXLkywJARTWAuwDbiIUTTO0FV7QZV58k5Yqkijj1\n" +
//                        "i/OF8XV4ZXuFIvN6e7rct4YQjb/OINAWcF2gQWoeuc1U2uXm7nBKiQJAR44RxrrC\n" +
//                        "7Uw4WYNHvjCacaW/BeZjoO2ZMBW38nZBKTqNs3YLzuxVLdL3Dph+Z5x0qlbUMw6C\n" +
//                        "2SKlPisT60BVhA==";
        String privateKey = "MIICWwIBAAKBgQCnRM8DbxarBZl9F+Yj8q32AWLkc95ffAY4kJBDC6jDFKiquAJw\n" +
                "5QbN1poVj5ont2VXrKDh397OI+AIxa/aB4lrQp5iYAVwsR+xnbG0crLRh8aeLz0W\n" +
                "U9V2EIDqzqGj08eTdY4gkD0C2sIc2afkcrog0N70kO15aIt/MD5ciyIRKQIDAQAB\n" +
                "AoGAAvEiNaHrONAnu5wt/2KHThUG05R4zB52+uEKsy3YprBi/w2uzU7Z6HORJjaZ\n" +
                "CYB07IPWwzeYHZwNQVo+FjbyYxngg30mpkZS1MT23S3lqtgVYoKSQpC+/Gt0f1hC\n" +
                "Lg9qI26BSjAsVNPMu91KAI5CzqGSn/pYmorosCl/NuEIKwECQQCzf6ahzcparjr3\n" +
                "b9UZM+uXA+Ea2znvltBIGUoduKYxBdT2oQdvzn/6x8sa/9NAwNb0zUChX8VC4vw9\n" +
                "dhz4kl85AkEA7o7PNrWhYjDQRc1QvpUXTPPpXCtq5n3CaLNn9prkHGuIKXthWjVu\n" +
                "23w+o70gYx+8FFmc6yFrtGnKkcqukI1RcQJAdETEV9yoLZLcCTj8TOXvvZM6tZ25\n" +
                "anNDGOCgvJ3FsM15RAPMgGS/S18tNaDuYuxGkK1/JsiVSx0ijGE8pNqTKQJAeyk5\n" +
                "q8or7pC7Bj6Q4EQCypTmttInl6X3t6xgET44ZL/ThW8QfM03VZOYl1puHR7mMC9v\n" +
                "xyjy/SrdDVXxt+lt0QJAW4XPpSJ0KdebrwTY9+K8sruXBvPpEXNlLod0/ItVkENs\n" +
                "lZbaUSEqaMtdP9WqLzqOomPX/Nx8wxfHUqMuffo0pw==";

        System.out.println(Base64Utils.hexEncode(Base64Utils.decode( privateKey.replace("\n",""))));
//        String mi= RSAUtils.sign(content.getBytes(),privateKey.replace("\n",""),"MD5");
//        System.out.println(mi);
//        String mi2Hex="9dfd7e90965470bca77dad2ac4f7b86835f2e710497c8af15d7edba96b9351ba63364192ed650b40a017e43f3c4636f1318de92f4a2b3bd7b0f1d4e561fa2f67709f51e50ad00c3942a76d44fdb05ac47407bf747d6df2dfdba4a0b713d6509ce6c4d47480812e9e7338c2dade9b98ea405d1ae14bf7fad233d2fd460f412f32";
//        assertEquals(mi,mi2Hex);
    }

    @Test
     public void testGenerateKeys() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
         KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA","BC");
         kpg.initialize(1024);
         KeyPair kp = kpg.genKeyPair();
         Key publicKey = kp.getPublic();
         Key privateKey = kp.getPrivate();

        PrivateKey priv = kp.getPrivate();
        byte[] privBytes = priv.getEncoded();

        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privBytes);
        ASN1Encodable encodable = pkInfo.parsePrivateKey();
        ASN1Primitive primitive = encodable.toASN1Primitive();
        byte[] privateKeyPKCS1 = primitive.getEncoded();

        PemObject pemObject = new PemObject("RSA PRIVATE KEY", privateKeyPKCS1);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        String pemString = stringWriter.toString();

        System.out.println("Key format: " + privateKey.getFormat());
        System.out.println("Key algorithm:" +privateKey.getAlgorithm());
        System.out.println("Public key Exponent(HEX): " + Base64Utils.hexEncode(publicKey.getEncoded()));
        System.out.println("Private key Exponent(HEX): " + Base64Utils.hexEncode(privateKeyPKCS1));
        System.out.println(pemString);

        PublicKey pub = kp.getPublic();
        byte[] pubBytes = pub.getEncoded();

        SubjectPublicKeyInfo spkInfo = SubjectPublicKeyInfo.getInstance(pubBytes);
        ASN1Primitive primitive2 = spkInfo.parsePublicKey();
        byte[] publicKeyPKCS1 = primitive2.getEncoded();

        PemObject pemObject2 = new PemObject("RSA PUBLIC KEY", publicKeyPKCS1);
        StringWriter stringWriter2 = new StringWriter();
        PemWriter pemWriter2 = new PemWriter(stringWriter2);
        pemWriter2.writeObject(pemObject2);
        pemWriter2.close();
        String pemString2 = stringWriter2.toString();
        System.out.println(pemString2);
//         KeyFactory fact = KeyFactory.getInstance("RSA");
//         RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(publicKey, RSAPublicKeySpec.class);
//         RSAPrivateKeySpec priv = (RSAPrivateKeySpec) fact.getKeySpec(privateKey, RSAPrivateKeySpec.class);
//
//         BigInteger modules = pub.getModulus();  // same as priv.getModulus()
//         BigInteger publicExponent = pub.getPublicExponent();
//         BigInteger privateExponent = priv.getPrivateExponent();
//         System.out.println("Key Modulus: " + modules);
//         System.out.println("Public key Exponent: " + publicExponent);
//         System.out.println("Private key Exponent: " + privateExponent);
//         System.out.println("----------");
//         System.out.println("Key Modulus(HEX): " + modules.toString(16));
//         System.out.println("Public key Exponent(HEX): " + publicExponent.toString(16));
//         System.out.println("Private key Exponent(HEX): " + privateExponent.toString(16));
     }



}