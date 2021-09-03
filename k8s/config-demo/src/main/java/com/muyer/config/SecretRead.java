package com.muyer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *  设置 SecretRead 类用于测试读取 Kubernetes 中 Secret 中的配置
 * date: 2021/8/30 14:28
 * @author YeJiang
 * @version
 */
@Configuration
@ConfigurationProperties(prefix = "secret")
public class SecretRead {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
