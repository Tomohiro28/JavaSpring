package com.example.demo.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.exmple.demo.models.User;

public class UserRowMapper implements RowMapper<User>{
	@Override //mapRowメソッドをオーバーライド
	//ResultSet select結果が入っている　その値をUserクラスにセット
	public User mapRow(ResultSet rs,int rowNum)throws SQLException{
		//戻り値のUserインスタンスを生成
		User user = new User();
		//Resultsetの取得結果をUserインスタンスにセット
		user.setUserId(rs.getString("user_id"));  
		user.setPassword(rs.getString("password"));
		user.setUserName(rs.getString("user_name"));
		user.setBirthday(rs.getDate("birthday"));
		user.setAge(rs.getInt("age"));
		user.setMarriage(rs.getBoolean("marriage"));
		user.setRole(rs.getString("role"));
		
		return user;
	}
}
