package com.muyer.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class XxlJobConfig {
    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses("http://127.0.0.1:8080/xxl-job-admin");
        xxlJobSpringExecutor.setAppname("myJob");
        xxlJobSpringExecutor.setAddress("");
        xxlJobSpringExecutor.setIp("127.0.0.1");
        xxlJobSpringExecutor.setPort(9999);
        xxlJobSpringExecutor.setAccessToken("");
        xxlJobSpringExecutor.setLogPath("/data/applogs/xxl-job/jobhandler");
        xxlJobSpringExecutor.setLogRetentionDays(30);
        return xxlJobSpringExecutor;
    }
}