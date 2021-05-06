package com.exmple.demo.models;

import lombok.Data;

@Data//getter,setterを自動生成
public class Employee {
  private int employeeId;//従業員ID
  private String employeeName;//従業員名
  private int age; //年齢
}
