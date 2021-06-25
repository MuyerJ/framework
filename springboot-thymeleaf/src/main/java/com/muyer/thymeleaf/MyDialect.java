package com.muyer.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 *  自定义的标签
 * date: 2021/6/25 12:53
 * @author YeJiang
 * @version
 */
public class MyDialect extends AbstractDialect {
    private static final String PREFIX = "testag";
    private static final Set<IProcessor> processors = new HashSet<IProcessor>();

    //自定义的处理器
    static {
//        processors.add(new HasPermissionAttrProcessor());
//        processors.add(new HasPermissionElementProcessor());
    }


    public static String getPREFIX() {
        return PREFIX;
    }

    protected MyDialect(String name) {
        super(name);
    }
}
