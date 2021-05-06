package com.example.demo.Repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.exmple.demo.models.User;
//JdbcTemplate,NamedParameterJdbcTemplate切り替えられるように
//Dao データベースなどのデータストアを操作
public interface UserDao {
    //Userテーブルの件数を取得
	public int count()throws DataAccessException;
	//Userテーブルにデータを1件挿入
	public int insertOne(User user)throws DataAccessException;
	//Userテーブルのデータを1件取得
	public User selectOne(String userId)throws DataAccessException;
	//Userテーブルの全データを取得
	public List<User>selectMany()throws DataAccessException;
	//Userテーブルを1件更新
	public int updateOne(User user)throws DataAccessException;
	//Userテーブルを1件削除
	public int deleteOne(String userId)throws DataAccessException;
	//SQL取得結果をサーバーにCSV(カンマで区切られたデータ形式)で保存
	public void userCsvOut()throws DataAccessException;
}
