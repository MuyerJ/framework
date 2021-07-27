package com.muyer.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:
 *  NIO:
 *      accept:保存地址、保存文件描述符
 *
 * date: 2021/7/26 22:56
 * @author YeJiang
 * @version
 */
public class SocketServer {
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        startServer(9999);
    }

    public synchronized static void startServer(int port) throws IOException {
        if (serverSocket != null) {
            return;
        }

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已经启动...");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    byte[] bytes = new byte[1024];
                    InputStream inputStream = null;
                    try {
                        inputStream = socket.getInputStream();
                        while (true) {
                            int read = inputStream.read(bytes);
                            if (read != -1) {
                                System.out.println("读取客户端数据" + new String(bytes, 0, read));
                            } else {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("服务端已经关闭");
                serverSocket = null;
            }
        }
    }
}
