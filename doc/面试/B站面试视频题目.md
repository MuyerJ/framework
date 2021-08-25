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