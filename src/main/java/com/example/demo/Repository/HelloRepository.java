package com.example.demo.Repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
//DIに登録される
public class HelloRepository {

	@Autowired//インスタンスを生成
	private JdbcTemplate jdbcTemplate;//JDBC接続クラスを使ってSELECT文実行
	
	public Map<String,Object>findOne(int id){
		//SELECT文(JDBCを使用してデータベースからデータを検索)
		String query = "SELECT"
			+" employee_id,"
			+" employee_name,"
			+" age"
			+" FROM employee"
			+" WHERE employee_id=?";
		//検索実行
		Map<String,Object>employee =jdbcTemplate.queryForMap(query,id);
		  return employee;
		}
}
