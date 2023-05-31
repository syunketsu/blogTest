package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Account;

@Repository //告诉Spring这是一个永久层的接口，提供一些和数据库操作有关的方法<表里数据的类型，数据ID的类型>
public interface AccountRepository extends JpaRepository<Account,Integer>{
	Account findByUserName(String userName); //接受从service传来的一个用户名，返回对应的用户信息，相当于SELECT *FROM account WHERE userName=[传入的参数]
	
	Account save(Account account); //向数据库插入一个从service传来的新的用户信息
	
	List<Account> findAll(); // ユーザ情報一覧を取得
}

