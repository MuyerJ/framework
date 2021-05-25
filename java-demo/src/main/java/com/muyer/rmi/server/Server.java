package com.muyer.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Description: 
 * date: 2021/5/24 22:46
 * @author YeJiang
 * @version
 */
public class Server {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        HelloServiceImpl helloService = new HelloServiceImpl();
        LocateRegistry.createRegistry(1099);
        Naming.rebind("rmi://127.0.0.1/Hello", helloService);
        System.out.println("服务启动成功！");
    }
}
