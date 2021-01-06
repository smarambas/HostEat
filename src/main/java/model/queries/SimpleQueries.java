package model.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleQueries {

	private SimpleQueries() {}
	
	public static ResultSet selectUserByUsername(Statement stmt, String username) throws SQLException {
		String statement = "SELECT * FROM user WHERE username = '" + username + "';";
		return stmt.executeQuery(statement);
	}
	
}
