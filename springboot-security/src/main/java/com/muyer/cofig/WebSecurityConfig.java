package com.muyer.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Description: 
 * date: 2021/6/16 23:52
 * @author YeJiang
 * @version
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 定义用户信息服务
     * 自定义用户信息服务 com.muyer.service.MyUserDetailsService
     */
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
////        userDetailsManager.createUser(User.withUsername("yejiang").password("$2a$10$0gq7KfdoDCDh6ofUI3xE.uCRhKlciElzfRRPXqpSkvsDrUgd4dFmi").authorities("r1").build());
//        userDetailsManager.createUser(User.withUsername("sunxu").password("123456").authorities("r2").build());
//        userDetailsManager.createUser(User.withUsername("sunxu2").password("123456").authorities("r1", "r2").build());
//        return userDetailsManager;
//    }


    /**
     * 密码编辑器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }



    /**
     * 安全拦截器
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //配置
                .antMatchers("/r/r1").hasAuthority("r1")
                .antMatchers("/r/r2").hasAuthority("r2")
                .antMatchers("/r/**").authenticated()
                .anyRequest().permitAll();

        /**
         * 自定义登录页面
         *
         * 表单的用户名name和password输入框的name=""要和security里面的验证的对应:name="username";name="password",否则无法识别
         * action="/authentication/form"要与.loginProcessingUrl("/authentication/form")相对应
         * 原因 ==>
         *  由于security是由UsernamePasswordAuthenticationFilter这个类定义登录的,里面默认是/login路径,
         *  我们要让他用我们的/authentication/form路径,就需要配置.loginProcessingUrl("/authentication/form")
         */
        //表单
        http.formLogin()
                //登录页面
                .loginPage("/login-view")
                //指定登录form请求路径
                .loginProcessingUrl("/login")
                .successForwardUrl("/login-success")
                .failureUrl("/login-fail")
                //必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll();

        //退出
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login-view?logout");


        //解决跨域
        http.csrf().disable();
    }
}
