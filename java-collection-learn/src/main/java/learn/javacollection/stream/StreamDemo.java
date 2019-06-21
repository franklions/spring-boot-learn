package learn.javacollection.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream 的使用就是实现一个 filter-map-reduce 过程，产生一个最终结果，或者导致一个副作用（side effect）。
 * 流的操作
 * Intermediate：
 * <p>
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
 * <p>
 * Terminal：
 * <p>
 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 * <p>
 * Short-circuiting：
 * <p>
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
 *
 * @author flsh
 * @version 1.0
 * @date 2019-06-21
 * @since Jdk 1.8
 */
public class StreamDemo {

    /**
     * 构造一个流
     */
    public void createStream() {
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
    }

    /**
     * 清单 5. 数值流的构造
     * IntStream、LongStream、DoubleStream
     */
    public void createNumberStream() {
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    /**
     * 清单 6. 流转换为其它数据结构
     */
    public void streamConvert() {
        // 1. Array
        Stream<String> stream = Stream.of("a", "b", "c");
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();

        //一个 Stream 只可以使用一次，上面的代码为了简洁而重复使用了数次。

    }

    /**
     * Intermediate 过程的操作方法 一个流可以后面跟随零个或多个 intermediate 操作。
     * 其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。
     * 这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
     * <p>
     * map： 把 input Stream 的每一个元素，映射成 output Stream 的另外一个元素
     * flatMap： 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终 output 的新 Stream 里面已经没有 List 了
     * mapToInt：把 input转换成整数
     * filter：filter 对原始 Stream 进行某项测试，通过测试的元素被留下来生成一个新 Stream。
     * distinct：去重复的
     * sorted：排序
     * peek： peek 对每个元素执行操作并返回一个新的 Stream
     * limit：limit 返回 Stream 的前面 n 个元素
     * skip：skip 则是扔掉前 n 个元素
     * parallel：
     * sequential：
     * unordered：
     */
    public void streamIntermediate() {
        //清单 7. 转换大写
        List<String> wordList = Arrays.asList("abc", "def");
        List<String> output = wordList.stream().
                map(String::toUpperCase).
                collect(Collectors.toList());
        //这段代码把所有的单词转换为大写。

        //清单 8. 平方数
        List<Integer> nums = Arrays.asList(1, 2, 3, 5);
        List<Integer> squareNums = nums.stream().map(n -> n * n)
                .collect(Collectors.toList());
        //这段代码生成一个整数 list 的平方数 {1, 4, 9, 16}。

        //清单 9. 一对多
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        //flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起

        //清单 10. 留下偶数
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
        //经过条件“被 2 整除”的 filter，剩下的数字为 {2, 4, 6}。

        //清单 11. 把单词挑出来
        BufferedReader reader = new BufferedReader(new FileReader("/user/log.log"));
        List<String> output = reader.lines().flatMap(line -> Stream.of(line.split("\n"))).
                filter(word -> word.length() > 0).collect(Collectors.toList());
        //这段代码首先把每行的单词用 flatMap 整理到新的 Stream，然后保留长度不为 0 的，就是整篇文章中的全部单词了。

        //清单 13. peek 对每个元素执行操作并返回一个新的 Stream
        Stream.of("one", "tow", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value:" + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value:" + e))
                .collect(Collectors.toList());

        //清单 16. limit 和 skip 对运行次数的影响
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);

        //清单 17. limit 和 skip 对 sorted 后的运行次数无影响
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName()))
                .limit(2).collect(Collectors.toList());
        System.out.println(personList2);

        //清单 18. 优化：排序前进行 limit 和 skip
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().limit(2)
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .collect(Collectors.toList());
        System.out.println(personList2);

        //下面的例子则使用 distinct 来找出不重复的单词。
        //清单 20. 找出全文的单词，转小写，并排序
        BufferedReader br = new BufferedReader(new FileReader("/usr/log.log"));
        List<String> words = br.lines().flatMap(line -> Stream.of(line.split(" ")))
                .filter(word -> word.length() > 0)
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        br.close();
        System.out.println(words);

    }

    /**
     * Termail 终端未端操作方法 一个流只能有一个 terminal 操作当这个操作执行后，流就被使用“光”了，
     * 无法再被操作。所以这必定是流的最后一个操作。Terminal 操作的执行，才会真正开始流的遍历，
     * 并且会生成一个结果，或者一个 side effect。
     * <p>
     * forEach、forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
     * forEachOrdered、
     * toArray、
     * reduce、这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、第 n 个元素组合。
     * 从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。
     * collect、
     * min、
     * max、
     * count、
     * anyMatch、
     * allMatch、
     * noneMatch、
     * findFirst、这是一个 termimal 兼 short-circuiting 操作，它总是返回 Stream 的第一个元素，或者空。
     * findAny、
     * iterator
     */
    public void streamTerminal() {

        //清单 12. 打印姓名（forEach 和 pre-java8 的对比）
        roster.stream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
                .forEach(p -> System.out.println(p.getName()));
        //另外一点需要注意，forEach 是 terminal 操作，因此它执行后，Stream 的元素就被“消费”掉了，
        // 你无法对一个 Stream 进行两次 terminal 运算。
        // 下面的代码是错误的：

        stream.forEach(element -> doOneThing(element));
        stream.forEach(element -> doAnotherThing(element));

        //相反，具有相似功能的 intermediate 操作 peek 可以达到上述目的
        //forEach 不能修改自己包含的本地变量值，也不能用 break/return 之类的关键字提前结束循环。

        //清单 14. Optional 的两个用例
        String strA = " abcd ", strB = null;
        print(strA);
        print("");
        print(strB);
        getLength(strA);
        getLength("");
        getLength(strB);
        public static void print (String text){
            // Java 8
            Optional.ofNullable(text).ifPresent(System.out::println);

        }
        public static int getLength (String text){
            // Java 8
            return Optional.ofNullable(text).map(String::length).orElse(-1);
        }

        //使用 Optional 代码的可读性更好，而且它提供的是编译时检查，能极大的降低 NPE 这种 Runtime Exception
        // 对程序的影响，或者迫使程序员更早的在编码阶段处理空值问题，而不是留到运行时再发现和调试。

        //清单 15. reduce 的用例
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        //求最小值minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        //求和 sumValue =10 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        //求和 sumValue = 10 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        //过滤 字符串连接 concat ="ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F")
                .filter(x -> x.compareTo("Z") > 0).reduce("", String::concat);

        //清单 19. 找出最长一行的长度
        BufferedReader br = new BufferedReader(new FileReader("/usr/test.log"));
        int longest = br.lines().mapToInt(String::length).max().getAsInt();
        br.close();
        System.out.println(longest);


    }

    /**
     * 还有一种操作被称为 short-circuiting。用以指：
     * <p>
     * 对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
     * 对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
     * 当操作一个无限大的 Stream，而又希望在有限时间内完成操作，则在管道内拥有一个 short-circuiting 操作是必要非充分条件。
     * Short-circuiting
     * <p>
     * anyMatch、Stream 中全部元素符合传入的 predicate，返回 true
     * allMatch、Stream 中只要有一个元素符合传入的 predicate，返回 true
     * noneMatch、Stream 中没有一个元素符合传入的 predicate，返回 true
     * findFirst、
     * findAny、
     * limit
     */
    public void streamShort_circuiting() {

        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));
        boolean isAllAdult = persons.stream()
                .allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);

        boolean isThereAnyChild = persons.stream().anyMatch(p -> p.getAge() < 12);
        System.out.printf("Any child? " + isThereAnyChild);


    }

    class Person {
        public int no;
        private String name;
        private int age;

        public Person(int no, String name, int age) {
            this.no = no;
            this.name = name;
            this.age = age;
        }

        public Person(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public String getName() {
            System.out.println(name);
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    /**
     * 自己生成流
     * <p>
     * Stream.generate
     * 通过实现 Supplier 接口，你可以自己来控制流的生成,由于它是无限的，
     * * 在管道中，必须利用 limit 之类的操作限制 Stream 大小
     * Stream.generate() 还接受自己实现的 Supplier
     * <p>
     * Stream.iterate
     * terate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
     * 然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
     */
    public void customCreateStream() {
        //清单 22. 生成 10 个随机整数
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);


        //清单 23. 自实现 Supplier
        Stream.generate(new PersonSupplier()).
                limit(10).
                forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));

        //清单 24. 生成一个等差数列
        Stream.iterate(0, n -> n + 3).limit(10)
                .forEach(x -> System.out.print(x + " "));
    }

    private class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        private Random random = new Random();

        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
    }

    /**
     * 用 Collectors 来进行 reduction 操作
     * java.util.stream.Collectors 类的主要作用就是辅助进行各类有用的 reduction 操作，
     * 例如转变输出为 Collection，把 Stream 元素进行归组。
     *
     * groupingBy/partitioningBy
     */
    private void collectorsReduction(){
        //清单 25. 按照年龄归组
        Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.groupingBy(Person::getAge));

        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }

        //清单 26. 按照未成年人和成年人归组
        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.partitioningBy(p -> p.getAge() < 18));

        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());
    }

    /**
     * Stream 的特性可以归纳为：
     *
     *     不是数据结构
     *
     *     它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
     *     它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
     *
     *     所有 Stream 的操作必须以 lambda 表达式为参数
     *     不支持索引访问
     *
     *     你可以请求第一个元素，但无法请求第二个，第三个，或最后一个。不过请参阅下一项。
     *
     *     很容易生成数组或者 List
     *     惰性化
     *
     *     很多 Stream 操作是向后延迟的，一直到它弄清楚了最后需要多少数据才会开始。
     *     Intermediate 操作永远是惰性化的。
     *
     *     并行能力
     *
     *     当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
     *
     *     可以是无限的
     *         集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成。
     */
}
