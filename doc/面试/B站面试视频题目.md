1.说一下ArrayList和LinkedList的区别?
```
1.底层数据结构不同，ArrayList是数组实现、LinkedList底层是基于链表实现
2.使用场景同,ArrayList更适合随机查找，LinkedList更适合和添加。查询、删除、添加的时间复杂度不同
3.另外ArrayList和LinkedList都实现了List接口，但是LinkedList还额外实现了Deque接口，所以LinkedList还可以用来当作队列（双端队列,addFirst/addLast）使用


ArrayList
    get(n)  //提前分配好，时间复杂度O(1)
    
    add(e) //添加到最后的非空一个位置，满了就需要扩容
    add(index,e)

LinkedList
    get(n)  //循环去查找
    getFirst()/getLast() //O(1)，内部有两个Node属性，一直维护着最后和第一个节点
    add(e) //没有扩容，直接往最后一个位置添加
    add(index,e)  //遍历再插入
    
    
```

2.说一下HashMap的put方法
```
1.根据key通过哈希算法与运算(和数组length-1运算)得出数组下标
2.判断数组下标元素是否为空
    为空,将key和value封装为Entry对象(7是Entry、8是Node)，并放入位置上
    不为空,分情况讨论：
    a.如果是jdk7，先判断是否需要扩容，需要扩容就进行扩容，不需要扩容则使用头插法添加到当前位置的链表中
    b.如果是jdk8，先判断当前位置上Node的类型，看是红黑树Node，还是链表Node
        若是红黑树Node，将key和value封装为一个红黑树节点并添加到红黑树中去，在这个过程会判断红黑树是否存在当前的key，若存在则更新value
        若是链表的Node，将key和value封装成一个链表的Node，并通过尾插法插入到链表最后位置，插入遍历过程中如果有key相同的，则覆盖value，若无直接插入到最后。当节点数超过8，则转成红黑树
```