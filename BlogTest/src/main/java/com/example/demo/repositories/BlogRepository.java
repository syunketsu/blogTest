package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>{

}
