package com.muyer.customer.client;

import org.springframework.stereotype.Component;

/**
 * Description: 
 * date: 2021/8/22 22:39
 * @author YeJiang
 * @version
 */
@Component
public class ProviderFallBack implements ProviderClient{
    @Override
    public String hello() {
        return "Error";
    }

    @Override
    public String hello2() {
        return "Error2";
    }
}
