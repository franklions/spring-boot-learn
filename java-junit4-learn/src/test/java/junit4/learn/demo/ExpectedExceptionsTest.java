package junit4.learn.demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/8/10
 * @since Jdk 1.8
 */
public class ExpectedExceptionsTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowExpectedException(){
        new ArrayList<Object>().get(0);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowParentException(){
        new ArrayList<Object>().get(0);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldTestExceptionMessage(){
        List<Object> list = new ArrayList<Object>();

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Index: 0, Size: 0");
        list.get(0); // execution will never get past this line
    }
}
