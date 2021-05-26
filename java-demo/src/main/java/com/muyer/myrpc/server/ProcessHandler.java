package com.muyer.myrpc.server;

import com.muyer.myrpc.model.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Objects;

/**
 * Description: 
 * date: 2021/5/26 0:38
 * @author YeJiang
 * @version
 */
public class ProcessHandler implements Runnable {

    private Socket socket;
    private Object service;

    public ProcessHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {

        ObjectInputStream objectInputStream =null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object[] parameters = rpcRequest.getParameters();
            Class<?>[] types = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                types[i] = parameters[i].getClass();
            }
            Method method = service.getClass().getMethod(rpcRequest.getMethondName(), types);
            Object result = method.invoke(service, parameters);

            System.out.println("结果是："+result);


            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            if(Objects.nonNull(objectInputStream)){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
