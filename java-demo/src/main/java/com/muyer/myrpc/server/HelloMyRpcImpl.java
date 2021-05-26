package com.muyer.myrpc.server;

import com.muyer.myrpc.model.HelloMyRpc;

/**
 * Description: 
 * date: 2021/5/26 0:11
 * @author YeJiang
 * @version
 */
public class HelloMyRpcImpl implements HelloMyRpc {
    @Override
    public String sayHello(String msg) {
        return "Hello , " + msg;
    }
}
