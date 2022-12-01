package com.muyer.security.service;

import com.google.common.collect.Lists;
import com.muyer.security.model.AuthenticationReq;
import com.muyer.security.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Description: 
 * date: 2021/6/16 0:46
 * @author YeJiang
 * @version
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public static Map<String, User> map = new HashMap<String, User>();

    static {
        map.put("yejiang", new User("1", "yejiang", "123456", "110", "yejiang", Lists.newArrayList("r1")));
        map.put("sunxu", new User("2", "sunxu", "123456", "112", "sunxu", Lists.newArrayList("r2")));
        map.put("sunxu2", new User("3", "sunxu2", "123456", "112", "sunxu", Lists.newArrayList("r1", "r2")));
        map.put("sunxu3", new User("3", "sunxu2", "123456", "112", "sunxu", Lists.newArrayList()));
    }


    @Override
    public User authentication(AuthenticationReq req) {
        //为空校验
        if (Objects.isNull(req) || StringUtils.isEmpty(req.getPassword()) || StringUtils.isEmpty(req.getUsername())) {
            throw new RuntimeException("账户或者密码不存在");
        }
        //查库
        //1）不存在用户报错
        User user = map.get(req.getUsername());
        if (Objects.isNull(user)) {
            throw new RuntimeException("查询不到当前用户");
        }
        //2）存在，但是用户密码错误
        if (!StringUtils.equals(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("输入密码错误");
        }
        //3）成功返回结果
        return user;
    }
}
