package junit4.learn.demo.mockitodemo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/8/10
 * @since Jdk 1.8
 */
public class MockitoTests {

    /**
     * 一旦mock对象被创建了，mock对象会记住所有的交互。
     * 然后你就可能选择性的验证你感兴趣的交互。
     */
    @Test
    public void testSomeVerifyMethods(){
        //mock creation 创建一个mock对象
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    /**
     * 默认情况下，所有的函数都有返回值。mock函数默认返回的是null，
     * 一个空的集合或者一个被对象类型包装的内置类型，例如0、false对应的
     *  对象类型为Integer、Boolean；
     */
    @Test
    public void testSomeStub(){

        //you can mock concrete classes,not ony interfaces
        //你可以mock具体的类型，不仅只是接口
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        //测试桩
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(IndexOutOfBoundsException.class);

        //following prints "first"
        // 输出“first”
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        // 抛出异常
        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        // 因为get(999) 没有打桩，因此输出null
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
        //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
        // 验证get(0)被调用的次数
        verify(mockedList).get(0);
    }

    /**
     * 如果你使用参数匹配器,所有参数都必须由匹配器提供。
     *  verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
     *  above is correct - eq() is also an argument matcher

     *  verify(mock).someMethod(anyInt(), anyString(), "third argument");
     *  above is incorrect - exception will be thrown because third argument is
     *  given without an argument matcher.
     */
    @Test
    public void testArgumentMatchers(){
        List mockedList = mock(List.class);

        //stubbing using built-in anyInt() argument matcher
        // 使用内置的anyInt()参数匹配器
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        // 使用自定义的参数匹配器( 在isValid()函数中返回你自己的匹配器实现 )
        when(mockedList.contains(argThat(m ->m.toString()==""))).thenReturn(true);

        //following prints "element"
        // 输出element
        System.out.println(mockedList.get(999));

        //you can also verify using an argument matcher
        // 你也可以验证参数匹配器
        verify(mockedList).get(anyInt());
    }

    @Test
    public void testMethodExactNumber(){
        List mockedList = mock(List.class);

        mockedList.add("once");
        mockedList.add("twice");
        mockedList.add("twice");
        mockedList.add("three");
        mockedList.add("three");
        mockedList.add("three");

        //following two verifications work exactly the same - times(1) is used by default
        // 下面的两个验证函数效果一样,因为verify默认验证的就是times(1)
        verify(mockedList).add("once");
        verify(mockedList,times(1)).add("once");

        //exact number of invocations verification
        //验证具体执行次数
        verify(mockedList,times(2)).add("twice");
        verify(mockedList,times(3)).add("three");

        //verification using never(). never() is an alias to times(0)
        // 使用never()进行验证,never相当于times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        // 使用atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("five times");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    public void testVoidMethodsWithExceptions(){

        List mockedList = mock(List.class);

        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        // 调用这句代码会抛出异常
        mockedList.clear();
    }

    /**
     * 验证执行顺序是非常灵活的-你不需要一个一个的验证所有交互,只需要验证你感兴趣的对象即可。
     * 另外，你可以仅通过那些需要验证顺序的mock对象来创建InOrder对象。
     */
    @Test
    public void testVerifyExectMethodInOrder(){
        // A. Single mock whose methods must be invoked in a particular order
        // A. 验证mock一个对象的函数执行顺序
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        // 为该mock对象创建一个inOrder对象
        InOrder inorder  = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        // 确保add函数首先执行的是add("was added first"),然后才是add("was added second")
        inorder.verify(singleMock).add("was added first");
        inorder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        // B .验证多个mock对象的函数执行顺序
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        // 为这两个Mock对象创建inOrder对象
        InOrder inOrder = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        // 验证它们的执行顺序
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }

    @Test
    public void testVerifyZeroInteractions(){
        List mockOne = mock(List.class);
        List mockTwo = mock(List.class);
        List mockThree = mock(List.class);

        //using mocks - only mockOne is interacted
        // 使用Mock对象
        mockOne.add("one");

        //ordinary verification
        // 普通验证
        verify(mockOne).add("one");

        //verify that method was never called on a mock
        // 验证某个交互是否从未被执行
        verify(mockOne, never()).add("two");

        //verify that other mocks were not interacted
        // 验证mock对象没有交互过
        verifyZeroInteractions(mockTwo, mockThree);
    }

    /**
     * 一些用户可能会在频繁地使用verifyNoMoreInteractions()，甚至在每个测试函数中都用。
     * 但是verifyNoMoreInteractions()并不建议在每个测试函数中都使用。
     * verifyNoMoreInteractions()在交互测试套件中只是一个便利的验证，
     * 它的作用是当你需要验证是否存在冗余调用时。滥用它将导致测试代码的可维护性降低。
     */
    @Test
    public void testVerifyNoMoreInteractions(){

        List mockedList = mock(List.class);

        //using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");

        //following verification will fail
        // 下面的验证将会失败
        verifyNoMoreInteractions(mockedList);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testStubConsecutiveCalls(){

        List mockedList = mock(List.class);

        when(mockedList.get(anyInt()))
                .thenThrow(new RuntimeException())
                .thenReturn("foo");

        //using mock object
        mockedList.add("some arg");

        //First call: throws runtime exception:
        thrown.expect(RuntimeException.class);
        System.out.println(mockedList.get(0));

        //Second call: prints "foo"
        System.out.println(mockedList.get(1));

        //Any consecutive call: prints "foo" as well (last stubbing wins).
        System.out.println(mockedList.get(2));
    }

    @Test
    public void testStubWithCallbacks(){
        List mockedList = mock(List.class);
        when(mockedList.add(anyString())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return true;
            }
        });

        //the following prints "called with arguments: foo"
        System.out.println(mockedList.add("foo"));
    }
}
