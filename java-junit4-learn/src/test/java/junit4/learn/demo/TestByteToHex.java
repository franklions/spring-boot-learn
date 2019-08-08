package junit4.learn.demo;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-08-08
 * @since Jdk 1.8
 */
@RunWith(JUnit4.class)
public class TestByteToHex {
    /**
     * 返回256位的字符串，不够的最后补零
     *
     * @param mqttSubProtocolJsonVal
     * @return
     */
    public String padding256Msg(String mqttSubProtocolJsonVal)   {
        if (mqttSubProtocolJsonVal.length() < 1) {
            return "";
        }
        int over = mqttSubProtocolJsonVal.length() % 32;
        if (over == 0) {
            return mqttSubProtocolJsonVal;
        }

        String paddingStr = "\0000000000000000000000000000000000";
        mqttSubProtocolJsonVal = mqttSubProtocolJsonVal + paddingStr.substring(0, 32 - over);
        return mqttSubProtocolJsonVal;
    }

    private String byteToHexString(byte[] dataArr) {

        if(dataArr ==  null || dataArr.length <1){
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : dataArr) {
            sb.append(String.format("%02X ", b).trim());
        }

        return sb.toString();
    }

    /**
     * convert HexString to byte[]
     *
     * @param hex
     * @return
     */
    public  byte[] hexStringToByteArray(String hex)
    {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for (int i = 0; i < len; i++)
        {
            int pos = i * 2;
            result[i]=toByte(hex.substring(i*2,i*2+2));
        }
        return result;

    }

    public byte toByte(String hexbytein){
        return (byte)Integer.parseInt(hexbytein,16);
    }
}
