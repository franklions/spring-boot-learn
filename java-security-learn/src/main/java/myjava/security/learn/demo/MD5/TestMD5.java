package myjava.security.learn.demo.MD5;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/2/25
 * @since Jdk 1.8
 */
public class TestMD5 {
    public static void main(String[] args) throws IOException {
        String path = "/Users/andon/Downloads/app-urlProduct-release.apk";
        String md5 =DigestUtils.md5Hex(new FileInputStream(path));
        System.out.println(md5);
    }

}
