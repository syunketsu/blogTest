package com.example.demo.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Account;
import com.example.demo.models.Blog;
import com.example.demo.repositories.BlogRepository;
import com.example.demo.services.AccountService;
import com.example.demo.services.BlogService;

@Controller
@RequestMapping("/user/blog")
public class AdminBlogController {
	/*
	 * accountテーブルを操作するための
	 * Serviceクラス
	 */
	@Autowired
	private AccountService accountService;
	
	/*
	 * blogテーブルを操作するための
	 * Serviceクラス
	 */
	@Autowired
	BlogService blogService;
	
	/*
	 * categoryテーブルを操作するための
	 * Serviceクラス
	 */
//	@Autowired
//	CategoryService categoryServie;

	/*
	 * 管理者側の処理
	 */

	/*
	 * 現在ログインしている人が記載した記事の一覧を出すための処理です。
	 * ―ログインしている人のメールアドレスを使用して、ログインしている人のuserIdとuserNameを取得します。
	 * ―取得したuserIdを使用してログインしている人のブログ記事を取得します。
	 * ―取得した情報をセットして画面から参照可能にします。
	 */
	//管理者側のブログ一覧を表示	
	@GetMapping("/all")
	public String getPersonalPage(Model model) {
		//現在のリクエストに紐づく Authentication を取得するには SecurityContextHolder.getContext().getAuthentication() とする。
		//SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している。
		//Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthority のコレクション）を取得できる。
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//ログインした人のユーザ名を取得
		String userName = auth.getName();
		
		//accountテーブルの中から、ユーザーのNameで検索をかけて該当するユーザーのID情報を引っ張り出す。
		Account account = accountService.selectById(userName);

		//accountテーブルの中からログインしているユーザーの名前の取得
		String username = account.getUserName();

		//accountテーブルの中からログインしているユーザーのIDを取得
		Integer userid = account.getUserId();

		//ブログテーブルの中からユーザーIDを使って、そのユーザーが書いたブログ記事のみを取得する
		List<Blog> blogList = blogService.selectByUserId(userid);
		//blogList（ブログの記事一覧情報）とuserName（管理者の名前）をmodelにセットし
		//admin_blog_all.htmlから参照可能にする。
		model.addAttribute("blogList",blogList);
		model.addAttribute("userName",username);
		return "personalpage.html";
	}


	/*
	 * ブログ記事の登録画面を表示させるための処理です。
	 * ―ログインしている人のメールアドレスを使用して、ログインしている人のuserIdとuserNameを取得します。
	 * ―カテゴリ一覧を取得します
	 * ―取得した情報をセットして画面から参照可能にします。
	 */
	//ブログ記事の登録
	@GetMapping("/publish")
	public String getBlogCreatePage(Model model) {
		//		現在のリクエストに紐づく Authentication を取得するには SecurityContextHolder.getContext().getAuthentication() とする。
		//		SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している。
		//		Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthority のコレクション）を取得できる。
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//ログインした人のメールアドレスを取得
		String userName = auth.getName();
		
		//accountテーブルの中から、ユーザーのEmailで検索をかけて該当するユーザーの情報を引っ張り出す。
		Account account = accountService.selectById(userName);
		
		//accountテーブルの中からログインしているユーザーの名前の取得
		String username = account.getUserName();
		
		//accountテーブルの中からログインしているユーザーのIDを取得
		Integer userid = account.getUserId();
		
		//カテゴリー一覧を取得
//		List<CategoryEntity>categoryList = categoryServie.findByAll();

		//userId（管理者のId）、categoryList（カテゴリ一覧）
		//userName（管理者の名前）をmodelにセットし
		//admin_blog_register.htmlから参照可能にする。
		model.addAttribute("userId",userid);
//		model.addAttribute("categoryList",categoryList);
		model.addAttribute("userName",username);

		return "publish.html";
	}

	/**
	 * ブログ記事を登録させるための処理です。
	 * ―画像名を取得し、blog-imageにアップロードする作業を行います。
	 * ―入力された情報によってblogテーブルに保存処理を行います。
	 * ―保存処理後は、ブログ一覧画面にリダイレクトさせます。
	 **/
	//登録内容を保存
	@PostMapping("/publish")
	public String publish(@RequestParam String blogTitle,@RequestParam String blogAbstract,@RequestParam String blogMessage,@RequestParam String nickName,@RequestParam String category,@RequestParam Integer userId) {

		//画像ファイル名を取得する
//		String fileName = blogImage.getOriginalFilename();

//		//ファイルのアップロード処理
//		try {
//			//画像ファイルの保存先を指定する。
//			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
//			//画像ファイルからバイナリデータを取得する
//			byte[] bytes = blogImage.getBytes();
//			//画像を保存（書き出し）するためのバッファを用意する
//			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
//			//画像ファイルの書き出しする。
//			out.write(bytes);
//			//バッファを閉じることにより、書き出しを正常終了させる。
//			out.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		//ファイルのアップロード処理後に、サービスクラスのメソッドに値を渡して保存する
		blogService.insert(blogTitle, blogAbstract, blogMessage, nickName, category, userId);

		return "redirect:/user/blog/all";
	}

	/**
	 * ブログ記事の編集画面を表示させるための処理です。
	 * ―リンクからblogIdを取得する
	 * ―blogIdに紐づくレコードを探す
	 * ―取得した情報をセットして画面から参照可能にします。
	 */
	//リンクタグで記載したblogIdを取得する
	@GetMapping("/edit/{blogId}")
	public String getBlogDetailPage(@PathVariable Integer blogId, Model model) {
		//		現在のリクエストに紐づく Authentication を取得するには SecurityContextHolder.getContext().getAuthentication() とする。
		//		SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している。
		//		Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthority のコレクション）を取得できる。
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//ログインした人のメールアドレスを取得
		String userName = auth.getName();
		
		//accountテーブルの中から、ユーザーのEmailで検索をかけて該当するユーザーの情報を引っ張り出す。
		Account account = accountService.selectById(userName);
		
		//accountテーブルの中から、ユーザー名を取得
		String username = account.getUserName();
		
		//accountテーブルの中から、ユーザーIDを取得
		Integer userid = account.getUserId();
		
		//カテゴリ―の一覧を取得
//		List<CategoryEntity>categoryList = categoryServie.findByAll();
		
		//blogのテーブルの中から、blogIdで検索をかけて該当する該当するブログの情報を引っ張り出す。
		Blog blog = blogService.selectByBlogId(blogId);

		//userId（管理者のId）、categoryList（カテゴリ一覧）
		//userName（管理者の名前）、blog(idに紐づいたブログ記事）をmodelにセットし
		//admin_blog_edit.htmlから参照可能にする。
		model.addAttribute("userId",userid);
		model.addAttribute("blog",blog);	
//		model.addAttribute("categoryList",categoryList);
		model.addAttribute("userName",username);
		return "edit.html";
	}
	
	/**
	 * ブログ記事を更新させるための処理です。
	 * ―画像名を取得し、blog-imageにアップロードする作業を行います。
	 * ―入力された情報によってblogテーブルに更新処理を行います。
	 * ―更新処理後は、ブログ一覧画面にリダイレクトさせます。
	 */
	//登録内容を修正（更新）
	@PostMapping("/update")
	public String updateData(@RequestParam Integer blogId,@RequestParam String blogTitle,@RequestParam String blogAbstract,@RequestParam String blogMessage,@RequestParam String nickName,@RequestParam String category,@RequestParam Integer userId) {
//		//画像ファイル名を取得する
//		String fileName = blogImage.getOriginalFilename();
//		//ファイルのアップロード処理
//		try {
//			//画像ファイルの保存先を指定する。
//			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
//			//画像ファイルからバイナリデータを取得する。
//			byte[] bytes = blogImage.getBytes();
//			//画像を保存（書き出し）するためのバッファを用意する。
//			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
//			//画像ファイルの書き出しする。
//			out.write(bytes);
//			//バッファを閉じることにより、書き出しを正常終了させる。
//			out.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		//ファイルのアップロード処理後に、サービスクラスのメソッドに値を渡して更新を行う。
		blogService.update(blogId, blogTitle, blogAbstract, blogMessage, nickName, category, userId);

		return "redirect:/user/blog/all";
	}

	/**
	 * ブログ記事を削除させるための処理です。
	 * ―blogIdに紐づくレコードを探す
	 * ―紐づくレコードを削除する
	 */
	//ブログの内容を削除
	@PostMapping("/delete")
	public String blogDelete(@RequestParam Integer blogId) {
		//Serviceクラスに値をわたし、削除処理を行う。
		blogService.deleteBlog(blogId);
		return "redirect:/user/blog/all";
	}
}


