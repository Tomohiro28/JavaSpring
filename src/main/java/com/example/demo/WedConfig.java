package com.example.demo;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration //Configuration(構成)クラスであることを表す
public class WedConfig {

	@Bean //DIコンテナに登録(DI=オブジェク注入)
	public MessageSource messageSource() {
		//ReloadableResourceBundleMessageSource プロパティファイルを読み取ってメッセージを取得
		ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
		//messages.propertiesファイルがセットされる
		bean.setBasename("classpath:messages");
		//メッセージプロパティの文字コードを指定
		bean.setDefaultEncoding("UTF-8");
		
		return bean;		
	}
	
	@Bean
	//LocalValidatorFactoryBean messageSourceを任意のものにする
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		
		localValidatorFactoryBean.setValidationMessageSource(messageSource());
		
		return localValidatorFactoryBean;
	}
}
