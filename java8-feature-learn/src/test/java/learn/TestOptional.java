package learn;

import learn.domain.Address;
import learn.domain.Country;
import learn.domain.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-06-29
 * @since Jdk 1.8
 */
public class TestOptional {

    Logger logger = LoggerFactory.getLogger("TestOptional");
    /**
     * 我们从一个简单的用例开始。在 Java 8 之前，任何访问对象方法或属性的调用都可能导致 NullPointerException：
     * 在这个小示例中，如果我们需要确保不触发异常，就得在访问每一个值之前对其进行明确地检查：
     */
    @Test(expected = NullPointerException.class)
    public void throwNPE() {
        User user = new User();
        //这样写直接报空指针异常
        String isocode = user.getAddress().getCountry().getIsocode().toUpperCase();

        //这样判断太麻烦
        if (user != null) {
            Address address = user.getAddress();
            if (address != null) {
                Country country = address.getCountry();
                if (country != null) {
                    isocode = country.getIsocode();
                    if (isocode != null) {
                        isocode = isocode.toUpperCase();
                    }
                }
            }
        }

        //这样写正确
        Optional<User> userOpt = Optional.ofNullable(user);
        Optional<String> isoOpt = userOpt.map(u -> u.getAddress())
                .map(a -> a.getCountry())
                .map(c -> c.getIsocode());
        if (isoOpt.isPresent()){
            System.out.printf(isoOpt.get());
        }else  {
            System.out.printf("isocode is null");
        }
    }

    /**
     * 创建 Optional  实例
     *
     * of
     * ofNullable
     * 两个方法的不同之处在于如果你把 null 值作为参数传递进去，of() 方法会抛出 NullPointerException：
     *
     */

    /**
     * 重申一下，这个类型的对象可能包含值，也可能为空。你可以使用同名方法创建一个空的 Optional。
     */
    @Test(expected = NoSuchElementException.class)
    public void whenCreateEmptyOptional_thenNull(){
        Optional emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptional_thenNullPointerException() {
        User user = new User();
        Optional<User> opt = Optional.of(user);

        //如果对象即可能是 null 也可能是非 null，你就应该使用 ofNullable() 方法：
        Optional<User> opt2 = Optional.ofNullable(user);
    }

    /**
     * 访问 Optional 对象的值
     * get: 从 Optional 实例中取回实际值对象的方法之一是使用 get() 方法：
     * isPresent:验证是否有值
     */

    @Test
    public void whenCreateOfNullableOptional_thenOk() {
        String name = "John";
        Optional<String> opt = Optional.ofNullable(name);

        assertEquals("John", opt.get());
    }

    @Test
    public void whenCheckIfPresent_thenOk() {
        User user = new User("john@gmail.com", "1234");
        Optional<User> opt = Optional.ofNullable(user);
        assertTrue(opt.isPresent());

        assertEquals(user.getEmail(), opt.get().getEmail());

        //检查是否有值的另一个选择是 ifPresent() 方法。该方法除了执行检查，
        // 还接受一个Consumer(消费者) 参数，如果对象不是空的，就对执行传入的 Lambda 表达式：
        //这个例子中，只有 user 用户不为 null 的时候才会执行断言。
        opt.ifPresent( u -> assertEquals(user.getEmail(), u.getEmail()));
    }


    /**
     * 返回默认值
     * orElse() Optional 类提供了 API 用以返回对象值，或者在对象为空的时候返回默认值。
     * orElseGet() 其行为略有不同。这个方法会在有值的时候返回值，如果没有值，
     *      它会执行作为参数传入的 Supplier(供应者) 函数式接口，并将返回其执行结果：
     */

    @Test
    public void whenEmptyValue_thenReturnDefault() {
        User user = null;
        User user2 = new User("anna@gmail.com", "1234");
        User result = Optional.ofNullable(user).orElse(user2);

        assertEquals(user2.getEmail(), result.getEmail());
    }

    /**
     * 这里 user 对象是空的，所以返回了作为默认值的 user2。
     *
     * 如果对象的初始值不是 null，那么默认值会被忽略：
     */
    @Test
    public void whenValueNotNull_thenIgnoreDefault() {
        User user = new User("john@gmail.com","1234");
        User user2 = new User("anna@gmail.com", "1234");
        User result = Optional.ofNullable(user).orElse(user2);

        assertEquals("john@gmail.com", result.getEmail());
    }

    /**
     * orElse() 和 orElseGet() 的不同之处
     *
     * 乍一看，这两种方法似乎起着同样的作用。然而事实并非如此。我们创建一些示例来突出二者行为上的异同。
     *
     * 我们先来看看对象为空时他们的行为：
     * Using orElse
     * Creating New User
     * Using orElseGet
     * Creating New User
     * 由此可见，当对象为空而返回默认对象时，行为并无差异。
     */
    @Test
    public void givenEmptyValue_whenCompare_thenOk() {
        User user = null;
        logger.debug("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        logger.debug("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    /**
     * 我们接下来看一个类似的示例，但这里 Optional  不为空：
     * Using orElse
     * Creating New User
     * Using orElseGet
     *
     * 这个示例中，两个 Optional  对象都包含非空值，两个方法都会返回对应的非空值。不过，orElse() 方法仍然创建了 User 对象。与之相反，orElseGet() 方法不创建 User 对象。
     *
     * 在执行较密集的调用时，比如调用 Web 服务或数据查询，这个差异会对性能产生重大影响。
     */
    @Test
    public void givenPresentValue_whenCompare_thenOk() {
        User user = new User("john@gmail.com", "1234");
        logger.info("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        logger.info("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    private User createNewUser() {
        logger.debug("Creating New User");
        return new User("extra@gmail.com", "1234");
    }

    /**
     * 返回异常
     *
     * 除了 orElse() 和 orElseGet() 方法，Optional
     * 还定义了 orElseThrow() API —— 它会在对象为空的时候抛出异常，而不是返回备选的值：
     *
     * 这里，如果 user 值为 null，会抛出 IllegalArgumentException。
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenThrowException_thenOk() {
        User user = new User();
        User result = Optional.ofNullable(user)
                .orElseThrow( () -> new IllegalArgumentException());
    }

    /**
     * 转换值
     * map()
     * flatMap()
     */

    //map() 对值应用(调用)作为参数的函数，然后将返回的值包装在 Optional 中。
    // 这就使对返回值进行链试调用的操作成为可能 —— 这里的下一环就是 orElse()。
    @Test
    public void whenMap_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        String email = Optional.ofNullable(user)
                .map(u -> u.getEmail()).orElse("default@gmail.com");

        assertEquals(email, user.getEmail());
    }

    /**
     * 既然 getter 方法返回 String 值的 Optional，你可以在对 User 的 Optional 对象调用 flatMap() 时，
     * 用它作为参数。其返回的值是解除包装的 String 值：
     */

    @Test
    public void whenFlatMap_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        user.setPosition("Developer");
        String position = Optional.ofNullable(user)
                .flatMap(u -> u.getPosition()).orElse("default");

        assertEquals(position, user.getPosition().get());
    }

    /**
     * 过滤值
     * filter() 接受一个 Predicate 参数，返回测试结果为 true 的值。如果测试结果为 false，会返回一个空的 Optional。
     */
    @Test
    public void whenFilter_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        //如果通过过滤器测试，result 对象会包含非空值。
        Optional<User> result = Optional.ofNullable(user)
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"));

        assertTrue(result.isPresent());
    }

    /**
     * Optional 类的链式方法
     */

    @Test
    public void whenChaining_thenOk() {
        User user = new User("anna@gmail.com", "1234");

        String result = Optional.ofNullable(user)
                .flatMap(u -> u.getAddress2())
                .flatMap(a -> a.getCountry2())
                .map(c -> c.getIsocode())
                .orElse("default");

        assertEquals(result, "default");

        //上面的代码可以通过方法引用进一步缩减：
         result = Optional.ofNullable(user)
                .flatMap(User::getAddress2)
                .flatMap(Address::getCountry2)
                .map(Country::getIsocode)
                .orElse("default");
    }




}
