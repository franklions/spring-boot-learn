package spring.boot.learn.commons.pool2.demo.service;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/1
 * @since Jdk 1.8
 */
public class FakeObjectFactory extends BaseKeyedPooledObjectFactory<String,FakeObject> {
    @Override
    public FakeObject create(String key) throws Exception{

        return new FakeObject(key);
    }

    @Override
    public PooledObject<FakeObject> wrap(FakeObject object){
        return new DefaultPooledObject<>(object);
    }

    public void activateObject(String key, PooledObject<FakeObject> p) throws Exception {
        System.out.println(p + " is activated");
    }

    public void passivateObject(String key, PooledObject<FakeObject> p) throws Exception {
        System.out.println(p + " is passivated");
    }

    public boolean validateObject(String key, PooledObject<FakeObject> p) {

        System.out.println(p + " is validated for " + p.getState());

        return p.getObject().isValid();
    }

    public void destroyObject(String key, PooledObject<FakeObject> p) throws Exception {
        System.out.println(p + " is destroyed");
    }
}
