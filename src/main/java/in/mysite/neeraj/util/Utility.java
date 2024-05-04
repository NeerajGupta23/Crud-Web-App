package in.mysite.neeraj.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Utility {

	private static HikariDataSource hikariDataSource = null;

	static {
		// prepering/loading the hikaricp connection pooling algorithm
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HikariConfig hikariConfig = new HikariConfig(
				"E:\\PW Skills\\02 Building MicroServices in Java for Cloud\\00 Assignment\\Projects\\CRUDWebApp\\src\\main\\java\\in\\mysite\\neeraj\\properties\\database.properties");

		hikariDataSource = new HikariDataSource(hikariConfig);
	}

	public static Connection getConnection() {
		// give the connection object from hikaricp connection pooling algorithm
		// If hikariDataSource reference contain null value than return null

		if (hikariDataSource != null) {
			try {
				return hikariDataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
