package com.example.demo.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Service.UserService;
import com.exmple.demo.models.GroupOrder;
import com.exmple.demo.models.SignupForm;
import com.exmple.demo.models.User;

@Controller
public class SignupController {
	@Autowired
	private UserService userService;
	
	//ラジオボタンの実装
	private Map<String, String>radioMarriage;
	//ラジオボタンの初期化メソッド
	private Map<String, String>initRadioMarrige(){
		Map<String, String> radio = new LinkedHashMap<>();
		//putメソッドでMapに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		
		return radio;
	}
	
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form,Model model) {
		//ラジオボタンの初期化メソッドの呼び出し
		radioMarriage = initRadioMarrige();
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage",radioMarriage);
		
		return "login/signup";
	}
	//ユーザー登録画面のPOST用コントローラー
	@PostMapping("/signup")
	//@ModelAttribute 自動でModelクラスに登録してくれる(addAttribute)
	//BindingResult  データバインド結果を受け取る
	//@Validated バリデーション実施
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form,BindingResult bindingResult,Model model) {
		//hasErrorsでデータバインドに失敗しているかわかる
		if (bindingResult.hasErrors()) {
			//GETリクエスト用のメソッド呼び出し、ユーザー登録画面に戻る
			return getSignUp(form, model);
		}
		
		System.out.println(form);
		//insert用変数
		User user = new User();
		//userオブジェクトの中にセット
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		user.setRole("ROLE_GENERAL");
		//ユーザー登録処理
		//userServiceのinsertメソッドへ
		boolean result = userService.insert(user);
		//ユーザー登録結果の判定
		if(result == true) {
			System.out.println("insert成功");
		}else {
			System.out.println("insert失敗");
		}
		
		return "redirect:/login";
	}
	@ExceptionHandler(DataAccessException.class)
	//Exception毎の例外処理を実装できる
	//例外クラスを指定することで例外毎処理を実行できる
	public String dataAccessExceptionHandler(DataAccessException e,Model model) {
		//例外クラスのメッセージをModelに登録
		model.addAttribute("error","内部サーバーエラー(DB):ExceptionHandeler");
		//例外クラスのメッセージをmodelに登録
		model.addAttribute("message","SignupControllerでDataAccessExceptionが発生しました");
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e,Model model) {
		//例外クラスのメッセージをModelに登録
		model.addAttribute("error","内部サーバーエラー(DB):ExceptionHandeler");
		//例外クラスのメッセージをmodelに登録
		model.addAttribute("message","SignupControllerでDataAccessExceptionが発生しました");
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
			
		return "error";
	}
}
