一、maven

```
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>2.10.2</version>
</dependency>

```


二、在src/main/resources/创建一个配置文件 ehcache.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd">

  <!-- 磁盘缓存位置 -->
  <diskStore path="java.io.tmpdir/ehcache"/>

  <!-- 默认缓存 -->
  <defaultCache
          maxEntriesLocalHeap="10000"
          eternal="false"
          timeToIdleSeconds="120"
          timeToLiveSeconds="120"
          maxEntriesLocalDisk="10000000"
          diskExpiryThreadIntervalSeconds="120"
          memoryStoreEvictionPolicy="LRU">
    <persistence strategy="localTempSwap"/>
  </defaultCache>

  <!-- helloworld缓存 -->
  <cache name="HelloWorldCache"
         maxElementsInMemory="1000"
         eternal="false"
         timeToIdleSeconds="5"
         timeToLiveSeconds="5"
         overflowToDisk="false"
         memoryStoreEvictionPolicy="LRU"/>
</ehcache>

```
===================
配置分析
```
diskStore ： ehcache支持内存和磁盘两种存储

path ：指定磁盘存储的位置
defaultCache ： 默认的缓存

maxEntriesLocalHeap=“10000”
eternal=“false”
timeToIdleSeconds=“120”
timeToLiveSeconds=“120”
maxEntriesLocalDisk=“10000000”
diskExpiryThreadIntervalSeconds=“120”
memoryStoreEvictionPolicy=“LRU”
cache ：自定的缓存，当自定的配置不满足实际情况时可以通过自定义（可以包含多个cache节点）

name : 缓存的名称，可以通过指定名称获取指定的某个Cache对象

maxElementsInMemory ：内存中允许存储的最大的元素个数，0代表无限个

clearOnFlush：内存数量最大时是否清除。

eternal ：设置缓存中对象是否为永久的，如果是，超时设置将被忽略，对象从不过期。根据存储数据的不同，例如一些静态不变的数据如省市区等可以设置为永不过时

timeToIdleSeconds ： 设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。

timeToLiveSeconds ：缓存数据的生存时间（TTL），也就是一个元素从构建到消亡的最大时间间隔值，这只能在元素不是永久驻留时有效，如果该值是0就意味着元素可以停顿无穷长的时间。

overflowToDisk ：内存不足时，是否启用磁盘缓存。

maxEntriesLocalDisk：当内存中对象数量达到maxElementsInMemory时，Ehcache将会对象写到磁盘中。

maxElementsOnDisk：硬盘最大缓存个数。

diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。

diskPersistent：是否在VM重启时存储硬盘的缓存数据。默认值是false。

diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。


```
====================
编程方式修改配制
```
Cache cache = manager.getCache("mycache");
CacheConfiguration config = cache.getCacheConfiguration();
config.setTimeToIdleSeconds(60);
config.setTimeToLiveSeconds(120);
config.setmaxEntriesLocalHeap(10000);
config.setmaxEntriesLocalDisk(1000000);
```

三、测试类

```
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
 
public class CacheTest {
    public static void main(String[] args) {
        // 1. 创建缓存管理器
         CacheManager cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");
        // 2. 获取缓存对象
        Cache cache = cacheManager.getCache("HelloWorldCache");
        // 3. 创建元素
        Element element = new Element("key1", "value1");
        // 4. 将元素添加到缓存
        cache.put(element);
        // 5. 获取缓存
        Element value = cache.get("key1");
        System.out.println("value: " + value);
        System.out.println(value.getObjectValue());
        // 6. 删除元素
        cache.remove("key1"); 
        Dog dog = new Dog("xiaohei", "black", 2);
        Element element2 = new Element("dog", dog);
        cache.put(element2);
        Element value2 = cache.get("dog");
        System.out.println("value2: "  + value2);
        Dog dog2 = (Dog) value2.getObjectValue();
        System.out.println(dog2);
        System.out.println(cache.getSize());
        // 7. 刷新缓存
        cache.flush(); 
        // 8. 关闭缓存管理器
        cacheManager.shutdown();
    }
}
```