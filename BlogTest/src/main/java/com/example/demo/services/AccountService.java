package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;

import com.example.demo.WebSecurityConfig;

@Service //告诉spring这是一个服务层的类
public class AccountService {
	@Autowired //让spring自动实现该接口并创建实例
	private AccountRepository accountRepository;
	
	public boolean validateAccount(String userName, String userPassword) { 
		Account account = accountRepository.findByUserName(userName);
		if (account == null || !account.getUserPassword().equals(userPassword)) {
			return false;
		} else {
			return true;
		}
	} //接收两个参数userName和userPassword，判断对应的数据是否已经在数据库中
	
	public boolean createAccount(String userName, String userPassword) {
		//RegisterControllerから渡されるユーザ情報（ユーザ名）を条件にDB検索で検索する
		Account account = accountRepository.findByUserName(userName);
		if (account == null) {
			accountRepository.save(new Account(userName, userPassword));
			WebSecurityConfig.addUser(userName, userPassword);
			return true;
		} else {
			return false;
		}
	} //接收两个参数userName和userPassword，并尝试插入一个新的用户信息，如果用户名已在数据库内，则插入失败，返回false，否则返回true
	
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	} //ユーザの一覧を取得する
	
	
	public Account selectById(String userName) {
		return accountRepository.findByUserName(userName);
	} //在数据库中用用户名来寻找id
}
