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
@FeignClient(name = "providers", fallback = ProviderFallBack.class) //feignclien 这里对应的name是k8s中service的name
public interface ProviderClient {

    @RequestMapping(value = "/providers/hello", method = RequestMethod.GET)
    String hello();

    /**
     * 测试熔断，provider服务没有hello2
     * @return
     */
    @RequestMapping(value = "/providers/hello2", method = RequestMethod.GET)
    String hello2();
}





