package sping.boot.learn.rocketmq.producer;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-08-03
 * @since Jdk 1.8
 */
public class LocalTransExecuteResult {
    public String name;
    public String value;

    public LocalTransExecuteResult(String test) {
        this.name = test;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LocalTransExecuteResult{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
