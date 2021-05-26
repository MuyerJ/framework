package com.muyer.myrpc.model;

import java.io.Serializable;

/**
 * Description: 
 * date: 2021/5/26 0:18
 * @author YeJiang
 * @version
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -7856628607083347105L;
    private String className;
    private String methondName;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public String getMethondName() {
        return methondName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethondName(String methondName) {
        this.methondName = methondName;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
