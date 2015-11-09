package com.saphanatutorial.util;

import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.saphanatutorial.config.Configurations;

public class HDBConnection {
	public static Connection connection = null;
	public static DataSource ds;
	public static InitialContext ctx;

	public static Connection getConnection() {
		try {
			ctx = new InitialContext();

			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
			// ds.setLoginTimeout(10);
			 if (connection == null || connection.isClosed()){
				 connection = ds.getConnection();
			 }
			// connection.setAutoCommit(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return connection;
	}
}
