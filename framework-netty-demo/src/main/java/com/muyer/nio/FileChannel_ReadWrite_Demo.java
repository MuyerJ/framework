package com.muyer.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Description: 
 * date: 2021/8/2 13:53
 * @author YeJiang
 * @version
 */
public class FileChannel_ReadWrite_Demo {

    public static void main(String args[]) throws IOException {
        //fileChannelWrite1();
        //fileChannelWrite2();
        //读数据或者用下面的方法
//        bufferWriteChannel();
        channelReadBuffer();
    }

    /**
     * 通过FileChannel读取数据
     * @throws IOException
     */
    private static void fileChannelRead() throws IOException {
        FileChannel fc = new FileInputStream("D:\\test.txt").getChannel();
        // fc = new RandomAccessFile("D:\\test.txt","r").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        fc.read(buff);
        //flip 写模式 --> 读模式
        buff.flip();
        System.out.println(Charset.defaultCharset().decode(buff));
        fc.close();
    }


    /**
     * 通过FileChannel写入数据
     * @throws IOException
     */
    private static void fileChannelWrite1() throws IOException {
        //向文件中写数据
        FileChannel fc = new FileOutputStream("D:\\test.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.wrap("你好，FileOutputStream".getBytes());
        fc.write(buffer);
        fc.close();
    }

    /**
     * 通过FileChannel写入数据
     * @throws IOException
     */
    private static void fileChannelWrite2() throws IOException {
        /**
         * 向文件末尾添加数据
         * 先按照“rw”访问模式打开D:\\test.txt文件，如果这个文件还不存在，RandomAccessFile的构造方法会创建该文件
         * RandomAccessFile不支持只写模式，因为把参数设为“w”是非法的
         */
        FileChannel fc = new RandomAccessFile("D:\\test.txt", "rw").getChannel();
        ByteBuffer buffer = ByteBuffer.wrap("你好，RandomAccessFile ".getBytes());
        fc.write(buffer);
        fc.close();
    }


    /**
     * Buffer 写入到channel
     * @throws IOException
     */
    public static void bufferWriteChannel() throws IOException {
        FileChannel fc = FileChannel.open(Paths.get("D:" + File.separator + "test.txt"),
                StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer buff = ByteBuffer.allocate(100);
        buff.put("bufferWriteChannel".getBytes());
        //flip 写模式 --> 读模式
        buff.flip();
        fc.write(buff);
        fc.close();
    }


    /**
     * channel 写入到 Buffer
     * @throws IOException
     */
    public static void channelReadBuffer() throws IOException {
        FileChannel fc = FileChannel.open(Paths.get("D:" + File.separator + "test.txt"),
                StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer buff = ByteBuffer.allocate(1024);
        fc.read(buff);
        //flip 写模式 --> 读模式
        buff.flip();
        System.out.println(Charset.defaultCharset().decode(buff));
    }


}
