package com.example.demo.models;


import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="blog")
public class Blog {
	public Blog(String blogTitle, Timestamp createTime, String blogAbstract, String blogMessage, String nickName, String category, Integer userId) {
		this.blogTitle = blogTitle;
		this.createTime = createTime;
		this.blogAbstract = blogAbstract;
		this.blogMessage = blogMessage;
//		this.scanCount = scanCount;
		this.nickName = nickName;
		this.category = category;
		this.userId = userId;
	}

	@Id
	@Column(name="blog_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;

	@NonNull
	@Column(name="blog_title")
	private String blogTitle;
	
    @Column(name="create_time")
    private Timestamp createTime;

	@NonNull
	@Column(name="blog_abstract")
	private String blogAbstract;
	
	@NonNull
	@Column(name="blog_message")
	private String blogMessage;
	
//	private Integer scanCount;
	
//	@ManyToOne
//	@JoinColumn(name="user_id")
//	private Account account;
	
	@NonNull
	@Column(name = "nick_name")
	private String nickName;
	
	@NonNull
	@Column(name="category")
	private String category;
	
	@NonNull
	@Column(name="user_id")
	private Integer userId;
}
