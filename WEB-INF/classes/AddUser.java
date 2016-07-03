package tomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.Exception;

/**
 * 用户修改界面
 */
public class AddUser extends HttpServlet {

	/**
	 * 用于 处理 Get 请求
	 *
	 * @param req 用于获得浏览器信息
	 * @param res 用于向浏览器返回信息
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		try {
			// 设置编码格式
			res.setContentType("text/html;charset=UTF-8");
			// 返回登陆界面
			PrintWriter pw = res.getWriter();
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<img src=imgs/logo1.gif>");
			pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=main>返回主界面</a>");
			pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=login>安全退出</a><hr>");
			pw.println("</head>");
			pw.println("<body bgcolor=#1A94E6><center>");


			pw.println("<h1>新增用户</h1>");

			pw.println("<form action=addusercl method=get>");
			pw.println("<table border=1>");
			pw.println("<tr><td>Id</td><td><input  type=text name=id ></td></tr>");
			pw.println("<tr><td>User</td><td><input  type=text  name=username ></td></tr>");
			pw.println("<tr><td>Password</td><td><input type=text name=password ></td></tr>");
			pw.println("<tr><td>Mail</td><td><input type=text name=mail ></td></tr>");
			pw.println("<tr><td>Grade</td><td><input type=text name=grade ></td></tr>");

			pw.println("</table>");
			pw.println("<br><input type=submit value=添加用户>");
			pw.println("</form><br><hr>");

			pw.println("</center><img src=imgs/logo1.gif>");
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

