package com.muyer.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: 
 * date: 2021/6/18 23:04
 * @author YeJiang
 * @version
 */
@Data
public class Role implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}
