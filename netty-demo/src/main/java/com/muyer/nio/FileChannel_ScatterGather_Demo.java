package com.muyer.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Description:
 *  Scatter：从Channel中读取数据到多个Buffer ==> java.nio.channels.FileChannel#read(java.nio.ByteBuffer[])
 *  Gather： 从多个Buffer写入到Channel中    ==> java.nio.channels.FileChannel#write(java.nio.ByteBuffer[])
 *
 * date: 2021/8/2 15:44
 * @author YeJiang
 * @version
 */
public class FileChannel_ScatterGather_Demo {
    public static void main(String[] args) throws Exception {
        //1.获取channel
        FileChannel fc = new RandomAccessFile("D:\\test.txt", "rw").getChannel();
        //2.获取buffer
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);

        //Scatter: 确保数据大于size > 100,才会把多出的数据写入其他的Buffer里面
        ByteBuffer[] buffers = {byteBuffer1, byteBuffer2};
        fc.read(buffers);
        for(ByteBuffer buffer:buffers){
            //write mode --> read mode
            buffer.flip();
        }
        System.out.println(new String(buffers[0].array(),0,buffers[0].limit()));
        System.out.println(new String(buffers[1].array(),0,buffers[1].limit()));


        //Gather
        FileChannel fc2 = new RandomAccessFile("D:\\test1.txt", "rw").getChannel();
        fc2.write(buffers);

        fc.close();
        fc2.close();
    }
}
