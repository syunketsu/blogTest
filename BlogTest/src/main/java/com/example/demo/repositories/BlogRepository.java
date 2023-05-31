package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Blog;

import jakarta.transaction.Transactional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> { 
	//ブログの内容を保存
	Blog save(Blog blog);
	
	//ブログテーブルのuser_idとアカウントテーブルのuserIdを使ってテーブルを結合させてuserIdで検索をかけてデータを取得
//	@Query(value="SELECT b.blog_id,b.blog_title,b.create_time,b.blog_abstract,b.blog_message,b.nick_name,b.category,b.user_id From blog b INNER JOIN account a ON b.user_id = a.user_id WHERE b.user_id=?1",nativeQuery = true)
	List<Blog> findByUserIdOrderByCreateTimeDesc(Integer userId);

	//blogIdを使用してDBに検索をかける
	Blog findByBlogId(Integer blogId);
	
	//ブログテーブルのすべての情報を取得
	List<Blog> findAllByOrderByCreateTimeDesc();
	
	//カテゴリー名を使用して、DBに検索をかける
//	List<Blog>findByCategoryName(String categoryName);

	//blogIdを取得して該当するブログ情報を削除する
	@Transactional
	List<Blog> deleteByBlogId(Integer blogId);
}
