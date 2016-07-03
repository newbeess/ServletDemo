package tomcat;

import java.lang.Exception;
import java.sql.*;
import java.sql.Connection;

/**
 * 获取数据库链接
 */
public class ConnDB  {

	private Connection con = null;

	/**
	 * 获取Servlet数据库的链接
	 *
	 * @return
	 */
	public Connection getConn() {
		try {
			// 1. 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 2.得到链接
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Servlet", "root", "root");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}

