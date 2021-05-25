package com.muyer.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Description: 
 * date: 2021/5/24 22:41
 * @author YeJiang
 * @version
 */
public interface IHelloService extends Remote {

    String sayHello(String msg) throws RemoteException;
}
