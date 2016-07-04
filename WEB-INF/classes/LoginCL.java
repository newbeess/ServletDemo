package tomcat;

import javax.servlet.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.IOException;
import java.lang.Class;
import java.lang.ClassNotFoundException;
import java.lang.Exception;
import java.lang.Integer;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by elephant on 16/6/5.
 */
public class LoginCL extends HttpServlet {

	// 网站计数

	/**
	 * 重写 init() 函数
	 */

	public void init() {
		try {
			// 读取文件
			FileReader fr = new FileReader("/usr/local/apache-tomcat-8.0.33/webapps/chaofu/WEB-INF/count.txt");
			BufferedReader br = new BufferedReader(fr);
			String value = br.readLine();
			br.close();

			this.getServletContext().setAttribute("visitTimes", value);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于 处理 Get 请求
	 *
	 * @param req 用于获得浏览器信息
	 * @param res 用于向浏览器返回信息
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		try {

			res.setContentType("text/html;charset=UTF-8");
			// 接收 登陆界面表单提交的 用户名 和 密码
			String name = req.getParameter("username");
			String password = req.getParameter("password");
			// 接收 选择框的值
			String keep = req.getParameter("keep");

			// 调用UserBeanCL，验证用户合法性
			UserBeanCL ubc = new UserBeanCL();
			if (ubc.checkUser(name, password)) {
				// 用户合法
				// 用户名 密码写入Session
				HttpSession hs = req.getSession(true);
				hs.setMaxInactiveInterval(30);// 设置存活时间 30s
				hs.setAttribute("username", name);
				hs.setAttribute("password", password);

				// 判断是否需要创建 Cookie
				if (keep != null) {
					Cookie c1 = new Cookie("user", name);
					Cookie c2 = new Cookie("pwd", password);
					c1.setMaxAge(300);
					c2.setMaxAge(300); // 设置存活时间 30s
					res.addCookie(c1);
					res.addCookie(c2);
				}
//---------------------------- 网站计数---------------
				// 登陆成功，次数加一
				ServletContext sc = this.getServletContext();
				String value = sc.getAttribute("visitTimes").toString();
				Integer times=Integer.parseInt(value);
				times++;
				this.getServletContext().setAttribute("visitTimes", times.toString());



				res.sendRedirect("main");
			} else {
				// 说明用户名存在，密码错误
				res.sendRedirect("login");
			}
		} catch (IOException e)

		{
			e.printStackTrace();
		}

	}

	/**
	 * 用于 处理 Get 请求
	 *
	 * @param req 用于获得浏览器信息
	 * @param res 用于向浏览器返回信息
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		try {
			this.doGet(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写 destory函数
	 */
	public void destroy() {
		try {
			// 将新的次数写回文件
			FileWriter fw = new FileWriter("/usr/local/apache-tomcat-8.0.33/webapps/chaofu/WEB-INF/count.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			ServletContext sc = this.getServletContext();
			String value = sc.getAttribute("visitTimes").toString();
			bw.write(value);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

