package com.muyer.nio.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Description: 
 * date: 2021/8/2 16:35
 * @author YeJiang
 * @version
 */
public class Server {

    public static void main(String[] args) throws IOException {

        //创建ServerSocketChannel，绑定端口，设置为阻塞式，监听请求
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.bind(new InetSocketAddress(6666));
        ssChannel.configureBlocking(true);
        //阻塞IO，接住连接过来的Channel
        SocketChannel sChannel = ssChannel.accept();

        //创建 Channel 和 Buffer 读取信息
        FileChannel fChannel = FileChannel.open(Paths.get("D:\\test.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (sChannel.read(buf) != -1) {
            //写模式-->读模式
            buf.flip();
            fChannel.write(buf);
            buf.clear();
        }

        //返回结果给客户端
        buf.put("server receive msg".getBytes());
        buf.flip();
        sChannel.write(buf);

        //关闭资源
        sChannel.close();
        fChannel.close();
    }
}
