package com.muyer.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Description: 
 * date: 2021/6/21 23:48
 * @author YeJiang
 * @version
 */
@Entity
@Table(name = "t_city")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;
    private String province;
    private String city;
    private int level;
}

