package tomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.Exception;
import java.lang.Integer;
import java.util.ArrayList;

/**
 * Created by elephant on 16/6/5.
 */
public class SearchUserCL extends HttpServlet {

	/**
	 * 用于 处理 Get 请求
	 *
	 * @param req 用于获得浏览器信息
	 * @param res 用于向浏览器返回信息
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
			// 将 arraylist写入Session
		try {
			// 调用UserBeanCL 完成用户 查找
			String name = req.getParameter("username");
			String method = req.getParameter("search");

			UserBeanCL ubc = new UserBeanCL();
			ArrayList arrayList=ubc.searchUser(name, method);
			if (arrayList!=null){
				HttpSession session=req.getSession();
				session.setAttribute("arrayList",arrayList);
				session.setMaxInactiveInterval(10);// 设置存活时间为10秒
				res.sendRedirect("searchuser");
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

