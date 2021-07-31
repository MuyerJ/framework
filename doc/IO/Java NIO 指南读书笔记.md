### 一、概述


#### 1.核心组件
```
channel:
    数据可以从channel读到Buffer，也可以从Buffer写入channel
    channel的实现：FileChannel,DatagramChannel,SocketChannel,ServerChannel
buffer:
    Buffer的实现：ByteBuffer、CharBuffer、DoubleBuffer、FloatBuffer、IntBuffer、LongBuffer、ShortBuffer
selector：
    Selector允许单线程处理多个channel
    使用：要使用Selector需要向Selector注册channel，然后调用select()方法。这个方法会一直阻塞到某个通道有时间就绪

unsafe

其他组件：
Pipe、FileLock
```


#### 2.通道
```
(1) NIO的通道和流的区别
通道即可以读数据，也可以写数据；流读写一般都是单向的
通道可以异步的读写
通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入

(2) 四种实现
FileChannel : 从文件中读写数据
DatagramChannel : 通过UDP读写网络中的数据
SocketChannel : 通过TCP读写网络中的数据
ServerSocketChannel : 可以监听新进来的TCP连接，对每一个新进来的TCP连接都会新建一个SocketChannel

(3) 以FileChannel为例读取Buffer代码

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
    
```

#### 3.缓冲区
```
(1) Buffer 的基本用法
    --> 写入数据到Buffer 
    --> 调用flip()
    --> 从buffer中读取数据 
    --> 调用clear()或compact方法,使buffer可被重写
    
    解释：
    当向buffer写入数据，buffer会记录写下多少数据
    当要读buffer读取数据，需要调用flip()将buffer从写模式变成读模式(limit=position,positon=0)
    当读完数据，需要通过clear()或compact()方法清空
    
(2) Buffer 的 capacity、position、limit、mark
    mark 可以做标记，初始值为-1
    position 记录游标写到哪里了
    limit 限制,超出了这个值就需要扩容
    capacity 数组最大容量
    这几个标记可以标识buffer的模式(读/写)

(3) Buffer 的类型
    ByteBuffer
    CharBuffer
    DoubleBuffer
    FloatBuffer
    IntBuffer
    LongBuffer
    ShortBuffer
    
(4) Buffer 的分配
    每个buffer都有一个allocate方法，通过此方法进行分配获取
    ByteBuffer buf = ByteBuffer.allocate(48);
    CharBuffer buf = CharBuffer.allocate(48);
    
(5) 向 Buffer 中写数据
    两种方式
    从channel中写到Buffer：  int bytesRead = inChannel.read(buf); 
    直接put到Buffer:  buf.put(127);
    
(6) 从 Buffer 中读取数据
    两种方式
    buffer读取到channel：int bytesWritten = inChannel.write(buf);
    通过get方法读取： byte aByte = buf.get();  
      
(7) flip() 方法
    将Buffer从写模式变成读模式
    flip()会将 limit = position , position = 0
    

(8) clear() 与 compact() 方法


(9) mark() 与 reset() 方法


(10) equals() 与 compareTo() 方法


```
