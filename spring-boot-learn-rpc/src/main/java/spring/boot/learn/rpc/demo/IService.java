package spring.boot.learn.rpc.demo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/15
 * @since Jdk 1.8
 */
public interface IService extends Remote {
    public String queryName(String no) throws RemoteException;
}
