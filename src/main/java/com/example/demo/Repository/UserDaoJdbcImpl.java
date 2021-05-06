package com.example.demo.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.exmple.demo.models.User;

	@Repository("UserDaoJdbcImpl")//Bean名をセット@Autowiredする時クラス指定できる
	public class UserDaoJdbcImpl implements UserDao{
	@Autowired
  	JdbcTemplate jdbc;
	@Autowired
	PasswordEncoder passwordEncoder;	
  
  	//Userテーブルの件数を取得
  	@Override
  	public int count()throws DataAccessException{
	  //全件取得してカウント
  		int count = jdbc.queryForObject("SELECT COUNT(*)FROM m_user",Integer.class);
  		return count; 	
  	}
  	//Userテーブルにデータを1件挿入
  	@Override
	public int insertOne(User user)throws DataAccessException{
  		 //パスワード暗号化
        String password = passwordEncoder.encode(user.getPassword());

        //１件登録
        int rowNumber = jdbc.update("INSERT INTO m_user(user_id,"
                + " password,"
                + " user_name,"
                + " birthday,"
                + " age,"
                + " marriage,"
                + " role)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)",
                user.getUserId(),
                password,
                user.getUserName(),
                user.getBirthday(),
                user.getAge(),
                user.isMarriage(),
                user.getRole());

        return rowNumber;
  	}
  			
  	@Override
	public User selectOne(String userId)throws DataAccessException{
  		//Userテーブルのデータを1件取得
  		//1レコードの内容をMapとして取得する(queryForMap)
  		//? 動的に変化する値
  		Map<String,Object>map = jdbc.queryForMap("SELECT * FROM m_user"+" WHERE user_id = ?",userId);
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
	//Userテーブルの全データを取得
  	@Override
	public List<User>selectMany()throws DataAccessException{
  		//複数行のレコードを取得する(queryForList)
  		List<Map<String,Object>>getList = jdbc.queryForList("SELECT*FROM m_user");
  		//結果返却用の変数
  		List<User>userList = new ArrayList<>();
  		//取得したデータを結果返却用のListに格納
  		for(Map<String, Object>map:getList) {
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
	//Userテーブルを1件更新
  	@Override
	public int updateOne(User user)throws DataAccessException{
  		 //パスワード暗号化
        String password = passwordEncoder.encode(user.getPassword());
        //１件更新
        int rowNumber = jdbc.update("UPDATE M_USER"
                + " SET"
                + " password = ?,"
                + " user_name = ?,"
                + " birthday = ?,"
                + " age = ?,"
                + " marriage = ?"
                + " WHERE user_id = ?",
                password,
                user.getUserName(),
                user.getBirthday(),
                user.getAge(),
                user.isMarriage(),
                user.getUserId());
  	  		//トランザクション確認、例外
//  		if (rowNumber > 0){
//  			throw new DataAccessException("トランザクションテスト"){};
//  		}
  				return rowNumber;
  		
  	}
	//Userテーブルを1件削除
  	@Override
	public int deleteOne(String userId)throws DataAccessException{
  		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?",userId);
  		
  		return rowNumber;	
  	}
	//SQL取得結果をサーバーにCSV(カンマで区切られたデータ形式)で保存
  	@Override
	public void userCsvOut()throws DataAccessException{
  		String sql = "SELECT * FROM m_user";
  		//ResultSetExtractorの生成
  		UserRowCallbackHandler handler = new UserRowCallbackHandler();
  		//SQLの実行、CSV出力
  		jdbc.query(sql,handler);
  		
  	}
}
