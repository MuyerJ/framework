package com.muyer.annotation;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Description: 
 * date: 2022/5/27 14:24
 * @author YeJiang
 * @version
 */
public abstract class Father {

    public String do1(){
        return getKidAnnotationValue();
    }

    protected String getKidAnnotationValue() {
        Description annotation = this.getClass().getAnnotation(Description.class);
        if(Objects.isNull(annotation)){
            return "";
        }
        return annotation.name();
    }
}
