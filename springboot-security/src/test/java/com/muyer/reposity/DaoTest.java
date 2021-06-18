package com.muyer.reposity;

import com.muyer.Application;
import com.muyer.repository.SysRoleDao;
import com.muyer.repository.SysUserDao;
import com.muyer.repository.SysUserRoleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description: 
 * date: 2021/6/19 0:20
 * @author YeJiang
 * @version
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class DaoTest {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysUserDao sysUserDao;


    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Test
    public void test(){
        sysRoleDao.findAll();
        sysUserDao.findAll();
        sysUserRoleDao.findAll();

        System.out.println(1);
    }
}
