package com.muyer.repository;

import com.muyer.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: 
 * date: 2021/6/21 23:51
 * @author YeJiang
 * @version
 */
@Repository
public interface CityDao extends JpaRepository<City, String> {
}

