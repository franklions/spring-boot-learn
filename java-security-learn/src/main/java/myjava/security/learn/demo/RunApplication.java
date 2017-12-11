package myjava.security.learn.demo;

import myjava.security.learn.demo.RSA.RSAUtils;

import java.util.Map;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/11/29
 * @since Jdk 1.8
 */
public class RunApplication {
    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap =  RSAUtils.genKeyPair();
        System.out.println("-----BEGIN RSA PRIVATE KEY-----");
        System.out.println(RSAUtils.getPrivateKey(keyMap));
        System.out.println("-----END RSA PRIVATE KEY-----");
    }
}
