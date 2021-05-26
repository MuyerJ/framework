package com.muyer.myrpc.client;

import com.muyer.myrpc.model.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * Description: 
 * date: 2021/5/26 0:25
 * @author YeJiang
 * @version
 */
public class TCPTransport {
    private String host;
    private int port;

    public TCPTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket() {
        System.out.println("创建一个连接");
        Socket socket;
        try {
            socket = new Socket(host, port);
            return socket;
        } catch (IOException e) {
            throw new RuntimeException("创建连接失败");
        }
    }

    public Object send(RpcRequest rpcRequest) {
        Socket socket = null;
        try {
            socket = newSocket();

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(rpcRequest);
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object result = in.readObject();
            in.close();
            out.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("");
        } finally {
            if(Objects.nonNull(socket)){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
