package model.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NestedQueries {
	
	private static String baseSelect = "SELECT * FROM event WHERE owner = any";

	private NestedQueries() {}
	
	public static ResultSet selectEventByRegion(Statement stmt, String region) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s);", baseSelect, region);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionDate(Statement stmt, String region, String date) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s) AND date = %s;", baseSelect, region, date);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionTime(Statement stmt, String region, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s) AND time = %s;", baseSelect, region, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionDateTime(Statement stmt, String region, String date, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s) AND date = %s AND time = %s;", baseSelect, region, date, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvince(Statement stmt, String region, String province) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s);", baseSelect, region, province);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvinceDate(Statement stmt, String region, String province, String date) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s) AND date = %s;", baseSelect, region, province, date);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvinceTime(Statement stmt, String region, String province, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s) AND time = %s;", baseSelect, region, province, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvinceDateTime(Statement stmt, String region, String province, String date, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s) AND date = %s AND time = %s;", baseSelect, region, province, date, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvinceCity(Statement stmt, String region, String province, String city) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s AND city = %s);", baseSelect, region, province, city);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvinceCityDate(Statement stmt, String region, String province, String city, String date) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s AND city = %s) AND date = %s;", baseSelect, region, province, city, date);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvinceCityTime(Statement stmt, String region, String province, String city, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s AND city = %s) AND time = %s;", baseSelect, region, province, city, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByRegionProvinceCityDateTime(Statement stmt, String region, String province, String city, String date, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE region = %s AND province = %s AND city = %s) AND date = %s AND time = %s;", baseSelect, region, province, city, date, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByProvince(Statement stmt, String province) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s);", baseSelect, province);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByProvinceDate(Statement stmt, String province, String date) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s) AND date = %s;", baseSelect, province, date);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByProvinceTime(Statement stmt, String province, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s) AND time = %s;", baseSelect, province, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByProvinceDateTime(Statement stmt, String province, String date, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s) AND date = %s AND time = %s;", baseSelect, province, date, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByProvinceCityDate(Statement stmt, String province, String city, String date) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s AND city = %s) AND date = %s;", baseSelect, province, city, date);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByProvinceCityTime(Statement stmt, String province, String city, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s AND city = %s) AND time = %s;", baseSelect, province, city, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByProvinceCityDateTime(Statement stmt, String province, String city, String date, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE province = %s AND city = %s) AND date = %s AND time = %s;", baseSelect, province, city, date, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByCity(Statement stmt, String city) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE city = %s);", baseSelect, city);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByCityDate(Statement stmt, String city, String date) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE city = %s) AND date = %s;", baseSelect, city, date);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByCityTime(Statement stmt, String city, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE city = %s) AND time = %s;", baseSelect, city, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByCityDateTime(Statement stmt, String city, String date, String time) throws SQLException {
		String statement = String.format("%s (SELECT username FROM user WHERE city = %s) AND date = %s AND time = %s;", baseSelect, city, date, time);
		return stmt.executeQuery(statement);
	}
		
}
