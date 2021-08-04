### 一、Netty是什么?
```
异步事件驱动框架，用于快速开发高性能服务端和客户端
封装了JDK底层BIO和NIO模型，提供高度可用的API
自带编码解码解决拆包粘包问题，用户只用关心业务逻辑
精心设计的Reactor线程模型支持高并发海量连接
自带协议栈，无需用户关系
```

### 二、Netty 核心组件?
```
https://blog.csdn.net/ningdunquan/article/details/80342970

BootStrap:
    netty服务端和客户端的启动器(ServerBootStrap和BootStrap)

EventLoop:
    它就是Reactor,分为boss Reactor和worker Reactor。
    通过ServerBootstrap.group(EventLoopGroup,EventLoopGroup)设置
    EventLoop内部有个无限循环,维护了一个Selector,处理注册到Selector的io事件

Channel:
    通常是NioServerSocketChannel和NioSocketChannel
    NioServerSocketChannel负责监听一个TCP端口,有连接进来通过boss reactor创建一个NioSocketChannel将其绑定到work Reactor上,然后work reactor服务NioSocketChannel的io事件

ChannelPipeline： 
    数据结构：双向链表，head和tail分别对应头和尾
    是ChannelHandler的容器。简单来讲就是用来装CH的双向链表
    所有io的操作(write/read/connect)都会通过这个链表
    
ChannelHandler
    io真正处理的单元，分为两种inBound和outBound对应io的read和write
    可以创建自己的ChannelHandler来处理自己的逻辑，这个应该放在pipeLine末尾
    ChannelHandle用ChannelHandlerContext包裹着，有prev、next节点，可以获取前后的Handler, read 从head->tail,write 从tail->head
    

```
### 三、Netty入门
```
nio基本概念：
    read数据的两个阶段：
        一阶段：os从网卡缓冲区拉取数据
        二阶段：数据从内核到用户进程中
        
    当有报文进入网卡就会触发中断,kernel把报文送入协议栈,报文在协议栈里由下至上经过链路层、ip层、传输层,一直送到socket的内核缓冲区,用户进程调用recv等读接口把报文读出来。
    
    当进程发出read操作时,如果kernel中的数据还没有准备好,那么它并不会block用户进程,而是立刻返回一个error.用户进程判断结果是一个error时,它就知道数据还没有准备好,于是它可以再次发送read操作.
    一旦kernel中的数据准备好了,并且又再次收到了用户进程的system call,那么它马上就将数据拷贝到了用户内存,然后返回。
    
    nio的特点是用户进程不断轮询kernel缓冲区的数据是否ready
    
多路复用 IO multiplexing
    实质：select poll epoll
    好处：单个进程就可以同时处理多个网络连接IO
    原理：


```
### 四、项目案例

```
手写推送系统
```
### 五、NIO入门
```
传统BIO编程
伪异步IO编程
NIO核心原理
AIO核心原理
```
### 六、Netty源码分析
```
线程模型
IO模型
Eventloop
channel
channelhandler
pipeline
内存分配
ByteBuffer
编解码
性能优化工具类
设计模式在Netty中的应用
单机百万连接调优
channelhandlercontext
Bootstrap
```