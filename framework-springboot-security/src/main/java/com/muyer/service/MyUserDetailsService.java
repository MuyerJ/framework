package com.muyer.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Description: 
 * date: 2021/6/19 15:35
 * @author YeJiang
 * @version
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if("yejiang".equals(s)||"sunxu".equals(s)){
//            return User.withUsername(s).password(BCrypt.hashpw("123456", BCrypt.gensalt())).authorities("r1").build();
            return User.withUsername(s).password("123456").authorities("r1").build(); //Encoded password does not look like BCrypt
        }

        return null;
    }
}
