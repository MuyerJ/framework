package com.muyer.thymeleaf;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: 
 * date: 2021/6/25 13:22
 * @author YeJiang
 * @version
 */
@Component
public class MyTag extends AbstractProcessorDialect {

    /**
     * 定义方言名称
     */
    private static final String NAME = "系统自定义标签";

    /**
     * 定义方言属性
     */
    private static final String PREFIX = "Fw";


    protected MyTag(){
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String s) {
        final Set<IProcessor> processor=new HashSet<>();
        //<Fw:select>标签
        processor.add(new MyTagSelect(PREFIX));
        return processor;
    }
}
