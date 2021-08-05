### java.nio.channels.SelectableChannel

```
A channel that can be multiplexed via a {@link Selector}. 
> 能被Selector多路复用的channel

In order to be used with a selector, an instance of this class must first be registered via the register method. This method returns a new SelectionKey object that represents the channel's registration with the selector.
Once registered with a selector, a channel remains registered until it is deregistered. This involves deallocating whatever resources were allocated to the channel by the selector.
A channel cannot be deregistered directly; instead, the key representing its registration must be cancelled. Cancelling a key requests that the channel be deregistered during the selector's next selection operation. A key may be cancelled explicitly by invoking its cancel method. All of a channel's keys are cancelled implicitly when the channel is closed, whether by invoking its close method or by interrupting a thread blocked in an I/O operation upon the channel.
If the selector itself is closed then the channel will be deregistered, and the key representing its registration will be invalidated, without further delay.
A channel may be registered at most once with any particular selector.
Whether or not a channel is registered with one or more selectors may be determined by invoking the isRegistered method.
Selectable channels are safe for use by multiple concurrent threads.


> 为了与选择器一起使用，必须首先通过register方法注册该类的实例。此方法返回一个新的SelectionKey对象，该对象表示通道与选择器的注册。
> 一旦注册选择器，通道将保持注册状态，直到它被注销。这涉及解除分配选择器分配给通道的任何资源。
> 渠道不能直接注销;相反，必须取消代表其注册的密钥。取消密钥请求在选择器的下一个选择操作期间取消注册该通道。可以通过调用其cancel方法显式取消密钥。当通道关闭时，无论是通过调用其close方法还是通过中断在通道上的IO操作中阻塞的线程，所有通道的键都会被隐式取消。
> 如果选择器本身已关闭，则将取消注册该通道，并且表示其注册的密钥将无效，而不会有进一步的延迟。
> 一个channel最多可以与任何特定选择器一起注册一次。
> 可以通过调用isRegistered方法来确定是否向一个或多个选择器注册了频道。
> 多个并发线程可以安全地使用可选择的通道。
```

```
这个抽象类提供实现通道可选择性所需要的公共方法
它是所有支持就绪检查通道的父类
FileChannel类没有继承SelectableChannel因此是不是可选通道，而所有socket通道都是可选择的
SelectableChannel可以被注册到Selector对象上，同时可以指定对那个选择器而言，那种操作是感兴趣的。一个通道可以被注册到多个选择器上，但对每个选择器而言只能被注册一次。
```

```
SelectableChannel#register 实现在 AbstractSelectableChannel#register

AbstractSelectableChannel#register

Registers this channel with the given selector, returning a selection key
> 将channel注册到selector上，并且返回一个selection Key

his method first verifies that this channel is open and that the given initial interest set is valid.
> 此方法首先验证此通道是否已打开，以及给定的初始兴趣集是否有效。

If this channel is already registered with the given selector then the selection key representing that registration is returned after setting its interest set to the given value.
> 如果此通道已在给定选择器中注册，则在将其兴趣设置为给定值后，将返回表示该注册的选择键。

Otherwise this channel has not yet been registered with the given selector, so the {@link AbstractSelector#register register} method of the selector is invoked while holding the appropriate locks. 
 The resulting key is added to this channel's key set before being returned.
> 否则，此通道尚未在给定的选择器中注册，因此在保持适当的锁定时调用选择器的register方法。返回后，生成的密钥将添加到此通道的密钥集中。



===> 
[code]
channel.configureBlocking(false);
SelectionKey key= channel.register(selector,SelectionKey.OP_READ);

> 通过调用通道的register()方法会将它注册到一个选择器上。与Selector一起使用时，Channel必须处于非阻塞模式下，否则将抛出IllegalBlockingModeException异常，这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式，而套接字通道都可以。

> 另外通道一旦被注册，将不能再回到阻塞状态，此时若调用通道的configureBlocking(true)将抛出BlockingModeException异常。

> register()方法的第二个参数是“interest集合”，表示选择器所关心的通道操作，它实际上是一个表示选择器在检查通道就绪状态时需要关心的操作的比特掩码。比如一个选择器对通道的read和write操作感兴趣，那么选择器在检查该通道时，只会检查通道的read和write操作是否已经处在就绪状态。

> SelectionKey有四种类型:
    SelectionKey.OP_CONNECT ==> Connect 连接
    SelectionKey.OP_ACCEPT  ==> Accept 接受
    SelectionKey.OP_READ    ==> Read 读
    SelectionKey.OP_WRITE   ==> Write 写
    并非所有操作在所有通道都能够被支持,eg ServerSocketChannel支持Accept，而SocketChannel中不支持
    Selector对通道的多操作类型感兴趣，可以用“位或”操作符来实现。eg int interestSet=SelectionKey.OP_READ|SelectionKey.OP_WRITE  
    
> 当通道触发了某个操作之后，表示该通道的某个操作已经就绪，可以被操作:
    一个SocketChannel成功连接到另一个服务器称为“连接就绪”(OP_CONNECT)
    一个ServerSocketChannel准备好接收新进入的连接称为“接收就绪”（OP_ACCEPT）
    一个有数据可读的通道可以说是“读就绪”(OP_READ)
    一个等待写数据的通道可以说是“写就绪”(OP_WRITE)
  
> register() 方法会返回一个SelectionKey对象，我们称之为键对象

```
