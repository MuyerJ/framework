### java.nio.channels.ServerSocketChannel

```
A selectable channel for stream-oriented listening sockets.
A server-socket channel is created by invoking the open method of this class. It is not possible to create a channel for an arbitrary, pre-existing ServerSocket. A newly-created server-socket channel is open but not yet bound. An attempt to invoke the accept method of an unbound server-socket channel will cause a NotYetBoundException to be thrown. A server-socket channel can be bound by invoking one of the bind methods defined by this class.
Socket options are configured using the setOption method. Server-socket channels support the following options:
Option Name
Description
SO_RCVBUF
The size of the socket receive buffer
SO_REUSEADDR
Re-use address
Additional (implementation specific) options may also be supported.
Server-socket channels are safe for use by multiple concurrent threads.

面向流的侦听套接字的可选通道。
通过调用此类的open方法创建服务器套接字通道。 无法为任意预先存在的ServerSocket创建通道。 新创建的服务器套接字通道已打开但尚未绑定。 尝试调用未绑定的服务器套接字通道的accept方法将导致抛出NotYetBoundException。 可以通过调用此类定义的绑定方法之一来绑定服务器套接字通道。
使用setOption方法配置套接字选项。 服务器套接字通道支持以下选项：
选项名称
描述
SO_RCVBUF
套接字接收缓冲区的大小
SO_REUSEADDR
重复使用地址
还可以支持其他（特定于实现的）选项。
服务器套接字通道可供多个并发线程使用。
```

```
ServerSocketChannel 继承了AbstractSelectableChannel，可以注册到Selector。
```


