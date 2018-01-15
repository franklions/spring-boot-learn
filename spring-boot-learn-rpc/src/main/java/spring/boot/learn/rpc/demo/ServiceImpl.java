package spring.boot.learn.rpc.demo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/15
 * @since Jdk 1.8
 */
public class ServiceImpl extends UnicastRemoteObject implements IService {

    private static  final long serialVersionUID = 682805210518738166L;

    protected ServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String queryName(String no) throws RemoteException {
        System.out.println("hello" + no);
        return String.valueOf(System.currentTimeMillis());
    }
}
