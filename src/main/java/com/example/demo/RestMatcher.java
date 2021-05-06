package com.example.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

//リクエストのパターン毎にセキュリティヘッダの出力を制御
public class RestMatcher implements RequestMatcher{
	//HTTP メソッドに一致する特定のパターンを持つマッチャーを作成
	private AntPathRequestMatcher matcher;
	//コンストラクタ
	public RestMatcher(String url) {
		super();//一世代上の親クラスのコンストラクタ
		matcher = new AntPathRequestMatcher(url);
	}
	//URLのマッチ条件
	@Override
	public boolean matches(HttpServletRequest request) {
		//GETメソッドならCSRFのチェックはしない
		if("GET".equals(request.getMethod())) {
			return false;
		}
		//特定のURLに該当する場合CSRFチェックしない
		if (matcher.matches(request)) {
			return false;
		}
		return true;	
	}
	
}
