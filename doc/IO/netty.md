### 一、Netty是什么?
```
异步事件驱动框架，用于快速开发高性能服务端和客户端
封装了JDK底层BIO和NIO模型，提供高度可用的API
自带编码解码解决拆包粘包问题，用户只用关心业务逻辑
精心设计的Reactor线程模型支持高并发海量连接
自带协议栈，无需用户关系
```

### 二、Java IO的演进?
#### 1.IO模型
```
(1) 1:1同步阻塞IO通信模型
问题? 阻塞、线程创建太多

(2) M:N同步阻塞IO通信模型
问题? 阻塞、线程池满

(3) 非阻塞IO
```
### 三、Netty入门
```
第一个demo
核心组件介绍
Netty VS NIO
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