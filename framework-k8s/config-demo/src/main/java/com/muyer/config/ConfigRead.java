package com.muyer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *  设置 ConfigRead 类用于测试读取 Kubernetes 中 ConfigMap 中的配置
 * date: 2021/8/30 14:27
 * @author YeJiang
 * @version
 */
@Configuration
@ConfigurationProperties(prefix = "config")
public class ConfigRead {
    private String stringValue;
    private Integer numberValue;
    private boolean booleanValue;

    public String getStringValue() {
        return stringValue;
    }
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
    public Integer getNumberValue() {
        return numberValue;
    }
    public void setNumberValue(Integer numberValue) {
        this.numberValue = numberValue;
    }
    public boolean isBooleanValue() {
        return booleanValue;
    }
    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
}
