package com.muyer.repository;

import com.muyer.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: 
 * date: 2021/6/27 12:35
 * @author YeJiang
 * @version
 */
@Repository
public interface MenuDao extends JpaRepository<Menu, Long> {
}
