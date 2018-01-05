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

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("-----BEGIN RSA PRIVATE KEY-----");
        stringBuffer.append("\n");
        String privateKey =  "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALOZq+kS/zRc4BTqWHfWAnzTyc8G\n" +
                "Y4haWDixj1czoo2OGUoF9NZci2QtkRr6bGyjIodIkT8o1nHhB62uBMNnCmjcaE2fAEmwbhWfquW5\n" +
                "xRwaH+4q/oUs48NjHMG5WtFDmKIk5m31dpZpw+9soxRsXwYp3MfemlwNGTS1VNnv1GPRAgMBAAEC\n" +
                "gYEAov2POhJ3cLno0pJqfzbFQyuhNjSCF7iF382kXGsL1pNi9/631+JczilcSrOBuoXCTCvbRQp2\n" +
                "sxqZsVEAGaVll4RQL7u9W9/5sZuQgKWli8IIJWEwSPb7uShdpgq3nBrHkJOswrp5+FwVlfqtRm4a\n" +
                "To6n4t70tlbULl6AHeOWYsECQQDzBLXJ2kG7Cu1iKCAq2emkaW2p5GD1Vjmkn4dXHaLXWayaOJXi\n" +
                "qjJ+ZFXtIWxkkuWOhOp3YVohM77i2/LCn0ZVAkEAvTG3FPM+FUii2sMX94mE0/EuHXXC4AS/h5s+\n" +
                "g6ZvIaD2FCa7Tpt7U2Pt7eNXTW/klYfTLmiFW7XH+GQEYMELjQJAbJ8lViTNDnpy4lXHEwA103wg\n" +
                "PTjMMbyppTB9AO5JM/JsGL7EF/009pOIaKZw2X9jRdGZL2T/MSn830+qcV1ZxQJBALeVCytYEJEE\n" +
                "h4oxLwtytKIX8ICDqZJI6TUDzjhfAo0mLEJHhw9yzcuQNurT4/3vKmZygtgQcf8YuC7tllXwXG0C\n" +
                "QQCSrER4MrSruJBgb+MmOvLJGq8jsTG5zVuT5kKUT6oDImui98aR5w1/O8CWiova1slOmOEjfwv0\n" +
                "O1HSLRsvbvGF";
        privateKey =privateKey.replace("\r","").replace("\n","");
        for(int index=0;index <privateKey.length();index +=64){
            if(index+64 <= privateKey.length()) {
                stringBuffer.append(privateKey.substring(index, index + 64));
            }else{
                stringBuffer.append(privateKey.substring(index,privateKey.length()));
            }
            stringBuffer.append("\n");
        }
        stringBuffer.append("-----END RSA PRIVATE KEY-----");
        System.out.println(stringBuffer.toString());
    }
}
