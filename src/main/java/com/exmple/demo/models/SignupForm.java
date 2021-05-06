package com.exmple.demo.models;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupForm {
	//groups属性(classオブジェクトの配列を指定 インターフェイスのみ)
	//groups属性にインターフェイスのクラスを指定してフィールドとグループの紐付け

	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String userId;//ユーザーID
	
	@NotBlank(groups = ValidGroup1.class)
	@Length(min=4,max=100,groups = ValidGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$",groups = ValidGroup3.class)
	private String password;//パスワード
	
	@NotBlank(groups = ValidGroup1.class)
	private String userName;//ユーザー名
	
	//画面から渡された文字列を日付型に変換
	//pattern属性でどのようなフォーマットでデータが渡されてくるか指定
	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday;//誕生日
	
	@Min(value = 20,groups = ValidGroup2.class)
	@Max(value = 100,groups = ValidGroup2.class)
	private int age;//年齢
	
	 //@AssertFalse falseのみ可能
	@AssertFalse(groups = ValidGroup2.class)
	private boolean marriage;//結婚ステータス
}
