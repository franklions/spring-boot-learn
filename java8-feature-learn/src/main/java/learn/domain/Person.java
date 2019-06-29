package learn.domain;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-06-29
 * @since Jdk 1.8
 */

import lombok.Data;

@Data
public class Person {
    public int no;
    private String name;
    private int age;
    private Sex gender;

    public Person(int no, String name, int age) {
        this.no = no;
        this.name = name;
        this.age = age;
    }

    public Person(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public enum Sex {
        MALE

    }
}
