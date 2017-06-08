package elasticsearch.client.demo.domain;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/8
 * @since Jdk 1.8
 */
public class Employee {
    private int id;
    private String first_name;
    private String last_name;
    private int age ;
    private String about;
    private List<String> interests;

    public Employee(int id, String firstname, String lastname, int age, String about, List<String> interests) {
        this.id = id;
        this.first_name = firstname;
        this.last_name = lastname;
        this.age = age;
        this.about = about;
        this.interests = interests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
