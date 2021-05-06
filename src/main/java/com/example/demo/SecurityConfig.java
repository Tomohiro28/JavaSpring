package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
//セキュリティ設定用クラス
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//WebSecurityConfigurerAdapterを継承することでメソッドをオーバーライドできる
	@Bean
	//パスワード暗号化、復号する(暗号を解く)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
				
	}
	
	@Autowired
	//データを格納するための機構
	private DataSource dataSource;
	//ユーザーIDとパスワードを取得するSQL文
	//クラスで定数を作成
	private static final String USER_SQL = "SELECT"
		+ " user_id,"
		+ " password,"
		+ " true"
		+ " FROM"
		+ " m_user"
		+ " WHERE"
		+ " user_id = ?";
	//ユーザーのロールを取得するSQL文
	private static final String ROLE_SQL = "SELECT"
			+ " user_id,"
			+ " role"
			+ " FROM"
			+ " m_user"
			+ " WHERE"
			+ " user_id = ?";
		
		
	
	
	@Override
	public void configure(WebSecurity wed)throws Exception{
		//静的リソースへのアクセスにはセキュリティを適応しない
		wed.ignoring().antMatchers("/wedjars/**","/css/**");
	}
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		//ログイン不要ページの設定
		http
        	.authorizeRequests()
            	.antMatchers("/webjars/**").permitAll() //webjarsへアクセス許可
            	.antMatchers("/css/**").permitAll() //cssへアクセス許可
            	.antMatchers("/login").permitAll() //ログインページは直リンクOK
            	.antMatchers("/signup").permitAll() //ユーザー登録画面は直リンクOK
            	.antMatchers("/rest/**").permitAll()
            	.antMatchers("/admin").hasAuthority("ROLE_ADMIN") //アドミンユーザーに許可
            	.anyRequest().authenticated(); //それ以外は直リンク禁止
		
		http
			.formLogin()
				.loginProcessingUrl("/login")//ログイン処理のパス
				.loginPage("/login")//ログインページの指定
				.failureUrl("/login")//ログイン失敗の推移先
				.usernameParameter("userId")//ログインページのユーザーID
				.passwordParameter("password")//ログインページのパスワード
				.defaultSuccessUrl("/home",true);//ログイン成功後の推移先
				
		//ログアウト処理
		http
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//HTTP メソッドに一致する特定のパターンを持つマッチャーを作成
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");
				
		
		//CSRFを無効にするURLを設定
		RequestMatcher csfMatcher = new RestMatcher("/rest/**");
		
//		//RESTのみCSRF対策を無効に設定
		http.csrf().requireCsrfProtectionMatcher(csfMatcher);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		//ログイン処理時のユーザー情報をDBから取得
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			//認証処理
			.usersByUsernameQuery(USER_SQL)
			.authoritiesByUsernameQuery(ROLE_SQL)
			//パスワード復号
			.passwordEncoder(passwordEncoder());
		
	}
	
}
