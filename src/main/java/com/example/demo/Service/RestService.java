package com.example.demo.Service;

import java.util.List;

import com.exmple.demo.models.User;

public interface RestService {
	//1件登録メソッド
	public boolean insert(User user);
	//1件検索用メソッド
	public User selectOne(String userId);
	//全件検索用メソッド
	public List<User> selectMany();
	//1件更新用メソッド
	public boolean update(User user);
	//1件削除用メソッド
	public boolean delete(String userId);
	
}
