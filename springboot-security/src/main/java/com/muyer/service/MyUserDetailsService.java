package com.muyer.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.repository.SysRoleDao;
import com.muyer.repository.SysUserDao;
import com.muyer.repository.SysUserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description: 
 * date: 2021/6/18 23:34
 * @author YeJiang
 * @version
 */

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysUserDao sysUserDao;


    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = Lists.newArrayList();

        return null;
    }
}
