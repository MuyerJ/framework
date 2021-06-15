package com.muyer.repository;

import com.alibaba.fastjson.JSONObject;
import com.muyer.JpaApp;
import com.muyer.entity.SubjectEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Description: 
 * date: 2021/6/15 11:32
 * @author YeJiang
 * @version
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaApp.class})
public class SubjectTest {

    @Autowired
    private SubjectDao subjectDao;

    @Test
    public void queryTest() {
        List<SubjectEntity> subjectList = subjectDao.findAll();

        for (SubjectEntity s : subjectList) {
            System.out.println(JSONObject.toJSONString(s));
            System.out.println("---" + s.getName());
        }
    }

}
