package com.zhucq.mobile.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class DBUtils {
	/**
	 * url格式：jdbc:mysql://localhost:3306/test_auto?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public static Connection getMySqlConnection(String url, String user, String password) {
		DbUtils.loadDriver("com.sql.jdbc.Driver");
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 第一种url格式：jdbc:oracle:thin:@host:port:SID
	 * 第二种url格式：jdbc:oracle:thin:@//host:port/service_name
	 * 第三种url格式：jdbc:oracle:thin:@TNSName,需要配置oracle.net.tns_admin属性，System.setProperty
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public static Connection getOracleConnection(String url, String user, String password) {
		DbUtils.loadDriver("oracle.jdbc.OracleDriver");
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 执行select语句，返回MapList格式
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> queryAsMapList(Connection conn, String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner();
			return qr.query(conn, sql, new MapListHandler(), params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	/**
	 * 执行insert，update，delete语句，返回被修改的记录数。
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(Connection conn, String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner();
			return qr.update(conn, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	/**
	 * 执行select语句，返回Map格式,对查询只有一条记录的结果有效。
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Map<String, Object> queryAsMap(Connection conn, String sql, Object... params) {
		List<Map<String, Object>> results = queryAsMapList(conn, sql, params);
		if (results.isEmpty())
			return null;
		return results.get(0);
	}
}
