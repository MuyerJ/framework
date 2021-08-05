### java.nio.channels.Selector

```
A multiplexor of {@link SelectableChannel} objects. 
> SelectableChannel对象的多路复用器
```

```
A selector may be created by invoking the {@link #open open} method of this class, which will use the system's default {@link java.nio.channels.spi.SelectorProvider selector provider} to create a new selector.
>   selector对象可以通过 selector#open创建,selector#open将使用系统默认的SelectorProvider创建新的选择器

A selector may also be created by invoking the {@link java.nio.channels.spi.SelectorProvider#openSelector openSelector} method of a custom selector provider.
> selector也可以通过SelectorProvider#openSelector创建自定义provider

A selector remains open until it is closed via its {@link #close close} method.
> selector将一直保持open,直到调用它的#close方法
```

```
A selectable channel's registration with a selector is represented by a {@link SelectionKey} object.  A selector maintains three sets of selection
> 注册到selector上的一个可选择channel可以被SelectionKey对象表示
> selector保持3套选择

```


> 方法

Selector#select()/select(long timeout):int
```
返回int值表示有多少通道已经就绪，是自从上次调用该方法后又多少个就绪状态的channel

selectNow():非阻塞，只要有通道就绪就立刻返回。
```

Selector#selectedKeys()
```
一旦调用select()方法且不为0时,可通过该方法返回 已选择键集合

Set selectedKeys = selector.selectedKeys();
Iterator keyIterator = selectedKeys.iterator();
while(keyIterator.hasNext()) {
    SelectionKey key = keyIterator.next();
    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
    } else if (key.isConnectable()) {
        // a connection was established with a remote server.
    } else if (key.isReadable()) {
        // a channel is ready for reading
    } else if (key.isWritable()) {
        // a channel is ready for writing
    }
    keyIterator.remove();
}

```

> Selector执行选择的过程

```
select()执行过程

两步：
1.检查已取消键集合(过cancle()取消的键)
如果该集合不为空，则清空该集合里的键，同时该集合中每个取消的键也将从已注册键集合和已选择键集合中移除。
2.再次检查已注册键集合（准确说是该集合中每个键的interest集合）。
系统底层会依次询问每个已经注册的通道是否准备好选择器所感兴趣的某种操作，一旦发现某个通道已经就绪了，则会首先判断该通道是否已经存在在已选择键集合当中，如果已经存在，则更新该通道在已注册键集合中对应的键的ready集合，如果不存在，则首先清空该通道的对应的键的ready集合，然后重设ready集合，最后将该键存至已注册键集合中。这里需要明白，当更新ready集合时，在上次select（）中已经就绪的操作不会被删除，也就是ready集合中的元素是累积的，比如在第一次的selector对某个通道的read和write操作感兴趣，在第一次执行select（）时，该通道的read操作就绪，此时该通道对应的键中的ready集合存有read元素，在第二次执行select()时，该通道的write操作也就绪了，此时该通道对应的ready集合中将同时有read和write元素。
```

