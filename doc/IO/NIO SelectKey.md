### java.nio.channels.SelectionKey
[SelectionKey](https://blog.csdn.net/qq_29951485/article/details/91314103)
```
A token representing the registration of a {@link SelectableChannel} with a {@link Selector}. 
> 注册到selector上channel的注册token信息

A selection key is created each time a channel is registered with a selector. A key remains valid until it is cancelled by invoking its cancel method, by closing its channel, or by closing its selector. Cancelling a key does not immediately remove it from its selector; it is instead added to the selector's cancelled-key set for removal during the next selection operation. The validity of a key may be tested by invoking its isValid method. 
A selection key contains two operation sets represented as integer values. Each bit of an operation set denotes a category of selectable operations that are supported by the key's channel.
The interest set determines which operation categories will be tested for readiness the next time one of the selector's selection methods is invoked. The interest set is initialized with the value given when the key is created; it may later be changed via the interestOps(int) method.
The ready set identifies the operation categories for which the key's channel has been detected to be ready by the key's selector. The ready set is initialized to zero when the key is created; it may later be updated by the selector during a selection operation, but it cannot be updated directly.
That a selection key's ready set indicates that its channel is ready for some operation category is a hint, but not a guarantee, that an operation in such a category may be performed by a thread without causing the thread to block. A ready set is most likely to be accurate immediately after the completion of a selection operation. It is likely to be made inaccurate by external events and by I/O operations that are invoked upon the corresponding channel.
This class defines all known operation-set bits, but precisely which bits are supported by a given channel depends upon the type of the channel. Each subclass of SelectableChannel defines an validOps() method which returns a set identifying just those operations that are supported by the channel. An attempt to set or test an operation-set bit that is not supported by a key's channel will result in an appropriate run-time exception.
It is often necessary to associate some application-specific data with a selection key, for example an object that represents the state of a higher-level protocol and handles readiness notifications in order to implement that protocol. Selection keys therefore support the attachment of a single arbitrary object to a key. An object can be attached via the attach method and then later retrieved via the attachment method.
Selection keys are safe for use by multiple concurrent threads. The operations of reading and writing the interest set will, in general, be synchronized with certain operations of the selector. Exactly how this synchronization is performed is implementation-dependent: In a naive implementation, reading or writing the interest set may block indefinitely if a selection operation is already in progress; in a high-performance implementation, reading or writing the interest set may block briefly, if at all. In any case, a selection operation will always use the interest-set value that was current at the moment that the operation began.

每次向选择器注册通道时，都会创建一个选择键。密钥保持有效，直到通过调用其取消方法，关闭其通道或关闭其选择器来取消密钥。取消密钥不会立即将其从选择器中删除;而是将其添加到选择器的已取消键集中，以便在下一个选择操作期间将其删除。可以通过调用其isValid方法来测试密钥的有效性。
选择键包含表示为整数值的两个操作集。操作集的每个位表示密钥通道支持的可选操作的类别。
- 兴趣集确定下次调用选择器的一个选择方法时将测试哪些操作类别的准备情况。利息集初始化为创建密钥时给定的值;稍后可以通过interestOps（int）方法进行更改。
就绪集合通过键的选择器识别检测到键的通道准备就绪的操作类别。创建密钥时，就绪集初始化为零;稍后可以在选择操作期间由选择器更新，但不能直接更新。
- 选择键的就绪集指示其通道已准备好用于某些操作类别是提示但不保证这样的类别中的操作可由线程执行而不会导致线程阻塞。在完成选择操作之后，就绪设置最有可能是准确的。外部事件和在相应通道上调用的I / O操作可能会使其不准确。
该类定义了所有已知的操作集位，但是精确地由给定通道支持哪些位取决于通道的类型。 SelectableChannel的每个子类定义一个validOps（）方法，该方法返回一个集合，该集合仅标识通道支持的那些操作。尝试设置或测试密钥通道不支持的操作设置位将导致适当的运行时异常。
通常需要将一些特定于应用程序的数据与选择键相关联，例如，表示更高级别协议状态的对象，并处理准备就绪通知以实现该协议。因此，选择键支持将单个任意对象附加到键。可以通过attach方法附加对象，然后通过附加方法检索。
选择键可安全地供多个并发线程使用。通常，读取和写入兴趣集的操作将与选择器的某些操作同步。具体如何执行此同步依赖于实现：在一个简单的实现中，如果选择操作已在进行中，则读取或写入兴趣集可能会无限期地阻塞;在高性能实现中，如果有的话，读取或写入兴趣集可能会暂时阻止。在任何情况下，选择操作将始终使用操作开始时当前的兴趣设定值。

```
```
选择键封装了通道和选择器的注册关系
选择键对象被SelectableChannel.register()返回并提供一个表示这种注册关系的标记
选择键包含了两个比特集(以整数的形式进行编码)，指示了该注册关系所关心的通道操作，以及通道已经准备好的操作
```

```
获取四种属性
1.SelectionKey#interestOps
    interest集合是Selector感兴趣的集合，用于指示选择器对通道关心的操作，可通过SelectionKey对象的interestOps()获取
    该兴趣集合是通道被注册到Selector时传进来的值。[register(selector,xxx)]
    该集合不会被选择器改变，但是可通过interestOps()改变
    判断对某个事件是否感兴趣：
         int interestSet=selectionKey.interestOps();
         boolean isInterestedInAccept  = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT；

2.SelectionKey#readyOps
    ready 集合是通道已经就绪的操作的集合，表示一个通道准备好要执行的操作了,可通过SelctionKey对象的readyOps()来获取相关通道已经就绪的操作。
    它是interest集合的子集，并且表示了interest集合中从上次调用select()以后已经就绪的那些操作。
    通过相关的选择键的readyOps()方法返回的就绪状态指示只是一个提示，底层的通道在任何时候都会不断改变，而其他线程也可能在通道上执行操作并影响到它的就绪状态。另外，我们不能直接修改ready集合。
    检查这些操作是否就绪：
    //int readSet=selectionKey.readOps();
    selectionKey.isAcceptable();//等价于selectionKey.readyOps()&SelectionKey.OP_ACCEPT
    selectionKey.isConnectable();
    selectionKey.isReadable();
    selectionKey.isWritable();

interestOps和readyOps返回的都是int,实际上他们返回的是OP_READ等"|"的值
判断集合中是否有某个常量：可以通过&，返回true则包含

    (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT ==> 返回值为true则代表存在
    
3.channel
4.selector
    取出SelectionKey所关联的Selector和Channel
    Channel channel =selectionKey.channel();
    Selector selector=selectionKey.selector();
```

```
SelectionKey#cancel

我们可以通过SelectionKey对象的cancel()方法来取消特定的注册关系。
该方法调用之后，该SelectionKey对象将会被”拷贝”至已取消键的集合中，该键此时已经失效，但是该注册关系并不会立刻终结。在下一次select()时，已取消键的集合中的元素会被清除，相应的注册关系也真正终结。
```            

```
SelectionKey绑定附加对象

可以将一个或者多个附加对象绑定到SelectionKey上，以便容易的识别给定的通道。通常有两种方式：
1.在注册的时候直接绑定:
    SelectionKey key=channel.register(selector,SelectionKey.OP_READ,theObject); 
2.在绑定完成之后附加：
    selectionKey.attach(theObject);//绑定
    
    绑定之后，可通过对应的SelectionKey取出该对象:
selectionKey.attachment();。
如果要取消该对象，则可以通过该种方式:
selectionKey.attach(null).

需要注意的是如果附加的对象不再使用，一定要人为清除，因为垃圾回收器不会回收该对象，若不清除的话会成内存泄漏。

一个单独的通道可被注册到多个选择器中，有些时候我们需要通过isRegistered（）方法来检查一个通道是否已经被注册到任何一个选择器上。 通常来说，我们并不会这么做。
```

```
Selector维护的三种类型SelectionKey集合：（重点）
> 选择器维护注册过的通道的集合，并且这种注册关系都被封装在SelectionKey当中    

A multiplexor of SelectableChannel objects.
A selector may be created by invoking the open method of this class, which will use the system's default selector provider to create a new selector. A selector may also be created by invoking the openSelector method of a custom selector provider. A selector remains open until it is closed via its close method. 
A selectable channel's registration with a selector is represented by a SelectionKey object. A selector maintains three sets of selection keys:
The key set contains the keys representing the current channel registrations of this selector. This set is returned by the keys method.
The selected-key set is the set of keys such that each key's channel was detected to be ready for at least one of the operations identified in the key's interest set during a prior selection operation. This set is returned by the selectedKeys method. The selected-key set is always a subset of the key set.
The cancelled-key set is the set of keys that have been cancelled but whose channels have not yet been deregistered. This set is not directly accessible. The cancelled-key set is always a subset of the key set.
All three sets are empty in a newly-created selector.
A key is added to a selector's key set as a side effect of registering a channel via the channel's register method. Cancelled keys are removed from the key set during selection operations. The key set itself is not directly modifiable.
A key is added to its selector's cancelled-key set when it is cancelled, whether by closing its channel or by invoking its cancel method. Cancelling a key will cause its channel to be deregistered during the next selection operation, at which time the key will removed from all of the selector's key sets. 
Keys are added to the selected-key set by selection operations. A key may be removed directly from the selected-key set by invoking the set's remove method or by invoking the remove method of an iterator obtained from the set. Keys are never removed from the selected-key set in any other way; they are not, in particular, removed as a side effect of selection operations. Keys may not be added directly to the selected-key set.

SelectableChannel对象的多路复用器。
- 可以通过调用此类的open方法来创建选择器，该方法将使用系统的默认选择器提供程序来创建新的选择器。还可以通过调用自定义选择器提供程序的openSelector方法来创建选择器。选择器保持打开状态，直到通过其关闭方法关闭。
可选择通道的选择器注册由SelectionKey对象表示。选择器维护三组选择键：
重点： 密钥集包含表示此选择器的当前通道注册的键。该方法由keys方法返回。
重点： 所选择的密钥集是一组密钥，使得检测到每个密钥的信道准备好用于在先前选择操作期间在密钥的兴趣集中识别的至少一个操作。这个集由selectedKeys方法返回。选定键集始终是键集的子集。
重点： 取消密钥集是已取消但其通道尚未取消注册的密钥集。此套装无法直接访问。取消密钥集始终是密钥集的子集。
在新创建的选择器中，所有三个组都是空的。
将一个键添加到选择器的键集中，作为通过通道的寄存器方法注册通道的副作用。在选择操作期间，取消的密钥将从密钥集中删除。密钥集本身不能直接修改。
无论是通过关闭其通道还是通过调用其cancel方法，都会在取消选择器的取消键集时添加一个键。取消密钥将导致其通道在下一个选择操作期间取消注册，此时密钥将从所有选择器的密钥集中删除。
通过选择操作将键添加到选定键集。可以通过调用set的remove方法或通过调用从set中获取的迭代器的remove方法，直接从selected-key集中删除键。密钥永远不会以任何其他方式从选定密钥集中删除;特别是，它们不会作为选择操作的副作用而被删除。密钥可能无法直接添加到选定密钥集。

keySet、selectedKeySet、cancelledKeySet
密钥集(key set)包含表示此选择器的当前通道注册的键。该方法由keys方法返回

所选择的密钥集(selected-key set)是一组密钥，使得检测到每个密钥的信道准备好用于在先前选择操作期间在密钥的兴趣集中识别的至少一个操作。这个集由selectedKeys方法返回。选定键集始终是键集的子集。

取消密钥集(cancelled-key set)是已取消但其通道尚未取消注册的密钥集。此套装无法直接访问。取消密钥集始终是密钥集的子集

```