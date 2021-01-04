package model.queries;

import java .sql.*;
import model.User;

public class CRUDQueries {

	public static int insertUser(Statement stmt, User user) throws SQLException {
		String statement = String.format("INSERT INTO user (username, email, password, type, name, surname, sex, region, province, city, address, age) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %d)", 
		user.getUsername(), user.getEmail(), user.getPassword(), user.getType(), user.getName(), user.getSurname(), user.getSex(), user.getRegion(), user.getProvince(), user.getCity(), user.getAddress(), user.getAge());
		return stmt.executeUpdate(statement);
	}
}
