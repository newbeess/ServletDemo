package tomcat;

import java.lang.Exception;
import java.sql.*;
import java.sql.Connection;

/**
 * 这是一个UserBean ， users表的映射
 * 他的一个对象 <------> users表的一条记录
 */
public class UserBean {
	private int id;
	private String user;
	private String password;
	private String mail;
	private int grade;
// id
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	// user
	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}
//	 password
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
// mail

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}
// grade

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}
}
