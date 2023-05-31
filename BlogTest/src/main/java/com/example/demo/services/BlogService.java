package com.example.demo.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Blog;
import com.example.demo.repositories.BlogRepository;


@Service
public class BlogService {
	@Autowired
	BlogRepository blogRepository;

	//内容を保存
	public void insert(String blogTitle, String blogAbstract, String blogMessage, String nickName, String category, Integer userId) {
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		blogRepository.save(new Blog(blogTitle, createTime, blogAbstract, blogMessage, nickName, category, userId));
	}
	
	//ユーザーブログ一覧
	public List<Blog> selectByUserId(Integer userId){
		return blogRepository.findByUserIdOrderByCreateTimeDesc(userId);
	}

	//あるブログ詳細
	public Blog selectByBlogId(Integer blogId){
		return blogRepository.findByBlogId(blogId);
	}
	
	//内容を更新
	public void update(Integer blogId, String blogTitle, String blogAbstract, String blogMessage, String nickName, String category, Integer userId) {
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		blogRepository.save(new Blog(blogId, blogTitle, createTime, blogAbstract, blogMessage, nickName, category, userId));
	}

	//ブログ一覧
	public List<Blog> selectByAll(){
		return blogRepository.findAllByOrderByCreateTimeDesc();
	}

	//カテゴリー一事の内容
//	public List<Blog> selectByCategoryName(String categoryName){
//		return blogRepository.findByCategoryName(categoryName);
//	}
	
	//削除
	public List<Blog> deleteBlog(Integer blogId){
		return blogRepository.deleteByBlogId(blogId);
	}
}
