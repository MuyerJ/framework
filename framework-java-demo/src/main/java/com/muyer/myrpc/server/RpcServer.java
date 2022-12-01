package com.muyer.myrpc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: 
 * date: 2021/5/26 0:14
 * @author YeJiang
 * @version
 */
public class RpcServer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.publish(new HelloMyRpcImpl(), 8989);

    }

    public void publish(final Object service, int port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket accept = serverSocket.accept();
                executorService.execute(new ProcessHandler(accept, service));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(serverSocket)) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
