package com.muyer.thymeleaf.util;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Set;

/**
 * Description: 
 * date: 2021/6/26 1:19
 * @author YeJiang
 * @version
 */
@Component
public class MyUtilFactory implements IExpressionObjectDialect {

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {

        return new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Sets.newHashSet("stringUtil");
            }

            @Override
            public Object buildObject(IExpressionContext iExpressionContext, String s) {
                System.out.println("buildObject" + s);
                if (StringUtils.equals(s, "stringUtil")) {
                    return new StringUtil();
                }
                return null;
            }

            @Override
            public boolean isCacheable(String s) {
                return s != null;
            }
        };
    }

    @Override
    public String getName() {
        return "util";
    }
}
