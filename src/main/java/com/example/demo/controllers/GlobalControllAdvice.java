package com.example.demo.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//アプリ全体で発生した例外処理を実装できる
@Component
public class GlobalControllAdvice {
	@ExceptionHandler(DataAccessException.class)
	//処理する例外クラスを指定
	public String dataAccessExceptionHandler(DataAccessException e,Model model) {
		//例外クラスのメッセージをModelに登録
		model.addAttribute("error","内部サーバーエラー(DB):GlobalControllAdvice");
		//例外クラスのメッセージをmodelに登録
		model.addAttribute("message","DataAccessExceptionが発生しました");
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e,Model model) {
		//例外クラスのメッセージをModelに登録
		model.addAttribute("error","内部サーバーエラー(DB):GlobalControllAdvice");
		//例外クラスのメッセージをmodelに登録
		model.addAttribute("message","Exceptionが発生しました");
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
			
		return "error";
	}

}
