package com.muyer.customer.client;

import org.springframework.stereotype.Component;

/**
 * Description: 
 * date: 2021/8/19 9:42
 * @author YeJiang
 * @version
 */

@Component
class ProviderClientFallback implements ProviderClient {

    @Override
    public String ping() {
        return "Error";
    }
}