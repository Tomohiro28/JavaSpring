package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //横断的(Aspect)
@Component //DIコンテナにBeanを定義
public class LogAspect {

//	@Before("execution(* *..*.*Controller.*(..))")
	//* 全ての戻り値を指定
	//*..* 全パッケージが対象
	//*Controller Controllerと付くクラス対象
//	public void startLog(JoinPoint jp) {
//		System.out.println("メソッド開始:"+jp.getSignature());
//	}
//	
//	@After("execution(* *..*.*Controller.*(..))")
//	public void endLog(JoinPoint jp) {
//		System.out.println("メソッド終了:"+jp.getSignature());
//	}
	@Around("execution(* *..*.*Controller.*(..))")
	public Object startLog(ProceedingJoinPoint jp)throws Throwable{
		System.out.println("メソッド開始:"+jp.getSignature());
		
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了:"+jp.getSignature());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("メソッド異常終了:"+jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
	@Around("execution(* *..*.*Controller.*(..))")
	public Object daoLog(ProceedingJoinPoint jp)throws Throwable{
		System.out.println("メソッド開始:"+jp.getSignature());
		
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了:"+jp.getSignature());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("メソッド異常終了:"+jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
}
