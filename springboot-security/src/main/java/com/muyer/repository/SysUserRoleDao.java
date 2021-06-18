package com.muyer.repository;

import com.muyer.entity.SysUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRoleDao extends JpaRepository<SysUserRoleEntity,Long> {
}

