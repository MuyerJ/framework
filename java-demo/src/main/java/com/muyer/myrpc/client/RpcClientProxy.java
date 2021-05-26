package com.muyer.myrpc.client;

import java.lang.reflect.Proxy;

/**
 * Description: 
 * date: 2021/5/26 0:20
 * @author YeJiang
 * @version
 */
public class RpcClientProxy {

    public <T> T clientProxy(Class<T> tClass, String host, int port) {
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new RemoteInvocationHandler(host, port));
    }
}
