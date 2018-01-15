package spring.boot.learn.rpc.demo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/15
 * @since Jdk 1.8
 */
public class Server {
    public static void main(String[] args) {
        Registry registry = null;

        try {
            registry = LocateRegistry.createRegistry(8088);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        ServiceImpl service = null;
        try {
            service = new ServiceImpl();
            registry.rebind("vince",service);
            System.out.println("bind server");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
