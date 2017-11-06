package spring.boot.learn.aliyun.log.demo.AliyunService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/11/6
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
public class AliyunClientServiceTest {

    AliyunClientService service ;

    @Before
    public void init(){
        service = new AliyunClientService();
    }

    @Test
    public void getLogstore() throws Exception {
        service.getLogstore("cztest","czstore");
    }

    @Test
    public void getListLogstores() throws Exception {
        service.getListLogstores("acslog-project-ceedc8b496-xajwd",
                0,100,"");
    }

    @Test
    public void getListShards() throws Exception {
        service.getListShards("acslog-project-ceedc8b496-xajwd",
                "acslog-device-service-dev-lw");
    }

    @Test
    public void getCursor() throws Exception {
    }

    @Test
    public void batchGetLogs() throws Exception {
    }

    @Test
    public void getLogs() throws Exception {
    }

    @Test
    public void getHistograms() throws Exception {
    }

}