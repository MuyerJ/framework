package com.muyer.repository;

import com.alibaba.fastjson.JSON;
import com.muyer.JpaApp;
import com.muyer.entity.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Description: 
 * date: 2021/6/14 23:32
 * @author YeJiang
 * @version
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaApp.class})
public class PostTest {

    @Autowired
    private PostDao postDao;

    @Test
    public void addOne() {
        Post post = new Post();
        post.setAuthorId(1L);
        post.setHtmlContent("this is a htmlContent!");
        post.setLastReplyTime(new Date());
        postDao.save(post);
    }

    @Test
    public void query() {
        System.out.println(postDao.count());
        System.out.println(JSON.toJSONString(postDao.findOne(8L)));
        List<Post> posts = postDao.findAll();
        for (Post p : posts) {
            System.out.println(JSON.toJSONString(p));
            Date lastReplyTime = p.getLastReplyTime();
            System.out.println(lastReplyTime);
        }
    }

    @Test
    public void queryByPage() {
        postDao.findAll(new PageRequest(0,2));
        postDao.findAll(new PageRequest(1,2));
        postDao.findAll(new PageRequest(2,2));

        System.out.println(1);
    }
}
