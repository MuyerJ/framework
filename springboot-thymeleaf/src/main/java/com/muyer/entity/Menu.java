package com.muyer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 
 * date: 2021/6/27 12:33
 * @author YeJiang
 * @version
 */
@Data
@Entity
@Table(name = "sys_menu")
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pid;
    private String pids;
    private String title;
    private String url;
    private String perms;
    private String icon;
    private Byte type;
    private Integer sort;
    private String remark;
    private Date createDate;
    private Date updateDate;
    private int createBy;
    private int updateBy;
    private Byte status = 1;

    @Transient
    @JsonIgnore
    private Map<Long, Menu> children = new HashMap<>();

}
