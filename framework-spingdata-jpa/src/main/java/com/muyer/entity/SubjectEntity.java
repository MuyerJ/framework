package com.muyer.entity;

import java.util.Date;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "t_subject")
public class SubjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Integer id;
	 /** 学科名称 */
	private String name;
	 /** 创建时间 */
	private Date createTime;
	 /** 更新时间 */
	private Date updateTime;
	 /** 题目数量 */
	private Integer questionNum;
	 /** 图片url */
	private String imgUrl;
	 /** 课程状态,0表示正常,1表示弃用 */
	private Integer state;
}

