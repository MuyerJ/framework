package com.muyer.customer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description: 
 * date: 2021/8/19 9:41
 * @author YeJiang
 * @version
 */
@Component
@FeignClient(name = "provider", fallback = ProviderClientFallback.class)
public interface ProviderClient {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping();
}
