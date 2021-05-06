package com.example.demo.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Service.UserService;
import com.exmple.demo.models.SignupForm;
import com.exmple.demo.models.User;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	//結婚ステータスのラジオボタン用変数
	private Map<String,String>radioMarriage;
	
	private Map<String,String>initRadioMarrige(){
		Map<String,String>radio = new LinkedHashMap<>();
		//既婚、未婚をMapに格納
		radio.put("既婚", "true");
		radio.put("未婚","false");
		
		return radio;
	}
	
	@GetMapping("/home")
	public String getHome(Model model) {
		//ホーム画面表示するための文字列を登録
		model.addAttribute("contents","login/home::home_contents");
		
		return "login/homeLayout";
	}
	
	//ユーザー一覧画面のGET用メソッド
	@GetMapping("/userList")
	public String getUserList(Model model) {
		//ユーザー一覧を表示するための文字列を登録
		model.addAttribute("contents","login/userList::userList_contents");
		//ユーザー一覧の生成
		List<User>userList = userService.selectMany();
		//Modelにユーザーリストを登録
		model.addAttribute("userList",userList);
		//データ件数を取得
		int count = userService.count();
		model.addAttribute("userListCount",count);
		
		return "login/homeLayout";
	}
	//{id:.+}メールアドレス受け取れる
	@GetMapping("/userDetail/{id:.+}")
	//@PathVariable 渡されたパス(URL)の値を引数の変数に入れられる
	public String getUserDetail(@ModelAttribute SignupForm form,Model model,@PathVariable("id")String userId ) {
		System.out.println("userId="+userId);
		//コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents","login/userDetail::userDetail_contents");
		//結婚ステータス用ラジオボタンの初期化
		radioMarriage =	initRadioMarrige();
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage",radioMarriage);
		//ユーザーIDの確認
		if (userId != null && userId.length() > 0) {
			//ユーザー情報を取得
			User user = userService.selectOne(userId);
			//Userクラスをフォームクラスに変換
			form.setUserId(user.getUserId());
			form.setPassword(user.getPassword());
			form.setUserName(user.getUserName());
			form.setBirthday(user.getBirthday());
			form.setAge(user.getAge());
			form.setMarriage(user.isMarriage());
			//Modelに登録
			model.addAttribute("signupForm",form);
		}
		return "login/homeLayout";
	}
	
	//ユーザー更新用処理
	@PostMapping(value = "/userDetail",params = "update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form,Model model) {
		System.out.println("更新ボタンの処理");
		
		User user = new User();
		
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		//更新実行
		try {
			boolean result = userService.updateOne(user);
			
			if (result == true) {
				model.addAttribute("result","更新成功");
			} else {
				model.addAttribute("result","更新失敗");
			}
		}catch(DataAccessException e){
			model.addAttribute("result","更新失敗(トランザクションテスト)");
		}
		
		
		return getUserList(model);
	}
	//ユーザー削除用処理
	@PostMapping(value = "/userDetail",params = "delete")
	public String postUserDelete(@ModelAttribute SignupForm form,Model model) {
		System.out.println("削除ボタンの処理");
		//削除実行
		boolean result =userService.deleteOne(form.getUserId());
		
		if (result == true) {
			model.addAttribute("result","削除成功");	
		}else {
			model.addAttribute("result","削除失敗");
		}
		return getUserList(model);
	}
	
	@PostMapping("/logout")
	public String postLogout() {
		return "redirect:/login";
	}
	
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]>getUserListCsv(Model model) {
		//ユーザーを全件取得して、CSVをサーバーに保存する
		userService.userCsvOut();
		
		byte[]bytes = null;
		
		try {//サーバーに保存されているsample.csvファイルをbyteで取得
			bytes = userService.getFile("sample.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//HTTPヘッダーの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type","text/csv; charset=UTF-8");
		//ファイル名を設定
		header.setContentDispositionFormData("filename","sample.csv");
		
		
		return new ResponseEntity<>(bytes,header,HttpStatus.OK);
	}
	//アドミン権限専用画面のGET用メソッド
	@GetMapping("/admin")
	public String getAdmin(Model model) {
		//コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents","login/admin::admin_contents");
		//レイアウト用テンプレート
		return "login/homeLayout";
				
	}
}
