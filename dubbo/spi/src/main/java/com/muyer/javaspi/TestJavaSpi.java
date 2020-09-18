package com.muyer.javaspi;


import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Description: <br/>
 * date: 2020/9/18 8:54<br/>
 *
 * @author MuyerJ<br   />
 */
public class TestJavaSpi {
    public static void main(String[] args) {
        ServiceLoader<Cat> cats = ServiceLoader.load(Cat.class);
        Iterator<Cat> iterator = cats.iterator();
        while(iterator.hasNext()){
            Cat cat = iterator.next();
            cat.color();
        }

    }
}
