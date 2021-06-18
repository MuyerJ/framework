package com.muyer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_user_role")
public class SysUserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**  */
    private Long userId;
    /**  */
    private Long roleId;
}

