package com.muyer.myrpc.client;

import com.muyer.myrpc.model.HelloMyRpc;

/**
 * Description: 
 * date: 2021/5/26 0:12
 * @author YeJiang
 * @version
 */
public class RpcClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy();
        HelloMyRpc helloMyRpc = proxy.clientProxy(HelloMyRpc.class, "localhost", 8989);
        System.out.println(helloMyRpc.sayHello("rpc"));
    }
}
