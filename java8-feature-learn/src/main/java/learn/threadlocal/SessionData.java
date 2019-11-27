package learn.threadlocal;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-11-26
 * @since Jdk 1.8
 */
public class SessionData {
    private String name;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
