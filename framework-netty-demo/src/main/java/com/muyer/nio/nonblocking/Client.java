package com.muyer.nio.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.Instant;

/**
 * Description: 
 * date: 2021/8/2 17:30
 * @author YeJiang
 * @version
 */
public class Client {

    public static void main(String[] args) throws IOException {
        //获取连接通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
        sChannel.configureBlocking(false);

        ///发送数据
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(Instant.now().toString().getBytes());
        buf.flip();
        sChannel.write(buf);
        buf.clear();

        //关闭通道
        sChannel.close();

    }
}
