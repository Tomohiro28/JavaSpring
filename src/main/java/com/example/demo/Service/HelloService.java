package com.example.demo.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.HelloRepository;
import com.exmple.demo.models.Employee;

@Service
public class HelloService {
  @Autowired
  private HelloRepository helloRepository;
  
  public Employee findOne(int id) {
	  Map<String,Object>map = helloRepository.findOne(id);
	  
	  int employeeId = (Integer)map.get("employee_id");
	  String employeeName = (String)map.get("employee_name");
	  int age = (Integer)map.get("age");
	  
	  Employee employee = new Employee();
	  employee.setEmployeeId(employeeId);
	  employee.setEmployeeName(employeeName);
	  employee.setAge(age);
	  
	  return employee;	  
  }
}
