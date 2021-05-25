package com.muyer.rmi.client;

import com.muyer.rmi.server.HelloServiceImpl;
import com.muyer.rmi.server.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Description: 
 * date: 2021/5/24 22:56
 * @author YeJiang
 * @version
 */
public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Remote lookup = Naming.lookup("rmi://127.0.0.1/Hello");
        IHelloService helloService = (IHelloService) lookup;
        System.out.println(helloService.sayHello("yejiang"));
    }
}
