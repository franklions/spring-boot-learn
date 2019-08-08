package junit4.learn.demo;

import org.hamcrest.core.CombinableMatcher;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.Arrays;

import static org.junit.Assert.*;
import  static org.hamcrest.CoreMatchers.*;
/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/8/10
 * @since Jdk 1.8
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssertTests {

    @Test
    public void testAssertArrayEquals(){
        byte[] expected = "trial".getBytes();
        byte[] actual = "trial".getBytes();
        assertArrayEquals("failure - byte arrays not same",expected,actual);
    }

    @Test
    public void testAssertEquals(){
        assertEquals("failure - strings are not equal","text","text");
    }

    @Test
    public void testAssertFalse(){
        assertFalse("failure - should be false",false);
    }

    @Test
    public void testAssertTrue(){
        assertTrue("failure - should be true",true);
    }

    @Test
    public void testAssertNotNull(){
        assertNotNull("should not be null",new Object());
    }

    @Test
    public void testAssertNotSame(){
        assertNotSame("should not be same object",new Object(),new Object());
    }

    @Test
    public void testAssertNull(){
        assertNull("should be null",null);
    }

    @Test
    public void testAssertSame(){
        Integer aNumber = Integer.valueOf(783);
        assertSame("should be same",aNumber,aNumber);
    }

    @Test
    public void testAssertThatBothContainsString(){
        assertThat("albumen",both(containsString("a")).and(containsString("b")));
    }

    @Test
    public void testAssertThatHasItems(){
        assertThat(Arrays.asList("one","tow","three"),hasItems("one","three"));
    }

    @Test
    public void testAssertThatEveryItemContainsString(){
        assertThat(Arrays.asList(new String[]{"fun","ban","net"}),everyItem(containsString("n")));
    }

    // Core Hamcrest Matchers with assertThat

    @Ignore("Test is ignored as a demonstration")
    @Test
    public void testAssertThatHamcrestCoreMatchers() {
        assertThat("good", allOf(equalTo("good"), startsWith("good")));
        assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
        assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
        assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
        assertThat(new Object(), not(sameInstance(new Object())));
    }


    @Test
    public void testSubString(){
        String str = "000000000000000090FD9FFFFE2E9F93";
        if(str.startsWith("0000000000000000")){
            str = str.substring(16).toUpperCase();
        }else{
            str.toLowerCase();
        }
        System.out.println(str);
    }
}
