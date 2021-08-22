package com.muyer.customer.client;

/**
 * Description: 
 * date: 2021/8/22 22:39
 * @author YeJiang
 * @version
 */
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
