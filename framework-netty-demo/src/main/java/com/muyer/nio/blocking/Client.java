package com.muyer.nio.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Description: 
 * date: 2021/8/2 17:03
 * @author YeJiang
 * @version
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //1.建立网络通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));

        //2.建立本地文件通道 和 缓冲
        FileChannel fChannel = FileChannel.open(Paths.get("D:\\test1.txt"), StandardOpenOption.READ);
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //本地fChannel里的数据读取到buf里
        while (fChannel.read(buf) != -1) {
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //通知服务端发送完毕
        sChannel.shutdownOutput();

        //3.接收服务端反馈
        int len = 0;
        while ((len = sChannel.read(buf)) != -1) {
            buf.flip();
            System.out.println(new String(buf.array(), 0, len));
            buf.clear();
        }

        //4.关闭资源
        fChannel.close();
        sChannel.close();

    }
}
