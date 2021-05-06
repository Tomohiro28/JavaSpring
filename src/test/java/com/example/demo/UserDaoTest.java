package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.UserDao;
//JUnit Javaプログラムの単体テストを行うためのツール
//テスト用のアノテーション
@RunWith(SpringRunner.class)//テストをどのクラスで実行するか指定(spring用のJUnitを使えるクラス)
@SpringBootTest//springbootを起動してからテスト開始
@Transactional//トランザクションの開始、コミット、ロールバックは自動
public class UserDaoTest {
	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;
	//カウントメソッドのテスト1
	@Test
	public void countTest1() {
		//カウントメソッドの結果が2件であることをテスト
		assertEquals(dao.count(),2);	
	}
	@Test
	@Sql("/testdata.sql")//SQLを実行した後の状態でテスト
	public void countTest2() {
		//カウントメソッドの結果が3件であることをテスト
		assertEquals(dao.count(),3);
	}

}
