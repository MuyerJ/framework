package com.muyer.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_role")
public class SysRoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	 /**  */
	private String name;
}

