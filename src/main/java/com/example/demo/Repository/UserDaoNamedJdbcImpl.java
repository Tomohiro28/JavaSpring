package com.example.demo.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.exmple.demo.models.User;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao{
	@Autowired
	//SQLのプレースホルダに「?」ではなく、任意の名前を使うことができる
	private NamedParameterJdbcTemplate jdbc;
	@Override
	public int count() {
		//SQL文
		String sql = "SELECT COUNT(*)FROM m_user";
		//パラメーター生成
		SqlParameterSource params = new MapSqlParameterSource();
		//全件取得してカウント
		return jdbc.queryForObject(sql,params,Integer.class);				
	}
	//Userテーブルにデータを1件インサート
	@Override
	public int insertOne(User user) {
		//SQL文
		String sql = "INSERT INTO m_user(user_id,"
  				+ " password,"
  				+ " user_name,"
  				+ " birthday,"
  				+ " age,"
  				+ " marriage,"
  				+ " role)"
  				+ " VALUES(:userId,"
  				+ " :password,"
  				+ " :userName,"
  				+ " :birtday,"
  				+ " :age,"
  				+ " :marriage,"
  				+ ":role)";
		//パラメーター
		SqlParameterSource params = new MapSqlParameterSource()
				//第一引数キー名、第二引数に値をセット(メソッドチェーン)
			.addValue("userId",user.getUserId())
			.addValue("password",user.getPassword())
			.addValue("userName",user.getUserName())
			.addValue("birthday",user.getBirthday())
			.addValue("age",user.getAge())
			.addValue("marriage",user.isMarriage())
			.addValue("role",user.getRole());
		//SQL実行
		return jdbc.update(sql,params);
	}
	//Userテーブルのデータを1件取得
	@Override
	public User selectOne(String userId) {
		//SQL文
		String sql = "SELECT * FROM m_user WHERE user_id = :userId";
		//パラメーター
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("userId",userId);
		//SQL実行
		Map<String,Object>map = jdbc.queryForMap(sql,params);
  		//結果返却用の変数
  		User user = new User();
  		//Userインスタンスにデータセット
  		user.setUserId((String)map.get("user_id"));  
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setBirthday((Date)map.get("birthday"));
		user.setAge((Integer)map.get("age"));
		user.setMarriage((Boolean)map.get("marriage"));
		user.setRole((String)map.get("role"));
		
		return user;
	}
	//userテーブル全データ取得
	@Override
	public List<User>selectMany(){
		//SQL文
		String sql = "SELECT * FROM m_user";
		//パラメーター
		SqlParameterSource params = new MapSqlParameterSource();
		//SQL実行
		List<Map<String,Object>>getList = jdbc.queryForList(sql,params);
		//返却用
		List<User>userList = new ArrayList<>();
		//取得テータ分loop
		for(Map<String,Object>map:getList) {
	  		User user = new User();
	  		//Userインスタンスにデータセット
	  		user.setUserId((String)map.get("user_id"));  
	  		user.setPassword((String)map.get("password"));
	  		user.setUserName((String)map.get("user_name"));
	  		user.setBirthday((Date)map.get("birthday"));
	  		user.setAge((Integer)map.get("age"));
	  		user.setMarriage((Boolean)map.get("marriage"));
	  		user.setRole((String)map.get("role"));
	  		//結果返却用のListに追加
	  		userList.add(user);
		}
		return userList;
	}
	//更新
	@Override
	public int updateOne(User user) {
		String sql = " UPDATE M_USER"
  	  			+ " SET"
  	  			+ " password = :password,"
  	  			+ " user_name = :userName,"
  	  			+ " birthday = :birthday,"
  	  			+ " age = :age,"
  	  			+ " marriage = :marriage"
  	  			+ " WHERE user_id = :userId";
		//パラメーター
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("userId",user.getUserId())
			.addValue("password",user.getPassword())
			.addValue("userName",user.getUserName())
			.addValue("birthday",user.getBirthday())
			.addValue("age",user.getAge())
			.addValue("marriage",user.isMarriage())
			.addValue("role",user.getRole());
		
		return jdbc.update(sql,params);
	}
	@Override
	public int deleteOne(String userId) {
		String sql = "SELECT * FROM m_user WHERE user_id = :userId";
		
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("userId",userId);
		
		int rowNumber = jdbc.update(sql,params);
		
		return rowNumber;
	}
	@Override
	public void userCsvOut() {
		String sql = "SELECT * FROM m_user";
		//ResultSetExtractorの生成
  		UserRowCallbackHandler handler = new UserRowCallbackHandler();
  		//SQLの実行、CSV出力
  		jdbc.query(sql,handler);
		
		
	}
	
}
