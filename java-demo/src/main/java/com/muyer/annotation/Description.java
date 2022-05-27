package com.muyer.annotation;

import java.lang.annotation.*;

/**
 * Description: 
 * date: 2022/5/27 14:27
 * @author YeJiang
 * @version
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
    String name() default "";
}
