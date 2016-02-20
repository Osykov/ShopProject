package osykov.ShopsProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
	
	private JdbcUtils() {}
	

	public static void rollbackQuietly(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException ex) {}
		}
		
	}

	public static void closeQuietly(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {}
		}
		
	}

	public static void closeQuietly(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {}
		}		
	}

	public static void closeQuietly(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {}
		}
	}

}
