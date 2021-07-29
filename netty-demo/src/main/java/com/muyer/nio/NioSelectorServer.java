package com.muyer.nio;

/**
 * Description: 
 * date: 2021/7/27 22:29
 * @author YeJiang
 * @version
 */
public class NioSelectorServer {

    public static void main(String[] args) {
        //创建NIO ServerSocketChannel
        //设置 ServerSocketChannel 为非阻塞
        //打开 selector(多路选择复用器)处理channel,即创建epoll
        //将ServerSocketChannel注册到selector上

        //阻塞等待需要处理的时间发生
        //获取selector中注册的全部事件的SelectionKey实例

        //遍历处理
    }
}
