package spring.boot.learn.rpc.demo;

import java.rmi.NotBoundException;
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
public class Client {
    public static void main(String[] args) {
        //注册管理
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("127.0.0.1",8088);
            String [] serverList = registry.list();
            for(String server:serverList){
                System.out.println(server);
            }
        }catch (RemoteException ex){

        }

        try {
            IService service = (IService) registry.lookup("vince");
            String result = service.queryName("test aaa");
            System.out.println("remote:" + result);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
