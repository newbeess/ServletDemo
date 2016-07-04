package tomcat;

import java.lang.Exception;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * 用来处理 users 表（与UserBean类一一对应）
 * <p>
 * 主要的 业务逻辑
 */

public class UserBeanCL {
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private int pageCount = 0;// 共有几页，计算出来的

	/**
	 * 查找用户，精确查找 或者 模糊查找
	 *
	 * @param name
	 * @param method
	 * @return
	 */
	public ArrayList searchUser(String name, String method) {

		ArrayList<UserBean> arrayList = new ArrayList<UserBean>();
		try {

			ConnDB connDB = new ConnDB();
			con = connDB.getConn();

			if (method.equals("jingque")) {
				ps = con.prepareStatement("select  * from users where user='"+name+"'");

			} else if (method.equals("mohu")) {
				ps = con.prepareStatement("select  * from users where user like '%"+name+"%'");
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				// 将 rs中的每一条记录 封装到 userBean中
				UserBean ub = new UserBean();
				ub.setId(rs.getInt(1));
				ub.setUser(rs.getString(2));
				ub.setPassword(rs.getString(3));
				ub.setMail(rs.getString(4));
				ub.setGrade(rs.getInt(5));
				arrayList.add(ub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return arrayList;
	}

	/**
	 * 新增用户
	 *
	 * @param id
	 * @param name
	 * @param password
	 * @param mail
	 * @param grade
	 * @return
	 */
	public boolean addUser(int id, String name, String password, String mail, int grade, String picture) {

		boolean flag = false;

		try {
			ConnDB connDB = new ConnDB();
			con = connDB.getConn();
			String sql = "INSERT INTO users (id,user,password,mail,grade,picture) VALUES (" + id + ",'" + name + "','" + password + "','" + mail + "'," + grade + ",'" + picture + "')";
			ps = con.prepareStatement(sql);
			int number = ps.executeUpdate();
			if (number == 1)
				flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return flag;
	}

	/**
	 * 修改用户
	 *
	 * @param id
	 * @param name
	 * @param password
	 * @param mail
	 * @param grade
	 * @return
	 */
	public boolean updateUser(int id, String password, String mail, int grade) {

		boolean flag = false;

		try {
			ConnDB connDB = new ConnDB();
			con = connDB.getConn();
			String sql = "UPDATE users set password='" + password + "' , mail='" + mail + "', grade='" + grade + "' WHERE id = " + id;

			ps = con.prepareStatement(sql);
			int number = ps.executeUpdate();
			if (number == 1)
				flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return flag;
	}

	/**
	 * 根据用户名找到 用户 头像名称
	 *
	 * @param name
	 * @return
	 */
	public String getIdByUserName(String name) {
		String picture = "2.bmp";
		try {
			ConnDB connDB = new ConnDB();
			con = connDB.getConn();
			//  创建Statement
			ps = con.prepareStatement("select picture from users WHERE user='" + name + "' limit 0,1");
			rs = ps.executeQuery();
			if (rs.next()) {
				picture = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return picture;


	}

	public int getPageCount() {
		return this.pageCount;
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 * @return
	 */
	public boolean delUser(int id) {
		boolean flag = false;

		try {
			ConnDB connDB = new ConnDB();
			con = connDB.getConn();
			String sql = "DELETE  FROM  users WHERE id =" + id;

			ps = con.prepareStatement(sql);
			//ps.setInt(1, id);
			int number = ps.executeUpdate();
			if (number == 1)
				flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return flag;
	}

	/**
	 * 分页显示
	 *
	 * @param pageNow  当前第几页
	 * @param pageSize 一页显示几条
	 * @return arrayList
	 */

	public ArrayList getResultByPage(int pageNow, int pageSize) {

		ArrayList<UserBean> arrayList = new ArrayList<UserBean>();
		try {
			int rowCount = 0;// 共有多少条记录 ，查表

			ConnDB connDB = new ConnDB();
			con = connDB.getConn();
			//  创建Statement
			ps = con.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			if (rs.next()) {
				rowCount = rs.getInt(1);
			}
			// 计算pageCount
			if (rowCount / pageSize == 0) {
				pageCount = rowCount / pageSize;
			} else {
				pageCount = rowCount / pageSize + 1;
			}
//select id,name from test limit 参数1,参数2;
			//	参数1，从第几条开始
			//	参数2，返回多少条数据
			ps = con.prepareStatement("select  * from users limit " + (pageNow - 1) * pageSize + "," + pageSize);
			// 给?赋值
			//ps.setInt(1,pageSize);
			//ps.setInt(2,pageSize*pageNow-1);

			// 查询
			rs = ps.executeQuery();

			while (rs.next()) {
				// 将 rs中的每一条记录 封装到 userBean中
				UserBean ub = new UserBean();
				ub.setId(rs.getInt(1));
				ub.setUser(rs.getString(2));
				ub.setPassword(rs.getString(3));
				ub.setMail(rs.getString(4));
				ub.setGrade(rs.getInt(5));
				arrayList.add(ub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return arrayList;

	}

	/**
	 * 验证用户合法性
	 *
	 * @param name
	 * @param password
	 * @return boolean
	 */
	public boolean checkUser(String name, String password) {
		boolean flag = false;
		try {
			ConnDB connDB = new ConnDB();
			con = connDB.getConn();
			String sql = "SELECT  PASSWORD FROM  users WHERE USER = ? LIMIT 0, 1";

			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				String pwd = rs.getString(1);
				if (pwd.equals(password))
					flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return flag;
	}

	/**
	 * 关闭数据库连接
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}