package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc//MockMvc を自動構成する
//Mock＝サーバ上にアプリケーションをデプロイしなくてもSpring MVCの動作を再現
public class LoginControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Test
	public void ログイン画面表示()throws Exception{
		//画面表示内容確認
		mockMvc.perform(get("/login"))//getリクエスト送る
			.andExpect(status().isOk())//HTTPリクエストが正常に終了したかチェック
			.andExpect(content().string(containsString("ユーザーID")));//htmlに「ユーザーID」文字が含まれているかチェック
				
	}
	

}
