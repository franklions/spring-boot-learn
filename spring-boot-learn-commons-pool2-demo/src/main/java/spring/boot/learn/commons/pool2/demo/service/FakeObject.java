package spring.boot.learn.commons.pool2.demo.service;

import java.util.UUID;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/1
 * @since Jdk 1.8
 */
public class FakeObject {
    private String id;
    private String name;
    private boolean valid;

    public FakeObject(String name){
        this.name = name;
        this.valid = true;

        id = UUID.randomUUID().toString();
        System.out.println("A new object is created: " + id);

    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString(){
        return "name: " + name + " id:" + id;
    }
}
