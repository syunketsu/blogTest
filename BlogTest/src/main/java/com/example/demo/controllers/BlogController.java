package com.example.demo.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.models.Blog;
import com.example.demo.repositories.BlogRepository;

@Controller
@RequestMapping("blog")
public class BlogController {
	@Autowired
	private BlogRepository blogRepository;
	
	@PutMapping
	@ResponseBody
	public String publishBlog(@RequestBody Blog blog) {
		blog.setPublishTime(new Timestamp(System.currentTimeMillis()));
		blog.setLikeCount(0);
		blog.setScanCount(0);
		blogRepository.save(blog);
		return "1";
	}
}
