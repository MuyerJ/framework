package com.muyer.nio;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Description:
 *  通道之间数据传输
 *      java.nio.channels.FileChannel#transferFrom(java.nio.channels.ReadableByteChannel, long, long)
 *      java.nio.channels.FileChannel#transferTo(long, long, java.nio.channels.WritableByteChannel)
 * date: 2021/8/2 15:58
 * @author YeJiang
 * @version
 */
public class FileChannel_Channel2Channel_Demo {

    public static void main(String[] args) throws IOException {
        //channel2Channel1();
        channel2Channel2();
    }

    public static void channel2Channel1() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\test.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D:\\test1.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    public static void channel2Channel2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\test.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D:\\test1.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
    }
}
