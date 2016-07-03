package tomcat;

import javax.servlet.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by elephant on 16/6/5.
 */
public class Welcome extends HttpServlet {

	/**
	 * 用于 处理 Get 请求
	 *
	 * @param req 用于获得浏览器信息
	 * @param res 用于向浏览器返回信息
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		// 检查是否有Session活着Cookie
		try {
			String name = null;
			String password = null;
			// 读取Cookies，取出客户端的用户名和密码
			HttpSession hs = req.getSession(true);
			name = (String) hs.getAttribute("username");
			if (name==null){
				Cookie[] cookie = req.getCookies();
				if (cookie != null) {
					for (int i = 0; i < cookie.length; i++) {
						Cookie temp = cookie[i];
						if (temp.getName().equals("name")) {
							name = temp.getValue();
						}
						if (temp.getName().equals("pwd")) {
							password = temp.getValue();
						}
					}
					if (!name.equals("") && !password.equals("")) {
						res.sendRedirect("logincl?username=" + name + "&password=" + password);
						return;
					}
				}
			}


			res.setContentType("text/html;charset=UTF-8");

			PrintWriter pw = res.getWriter();
//----------------网站计数器----------------------

			ServletContext sc = this.getServletContext();
			String number = sc.getAttribute("visitTimes").toString();



//-----------------调用UserBeanCL实现分页功能------------------
			int pageNow=1;
			int pageSize=3;
			if (req.getParameter("pageNoww")!=null){
				pageNow=Integer.parseInt(req.getParameter("pageNoww"));
			}
			UserBeanCL ubc=new UserBeanCL();
			ArrayList arrayList=ubc.getResultByPage(pageNow,pageSize);
			String picture=ubc.getIdByUserName(name);


			pw.println("<html>");
			pw.println("<head>");
			pw.println("</head>");
			pw.println("<body bgcolor=#1A94E6>");
			pw.println("<img src=imgs/logo1.gif>");
			pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=main>返回主界面</a>");
			pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=login>安全退出</a>");

			pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎 " + name + "<img src=imgs/"+picture+"><br>");

			pw.println("<hr><h1 ><center>管理用户</center></h1>");


			// 显示表格
			String[] colors={"pink","white"};
			pw.println("<center><table border=1>");
			pw.println("<tr><th>id</th><th>user</th><th>password</th><th>mail</th><th>grade</th><th>修改用户</th><th>删除用户</th></tr>");
			for (int i=0;i<arrayList.size();i++) {
				UserBean ub=(UserBean)arrayList.get(i);
				pw.println("<tr bgcolor="+colors[i%2]+">");
				pw.println("<td>" + ub.getId() + "</td>");
				pw.println("<td>" +ub.getUser() + "</td>");
				pw.println("<td>" +ub.getPassword() + "</td>");
				pw.println("<td>" + ub.getMail() + "</td>");
				pw.println("<td>" +ub.getGrade() + "</td>");
				pw.println("<td><a href=modifyuser?id="+ub.getId()+">修改用户</a></td>");
				pw.println("<td><a href=deluser?id="+ub.getId()+" onclick=\"return window.confirm('确认删除用户')\" >删除用户</a></td>");

				pw.println("</tr>");
			}
			pw.println("</table>");
			// 结束表格显示
			int pageCount=ubc.getPageCount();
			// 上一页，下一页
			pw.println("<br>");
			if (pageNow != 1)
				pw.println("<a href=welcome?pageNoww=" + (pageNow - 1) + ">上一页</a>");
			for (int i = 1; i <= pageCount; i++)
				pw.println("<a href=welcome?pageNoww=" + i + ">" + i + "</a>");
			if (pageNow != pageCount)
				pw.println("<a href=welcome?pageNoww=" + (pageNow + 1) + ">下一页</a>");
			// 跳转到某一页
			pw.println("<form action=welcome method=get>");
			pw.println("<br><br>跳转到：<input type=text name=pageNoww>");
			pw.println("<input type=submit value=Go></form></center><br>");

			pw.println("<br><hr><img src=imgs/logo1.gif>");
			pw.println("<br><center>您的IP为："+req.getRemoteAddr());
			pw.println("<br>您的主机名为："+req.getRemoteHost());
			pw.println("<br>该网页被访问了"+number+"次</center>");;
			pw.println("</body>");
			pw.println("</html>");

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
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		try {
			this.doGet(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

