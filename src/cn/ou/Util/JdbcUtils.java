package cn.ou.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库工具
 * @author Administrator
 *
 */
public class JdbcUtils {
	private JdbcUtils(){}
	
	/**
	 * 关闭资源
	 * @param conn		数据库连接
	 * @param statement	传输器
	 * @param rs		结果集
	 */
	public static void close(Connection conn,Statement statement,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
}
