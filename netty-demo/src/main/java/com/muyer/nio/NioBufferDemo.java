package com.muyer.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Description: 
 * date: 2021/7/29 0:31
 * @author YeJiang
 * @version
 */
public class NioBufferDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("F:" + File.separator + "buffer.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        //创建一个capacity为48bytes的Buffer
        ByteBuffer buf = ByteBuffer.allocate(48);
        //数据写入buffer
        /**
         * 有两种写入buffer的方式
         * 1.buf.put(127)
         * 2.从Channel写到Buffer
         */
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            //flip一下,让buffer可读
            buf.flip();
            while (buf.hasRemaining()) {
                //一次读取1byte
                System.out.print((char) buf.get());
            }
            //清空,使buffer可被重写
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
