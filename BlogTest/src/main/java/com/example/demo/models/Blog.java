package com.example.demo.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="blogs")
public class Blog {
	public Blog(String blogTitle, String blogAbstract, String blogMessage, Integer scanCount, Integer likeCount) {
		this.blogTitle = blogTitle;
		this.blogAbstract = blogAbstract;
		this.blogMessage = blogMessage;
		this.scanCount = scanCount;
		this.likeCount = likeCount;
	}

	@Id
	@Column(name="blog_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;
	
	private Timestamp publishTime;

	@NonNull
	@Column(name="blog_title",columnDefinition="varchar(200)")
	private String blogTitle;

	@NonNull
	@Column(name="blog_abstract",columnDefinition="text")
	private String blogAbstract;
	
	@NonNull
	@Column(name="blog_message",columnDefinition="text")
	private String blogMessage;
	
	private Integer scanCount;
	
	private Integer likeCount;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Account account;
}
