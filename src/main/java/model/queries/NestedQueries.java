package model.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NestedQueries {
	
	private static String baseSelect = "SELECT * FROM event WHERE owner = any";

	private NestedQueries() {}
	
	public static ResultSet selectEventsByRegion(Statement stmt, String region, String dateTime) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s) AND date > %s;", baseSelect, region, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByRegionProvince(Statement stmt, String region, String province, String dateTime) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s) AND date > %s;", baseSelect, region, province, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByRegionProvinceCity(Statement stmt, String region, String province, String city, String dateTime) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s AND city = %s) AND date > %s;", baseSelect, region, province, city, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByRegionCity(Statement stmt, String region, String city, String dateTime) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND city = %s) AND date > %s;", baseSelect, region, city, dateTime);
		return stmt.executeQuery(statement);
	}	

	public static ResultSet selectEventsByProvince(Statement stmt, String province, String dateTime) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s) AND date > %s;", baseSelect, province, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByProvinceCity(Statement stmt, String province, String city, String dateTime) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s AND city = %s) AND date > %s;", baseSelect, province, city, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByCity(Statement stmt, String city, String dateTime) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE city = %s) AND date > %s;", baseSelect, city, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByDate(Statement stmt, String dateTime) throws SQLException {
		String statement = String.format("SELECT * FROM event WHERE date > %s;", dateTime);
		return stmt.executeQuery(statement);
	}
		
}
