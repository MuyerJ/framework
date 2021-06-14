package com.muyer.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.InputStream;

/**
 * Description: 
 * date: 2021/6/11 16:29
 * @author YeJiang
 * @version
 */
public class EhcacheDemo {
    public static void main(String[] args) {
        //获取cache对象
        //使用自定义配置的缓存管理器
//        CacheManager manager = CacheManager.create(EhcacheDemo.class.getClassLoader().getResourceAsStream("ehcache.xml"));
        //使用默认配置的缓存管理器
        CacheManager manager = CacheManager.create();
        Cache myCache = manager.getCache("myCache");

        System.out.println(myCache.getName());

        //创建cache对象
        Element e = new Element("myCacheKey", "myCacheValue");

        myCache.put(e);

        //获取cache
        Element key = myCache.get("myCacheKey");

        System.out.println(key);
        System.out.println(key.getObjectValue());

        myCache.remove("myCacheKey");

        System.out.println(myCache.get("myCacheKey"));

        //关闭
        myCache.flush();
        manager.shutdown();

    }
}
