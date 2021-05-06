package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.HelloService;
import com.exmple.demo.models.Employee;

@Controller
//DIで利用できるようになる
public class HelloController {
	@Autowired
	private HelloService helloService;
	
	@GetMapping("/hello")
	//GETメソッドを処理
	public String getHello() {
		//htmlに推移
		return "hello";
	}
	
	@PostMapping("/hello")
	//postメソッドの処理
	public String postRequest(@RequestParam("text1")String str,Model model) {
		//@RequestParam 画面入力内容を受け取ることできる name属性の値を指定
		model.addAttribute("Sample",str);
		//htmlから指定したキーの値を受け取れる
		return "helloResponse";
	}

	@PostMapping("/hello/db")
	public String postDbRequest(@RequestParam("text2")String str,Model model) {
		//Stringからint型に変更
		int id = Integer.parseInt(str);
		
		Employee employee = helloService.findOne(id);
		
		model.addAttribute("id",employee.getEmployeeId());
		model.addAttribute("name",employee.getEmployeeName());
		model.addAttribute("age",employee.getAge());
		
		return "helloRrsponseDB";
	}
}
