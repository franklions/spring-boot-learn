package myjava.security.learn.demo.TEA;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/12/1
 * @since Jdk 1.8
 */
@RunWith(JUnit4.class)
public class XXTEAUtilsTest {
    @Test
    public void encryptToBase64String() throws Exception {
        String str = "Hello World! 你好，中国！";
        String key = "1234567890";
        String encrypt_data = XXTEAUtils.encryptToBase64String(str, key);
        System.out.println(encrypt_data);
        assert("QncB1C0rHQoZ1eRiPM4dsZtRi9pNrp7sqvX76cFXvrrIHXL6".equals(encrypt_data));
        String decrypt_data = XXTEAUtils.decryptBase64StringToString(encrypt_data, key);
        System.out.println(decrypt_data);
        assert(str.equals(decrypt_data));
    }

    @Test
    public void decryptBase64StringToString() throws Exception {
    }

}