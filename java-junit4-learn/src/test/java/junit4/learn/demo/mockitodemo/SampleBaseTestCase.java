package junit4.learn.demo.mockitodemo;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/8/10
 * @since Jdk 1.8
 */
public class SampleBaseTestCase {
    @Before
    public void  initMocks(){
        MockitoAnnotations.initMocks(this);
    }
}
