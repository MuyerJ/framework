package com.muyer.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Description: 
 * date: 2021/7/29 22:41
 * @author YeJiang
 * @version
 */
public class NioSocketChannelDemo {


    public static class TCPEchoServer implements Runnable {

        /*服务器地址*/
        private InetSocketAddress inetSocketAddress;

        public TCPEchoServer(int port) {
            this.inetSocketAddress = new InetSocketAddress(port);
        }

        @Override
        public void run() {
            Charset charset = Charset.forName("UTF-8");

            ServerSocketChannel channel = null;
            Selector selector = null;

            try {
                /*创建选择器*/
                selector = Selector.open();
                /*创建服务器通道*/
                channel = ServerSocketChannel.open();
                /*设置为非阻塞*/
                channel.configureBlocking(false);
                /*设置监听端口号和缓冲最大连接数*/
                channel.bind(inetSocketAddress, 100);
                /*为通道设置selector*/
                channel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                System.out.println("启动失败。。。");
                return;
            }

            try {
                /*服务器线程被中断后会退出*/
                while (!Thread.currentThread().isInterrupted()) {
                    if (selector.select() == 0) {
                        continue;
                    }
                    /*
                     * 什么是selectKey? 可以理解为同过selectKey可以获取事件的
                     * */
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                    SelectionKey key = null;
                    /*遍历*/
                    while (keyIterator.hasNext()) {
                        key = keyIterator.next();
                        /*防止下次select方法返回处理自已处理过的通道*/
                        keyIterator.remove();


                        if(key.isAcceptable()){
                            /*accept方法会返回一个普通的通道,每个通道在内核中都对应一个socket缓冲区*/
                            SocketChannel sc = channel.accept();
                            sc.configureBlocking(false);

//                            sc.register(selector,SelectionKey.OP_READ,new Buffer());
                            System.out.println("accept from "+sc.getRemoteAddress());
                        }

                        if(key.isReadable()){

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
