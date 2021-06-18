package com.muyer.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_user")
public class SysUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	 /**  */
	private String name;
	 /**  */
	private String password;
}

