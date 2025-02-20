package com.easycompany.service;

public class Employee {

	private String employeeid;
	private String name;
	private int age;
	private String departmentid;
	private String password;
	private String email;
	private String superdeptid;
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSuperdeptid() {
		return superdeptid;
	}
	public void setSuperdeptid(String superdeptid) {
		this.superdeptid = superdeptid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Employee() {
	}

	public Employee(String employeeid, String name, int age, String departmentid, String password, String email) {
		this.employeeid = employeeid;
		this.name = name;
		this.age = age;
		this.departmentid = departmentid;
		this.password = password;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [employeeid=" + employeeid + ", name=" + name + ", age=" + age + ", departmentid="
			+ departmentid + ", password=" + password + ", email=" + email + ", superdeptid=" + superdeptid + "]";
	}
}
