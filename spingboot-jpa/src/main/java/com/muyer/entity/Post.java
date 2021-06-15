package com.muyer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: 
 * date: 2021/6/14 17:47
 * @author YeJiang
 * @version
 */
@Entity
@Table(name = "t_post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long authorId;
    private String htmlContent;
    private String textContent;
    private Date createTime;
    private Date updateTime;
    private Date lastReplyTime;
    private int replyNum;
    private String title;

}
