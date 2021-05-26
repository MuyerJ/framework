package com.muyer.myrpc.client;

import com.muyer.myrpc.model.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Description: 
 * date: 2021/5/26 0:23
 * @author YeJiang
 * @version
 */
public class RemoteInvocationHandler implements InvocationHandler {
    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethondName(method.getName());
        rpcRequest.setParameters(args);
        TCPTransport tcpTransport = new TCPTransport(host,port);
        return tcpTransport.send(rpcRequest);
    }
}
