package com.muyer.reposity;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Description: 
 * date: 2021/6/19 15:16
 * @author YeJiang
 * @version
 */
public class Test {
    public static void main(String[] args) {
        String hashpw = BCrypt.hashpw("123456", BCrypt.gensalt());
        //$2a$10$0gq7KfdoDCDh6ofUI3xE.uCRhKlciElzfRRPXqpSkvsDrUgd4dFmi
        System.out.println(hashpw);
    }
}
