### java.nio.channels.ServerSocketChannel

```
A selectable channel for stream-oriented connecting sockets.
A socket channel is created by invoking one of the open methods of this class. It is not possible to create a channel for an arbitrary, pre-existing socket. A newly-created socket channel is open but not yet connected. An attempt to invoke an I/O operation upon an unconnected channel will cause a NotYetConnectedException to be thrown. A socket channel can be connected by invoking its connect method; once connected, a socket channel remains connected until it is closed. Whether or not a socket channel is connected may be determined by invoking its isConnected method.
Socket channels support non-blocking connection: A socket channel may be created and the process of establishing the link to the remote socket may be initiated via the connect method for later completion by the finishConnect method. Whether or not a connection operation is in progress may be determined by invoking the isConnectionPending method.
Socket channels support asynchronous shutdown, which is similar to the asynchronous close operation specified in the Channel class. If the input side of a socket is shut down by one thread while another thread is blocked in a read operation on the socket's channel, then the read operation in the blocked thread will complete without reading any bytes and will return -1. If the output side of a socket is shut down by one thread while another thread is blocked in a write operation on the socket's channel, then the blocked thread will receive an AsynchronousCloseException.
Socket options are configured using the setOption method. Socket channels support the following options:
Option Name
Description
SO_SNDBUF
The size of the socket send buffer
SO_RCVBUF
The size of the socket receive buffer
SO_KEEPALIVE
Keep connection alive
SO_REUSEADDR
Re-use address
SO_LINGER
Linger on close if data is present (when configured in blocking mode only)
TCP_NODELAY
Disable the Nagle algorithm
Additional (implementation specific) options may also be supported.
Socket channels are safe for use by multiple concurrent threads. They support concurrent reading and writing, though at most one thread may be reading and at most one thread may be writing at any given time. The connect and finishConnect methods are mutually synchronized against each other, and an attempt to initiate a read or write operation while an invocation of one of these methods is in progress will block until that invocation is complete.

用于面向流的连接套接字的可选通道。
通过调用此类的一个打开方法来创建套接字通道。无法为任意预先存在的套接字创建通道。新创建的套接字通道已打开但尚未连接。尝试在未连接的通道上调用I / O操作将导致抛出NotYetConnectedException。可以通过调用connect方法连接套接字通道;连接后，插座通道保持连接状态，直到它关闭。是否连接套接字通道可以通过调用其isConnected方法来确定。
套接字通道支持非阻塞连接：可以创建套接字通道，并且可以通过connect方法启动建立到远程套接字的链接的过程，以便稍后通过finishConnect方法完成。可以通过调用isConnectionPending方法来确定连接操作是否正在进行。
套接字通道支持异步关闭，这类似于Channel类中指定的异步关闭操作。如果套接字的输入端被一个线程关闭而另一个线程在套接字通道上的读操作中被阻塞，那么被阻塞线程中的读操作将完成而不读取任何字节并返回-1。如果套接字的输出端被一个线程关闭而另一个线程在套接字通道上的写操作中被阻塞，则被阻塞的线程将收到AsynchronousCloseException。
使用setOption方法配置套接字选项。套接字通道支持以下选项：
选项名称
描述
SO_SNDBUF
套接字发送缓冲区的大小
SO_RCVBUF
套接字接收缓冲区的大小
SO_KEEPALIVE
保持连接活着
SO_REUSEADDR
重复使用地址
SO_LINGER
如果存在数据则关闭（仅在阻止模式下配置时）
TCP_NODELAY
禁用Nagle算法
还可以支持其他（特定于实现的）选项。
套接字通道可以安全地由多个并发线程使用。它们支持并发读写，但最多只有一个线程可能正在读取，并且最多一个线程可能在任何给定时间写入。 connect和finishConnect方法彼此相互同步，并且在调用其中一个方法时尝试启动读取或写入操作将阻塞，直到该调用完成。
```

```
SocketChannel 继承了AbstractSelectableChannel，可以注册到Selector。
```

