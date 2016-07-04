package tomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.Exception;

/**
 * Created by elephant on 16/6/5.
 */
public class SearchUser extends HttpServlet {

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
			pw.println("<img src=imgs/logo1.gif><hr>");
			pw.println("<h1><center>" + "用户查询" + "</center></h1>");
			pw.println("</head>");
			pw.println("<body bgcolor=#1A94E6><center>");

			pw.println("<form action=searchusercl method=get >");
			pw.println("<br>输入用户名 ：<input type=text name=username>");
			pw.println("<input type=submit value=查询><br>");
			pw.println("<br><input type=radio name=search value=mohu>模糊查询");
			pw.println("<input type=radio name=search value=jingque>精确查询</form><br></center>");

			pw.println("<br><hr><img src=imgs/logo1.gif>");
			pw.println("</body>");
			pw.println("</html>");
		} catch (Exception e ){
			e.printStackTrace();
		}


	}

	/**
	 * 用于 处理 Get 请求
	 *
	 * @param req 用于获得浏览器信息
	 * @param res 用于向浏览器返回信息
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)  {
		try {
			this.doGet(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

