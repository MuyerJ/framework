package com.muyer.repository;

import com.muyer.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: 
 * date: 2021/6/14 23:29
 * @author YeJiang
 * @version
 */
@Repository
public interface PostDao extends JpaRepository<Post,Long> {
}
