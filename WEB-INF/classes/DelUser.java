package tomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.Exception;
import java.lang.Integer;

/**
 * Created by elephant on 16/6/5.
 */
public class DelUser extends HttpServlet {

	/**
	 * 用于 处理 Get 请求
	 *
	 * @param req 用于获得浏览器信息
	 * @param res 用于向浏览器返回信息
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		try {
			// 调用UserBeanCL 完成删除用户
			Integer id = Integer.parseInt(req.getParameter("id"));
			UserBeanCL ubc = new UserBeanCL();
			if ( ubc.delUser(id)){
				res.sendRedirect("sucess");
			}
			else {
				res.sendRedirect("failed");
			}
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

