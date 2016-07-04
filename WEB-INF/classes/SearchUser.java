package tomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.Exception;
import java.util.ArrayList;

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
			pw.println("<img src=imgs/logo1.gif>");
			pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=main>返回主界面</a>");
			pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=login>安全退出</a>");
			pw.println("<hr><h1><center>" + "用户查询" + "</center></h1>");
			pw.println("</head>");
			pw.println("<body bgcolor=#1A94E6><center>");

			pw.println("<form action=searchusercl method=get >");
			pw.println("<br>输入用户名 ：<input type=text name=username>");
			pw.println("<input type=submit value=查询><br>");
			pw.println("<br><input type=radio name=search value=mohu>模糊查询");
			pw.println("<input type=radio name=search value=jingque>精确查询</form><br></center>");
			HttpSession session=req.getSession();
			ArrayList arrayList=(ArrayList)session.getAttribute("arrayList");

			if (arrayList!=null){
				// 显示 表格
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
					pw.println("<td><a href=update?id="+ub.getId()+"&name="+ub.getUser()+"&password="+ub.getPassword()+"&mail="+ub.getMail()+"&grade="+ub.getGrade()+">修改用户</a></td>");
					pw.println("<td><a href=deluser?id="+ub.getId()+" onclick=\"return window.confirm('确认删除用户')\" >删除用户</a></td>");

					pw.println("</tr>");
				}
				pw.println("</table>");
				// 结束显示
			}

			pw.println("</center><br><hr><img src=imgs/logo1.gif>");
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

